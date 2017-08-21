package com.szyciov.supervision.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szyciov.supervision.api.request.BasicRequest;
import com.szyciov.supervision.api.responce.EntityInfoList;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/7/6.
 */
@Aspect
@Component
@SuppressWarnings("rawtypes")
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
		EntityInfoList result = getResult(args);
		if(result!=null)
		    logger.info("请求后置拦截,request:{}, isAllSuccess:{}, result:{}", JSONUtil.toJackson(getBasicRequest(args)), result.isAllSuccess(), JSONUtil.toJackson(result));

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

	private EntityInfoList getResult(Object[] params){
        if(params==null||params.length<=0){
            return null;
        }
        for(int i=0;i<params.length;i++){
            Object obj = params[i];
            if(obj instanceof EntityInfoList){
                return (EntityInfoList)obj;
            }
        }
        return null;
    }
}
