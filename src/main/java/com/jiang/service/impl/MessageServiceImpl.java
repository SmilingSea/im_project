package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiang.common.ResultWithData;
import com.jiang.dao.MessageDO;
import com.jiang.mapper.MessageMapper;
import com.jiang.service.MessageService;
import com.jiang.utils.COSUtils;
import com.jiang.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageDO> implements MessageService {

    private static final String MESSAGE_PREFIX = "message:";
    private static final String CONVERSATION_PREFIX = "conversation:";


    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ResultWithData<String> sendPicture(String token, MultipartFile multipartFile) throws IOException {
        // 获取用户id
        Long usrId = JWTUtils.getIdByToken(token);
        // 将MultipartFile 转转化为file
        File file = File.createTempFile(String.valueOf(IdWorker.getId()), ".png");
        multipartFile.transferTo(file);

        // 通过腾讯云对象存储上传
        String pictureUrl = COSUtils.UploadPicture(file);
        log.info(pictureUrl);

        // TODO：大文件分片上传

        return ResultWithData.success("上传成功",pictureUrl);
    }

    @Override
    public ResultWithData history(String token, Long conversationId) throws JsonProcessingException {
        List<String> messages = getMessagesByConversationId(conversationId);

        return ResultWithData.success(messages,"查找成功");
    }

    public List<String> getMessagesByConversationId(Long conversationId) throws JsonProcessingException {
        // 构建会话对应的 Redis key
        String conversationKey = CONVERSATION_PREFIX + conversationId;
        // 从 Redis 中获取会话对应的消息 key 集合
        Set<String> messageKeys = redisTemplate.opsForZSet().range(conversationKey, 0, -1);
        List<MessageDO> messages = new ArrayList<>();
        List<String> jsons = new ArrayList<>();
        if (messageKeys != null) {
            // 遍历消息 key 集合，从 Redis 中获取消息 JSON 字符串，并转换成 MessageDO 对象
            for (String messageKey : messageKeys) {
                String messageJson = redisTemplate.opsForValue().get(messageKey);
                jsons.add(messageJson);
            }
        }
        return jsons;
    }



}
