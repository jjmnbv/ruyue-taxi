package com.szyciov.carservice.service;

import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.CommonRabbitData;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.enums.CommonRabbitEnum;
import com.szyciov.util.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * rabbitMQ消息发送
 * Created by shikang on 2017/6/10.
 */
@Service("RabbitService")
public class RabbitService {

    private static final Logger LOGGER = Logger.getLogger(RabbitService.class);

    private static final String APP_EXCHANGE = SystemConfig.getSystemProperty("rabbit.appExchange");

    private static final String APP_PATTERN = SystemConfig.getSystemProperty("rabbit.appPattern");

    private static final String COMMON_EXCHANGE = SystemConfig.getSystemProperty("rabbit.commonExchange");

    private static final String COMMON_DLX_PATTERN = SystemConfig.getSystemProperty("rabbit.commonDlxPattern");

    private static final String COUPON_PATTERN = SystemConfig.getSystemProperty("rabbit.couponPattern");



    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;

    /**
     * 推送数据到APPGPS队列
     * @param object
     */
    public void sendAppgpsRabbit(PubOrdergpsdata object) {
        amqpTemplate.convertAndSend(APP_EXCHANGE, APP_PATTERN, object);
    }


    /**
     * 推送数据到APPGPS队列
     * @param jsonStr
     */
    public void sendCouponRabbit(PubCouponActivityDto jsonStr) {
        amqpTemplate.convertAndSend(COMMON_EXCHANGE, COUPON_PATTERN, jsonStr);
    }

    /**
     * 推送数据到公共队列
     * @param commonRabbit
     * @param obj
     */
    public void sendCommonMsg(CommonRabbitData commonRabbitData) {
        if(CommonRabbitEnum.SEND_RULE_PUSH.code.equals(commonRabbitData.getType())) { //派单消息推送
            sendCommonRabbit(commonRabbitData, commonRabbitData.getExpiration());
        }
    }

    /**
     * 发送消息到队列
     * @param obj
     * @param expiration
     */
    private void sendCommonRabbit(Object obj, int expiration) {
        amqpTemplate.convertAndSend(COMMON_EXCHANGE, COMMON_DLX_PATTERN, obj, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(expiration * 1000)); //设置单个消息在队列中保留时间
                return message;
            }
        });
    }

}
