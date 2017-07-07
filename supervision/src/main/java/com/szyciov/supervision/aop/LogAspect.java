package com.szyciov.supervision.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szyciov.supervision.util.BasicRequest;
import com.szyciov.supervision.util.GzwycResult;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/7/6.
 */
@Component
@Aspect
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Pointcut("execution(* com.szyciov.supervision.*.service..*(..))")
    public void aspect(){

    }

    /**
     *  配置抛出异常后通知,使用在方法aspect()上注册的切入点
     */
    @AfterThrowing(pointcut="aspect()", throwing="ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex)throws JsonProcessingException{
        Object[] args = joinPoint.getArgs();
        //TODO 日志记录
        logger.error("请求异常拦截,request:{}, errorMessage:{} ", JSONUtil.toJackson(getBasicRequest(args)) ,ex.getMessage());

    }

    @Before(value = "aspect()")
    public void Before(JoinPoint joinPoint) throws JsonProcessingException {

        Object[] args = joinPoint.getArgs();
        //TODO 日志记录
        logger.info("请求前置拦截,request:{}, result:{}", JSONUtil.toJackson(getBasicRequest(args)) ,JSONUtil.toJackson(getResult(args)));


    }

    @After(value = "aspect()")
    public void alter(JoinPoint joinPoint) throws JsonProcessingException {

        Object[] args = joinPoint.getArgs();
        //TODO 日志记录
        logger.info("请求后置拦截,request:{}, result:{}", JSONUtil.toJackson(getBasicRequest(args)) ,JSONUtil.toJackson(getResult(args)));

    }

    private BasicRequest getBasicRequest(Object[] params){
        if(params==null||params.length<=0){
            return null;
        }
        for(int i=0;i<params.length;i++){
            Object obj = params[i];
            if(obj instanceof BasicRequest){
                return (BasicRequest)obj;
            }
        }
        return null;
    }

    private GzwycResult getResult(Object[] params){
        if(params==null||params.length<=0){
            return null;
        }
        for(int i=0;i<params.length;i++){
            Object obj = params[i];
            if(obj instanceof GzwycResult){
                return (GzwycResult)obj;
            }
        }
        return null;
    }
}
