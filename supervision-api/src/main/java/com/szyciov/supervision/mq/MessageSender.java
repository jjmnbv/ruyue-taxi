package com.szyciov.supervision.mq;

import com.supervision.dto.SupervisionDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 消息发送者,用于发送命令对象
 * Created by 林志伟 on 2017/7/12.
 */
@Component
public class MessageSender {
    private @Autowired
    AmqpTemplate amqpTemplate;
    private @Value("${spring.rabbitmq.queue-default-name:hello}") String queueName;

    public void send(SupervisionDto supervisionDto){
        amqpTemplate.convertAndSend(queueName,supervisionDto);

    }
}
