/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * @ClassName DriverMessageEnum 
 * @author Efy Shu
 * @Description 司机端消息类型枚举类
 * @date 2017年4月11日 下午3:08:09 
 */
public enum DriverMessageEnum {
	NEWS_STATE_UNREAD("0","未读"),
	NEWS_STATE_READED("1","已读"),
	
	/**
	 * 消息类型 0-抢单信息
	 */
	NEWS_TYPE_TAKEORDER("0","抢单信息"),
	/**
	 * 消息类型 1-新的任务
	 */
	NEWS_TYPE_NEWORDER("1","新的任务"),
	/**
	 * 消息类型 2-取消订单
	 */
	NEWS_TYPE_CANCELORDER("2","取消订单"),
	/**
	 * 消息类型 3-复核反馈
	 */
	NEWS_TYPE_REVIEWRESULT("3","复核反馈"),
	/**
	 * 消息类型 4-提现通知
	 */
	NEWS_TYPE_WITHDRAW("4","提现通知"),
	/**
	 * 消息类型 5-解绑成功
	 */
	NEWS_TYPE_UNBINDCAR("5","解绑成功"),
	/**
	 * 消息类型 6-绑定成功
	 */
	NEWS_TYPE_BINDCAR("6","绑定成功"),
	/**
	 * 消息类型 7-行程提醒
	 */
	NEWS_TYPE_REMINDORDER("7","行程提醒"),
	
	/**
	 * 订单消息
	 */
	TYPE_ORDER("0","订单消息"),
	/**
	 * 系统消息
	 */
	TYPE_SYSTEM("1","系统消息");
	
	/**
	 * 检查是否已读
	 * @param newsstate
	 * @return
	 */
	public static boolean isReaded(String newsstate){
		return NEWS_STATE_READED.code.equals(newsstate);
	}
	
	public String code;
	public String msg;
	
	DriverMessageEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
