package com.jiang.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * Rabbitmq配置类
 * @author SmilingSea
 * 2024/4/11
 */
@Slf4j
@Configuration
public class RabbitConfig {

    public final static String QUEUE_NAME = "improject";

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue makeQueue(){
        return new Queue(QUEUE_NAME);
    }


}
