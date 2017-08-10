package com.szyciov.supervision.api.receiver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.api.BaseApi;
import com.szyciov.supervision.api.service.BaseService;
import com.szyciov.supervision.mq.MessageReceiver;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * 队列接收者
 * Created by 林志伟 on 2017/7/12.
 */
@Component
@RabbitListener(queues = "supervision_api_object_queue")
public class BaseReceiver extends MessageReceiver
{
    private static Logger logger = LoggerFactory.getLogger(BaseReceiver.class);

    private @Autowired
    BaseService basicService;


    @RabbitHandler
    public void consumer(List<BaseApi> list) throws Exception
    {
        logger.info("API_INFO:接收队列数据:{}", JSONUtil.toJackson(list));
        basicService.sendApi(list);

    }
}
