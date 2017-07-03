package com.szyciov.enums;

public enum OrderVarietyEnum {
	
	LEASE_NET("0", "I", "租赁端网约车"),
	OPERATING_NET("1", "I", "运管端网约车"),
	OPERATING_TAXI("2", "T", "运管端出租车");

	/**
	 * 获取订单所属表
	 * @param usetype 用车类型(因公、因私、个人)
	 * @param ordertype 订单类型(约车、接机、送机、出租车)
	 * @return
	 */
	public static OrderVarietyEnum getOrderVariety(String usetype, String ordertype){
		if(OrderEnum.USETYPE_PUBLIC.code.equals(usetype)) { //因公
			return LEASE_NET;
		} else if(OrderEnum.USETYPE_PRIVATE.code.equals(usetype)) { //因私
			return LEASE_NET;
		} else if(OrderEnum.USETYPE_PERSONAL.code.equals(usetype)){ //个人
			if(OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype)) { //出租车
				return OPERATING_TAXI;
			} else {
				return OPERATING_NET;
			}
		}
		return LEASE_NET;
	}
	
	public String code;
	public int icode;
	public String variety;
	public String msg;
	OrderVarietyEnum(String code, String variety, String msg){
		this.code = code;
		this.icode = Integer.valueOf(code);
		this.variety = variety;
		this.msg = msg;
	}
	
}
