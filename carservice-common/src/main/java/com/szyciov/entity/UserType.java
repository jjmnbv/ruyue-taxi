/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName UserType 
 * @author Efy Shu
 * @Description 用户类型枚举类(0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端)
 * @date 2016年9月29日 上午11:38:02 
 */
public enum UserType {
	/**
	 * 机构用户
	 */
	ORGUSER("0","机构用户"),
	/**
	 * 个人用户
	 */
	PERSONAL("1","个人用户"),
	/**
	 * 司机
	 */
	DRVER("2","司机"),
	/**
	 * 租赁端
	 */
	LEASESERVER("3","租赁端"),
	/**
	 * 机构端
	 */
	ORGSERVER("4","机构端"),

	/**
	 * 提现申请-个人用户
	 */
	WITHDRAW_PASSENGER_OP("0","个人用户"),
	/**
	 * 提现申请-机构用户
	 */
	WITHDRAW_PASSENGER_ORG("1","机构用户"),
	/**
	 * 提现申请-司机
	 */
	WITHDRAW_DRIVER("2","司机"),
	/**
	 * 提现申请-机构
	 */
	WITHDRAW_ORGAN("3","机构");
	
	public String code;
	public String msg;
	UserType(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
