package com.szyciov.carservice.util.sendservice.factory;

import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.entity.AbstractOrder;

/**
 * 派单规则工厂类
 * @author zhu
 *
 */
public abstract class SendRuleFactory {
	
	/**
	 * 根据订单信息获取派单规则
	 */
	public abstract SendRuleHelper createSendRule(AbstractOrder orderinfo);

}
