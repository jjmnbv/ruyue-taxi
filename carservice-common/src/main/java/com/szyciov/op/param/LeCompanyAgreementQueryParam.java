package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class LeCompanyAgreementQueryParam extends QueryParam {
	
	/**
	 * 租赁公司名称
	 */
	private String leasecompanyName;

	public String getLeasecompanyName() {
		return leasecompanyName;
	}

	public void setLeasecompanyName(String leasecompanyName) {
		this.leasecompanyName = leasecompanyName;
	}
	
}
