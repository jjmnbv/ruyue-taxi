package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class OpTaxiSendrulesHistoryQueryParam extends QueryParam {
	
	/**
	 * 所属派单规则
	 */
	private String taxisendrulesid;

	public String getTaxisendrulesid() {
		return taxisendrulesid;
	}

	public void setTaxisendrulesid(String taxisendrulesid) {
		this.taxisendrulesid = taxisendrulesid;
	}
	
}
