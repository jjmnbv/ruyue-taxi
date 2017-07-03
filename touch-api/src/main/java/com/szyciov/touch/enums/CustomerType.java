package com.szyciov.touch.enums;

public enum CustomerType {

	/**
	 * 机构用户的客户类型
	 * 渠道客户
	 */
	CHANNELCUSTOMER("1","渠道客户"),
	
	/**
	 * 非渠道客户
	 */
	NONCHANNELCUSTOMER("0","非渠道客户");
	
	public String type;
	public String message;
	
	private CustomerType(String type, String message) {
		this.type = type;
		this.message = message;
	}
	
}
