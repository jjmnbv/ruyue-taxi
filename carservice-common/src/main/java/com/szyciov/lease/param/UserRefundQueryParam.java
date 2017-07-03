package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class UserRefundQueryParam extends QueryParam {
	public UserRefundQueryParam() {
	}
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 退款状态
	 */
	public String refundStatus;
	
	/**
	 * 用户类型  0-普通，1-超管
	 */
	public String specialState;
	
	/**
	 * 账号（手机号）
	 */
	public String account;

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
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
	
}
