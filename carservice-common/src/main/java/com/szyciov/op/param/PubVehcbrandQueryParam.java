package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 运管端 品牌  查询关系
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
