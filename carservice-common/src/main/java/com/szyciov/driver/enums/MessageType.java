package com.szyciov.driver.enums;

/**
 * 消息类型
 * @ClassName MessageType 
 * @author Efy Shu
 * @Description 消息类型枚举类
 * @date 2016年8月23日 下午7:45:59
 */
public enum MessageType {
	/**
	 * 订单消息
	 */
	ORDER("0","订单消息"),
	/**
	 * 系统消息
	 */
	SYSTEM("1","系统消息"),
	/**
	 * 推广消息
	 */
	SPREAD("2","推广消息"),
	/**
	 * 其他消息
	 */
	OTHER("3","其它消息");
	
	public String type;
	public String msg;
	MessageType(String type,String msg) {
		this.type = type;
		this.msg = msg;
	}
}
