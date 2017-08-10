package com.szyciov.enums;

/**
 * @ClassName OrderEnum 
 * @author Efy Shu
 * @Description 订单公共枚举类
 * @date 2017年4月13日 下午2:31:04 
 */
public enum OrderEnum {
	/*****************************************************用车类型*********************************************/
	USETYPE_PUBLIC("0","因公用车"),
	USETYPE_PRIVATE("1","因私用车"),
	USETYPE_PERSONAL("2","个人用车"),
	
	/*****************************************************支付类型*********************************************/
	PAYTYPE_BALANCE("1","余额支付"),
	PAYTYPE_WECHAT("2","微信支付"),
	PAYTYPE_ALIPAY("3","支付宝支付"),
	
	/*****************************************************订单类型*********************************************/
	ORDERTYPE_RESERVE("1","约车"),
	ORDERTYPE_PICKUP("2","接机"),
	ORDERTYPE_DROPOFF("3","送机"),
	ORDERTYPE_TAXI("4","出租车"),
	
	/*****************************************************订单来源*********************************************/
	ORDERSOURCE_PASSENGER_ANDROID("00","安卓乘客端"),
	ORDERSOURCE_PASSENGER_IOS("01","IOS乘客端"),
	ORDERSOURCE_PASSENGER_WECHAT("02","微信乘客端"),
	ORDERSOURCE_LEASE("10","租赁端"),
	ORDERSOURCE_ORGAN("20","机构端"),
	ORDERSOURCE_OPERATE("30","运管端"),
	ORDERSOURCE_PARTNER("40","合作方"),
	
	/******************************************************复核方**********************************************/
	REVIEWPERSON_PASSENGER("0","乘客"),
	REVIEWPERSON_DRIVER("1","司机"),
	
	/******************************************************复核状态********************************************/
	REVIEWSTATUS_UNREVIEW("0","未复核"),
	REVIEWSTATUS_WAITREVIEW("1","异常待复核"),
	REVIEWSTATUS_ENDREVIEW("2","已复核"),

	/*****************************************************派单类型*********************************************/
	SENDORDERTYPE_SYSTEM("0","系统指派"),
	SENDORDERTYPE_MANTIC("1","手工指派"),
	/*****************************************************费用类型*********************************************/
	EXPENSETYPE_SERVICE_DONE("1","行程服务"),
	EXPENSETYPE_CANCEL_PUNISH("2","取消处罚"),
	/*****************************************************订单分类*********************************************/
	ORDERSTYPE_CAR("0","网约车"),
	ORDERSTYPE_TAXI("1","出租车");
	
	/**
	 * 根据值获取对应usetype
	 * @param usetype
	 * @return
	 */
	public static OrderEnum getUseType(String usetype){
		return USETYPE_PUBLIC.code.equals(usetype) ? OrderEnum.USETYPE_PUBLIC : 
					USETYPE_PRIVATE.code.equals(usetype) ? OrderEnum.USETYPE_PRIVATE : 
					USETYPE_PERSONAL.code.equals(usetype) ? OrderEnum.USETYPE_PERSONAL : 
					null;
	}
	
	/**
	 * 根据值获取对应ordertype
	 * @param ordertype
	 * @return
	 */
	public static OrderEnum getOrderType(String ordertype){
		return ORDERTYPE_RESERVE.code.equals(ordertype) ? OrderEnum.ORDERTYPE_RESERVE : 
					ORDERTYPE_PICKUP.code.equals(ordertype) ? OrderEnum.ORDERTYPE_PICKUP : 
					ORDERTYPE_DROPOFF.code.equals(ordertype) ? OrderEnum.ORDERTYPE_DROPOFF : 
					ORDERTYPE_TAXI.code.equals(ordertype) ? OrderEnum.ORDERTYPE_TAXI : 
					null;
	}
	
	/**
	 * 根据值获取对应ordersource
	 * @param ordersource
	 * @return
	 */
	public static OrderEnum getOrderSource(String ordersource){
		return ORDERSOURCE_PASSENGER_ANDROID.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_PASSENGER_ANDROID : 
					ORDERSOURCE_PASSENGER_IOS.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_PASSENGER_IOS : 
					ORDERSOURCE_PASSENGER_WECHAT.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_PASSENGER_WECHAT : 
					ORDERSOURCE_LEASE.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_LEASE : 
					ORDERSOURCE_ORGAN.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_ORGAN : 
					ORDERSOURCE_OPERATE.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_OPERATE :
					ORDERSOURCE_PARTNER.code.equals(ordersource) ? OrderEnum.ORDERSOURCE_PARTNER :
					null;
	}
	
    public String code;
    public String msg;

    OrderEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
