/**
 * 
 */
package com.ry.taxi.base.constant;

/**
 * @Title:UrlRequestConstant.java
 * @Package com.ry.taxi.base.common
 * @Description
 * @author zhangdd
 * @date 2017年7月12日 上午11:44:56
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class UrlRequestConstant {

	
	public static final String CMD = "Cmd";
	
	public static final String KEY = "Key";
	
	public static final String USERID = "UserId";
	
	public static final String ARGS = "Args";
	
	public static final String SIGN = "Sign";
	
	public static final String CMD_DRIVERTAKEORDER = "DriverTakeOrder";//司机应邀通知
	
	public static final String CMD_DRIVERSTARTORDER = "DriverStartOrder";//司机执行订单通知
	
	public static final String CMD_DRIVERARRIVAL  = "DriverArrival";//司机到达乘客起点通知
	
	public static final String CMD_DRIVERCANCELORDER = "DriverCancelOrder";//司机取消通知
	
	public static final String CMD_STARTCALCULATION = "StartCalculation";//压表通知
	
	public static final String CMD_ENDCALCULATION = "EndCalculation";//起表通知
	
	public static final String CMD_PAYMENTCONFIRMATION = "PaymentConfirmation";//支付确认通知
	

}
