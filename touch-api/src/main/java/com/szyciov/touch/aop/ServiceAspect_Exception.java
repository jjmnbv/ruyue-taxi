package com.szyciov.touch.aop;

import com.szyciov.touch.service.PubInfoService;
import com.szyciov.util.GUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhu on 2017/5/22.
 * 业务方法处理异常的情况记录日志
 */

@Component
@Aspect
public class ServiceAspect_Exception {
    private final static Log logger = LogFactory.getLog(ServiceAspect_Exception.class);

    /**
     *  配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     */
    @Pointcut("execution(* com.szyciov.touch.service..*(..))")
    public void aspect(){}

    /**
     *  配置抛出异常后通知,使用在方法aspect()上注册的切入点
     */
    @AfterThrowing(pointcut="aspect()", throwing="ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex){
        if(logger.isErrorEnabled()){
            logger.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
        }
        HttpServletRequest http = getHttpRequest(joinPoint.getArgs());
        if(http!=null){
            http.setAttribute("errormessage",ex.getMessage());
        }
    }

    /**
     * 从请求参数中获取请求对象
     * @param params
     * @return
     */
    private HttpServletRequest getHttpRequest(Object[] params){
        if(params==null||params.length<=0){
            return null;
        }
        for(int i=0;i<params.length;i++){
            Object httpobj = params[i];
            if(httpobj instanceof HttpServletRequest){
                return (HttpServletRequest) httpobj;
            }
        }
        return null;
    }

}
