package com.szyciov.carservice.util.sendservice.factory;

/**
 * 派单服务工厂类
 * @author zhu
 *
 */
public abstract class SendServiceFactory {
	
	/**
	 * 获取派单规则的工厂类
	 */
	public abstract SendRuleFactory createSendRuleFactory();
	
	/**
	 * 获取派单方法的工厂类
	 */
	public abstract SendMethodFactory createSendMethodFactory();
	
}
