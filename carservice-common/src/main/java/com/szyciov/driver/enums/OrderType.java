package com.szyciov.driver.enums;

public enum OrderType {
	/**
	 * 网约车
	 */
	ONLINECAR("0","网约车订单"),
	/**
	 * 出租车
	 */
	TAXI("1","出租车订单"),
	
	/**
	 * 预约用车
	 */
	BOOK("1","约车"),
	/**
	 * 接机
	 */
	PICKUP("2","接机"),
	/**
	 * 送机
	 */
	PICKDOWN("3","送机");
	
	public String type;
	public String msg;
	OrderType(String type,String msg){
		this.msg = msg;
		this.type = type;
	}
}
