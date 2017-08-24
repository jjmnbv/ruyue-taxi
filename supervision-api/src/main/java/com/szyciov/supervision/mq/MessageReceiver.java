package com.szyciov.supervision.mq;



import com.supervision.dto.SupervisionDto;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.responce.HttpContent;
import com.szyciov.supervision.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.api.service.BaseApiService;


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
    private OrderService orderService;
    @Autowired
    private GpsService gpsService;
    @Autowired
    private EvaluteService evaluteService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private BasicService basicService;


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
            logger.info("消息超时失效："+supervisionDto);
            return;
        }
        HttpContent httpContent=null;
        BaseApi baseApi=null;
       switch ( supervisionDto.getInterfaceType()){
           case ORDER:
               //营运订单相关数据，统一调用
               baseApi = orderService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;
           case BASIC:
               baseApi =  basicService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;
           case EVALUATE:
               baseApi=evaluteService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;
           case GPS:
               baseApi = gpsService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;
           case OPERATION:
               baseApi = operationService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;
           case RELATIONSHIP:
               baseApi=relationshipService.execute(supervisionDto.getCommandEnum(),supervisionDto.getDataMap());
               break;

           default:

       }
        if(baseApi==null){//查询不到数据
            return;
        }
        httpContent=baseApiService.sendApi(baseApi);
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
