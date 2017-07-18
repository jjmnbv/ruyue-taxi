/**
 * 
 */
package com.ry.taxi.order.service;



import com.ry.taxi.order.request.DriverStartParam;
import com.ry.taxi.order.request.DriverTakeParam;

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
	
	int doTakingOrder (DriverTakeParam param);
	
	String doStartOrder(DriverStartParam param);

	int doDriverArrival (DriverArrivalParam param);

	
}
