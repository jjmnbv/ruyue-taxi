package com.szyciov.op.entity;

import java.util.Date;

public class OpTaxivehiclechanges {
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 所属订单
	 */
	private String orderno;
	
	/**
	 * 更换前所属车辆
	 */
	private String beforevehicleid;
	
	/**
	 * 更换后所属车辆
	 */
	private String aftervehicleid;
	
	/**
	 * 更换前车牌号
	 */
	private String beforeplateno;
	
	/**
	 * 更换后车牌号
	 */
	private String afterplateno;
	
	/**
	 * 接单司机
	 */
	private String orderdriverid;
	
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

	public String getBeforevehicleid() {
		return beforevehicleid;
	}

	public void setBeforevehicleid(String beforevehicleid) {
		this.beforevehicleid = beforevehicleid;
	}

	public String getAftervehicleid() {
		return aftervehicleid;
	}

	public void setAftervehicleid(String aftervehicleid) {
		this.aftervehicleid = aftervehicleid;
	}

	public String getBeforeplateno() {
		return beforeplateno;
	}

	public void setBeforeplateno(String beforeplateno) {
		this.beforeplateno = beforeplateno;
	}

	public String getAfterplateno() {
		return afterplateno;
	}

	public void setAfterplateno(String afterplateno) {
		this.afterplateno = afterplateno;
	}

	public String getOrderdriverid() {
		return orderdriverid;
	}

	public void setOrderdriverid(String orderdriverid) {
		this.orderdriverid = orderdriverid;
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
	
}
