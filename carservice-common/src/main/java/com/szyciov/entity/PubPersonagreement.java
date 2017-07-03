package com.szyciov.entity;

import java.util.Date;

public class PubPersonagreement {
	
	private String id;
	
	/**
	 * 协议内容
	 */
	private String content;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新人
	 */
	private String updater;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
