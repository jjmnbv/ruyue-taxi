package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class OpSendRulesHistoryQueryParam extends QueryParam {
	
	/**
	 * 所属派单规则
	 */
	private String sendRulesId;

	 

	public String getSendRulesId() {
		return sendRulesId;
	}



	public void setSendRulesId(String sendRulesId) {
		this.sendRulesId = sendRulesId;
	}



	public OpSendRulesHistoryQueryParam() {
		super();
	}

	
}
