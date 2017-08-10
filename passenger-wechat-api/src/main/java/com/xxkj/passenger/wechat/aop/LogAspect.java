/**
 * 
 */
package com.xxkj.passenger.wechat.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @Title:LogAspect.java
 * @Package com.ry.taxi.intercepter
 * @Description
 * @author zhangdd
 * @date 2017年7月7日 上午9:16:35
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Component
@Aspect
public class LogAspect {
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);     
	
	@Pointcut("execution(* com.xxkj.passenger.*.service..*.*(..))")    
	public void serviceAspect(){    
	}
	
	@Pointcut("execution(* com.xxkj.passenger.*.web.*.*(..))")    
	public void controllerAspect(){   
		
	}
	
	@Before(value ="serviceAspect() || controllerAspect()")
	public void before(JoinPoint jp) throws JsonProcessingException, ClassNotFoundException {
		String classType = jp.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);  
		String className = clazz.getSimpleName(); 
		String methodName = jp.getSignature().getName();   //获得方法名
		Object[] args = jp.getArgs();  //获得参数列表	//, getArgsList(args)
		logger.info("请求前置拦截{},method:{}, request:{}", className, methodName,getArgsList(args));
	}
	
	@AfterThrowing(pointcut="serviceAspect() || controllerAspect()", throwing="ex")    
	public void afterThrow(JoinPoint jp, Exception ex)throws JsonProcessingException, ClassNotFoundException{        
		String classType = jp.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);  
		String className = clazz.getSimpleName(); 
		String methodName = jp.getSignature().getName();   //获得方法名
		Object[] args = jp.getArgs();  //获得参数列表
		logger.error("请求异常拦截{},method:{},request:{}, errorMessage:{} ",className, methodName,getArgsList(args) ,ex.getMessage());    
	}
	
    @AfterReturning(pointcut="serviceAspect() || controllerAspect()", returning="rvt")
    public void log(JoinPoint jp, Object rvt) throws JsonProcessingException, ClassNotFoundException {
		String classType = jp.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);  
		String className = clazz.getSimpleName(); 
		String methodName = jp.getSignature().getName();   //获得方法名
		Object[] args = jp.getArgs();  //获得参数列表      
		logger.info("请求返回值拦截{},method:{}, request:{}, result:{}", className, methodName, getArgsList(args),rvt);
    }
	
	@After(value = "serviceAspect() || controllerAspect()")    
	public void alter(JoinPoint jp) throws JsonProcessingException, ClassNotFoundException {        
		String classType = jp.getTarget().getClass().getName();  
        Class<?> clazz = Class.forName(classType);  
		String className = clazz.getSimpleName(); 
		String methodName = jp.getSignature().getName();   //获得方法名
		Object[] args = jp.getArgs();  //获得参数列表      
		logger.info("请求后置拦截{},method:{}, request:{}", className, methodName, getArgsList(args));
	}
	
	
	public String getArgsList(Object[] args) throws JsonProcessingException{
		if (args == null || args.length == 0)
			return null;
		StringBuilder argStr = new StringBuilder("");
		for(int i = 0; i < args.length ; i ++){
			argStr.append("参数").append(i).append(":").append(args[i]).append(";");
		}
		return argStr.toString();
	}
	
	
	
}