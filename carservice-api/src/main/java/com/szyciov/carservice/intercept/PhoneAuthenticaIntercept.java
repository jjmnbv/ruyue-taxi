package com.szyciov.carservice.intercept;

import com.szyciov.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用于调用第三方API的拦截器
 * Created by LC on 2017/4/8.
 */
@Aspect
@Component
public class PhoneAuthenticaIntercept {

    private Logger logger = LoggerFactory.getLogger(PhoneAuthenticaIntercept.class);


    @Pointcut("execution(* com.szyciov.carservice.service.PhoneAuthenticationService.*Authentication(..))")
    public void phoneAuthentication(){
    }


    @Around("phoneAuthentication()")
    public boolean around(ProceedingJoinPoint pjp) throws Throwable{
        String open = SystemConfig.getSystemProperty("apiopen");
        logger.debug("手机实名制认证接口开启状态：{}",open);
        //如果开关不为空且开启，则进行调用第三方接口
        if(StringUtils.isNotEmpty(open)&&Boolean.parseBoolean(open)){
            return (Boolean) pjp.proceed();
        }
        //默认验证通过
        return true;
    }

}
 