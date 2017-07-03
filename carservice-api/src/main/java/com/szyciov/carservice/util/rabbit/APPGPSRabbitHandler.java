package com.szyciov.carservice.util.rabbit;

import com.rabbitmq.client.Channel;
import com.szyciov.carservice.service.MileageApiService;
import com.szyciov.carservice.util.rabbit.task.GPSRabbitTask;
import com.szyciov.enums.GpsSourceEnum;
import com.szyciov.util.threadpool.CarServiceThreadPool;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import javax.annotation.Resource;

/**
 * Created by shikang on 2017/6/27.
 */
public class APPGPSRabbitHandler implements ChannelAwareMessageListener {

    @Resource(name = "MileageApiService")
    private MileageApiService mileageApiService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        GPSRabbitTask gpsRabbitTask = new GPSRabbitTask(mileageApiService, message, channel, GpsSourceEnum.APP_GPS);
        CarServiceThreadPool.getInstance().execute(gpsRabbitTask);
    }
}
