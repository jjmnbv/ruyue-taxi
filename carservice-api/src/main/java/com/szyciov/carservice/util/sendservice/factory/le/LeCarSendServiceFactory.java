package com.szyciov.carservice.util.sendservice.factory.le;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.szyciov.carservice.util.sendservice.factory.SendMethodFactory;
import com.szyciov.carservice.util.sendservice.factory.SendRuleFactory;
import com.szyciov.carservice.util.sendservice.factory.SendServiceFactory;

/**
 * 租赁端网约车派单类，主要区别隔离出租车
 * @author zhu
 *
 */
public class LeCarSendServiceFactory extends SendServiceFactory{
	
	/**
	 * 获取派单规则的工厂类
	 */
	public SendRuleFactory createSendRuleFactory(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		return (SendRuleFactory) wac.getBean("LeCarSendRuleFactory");
	}
	
	/**
	 * 获取派单方法的工厂类
	 */
	public SendMethodFactory createSendMethodFactory(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		return (SendMethodFactory) wac.getBean("LeCarSendMethodFactory");
	}
	
}
