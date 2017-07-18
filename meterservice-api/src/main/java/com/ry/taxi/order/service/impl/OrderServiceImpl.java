/**
 * 
 */
package com.ry.taxi.order.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ry.taxi.base.constant.ErrorEnum;
import com.ry.taxi.order.domain.OpTaxiOrder;
import com.ry.taxi.order.mapper.DriverMapper;
import com.ry.taxi.order.mapper.OpTaxiOrderMapper;
import com.ry.taxi.order.request.DriverTakeParam;
import com.ry.taxi.order.service.OrderService;
import com.szyciov.driver.enums.OrderState;

/**
 * @Title:OrderServiceImpl.java
 * @Package com.ry.taxi.order.service.impl
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 下午2:28:35
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private DriverMapper driverMapper;
	
	@Autowired
	private OpTaxiOrderMapper opTaxiOrderMapper;

	@Transactional
	@Override
	public int doTakingOrder(DriverTakeParam param) {
		OpTaxiOrder taxiOrder = opTaxiOrderMapper.getOpTaxiOrder(param.getOrderNum());
		String orderStatus = null;
		if (taxiOrder == null)
			return ErrorEnum.e3012.getValue();//订单不存在
//		
//		WAITTAKE("0","待接单"),
//		/**
//		 * 待人工派单
//		 */
//		MANTICSEND("1","待人工派单"),
//		OrderState.WAITTAKE(orderStatus = taxiOrder.getStatus())
		else if (!(StringUtils.equals(OrderState.WAITTAKE.state, taxiOrder.getStatus()) || StringUtils.equals(OrderState.MANTICSEND.state, taxiOrder.getStatus())))
		
		
					
		return 0;
	}
	
	


}
