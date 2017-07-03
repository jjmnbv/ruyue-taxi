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
 * 监听rabbit队列数据
 * Created by shikang on 2017/5/24.
 */
public class OBDRabbitHandler implements ChannelAwareMessageListener {

    @Resource(name = "MileageApiService")
    private MileageApiService mileageApiService;

    @Override
    public void onMessage(Message message, Channel channel) {
        GPSRabbitTask gpsRabbitTask = new GPSRabbitTask(mileageApiService, message, channel, GpsSourceEnum.OBD_GPS);
        CarServiceThreadPool.getInstance().execute(gpsRabbitTask);
    }
}
