package com.szyciov.lease.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 机构账单状态记录
 */
public class OrgOrganBillState {
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 账单状态
	 */
	public String billState;
	
	/**
	 * 操作时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date operationTime;
	
	/**
	 * 所属账单
	 */
	public String billsId;
	
	/**
	 * 操作说明
	 */
	public String comment;
	
	/**
	 * 创建时间
	 */
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

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
