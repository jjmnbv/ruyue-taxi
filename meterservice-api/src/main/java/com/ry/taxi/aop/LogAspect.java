/**
 * 
 */
package com.ry.taxi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * @Title:LogAspect.java
 * @Package com.ry.taxi.intercepter
 * @Description
 * @author zhangdd
 * @date 2017��7��7�� ����9:16:35
 * @version 
 *
 * @Copyrigth  ��Ȩ���� (C) 2017 ����Ѷ����Ϣ�Ƽ����޹�˾.
 */
@Component
@Aspect
public class LogAspect {
	private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);        
	
	@Pointcut("execution(* com.ry.taxi.*.service..*(..))")    
	public void aspect(){    
	}
	
	@Before(value = "aspect()")
	public void before(JoinPoint jp) throws JsonProcessingException {
		String className = jp.getThis().toString();
		String methodName = jp.getSignature().getName();   //��÷�����
		Object[] args = jp.getArgs();  //��ò����б�	
		logger.info("����ǰ������,{}.{}, request:{}", className, methodName, getArgsList(args));
	}
	
	
	@AfterThrowing(pointcut="aspect()", throwing="ex")    
	public void afterThrow(JoinPoint jp, Exception ex)throws JsonProcessingException{        
		String className = jp.getThis().toString();
		String methodName = jp.getSignature().getName();   //��÷�����
		Object[] args = jp.getArgs();  //��ò����б�
		logger.error("�����쳣����,{}.{},request:{}, errorMessage:{} ",className, methodName,getArgsList(args) ,ex.getMessage());    
	}
	
	@After(value = "aspect()")    
	public void alter(JoinPoint jp) throws JsonProcessingException {        
		String className = jp.getThis().toString();
		String methodName = jp.getSignature().getName();   //��÷�����
		Object[] args = jp.getArgs();  //��ò����б�      
		logger.info("�����������,{}.{}, request:{}", className, methodName, getArgsList(args));
	}
	
	
	public String getArgsList(Object[] args) throws JsonProcessingException{
		if (args == null || args.length == 0)
			return null;
		StringBuilder argStr = new StringBuilder("");
		for(int i = 0; i < args.length ; i ++){
			argStr.append("����").append(i).append(":").append(JSONUtil.toJackson(args[i])).append(";");
		}
		return argStr.toString();
	}
	
	
	
}