package com.jiang.mq;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.domain.dao.MessageDO;
import com.jiang.service.MessageService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.time.Duration;
import java.time.ZoneOffset;

import static com.jiang.config.RabbitConfig.QUEUE_NAME;

@Component
public class SpringRabbitListener {

    private static final String MESSAGE_PREFIX = "message:";
    private static final String CONVERSATION_PREFIX = "conversation:";


    @Resource
    private MessageService messageService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @RabbitListener(queuesToDeclare = @Queue(QUEUE_NAME))
    public void listenSimpleQueueMessage(String msg) throws InterruptedException, JsonProcessingException {
        // 接收json实体类字符串，转化成实体
        MessageDO messageDO = JSONObject.parseObject(msg, MessageDO.class);
        System.out.println(messageDO);

        messageService.save(messageDO);
        saveMessage(messageDO);
    }

    public void saveMessage(MessageDO message) throws JsonProcessingException {
        // 构建 Redis 中存储消息的 key
        String key = MESSAGE_PREFIX + message.getId();
        // 将 MessageDO 对象转换成 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(message);
        // 将消息存储到 Redis 中，并设置过期时间为7天
        redisTemplate.opsForValue().set(key, value, Duration.ofDays(7));

        // 将消息添加到会话对应的有序集合中
        String conversationKey = CONVERSATION_PREFIX + message.getConversationId();
        redisTemplate.opsForZSet().add(conversationKey, key, message.getSentAt().toEpochSecond(ZoneOffset.UTC));
    }

}