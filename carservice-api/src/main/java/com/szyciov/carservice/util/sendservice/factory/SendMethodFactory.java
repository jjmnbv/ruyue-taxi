package com.szyciov.carservice.util.sendservice.factory;

import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;

/**
 * 派单方法工厂类
 * @author zhu
 *
 */
public abstract class SendMethodFactory {

	/**
	 * 根据派单规则获取派单方法
	 */
	public abstract SendMethodHelper createSendMethod(SendRuleHelper sendrule);
}
