package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 机构信息模糊检索
 */
public class ServiceOrgQueryParam extends QueryParam{
	/**
	 * 机构简称
	 */
	public String queryServiceOrgFullName;
	/**
	 * 机构地址（市）
	 */
	public String queryServiceOrgCity;
	/**
	 * 公司id
	 * */
	public String leasesCompanyId;
	
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getQueryServiceOrgFullName() {
		return queryServiceOrgFullName;
	}
	public void setQueryServiceOrgFullName(String queryServiceOrgFullName) {
		this.queryServiceOrgFullName = queryServiceOrgFullName;
	}
	public String getQueryServiceOrgCity() {
		return queryServiceOrgCity;
	}
	public void setQueryServiceOrgCity(String queryServiceOrgCity) {
		this.queryServiceOrgCity = queryServiceOrgCity;
	}
	
	
}
