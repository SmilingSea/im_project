package com.jiang.mq;

import com.alibaba.fastjson.JSONObject;
import com.jiang.dao.MessageDO;
import com.jiang.service.MessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.jiang.config.RabbitConfig.QUEUE_NAME;

@Component
public class SpringRabbitListener {

    @Resource
    private MessageService messageService;

    @RabbitListener(queues = QUEUE_NAME)
    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
        // System.out.println("spring 消费者接收到消息：【" + msg + "】");

        // 接收json实体类字符串，转化成实体
        MessageDO messageDO = JSONObject.parseObject(msg, MessageDO.class);
        System.out.println(messageDO);

        messageService.save(messageDO);

    }
}