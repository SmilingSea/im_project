package com.jiang.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;



/**
 * @author SmilingSea
 * 2024/4/11
 */
public class RabbitConfig {
    public final static String QUEUE_NAME = "improject";

    @Bean
    public Queue makeQueue(){
        return new Queue(QUEUE_NAME);
    }
}
