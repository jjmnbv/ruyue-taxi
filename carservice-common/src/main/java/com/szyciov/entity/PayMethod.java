/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName PayMethod 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月26日 下午5:15:20 
 */
public enum PayMethod {
	/**
	 * 个人支付
	 */
	PERSONAL("0","个人支付"),
	/**
	 * 个人垫付
	 */
	ADVANCE("1","个人垫付"),
	/**
	 * 机构支付
	 */
	ORGAN("2","机构支付"),
	
	/**
	 * 支付方式-余额支付
	 */
	BALANCE("3","余额支付"),
	/**
	 * 支付方式-微信支付
	 */
	WECHAT("1","微信支付"),
	/**
	 * 支付方式-支付宝支付
	 */
	ALIPAY("2","支付宝支付"),
	
	/**
	 * 线上支付
	 */
	ONLINE("0","线上"),
	/**
	 * 线下支付
	 */
	OFFLINE("1","线下");
	
	public String code ;
	public String msg;
	PayMethod(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
