package com.szyciov.op.entity;

import java.util.Date;

public class OpOrdercomment {
	
	private String id;
	
	/**
	 * 所属订单号
	 */
	private String orderno;
	
	/**
	 * 备注内容
	 */
	private String remark;
	
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
	 * 客服备注(0-复核，1-投诉，2-其他)
	 */
	private String remarktype;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getRemarktype() {
		return remarktype;
	}

	public void setRemarktype(String remarktype) {
		this.remarktype = remarktype;
	}
	
}
