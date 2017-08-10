package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class OrganBillQueryParam extends QueryParam  {
	public OrganBillQueryParam() {
	}
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 起始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 状态
	 */
	public String billState;
	
	/**
	 * 所属账单
	 */
	public String billsId;
	
	/**
	 * 账单类别
	 */
	public String billClass;
	
	/**
	 * 订单号
	 */
	public String orderNo;
	
	/**
	 * 用户类型  0-普通，1-超管
	 */
	public String specialState;
	
	/**
	 * 账号（手机号）
	 */
	public String account;
	
	/**
	 * 费用类型
	 */
	public Integer expensetype;

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillsId() {
		return billsId;
	}

	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}

	public String getBillClass() {
		return billClass;
	}

	public void setBillClass(String billClass) {
		this.billClass = billClass;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSpecialState() {
		return specialState;
	}

	public void setSpecialState(String specialState) {
		this.specialState = specialState;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(Integer expensetype) {
		this.expensetype = expensetype;
	}
}
