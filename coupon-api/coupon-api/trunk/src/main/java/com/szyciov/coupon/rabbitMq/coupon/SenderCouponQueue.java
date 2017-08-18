package com.szyciov.coupon.rabbitMq.coupon;

import com.szyciov.coupon.rabbitMq.RabbitConfig;
import com.szyciov.util.GUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 抵用券生成队列
 * @author LC
 * @date 2017/8/10
 */
@Component
public class SenderCouponQueue implements RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(SenderCouponQueue.class);


    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入
     */
    @Autowired
    public SenderCouponQueue(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);

    }

    public void pushGenerateMsg(String content) {
        CorrelationData correlationId = new CorrelationData(GUIDGenerator.newGUID());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,RabbitConfig.ROUTINGKEY, content, correlationId);
        logger.info("-------------放入队列完毕-------------");
    }


    public void pushAutoMsg(String content) {
        CorrelationData correlationId = new CorrelationData(GUIDGenerator.newGUID());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,RabbitConfig.ROUTINGKEY_AUTO, content, correlationId);
        logger.info("-------------放入队列完毕-------------");
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("-------------回调消息:"+correlationData);
        if (ack) {
            logger.info("-------------消息成功消费:"+correlationData);
        } else {
            logger.info("-------------消息消费失败:"+correlationData);
        }
    }
}
 