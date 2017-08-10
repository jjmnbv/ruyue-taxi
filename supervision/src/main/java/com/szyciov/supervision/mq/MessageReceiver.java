package com.szyciov.supervision.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by 林志伟 on 2017/7/12.
 */
@Component
@RabbitListener(queues = "supervision_api_object_queue")
public class MessageReceiver {


}
