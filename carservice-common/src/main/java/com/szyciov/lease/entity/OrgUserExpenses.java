package com.szyciov.lease.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 机构用户消费记录
 */
public class OrgUserExpenses {
	
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 所属用户
	 */
	public String userId;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 交易渠道
	 */
	public String expenseType;
	
	/**
	 * 交易时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date expenseTime;
	
	/**
	 * 消费金额
	 */
	public String amount;
	
	/**
	 * 账户余额
	 */
	public String balance;
	
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
	 * 交易类型
	 */
	public String tradeType;
	
	/**
	 * 明细类型
	 */
	public String detailType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Date getExpenseTime() {
		return expenseTime;
	}

	public void setExpenseTime(Date expenseTime) {
		this.expenseTime = expenseTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
}
