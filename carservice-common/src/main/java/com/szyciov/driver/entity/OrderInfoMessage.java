/**
 * 
 */
package com.szyciov.driver.entity;

import java.util.Date;

import com.szyciov.util.InvokeUtil;

import net.sf.json.JSONObject;

/**
 * @ClassName OrderInfoMessage 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年11月29日 上午11:19:26 
 */
public class OrderInfoMessage {
	/**
	 * 消息类型
	 */
	private int type;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息文本
	 */
	private String content;
	/**
	 * 订单号
	 */
	private String orderid;
	/**
	 * 上车地址
	 */
	private String onaddr;
	
	/**
	 * 下车地址
	 */
	private String offaddr;
	
	/**
	 * 订单类型(格式化后)
	 */
	private String ordertype;
	
	/**
	 * 复核时间
	 */
	private Date reviewtime;
	/**
	 * 出发时间()
	 */
	private Date usetime;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 是否即刻用车
	 */
	private String usenow;
	/**
	 * 剩余时间
	 */
	private String lasttime;
	/**
	 * 用车类型
	 */
	private String usetype;
	
	/**  
	 * 获取剩余时间  
	 * @return lasttime 剩余时间  
	 */
	public String getLasttime() {
		return lasttime;
	}
	
	/**  
	 * 设置剩余时间  
	 * @param lasttime 剩余时间  
	 */
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	/**  
	 * 获取订单号  
	 * @return orderid 订单号  
	 */
	public String getOrderid() {
		return orderid;
	}
	
	/**  
	 * 设置订单号  
	 * @param orderid 订单号  
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	/**  
	 * 获取消息类型  
	 * @return type 消息类型  
	 */
	public int getType() {
		return type;
	}
	
	/**  
	 * 设置消息类型  
	 * @param type 消息类型  
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**  
	 * 获取消息标题  
	 * @return title 消息标题  
	 */
	public String getTitle() {
		return title;
	}
	
	/**  
	 * 设置消息标题  
	 * @param title 消息标题  
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**  
	 * 获取消息文本  
	 * @return content 消息文本  
	 */
	public String getContent() {
		return content;
	}
	

	/**  
	 * 设置消息文本  
	 * @param content 消息文本  
	 */
	public void setContent(String content) {
		this.content = content;
	}
	

	/**  
	 * 获取上车地址  
	 * @return onaddr 上车地址  
	 */
	public String getOnaddr() {
		return onaddr;
	}

	/**  
	 * 设置上车地址  
	 * @param onaddr 上车地址  
	 */
	public void setOnaddr(String onaddr) {
		this.onaddr = onaddr;
	}
	
	/**  
	 * 获取下车地址  
	 * @return offaddr 下车地址  
	 */
	public String getOffaddr() {
		return offaddr;
	}

	/**  
	 * 设置下车地址  
	 * @param offaddr 下车地址  
	 */
	public void setOffaddr(String offaddr) {
		this.offaddr = offaddr;
	}
	
	/**  
	 * 获取订单类型(格式化后)  
	 * @return ordertype 订单类型(格式化后)  
	 */
	public String getOrdertype() {
		return ordertype;
	}
	
	/**  
	 * 设置订单类型(格式化后)  
	 * @param ordertype 订单类型(格式化后)  
	 */
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	
	/**  
	 * 获取复核时间  
	 * @return reviewtime 复核时间  
	 */
	public Date getReviewtime() {
		return reviewtime;
	}
	
	/**  
	 * 设置复核时间  
	 * @param reviewtime 复核时间  
	 */
	public void setReviewtime(Date reviewtime) {
		this.reviewtime = reviewtime;
	}
	
	/**  
	 * 获取出发时间(格式化后)  
	 * @return usetime 出发时间(格式化后)  
	 */
	public Date getUsetime() {
		return usetime;
	}
	
	/**  
	 * 设置出发时间(格式化后)  
	 * @param usetime 出发时间(格式化后)  
	 */
	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}
	
	/**  
	 * 获取订单状态  
	 * @return status 订单状态  
	 */
	public String getStatus() {
		return status;
	}
	
	/**  
	 * 设置订单状态  
	 * @param status 订单状态  
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		InvokeUtil.removeNullObejct(json);
		return json.toString();
	}

	/**  
	 * 获取是否即刻用车  
	 * @return usenow 是否即刻用车  
	 */
	public String getUsenow() {
		return usenow;
	}
	
	/**  
	 * 设置是否即刻用车  
	 * @param usenow 是否即刻用车  
	 */
	public void setUsenow(String usenow) {
		this.usenow = usenow;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
}
