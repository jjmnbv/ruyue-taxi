/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName CancelParty 
 * @author Efy Shu
 * @Description 取消方：0-租赁端，1-运管端，2-机构端 , 3-乘客端 ,4-派单失败,系统自动取消
 * @date 2016年9月29日 下午8:23:17 
 */
public enum CancelParty {
	/**
	 * 租赁端
	 */
	LEASE("0","租赁端"),
	/**
	 * 运管端
	 */
	OPERATOR("1","运管端"),
	/**
	 * 机构端
	 */
	ORGAN("2","机构端"),
	/** 
	 * 乘客端
	 */
	PASSENGER("3","乘客端"),
	/**
	 * 系统
	 */
	SYSTEM("4","系统"),
	/**
	 * 合作方
	 */
	PARTNER("5","合作方");
	
	public String code;
	public String msg;
	
	public static CancelParty getByCode(String code){
		return code.equals("0") ? LEASE : 
					code.equals("1") ? OPERATOR : 
					code.equals("2") ? ORGAN : 
					code.equals("3") ? PASSENGER : 
					code.equals("4") ? SYSTEM :
					code.equals("5") ? PARTNER : null;
	}
	CancelParty(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
