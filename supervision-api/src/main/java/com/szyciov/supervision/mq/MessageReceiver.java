package com.szyciov.supervision.mq;



import com.supervision.dto.SupervisionDto;
import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import com.szyciov.supervision.api.responce.HttpContent;
import com.szyciov.supervision.serivice.PubDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.api.service.BaseApiService;

import java.util.ArrayList;
import java.util.List;


/**
 * 队列接收者
 * Created by 林志伟 on 2017/7/12.
 */
@Component
@RabbitListener(queues = "supervision_api_object_queue")
public class MessageReceiver {
    private static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    private @Value("${queue-default-validtime:300000}") long validTime;

    @Autowired
    private BaseApiService baseApiService;
    @Autowired
    private PubDriverService pubDriverService;


    /**
     * 接收到系统传递的监管对象数据
     * 1.检查数据是否合格
     * 2.根据接口类型，和命令类型的不同，调用不同的【类/方法】处理
     * @param supervisionDto
     * @throws Exception
     */
    @RabbitHandler
    public void consumer(SupervisionDto supervisionDto) throws Exception
    {
        logger.info("接收到数据："+supervisionDto);
        if(!isValid(supervisionDto.getTimestamp())){
            return;
        }
        HttpContent httpContent=null;
       switch ( supervisionDto.getInterfaceType()){
           case ORDER:
               switch (supervisionDto.getCommandEnum()){
                   case DriverOnWork:
                       DriverOnWork driverOnWork=pubDriverService.onWork(supervisionDto.getDataMap());
                       if(driverOnWork==null){//查询不到数据
                           return;
                       }
                       httpContent=baseApiService.sendApi(driverOnWork);

                       break;
                   case DriverOffWork:
                       DriverOffWork driverOffWork=pubDriverService.offWork(supervisionDto.getDataMap());
                       if(driverOffWork==null){//查询不到数据
                           return;
                       }
                        httpContent=baseApiService.sendApi(driverOffWork);
               }
               break;
           case BASIC:
               break;
           case EVALUATE:
               break;
           default:
       }

        logger.info("处理数据："+httpContent);



    }

    /**
     * 验证过期时间
     * @param timestamp
     * @return
     */
    public boolean isValid(long timestamp){
        long currentTime=System.currentTimeMillis();
        if((currentTime-timestamp)>validTime){
            return  false;
        }else {
            return true;
        }
    }
}
