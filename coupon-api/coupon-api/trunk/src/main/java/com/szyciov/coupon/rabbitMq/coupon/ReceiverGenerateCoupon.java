package com.szyciov.coupon.rabbitMq.coupon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.szyciov.coupon.factory.generate.GenerateCoupon;
import com.szyciov.coupon.factory.generate.GenerateCouponFactory;
import com.szyciov.coupon.rabbitMq.RabbitQueues;
import com.szyciov.coupon.service.PubCouponService;
import com.szyciov.coupon.task.GenerateCouponTask;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.threadpool.CarServiceThreadPool;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author LC
 * @date 2017/8/10
 */
@Component

public class ReceiverGenerateCoupon {


    private static ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("coupon-generage-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5,200, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(1024),nameThreadFactory,new AbortPolicy());

    @Resource
    private PubCouponService couponService;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueues.COUPON_GENERAGE)
    public void process(String jsonStr) {

        GenerateCouponTask couponTask = new GenerateCouponTask(jsonStr);

        CarServiceThreadPool.getInstance(20,0).execute(couponTask);
    }


    @RabbitHandler
    @RabbitListener(queues = RabbitQueues.COUPON_AUTO_GENERAGE)
    public void process1(@Payload  byte[] data) {
        String jsonStr = null;

        try{
            jsonStr = new String(data,"UTF-8");
            couponService.aotuGenerateCoupon(jsonStr);
        }catch (Exception e){

        }
        //System.out.println("coupon-auto-queue");
    }

}
 