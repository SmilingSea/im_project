package com.jiang.utils;

/**
 * @author SmilingSea
 * 2024/3/20
 */


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.dao.BanDO;
import com.jiang.dao.ConversationUser;
import com.jiang.dao.MessageDO;
import com.jiang.dao.MessageType;

import com.jiang.dto.MessageDTO;
import com.jiang.filter.TextFilter;
import com.jiang.mq.Sender;
import com.jiang.service.BanService;
import com.jiang.service.ConversationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;


/**
 * @ServerEndpoint 这个注解有什么作用？
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 */


@Component
@Slf4j
@Service
@ServerEndpoint("/{connectId}")
public class WebSocketServer {


    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 会话id
     */
    private String connectId = "";

    /**
     * 消息内容
     */
    private String content;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static Sender sender;


    @Resource
    public void setSender(Sender sender) {
        WebSocketServer.sender = sender;
    }

    private static Base64Util base64Util;

    @Resource
    public void setBase64Util(Base64Util base64Util) {
        WebSocketServer.base64Util = base64Util;
    }

    private static ConversationUserService conversationUserService;

    @Resource
    public void setConversationUserService(ConversationUserService conversationUserService){
        WebSocketServer.conversationUserService = conversationUserService;
    }
    
    private static BanService banService;
    
    @Resource
    public void setBanService(BanService banService){
        WebSocketServer.banService  = banService;
    }
    


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("connectId") String connectId) {
        InetSocketAddress remoteAddress = WebsocketUtils.getRemoteAddress(session);
        log.info("远程ip地址："+remoteAddress);
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        this.connectId = connectId;
        //在线数加1
        addOnlineCount();
        try {
            sendMessage("conn_success");
            log.info("有新窗口开始监听:" + connectId + ",当前在线人数为:" + getOnlineCount());
        } catch (IOException e) {
            log.error("websocket IO Exception");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        //断开连接情况下，更新主板占用情况为释放
        log.info("释放的sid为：" + connectId);
        //这里写释放的时候，要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }


    // TODO:4.存消息用groupid存，查消息列表的时候顺便查被屏蔽的人，如果被屏蔽则不显示


    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("发送消息");
        log.info("收到来自窗口" + connectId + "的信息:" + message);

        // 解析收到的json
        MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);
        Long conversationId = messageDTO.getConversationId();
        String content = messageDTO.getContent();


        // connectid就是用户id
        Long userId = Long.parseLong(connectId);

        // 判断消息类型（文字，图片）
        MessageType type = MessageType.TEXT;
        String regex = "^https://improject-1322480945\\.cos\\.ap-nanjing\\.myqcloud\\.com/.*$";
        boolean ifPicture = content.matches(regex);
        if (ifPicture){
            type = MessageType.PICTURE;
        }

        // 创建MessageDO对象
        LocalDateTime now = LocalDateTime.now();
        MessageDO messageDO = new MessageDO(null, conversationId, userId, content, type, now, now);

        // 查表发现是发给谁
        LambdaQueryWrapper<ConversationUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConversationUser::getConversationId,conversationId);
        List<ConversationUser> list = conversationUserService.list(queryWrapper);
        
        // 查看是否被人屏蔽
        LambdaQueryWrapper<BanDO> banQuery = new LambdaQueryWrapper<>();
        banQuery.eq(BanDO::getBannerId,userId);
        List<BanDO> beBanList = banService.list();

        // 检测敏感词并替换
        if(type.equals(MessageType.TEXT)){
            content = TextFilter.blockSensitiveWords(content);
        }

        // 发送消息，但是如果被屏蔽，则不接受消息
        if (!(beBanList == null)){
            List<Long> bannerIds = new ArrayList<>();
            List<Long> receiverIds = new ArrayList<>();
            for (BanDO banDO : beBanList) {
                bannerIds.add(banDO.getUserId());
            }
            for (ConversationUser conversationUser : list) {
                receiverIds.add(conversationUser.getUserId());
            }

            List<Long> resultList = receiverIds.stream()
                    .filter(item -> !bannerIds.contains(item))
                    .collect(Collectors.toList());
            for (Long receiver : resultList) {
                sendInfo(content, receiver.toString());
            }

        }else{
            for (ConversationUser Users : list) {
                // 发送到指定会话
                sendInfo(content, Users.getUserId().toString());
            }
        }





        // 发送message对象到消息队列中
        sender.send(messageDO);
        log.info("消息已发送");
    }



    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息到窗口" + sid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个connectId的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.connectId.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

}
