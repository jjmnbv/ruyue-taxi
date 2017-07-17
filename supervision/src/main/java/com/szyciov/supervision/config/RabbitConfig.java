package com.szyciov.supervision.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 林志伟 on 2017/7/12.
 */
@Configuration
public class RabbitConfig {

    private @Value("${spring.rabbitmq.queue-default-name:hello}") String queueName;
    @Bean
    public Queue Queue() {
        return new Queue(queueName);
    }

}
