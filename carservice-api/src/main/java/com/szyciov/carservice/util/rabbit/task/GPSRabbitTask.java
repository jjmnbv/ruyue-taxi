package com.szyciov.carservice.util.rabbit.task;

import com.rabbitmq.client.Channel;
import com.szyciov.carservice.service.MileageApiService;
import com.szyciov.entity.ObdGps;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.enums.GpsSourceEnum;
import com.szyciov.util.StringUtil;
import com.szyciov.util.threadpool.AsyncTask;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * Created by shikang on 2017/6/27.
 */
public class GPSRabbitTask implements AsyncTask {

    private static final Logger LOGGER = Logger.getLogger(GPSRabbitTask.class);

    private MileageApiService mileageApiService;

    private Channel channel;

    private Message message;

    private GpsSourceEnum gpsSource;

    public GPSRabbitTask(MileageApiService mileageApiService, Message message, Channel channel, GpsSourceEnum gpsSource) {
        this.mileageApiService = mileageApiService;
        this.message = message;
        this.channel = channel;
        this.gpsSource = gpsSource;
    }

    @Override
    public void run() {
        String msg = null;
        try {
            //获取队列数据
            msg = new String(message.getBody(), "UTF-8");
            PubOrdergpsdata ordergpsdata = null;
            if(GpsSourceEnum.OBD_GPS == gpsSource) {
                ordergpsdata = obdRabbit(msg);
            } else if(GpsSourceEnum.APP_GPS == gpsSource) {
                ordergpsdata = appRabbit(msg);
            }
            mileageApiService.uploadGps(ordergpsdata);
            //消息回复
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("GPS获取队列数据出错", e);
            try {
                if(message.getMessageProperties().getRedelivered()) {
                    LOGGER.error("GPS消息(" + msg + ")重复处理失败，拒绝再次接收");
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    LOGGER.error("GPS消息(" + msg + ")处理失败，返回队列");
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            } catch (IOException e1) {

            }
        }
    }

    /**
     * 解析OBD队列数据
     * @param msg
     */
    private PubOrdergpsdata obdRabbit(String msg) {
        LOGGER.info("OBD队列上传数据:" + msg);

        JSONObject rabbitJson = JSONObject.fromObject(msg);
        String msgBody = rabbitJson.getString("MessageBody");
        ObdGps obdGps = StringUtil.parseJSONToBean(msgBody, ObdGps.class);
        //获取上报GPS所需数据
        PubOrdergpsdata ordergpsdata = new PubOrdergpsdata();
        ordergpsdata.setDeviceid(obdGps.getEqpId());
        ordergpsdata.setVehicleid(obdGps.getRelationId());
        ordergpsdata.setLng(obdGps.getLongitude());
        ordergpsdata.setLat(obdGps.getLatitude());
        ordergpsdata.setGpsdirection(obdGps.getGpsDrct());
        ordergpsdata.setGpsspeed(obdGps.getGpsSpeed());
        ordergpsdata.setObdspeed(obdGps.getGpsSpeed());
        ordergpsdata.setGpstime(StringUtil.parseDate(obdGps.getLocTime(), StringUtil.DATE_TIME_FORMAT));
        ordergpsdata.setGpssource(GpsSourceEnum.OBD_GPS.code);
        if(null != obdGps.getLocTotalMileage()) {
            ordergpsdata.setMileage(obdGps.getLocTotalMileage() * 1000);
        }
        ordergpsdata.setFuel(obdGps.getLocTotalFuel());
        ordergpsdata.setGpsstatus(obdGps.getIsPosition());
        return ordergpsdata;
    }

    /**
     * 解析APPGPS队列数据
     * @param msg
     */
    private PubOrdergpsdata appRabbit(String msg) {
        LOGGER.info("APPGPS队列上传数据:" + msg);

        return StringUtil.parseJSONToBean(msg, PubOrdergpsdata.class);
    }

}
