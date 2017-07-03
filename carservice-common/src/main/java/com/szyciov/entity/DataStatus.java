/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName DataStatus 
 * @author Efy Shu
 * @Description 数据状态枚举类
 * @date 2016年9月29日 上午11:34:35 
 */
public enum DataStatus {
	/**
	 * 正常
	 */
	OK(1,"正常"),
	/**
	 * 删除
	 */
	DELETE(2,"删除");
	
	public int code;
	public String msg;
	DataStatus(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
