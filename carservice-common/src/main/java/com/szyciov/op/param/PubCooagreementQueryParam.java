package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 运管端  模糊检索 条件
 */
public class PubCooagreementQueryParam extends QueryParam{
	private String queryCompanyname;
	
	private String queryServicetype;

	public String getQueryCompanyname() {
		return queryCompanyname;
	}

	public void setQueryCompanyname(String queryCompanyname) {
		this.queryCompanyname = queryCompanyname;
	}

	public String getQueryServicetype() {
		return queryServicetype;
	}

	public void setQueryServicetype(String queryServicetype) {
		this.queryServicetype = queryServicetype;
	}
	
	
}
