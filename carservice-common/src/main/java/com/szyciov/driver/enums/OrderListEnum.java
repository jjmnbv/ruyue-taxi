/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * @ClassName OrderListEnum 
 * @author Efy Shu
 * @Description 订单列表参数枚举类
 * @date 2017年4月10日 下午3:31:18 
 */
public enum OrderListEnum {
	/**
	 * 当前订单(未出行,服务中订单)
	 */
	CURRENT(1,"当前"),
	/**
	 * 待付结订单
	 */
	WAITPAY(2,"待付结"),
	/**
	 * 已完结订单(已完成,司机已支付,已取消)
	 */
	COMPLETE(3,"已完结"),
	/**
	 * 默认是服务中订单
	 */
	DEFAULT(0,"服务中");
	public int state;
	public String msg;
	OrderListEnum(int state,String msg){
		this.msg = msg;
		this.state = state;
	}
}
