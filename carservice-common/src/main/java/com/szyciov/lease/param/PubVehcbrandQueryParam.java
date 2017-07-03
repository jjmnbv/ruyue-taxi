package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

/**
 * 绑定模糊检索
 */
public class PubVehcbrandQueryParam extends QueryParam{
	
	public String leasesCompanyId;
	
	
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
}
