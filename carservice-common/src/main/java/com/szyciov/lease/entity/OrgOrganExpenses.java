package com.szyciov.lease.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 机构消费记录表
 */
public class OrgOrganExpenses {
	
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 类型
	 */
	public String type;
	
	/**
	 * 交易时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date expenseTime;
	
	/**
	 * 金额
	 */
	public BigDecimal amount;
	
	/**
	 * 余额
	 */
	public BigDecimal balance;
	
	/**
	 * 备注
	 */
	public String remark;
	
	/**
	 * 创建时间
	 */
	public Date createTime;
	
	/**
	 * 更新时间
	 */
	public Date updateTime;
	
	/**
	 * 创建人
	 */
	public String creater;
	
	/**
	 * 更新人
	 */
	public String updater;
	
	/**
	 * 数据状态
	 */
	public String status;
	
	/**
	 * 类型名称
	 */
	public String typeName;
	
	/**
	 * 金额显示
	 */
	public String amountVisual;
	
	/**
	 * 备注显示
	 */
	public String remarkVisual;
	
	/**
	 * 操作人类型
	 */
	public String operatorType;
	
	/**
	 * 交易结果
	 */
	public String operateResult;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getExpenseTime() {
		return expenseTime;
	}

	public void setExpenseTime(Date expenseTime) {
		this.expenseTime = expenseTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAmountVisual() {
		return amountVisual;
	}

	public void setAmountVisual(String amountVisual) {
		this.amountVisual = amountVisual;
	}

	public String getRemarkVisual() {
		return remarkVisual;
	}

	public void setRemarkVisual(String remarkVisual) {
		this.remarkVisual = remarkVisual;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

}
