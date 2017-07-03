/**
 *
 */
package com.szyciov.enums;

/**
 * @ClassName UserSpecialEnum
 * @author LC
 * @Description 用户级别(0-普通用户，1-超管用户)
 * @date 2016年9月29日 上午11:38:02
 */
public enum UserSpecialEnum {
	/**
	 * 默认普通用户
	 */
	DEFAULT("0","普通用户"),
	/**
	 * 超管用户
	 */
	ADMIN("1","超管用户");


	public String code;
	public String msg;
	UserSpecialEnum(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
}
