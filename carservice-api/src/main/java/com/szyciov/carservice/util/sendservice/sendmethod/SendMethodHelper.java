package com.szyciov.carservice.util.sendservice.sendmethod;

import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.entity.AbstractOrder;

/**
 * 派单方法接口
 * @author zhu
 *
 */
public interface SendMethodHelper {

	/**
	 * 派单方法
	 * @param rules
	 * @param orderinfo
	 */
	public void send(SendRuleHelper rules,AbstractOrder orderinfo);
}
