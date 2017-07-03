package com.szyciov.lease.entity;

import java.util.Date;

public class OrgDriverchanges {
	
	private String id;
	
	/**
	 * 所属订单
	 */
	private String orderno;
	
	/**
	 * 更换前所属司机
	 */
	private String beforedriverid;
	
	/**
	 * 更换后所属司机
	 */
	private String afterdriverid;
	
	/**
	 * 更换原因
	 */
	private String reason;
	
	/**
	 * 更换时间
	 */
	private Date sendtime;
	
	/**
	 * 操作人
	 */
	private String operator;
	
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
	
	/**
	 * 计费方式
	 */
	private String chargemodel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getBeforedriverid() {
		return beforedriverid;
	}

	public void setBeforedriverid(String beforedriverid) {
		this.beforedriverid = beforedriverid;
	}

	public String getAfterdriverid() {
		return afterdriverid;
	}

	public void setAfterdriverid(String afterdriverid) {
		this.afterdriverid = afterdriverid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getChargemodel() {
		return chargemodel;
	}

	public void setChargemodel(String chargemodel) {
		this.chargemodel = chargemodel;
	}

}
