package com.szyciov.carservice.util.sendservice.factory.op.car;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.szyciov.carservice.util.sendservice.factory.SendMethodFactory;
import com.szyciov.carservice.util.sendservice.factory.SendRuleFactory;
import com.szyciov.carservice.util.sendservice.factory.SendServiceFactory;

/**
 * 出租车派单类，主要区别隔离网约车
 * @author zhu
 *
 */
public class OpCarSendServiceFactory extends SendServiceFactory{
	
	/**
	 * 获取派单规则的工厂类
	 */
	@Override
	public SendRuleFactory createSendRuleFactory(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		return (SendRuleFactory) wac.getBean("OpCarSendRuleFactory");
	}
	
	/**
	 * 获取派单方法的工厂类
	 */
	@Override
	public SendMethodFactory createSendMethodFactory(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		return (SendMethodFactory) wac.getBean("OpCarSendMethodFactory");
	}
	
}
