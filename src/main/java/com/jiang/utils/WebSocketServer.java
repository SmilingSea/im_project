package com.jiang.utils;

/**
 * @author SmilingSea
 * 2024/3/20
 */



import com.alibaba.druid.sql.visitor.functions.Now;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.dao.MessageDO;
import com.jiang.dao.MessageType;

import com.jiang.dto.MessageDTO;
import com.jiang.mq.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArraySet;

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
     * 连接服务器的用户的id
     */
    private Long userId;

    /**
     * 消息内容
     */
    private String content;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static Sender sender;


    @Resource
    public void setSender(Sender sender){
        WebSocketServer.sender = sender;
    }






    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("connectId") String connectId) {
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

    /**
     * 收到客户端消息后调用的方法
     *
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        long groupId = Long.parseLong(connectId);
        log.info("收到来自窗口" + connectId + "的信息:" + message);

        // 发送到指定群
        sendInfo(message, connectId);

        //将接收到的json转化为对象
        ObjectMapper mapper = new ObjectMapper();
        try{
            MessageDTO messageDTO = mapper.readValue(message, MessageDTO.class);
            // 拿到发送者的id和消息内容
            Long senderId = messageDTO.getUserId();
            String content = messageDTO.getContent();
            MessageType type = messageDTO.getType();
            // 创建MessageDO对象
            LocalDateTime now = LocalDateTime.now();
            MessageDO messageDO = new MessageDO(null,groupId,senderId,content,type,now,now);

            // 发送message对象到消息队列中
            sender.send(messageDO);
            log.info("消息已发送");

        }catch (IOException e){
            e.printStackTrace();
        }
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
