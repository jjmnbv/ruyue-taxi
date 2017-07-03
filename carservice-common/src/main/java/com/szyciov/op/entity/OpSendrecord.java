package com.szyciov.op.entity;

import java.util.Date;

public class OpSendrecord {
	
	private String id;
	
	/**
	 * 所属订单
	 */
	private String orderno;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 车型计费方式
	 */
	private String chargemodel;
	
	/**
	 * 派单原因
	 */
	private String reason;
	
	/**
	 * 派单时间
	 */
	private Date sendtime;
	
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
	 * 司机id
	 */
	private String driverid;

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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getChargemodel() {
		return chargemodel;
	}

	public void setChargemodel(String chargemodel) {
		this.chargemodel = chargemodel;
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

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}
	
}
