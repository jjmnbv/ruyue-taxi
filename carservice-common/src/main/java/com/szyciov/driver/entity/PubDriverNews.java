package com.szyciov.driver.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @ClassName PubDriverNews
 * @author Efy
 * @Description 司机消息类
 * @date 2016年9月19日 17:38:19
 */
public class PubDriverNews {
	/**
	 * id
	 */
	private String id;

	/**
	 * 与司机表主键关联
	 */
	private String userid;

	/**
	 * 0-订单消息,1-系统消息,2-推广信息,3-其它
	 */
	private String type;

	/**
	 * content
	 */
	private String content;

	/**
	 * 0-未读，1-已读
	 */
	private String newsstate;

	/**
	 * createtime
	 */
	private Date createtime;

	/**
	 * updatetime
	 */
	private Date updatetime;

	/**
	 * status
	 */
	private int status;

	/**
	 * 设置id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置与司机表主键关联
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 获取与司机表主键关联
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 设置0-订单消息,1-系统消息,2-推广信息,3-其它
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取0-订单消息,1-系统消息,2-推广信息,3-其它
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置0-未读，1-已读
	 */
	public void setNewsstate(String newsstate) {
		this.newsstate = newsstate;
	}

	/**
	 * 获取0-未读，1-已读
	 */
	public String getNewsstate() {
		return newsstate;
	}

	/**
	 * 设置createtime
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 获取createtime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * 设置updatetime
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 获取updatetime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}

	/**
	 * 设置status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获取status
	 */
	public int getStatus() {
		return status;
	}
}
