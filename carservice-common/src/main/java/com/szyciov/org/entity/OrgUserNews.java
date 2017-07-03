package com.szyciov.org.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 机构用户消息表
 */
public class OrgUserNews {
	/**
	 * 主键id
	 */
	public String id;
	/**
	 * 所属订单
	 */
	public String orderNo;
	/**
	 * 所属用户  与用户表主键关联
	 */
	public String userId;
	/**
	 * 消息类型   0-订单消息,1-系统消息,2-推广信息,3-其它
	 */
	public String type;
	/**
	 * 消息标题
	 */
	public String title;
	/**
	 * 消息内容
	 */
	public String content;
	/**
	 * 消息状态  0-未读，1-已读
	 */
	public String newsState;
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date createTime;
	/**
	 * 更新时间
	 */
	public Date updateTime;
	/**
	 * 数据状态
	 */
	public int status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNewsState() {
		return newsState;
	}
	public void setNewsState(String newsState) {
		this.newsState = newsState;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
