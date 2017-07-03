package com.szyciov.enums;

public enum OrderUseTypeEnum {
	
	/**
	 * 因公用车
	 */
	ORDER_PUB("0", "因公用车"),
	
	/**
	 * 因私用车
	 */
	ORDER_PRI("1", "因私用车");
	
	public String code;
	public String msg;
	OrderUseTypeEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
}
