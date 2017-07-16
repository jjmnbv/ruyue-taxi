package com.szyciov.supervision.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者
 * Created by 林志伟 on 2017/7/12.
 */
@Component
public class MessageSender {
    private @Autowired
    AmqpTemplate amqpTemplate;
    private @Value("${spring.rabbitmq.queue-default-name:hello}") String queueName;

    public void send(Object object){
        amqpTemplate.convertAndSend(queueName,object);

    }
}
