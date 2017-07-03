package com.szyciov.driver.enums;

/**
 * 第三方支付枚举类
 * @ClassName PayUtilEnum 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2017年4月5日 下午7:26:29
 */
public enum PayUtilEnum{
	/**
	 * 充值
	 */
	RECHARGE("0","——充值"),
	/**
	 * 订单付款
	 */
	PAYORDER("1","——"),
	
	/**
	 * 微信支付--失败
	 */
	WECHATFAILED("0","FAIL"),
	/**
	 * 微信支付--成功
	 */
	WECHATSUCCESS("1","SUCCESS"),
	
	/**
	 * 支付宝支付--失败
	 */
	ALIPAYFAILED("0","failure"),
	/**
	 * 支付宝支付--成功
	 */
	ALIPAYSUCCESS("1","success"),
	
	/**
	 * 交易状态 --待确认
	 */
	TRADING_PROCESSING("0","待确认"),
	/**
	 * 交易状态 --成功
	 */
	TRADING_SUCCESS("1","成功"),
	/**
	 * 交易状态 --取消(失败)
	 */
	TRADING_FAILED("2","取消(失败)");
	public String code;
	public String msg;
	
	private PayUtilEnum(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
}