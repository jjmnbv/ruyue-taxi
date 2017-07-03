package com.szyciov.org.param;

import com.szyciov.param.QueryParam;

public class FinancialManagementQueryParam extends QueryParam {
	public FinancialManagementQueryParam() {
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
	 * 时间段类型
	 */
	public String timeType;
	
	/**
	 * 账单状态
	 */
	public String billState;

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

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}
}
