/**
 * 
 */
package com.ry.taxi.order.service;

import com.ry.taxi.order.domain.OpTaxiOrder;

/**
 * @Title:OrderService.java
 * @Package com.ry.taxi.order.service
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 下午1:54:25
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public interface OrderService {
	
	public OpTaxiOrder getOpTaxiOrder(OpTaxiOrder order);
	
}
