package com.szyciov.entity;

/**
 * @ClassName OrderSource4WithdrawNO 
 * @author 
 * @Description 提现编号里的订单来源枚举
 * @date 2017年03月17日 上午11:53:33 
 */
public enum OrderSource4WithdrawNO {
	/**
	 * 乘客端
	 */
	PASSENGER("C","乘客端"),
	/**
	 * 司机端
	 */
	DRIVER("S","司机端"),
	/**
	 * 机构端
	 */
	ORGAN("J","机构端");

	
	public static OrderSource4WithdrawNO getByCode(String code){
		switch (code) {
		case "C":
			return PASSENGER;
		case "S":
			return DRIVER;
		case "J":
			return ORGAN;
		default:
			return ORGAN;
		}
	}

	public String code;
	public String msg;
	OrderSource4WithdrawNO(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
