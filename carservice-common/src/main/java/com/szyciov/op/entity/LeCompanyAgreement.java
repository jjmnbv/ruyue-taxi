package com.szyciov.op.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LeCompanyAgreement {
	
	private String id;
	
	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;
	
	/**
	 * 协议简称
	 */
	private String shortname;
	
	/**
	 * 协议文本
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatetime;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新人
	 */
	private String updater;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	/**
	 * 租赁公司名称
	 */
	private String leasecompanyName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLeasecompanyName() {
		return leasecompanyName;
	}

	public void setLeasecompanyName(String leasecompanyName) {
		this.leasecompanyName = leasecompanyName;
	}

}
