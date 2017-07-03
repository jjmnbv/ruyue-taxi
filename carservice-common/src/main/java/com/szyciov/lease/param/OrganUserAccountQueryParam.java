package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class OrganUserAccountQueryParam extends QueryParam {
	public OrganUserAccountQueryParam() {
	}
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 关键字(用户名称和手机号码)
	 */
	public String userId;
	
	/**
	 * 交易类型
	 */
	public String expenseType;
	
	/**
	 * 起始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 用户类型  0-普通，1-超管
	 */
	public String specialState;
	
	/**
	 * 账号（手机号）
	 */
	public String account;
	
	/**
	 * 明细类型
	 */
	public String detailType;

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
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

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
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

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
}
