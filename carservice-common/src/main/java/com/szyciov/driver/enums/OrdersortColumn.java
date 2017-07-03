package com.szyciov.driver.enums;

public enum OrdersortColumn {
	
	/**
	 * 待接单
	 */
	WAITTAKE(1,"待接单"),
	/**
	 * 司机未出发
	 */
	WAITSTART(2,"待出发"),
	/**
	 * 司机已出发
	 */
	START(3,"已出发"),
	/**
	 * 司机已抵达
	 */
	ARRIVAL(4,"已抵达"),
	/**
	 * 服务中
	 */
	INSERVICE(5,"服务中"),
	/**
	 * 待确费
	 */
	WAITMONEY(6,"待确费"),
	/**
	 * 未支付
	 */
	NOTPAY(7,"未支付"),
	/**
	 * 已支付
	 */
	PAYED(8,"已支付"),
	/**
	 * 未结算
	 */
	MENTED(9,"未结算"),
	/**
	 * 结算中
	 */
	STATEMENTING(10,"结算中"),
	/**
	 * 已结算
	 */
	STATEMENTED(11,"已结算"),
	/**
	 * 未付结
	 */
	NOPAYEND(12,"未付结"),
	/**
	 * 已付结
	 */
	PAYOVER(13,"已付结"),
	/**
	 * 订单取消
	 */
	CANCEL(14,"已取消");
	
	public int state;
	public String msg;
	OrdersortColumn(int state,String msg){
		this.msg = msg;
		this.state = state;
	}
	
}
