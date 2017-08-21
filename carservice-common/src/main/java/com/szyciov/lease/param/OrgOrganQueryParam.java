package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

/**
 * 机构信息模糊检索
 */
public class OrgOrganQueryParam extends QueryParam{
	/**
	 * 机构简称
	 */
	public String queryShortName;
	/**
	 * 机构地址（市）
	 */
	public String queryCity;
	/**
	 * 所属租赁公司
	 * */
	public String leasesCompanyId;
	
	public String account;
	
	public String specialState;
	
	/**
	 * 合作状态
	 */
	public String cooperationStatus;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 供车主体
	 * */
	public String queryCustomertype;
	
	private String[] queryCustomertypes;
	
	public String[] getQueryCustomertypes() {
		return queryCustomertypes;
	}
	public void setQueryCustomertypes(String[] queryCustomertypes) {
		this.queryCustomertypes = queryCustomertypes;
	}
	public String getQueryCustomertype() {
		return queryCustomertype;
	}
	public void setQueryCustomertype(String queryCustomertype) {
		this.queryCustomertype = queryCustomertype;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSpecialState() {
		return specialState;
	}
	public void setSpecialState(String specialState) {
		this.specialState = specialState;
	}
	public String getQueryShortName() {
		return queryShortName;
	}
	public void setQueryShortName(String queryShortName) {
		this.queryShortName = queryShortName;
	}
	public String getQueryCity() {
		return queryCity;
	}
	public void setQueryCity(String queryCity) {
		this.queryCity = queryCity;
	}
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getCooperationStatus() {
		return cooperationStatus;
	}
	public void setCooperationStatus(String cooperationStatus) {
		this.cooperationStatus = cooperationStatus;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
}
