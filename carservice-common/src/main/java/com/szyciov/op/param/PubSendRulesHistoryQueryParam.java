package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PubSendRulesHistoryQueryParam extends QueryParam {
	
	/**
	 * 所属派单规则
	 */
	private String sendRulesId;

	/**
	 * 所述租赁公司 
	 * @return
	 */
	private String leasesCompanyId;

	public String getSendRulesId() {
		return sendRulesId;
	}

	public void setSendRulesId(String sendRulesId) {
		this.sendRulesId = sendRulesId;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public PubSendRulesHistoryQueryParam() {
		super();
	}

	
}
