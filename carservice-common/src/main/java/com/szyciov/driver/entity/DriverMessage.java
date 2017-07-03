/**
 * 
 */
package com.szyciov.driver.entity;

import java.util.Date;

import net.sf.json.JSONObject;

/**
 * @ClassName DriverMessage 
 * @author Efy Shu
 * @Description 司机消息类
 * @date 2017年4月11日 下午3:42:49 
 */
public class DriverMessage {
	/**
	 * 消息ID,
	 */
	private String newsid;
	/**
	 * 消息创建时间
	 */
	private Date createtime;
	/**
	 * 是否已读
	 */
	private boolean readed;
	/**
	 * 消息类型(
	 * 0-抢单信息,1-新的任务,2-取消订单,3-复核反馈
	 * 4-提现通知,5-解绑成功,6-绑定成功,7-行程提醒
	 * )
	 */
	private String newstype;
	/**
	 * 消息标题(对应消息类型的中文),
	 */
	private String title;
	/**
	 * 消息内容(比如解绑、复核时的备注,说明等)
	 */
	private String content;
	/**
	 * 订单信息
	 */
	private JSONObject orderinfo;
	/**
	 * 提现信息
	 */
	private JSONObject takecashinfo;
	
	
	/**************************************Getter & Setter********************************************/
	
	/**  
	 * 获取消息ID  
	 * @return newsid 消息ID  
	 */
	public String getNewsid() {
		return newsid;
	}
	
	/**  
	 * 设置消息ID  
	 * @param newsid 消息ID  
	 */
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	/**  
	 * 获取消息创建时间  
	 * @return createtime 消息创建时间  
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**  
	 * 设置消息创建时间  
	 * @param createtime 消息创建时间  
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	/**  
	 * 获取是否已读  
	 * @return readed 是否已读  
	 */
	public boolean isReaded() {
		return readed;
	}

	/**  
	 * 设置是否已读  
	 * @param readed 是否已读  
	 */
	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	/**  
	 * 获取消息类型(0-抢单信息1-新的任务2-取消订单3-复核反馈4-提现通知5-解绑成功6-绑定成功7-行程提醒)  
	 * @return newstype 消息类型(0-抢单信息1-新的任务2-取消订单3-复核反馈4-提现通知5-解绑成功6-绑定成功7-行程提醒)  
	 */
	public String getNewstype() {
		return newstype;
	}
	

	/**  
	 * 设置消息类型(0-抢单信息1-新的任务2-取消订单3-复核反馈4-提现通知5-解绑成功6-绑定成功7-行程提醒)  
	 * @param newstype 消息类型(0-抢单信息1-新的任务2-取消订单3-复核反馈4-提现通知5-解绑成功6-绑定成功7-行程提醒)  
	 */
	public void setNewstype(String newstype) {
		this.newstype = newstype;
	}
	
	/**  
	 * 获取消息标题(对应消息类型的中文)  
	 * @return title 消息标题(对应消息类型的中文)  
	 */
	public String getTitle() {
		return title;
	}

	/**  
	 * 设置消息标题(对应消息类型的中文)  
	 * @param title 消息标题(对应消息类型的中文)  
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**  
	 * 获取消息内容(比如解绑、复核时的备注说明等)  
	 * @return content 消息内容(比如解绑、复核时的备注说明等)  
	 */
	public String getContent() {
		return content;
	}

	/**  
	 * 设置消息内容(比如解绑、复核时的备注说明等)  
	 * @param content 消息内容(比如解绑、复核时的备注说明等)  
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**  
	 * 获取订单信息  
	 * @return orderinfo 订单信息  
	 */
	public JSONObject getOrderinfo() {
		return orderinfo;
	}
	
	/**  
	 * 设置订单信息  
	 * @param orderinfo 订单信息  
	 */
	public void setOrderinfo(JSONObject orderinfo) {
		this.orderinfo = orderinfo;
	}
	
	/**  
	 * 获取提现信息  
	 * @return takecashinfo 提现信息  
	 */
	public JSONObject getTakecashinfo() {
		return takecashinfo;
	}
	
	/**  
	 * 设置提现信息  
	 * @param takecashinfo 提现信息  
	 */
	public void setTakecashinfo(JSONObject takecashinfo) {
		this.takecashinfo = takecashinfo;
	}
}
