/**
 * 
 */
package com.ry.taxi.order.service;



import com.ry.taxi.order.request.DriverArrivalParam;
import com.ry.taxi.order.request.DriverCancelParam;
import com.ry.taxi.order.request.DriverStartParam;
import com.ry.taxi.order.request.DriverTakeParam;
import com.ry.taxi.order.request.StartCalculationParam;
import com.szyciov.param.BaiduApiQueryParam;

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
	
	int doDriverStart(DriverStartParam param);

	int doDriverArrival (DriverArrivalParam param);
	
	int doDriverCancel(DriverCancelParam param);
    
	int doStartCalculation(StartCalculationParam param);

	
}
