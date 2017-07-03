package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeAccountRulesModiLogQueryParam extends QueryParam {
	public LeAccountRulesModiLogQueryParam() {
	}
	
	/**
	 * 所属计费规则
	 */
	public String accountRulesId;
	
	/**
	 * 更新时间
	 */
	public String updateTime;

	public String getAccountRulesId() {
		return accountRulesId;
	}

	public void setAccountRulesId(String accountRulesId) {
		this.accountRulesId = accountRulesId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
