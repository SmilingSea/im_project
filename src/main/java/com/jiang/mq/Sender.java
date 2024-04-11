package com.jiang.mq;

import com.alibaba.fastjson.JSON;
import com.jiang.config.RabbitConfig;
import com.jiang.dao.MessageDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author SmilingSea
 * 2024/4/11
 */
@Component
@Slf4j
public class Sender {

    // 通过AmqpTemplate传次奥西
    @Resource
    private AmqpTemplate rabbitTemplate;

    public Sender() {
    }

    /**
     * 传输MessageDO对象到rabbitmq
     */
    public void send(MessageDO messageDO){
        String messageString = JSON.toJSONString(messageDO);
        log.info("SenderId:"+messageDO.getSenderId());
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME,messageString);

    }
}
