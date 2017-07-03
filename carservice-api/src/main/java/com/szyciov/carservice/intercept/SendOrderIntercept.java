package com.szyciov.carservice.intercept;

import com.szyciov.entity.AbstractOrder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 订单派单的拦截器
 * Created by LC on 2017/4/8.
 */
@Aspect
@Component
public class SendOrderIntercept {

    private Logger logger = LoggerFactory.getLogger(SendOrderIntercept.class);

    @Before("execution(* com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarGrabSingleSendMethodImp.send(..))")
    public void beforeInsert(JoinPoint joinPoint) {
        System.out.println("--beforeInsert with joinPoint------------");
        //获取方法名称
        String methodName = joinPoint.getSignature().getName();
        //获取参数值

        System.out.println("Taget method: " + methodName);
    }


    @Around("execution(* com.szyciov.carservice.util.sendservice.sendmethod.op.*.send(..))")
    public Object send(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        String orderno = "";
        if (args.length >= 1 && args[1] instanceof AbstractOrder) {
            AbstractOrder order = (AbstractOrder) args[1];
            orderno = order.getOrderno();
        }

        logger.warn("开始订单" + orderno + "派单流程");
        Object object = point.proceed();//执行该方法
        logger.warn("结束订单" + orderno + "派单流程");
        return object;
    }

    @Around("execution(* com.szyciov.carservice.util.sendservice.sendmethod.op.*.send_UseNow(..))")
    public Object send_UseNow(ProceedingJoinPoint point) throws Throwable {
        logger.warn("开始即可用车流程");
        Object object = point.proceed();//执行该方法
        logger.warn("结束即可用车流程");
        return object;
    }

    @Around("execution(* com.szyciov.carservice.util.sendservice.sendmethod.op.*.send_Reserve(..))")
    public Object send_Reserve(ProceedingJoinPoint point) throws Throwable {
        logger.warn("开始预约用车流程");
        Object object = point.proceed();//执行该方法
        logger.warn("结束预约用车流程");
        return object;
    }


    @Around("execution(* com.szyciov.carservice.util.sendservice.sendmethod.op.*.send_Reserve_SOW(..))")
    public Object send_Reserve_SOW(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        Boolean sow = null;
        if (args.length >= 2 && args[2] instanceof Boolean) {
            sow = (Boolean) args[2];
        }

        if (sow == null) {
        } else if (sow) {
            logger.warn("开始强调度流程");
        }else{
            logger.warn("开始弱调度流程");
        }

        Object object = point.proceed();//执行该方法

        if (sow == null) {
        } else if (sow) {
            logger.warn("结束强调度流程");
        }else{
            logger.warn("结束弱调度流程");
        }

        return object;
    }


}
