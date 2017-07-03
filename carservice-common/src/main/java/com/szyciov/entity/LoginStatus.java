/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName LoginStatus 
 * @author Efy Shu
 * @Description 登录状态枚举类
 * @date 2016年9月29日 上午11:45:12 
 */
public enum LoginStatus {
	/**
	 * 正常
	 */
	OK("0","正常"),
	/**
	 * 失败
	 */
	FAILED("1","失败"),
	/**
	 * 异常
	 */
	EXCEPTION("2","异常");
	
	public String code;
	public String msg;
	LoginStatus(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
