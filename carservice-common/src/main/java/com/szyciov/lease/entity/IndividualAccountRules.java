package com.szyciov.lease.entity;

public class IndividualAccountRules extends LeAccountRules {
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 所属规则
	 */
	public String rulesId;
	
	/**
	 * 启用状态
	 */
	public String ruleState;
	
	/**
	 * 有效起始时间
	 */
	public String startTime;
	
	/**
	 * 有效截止时间
	 */
	public String endTime;

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getRulesId() {
		return rulesId;
	}

	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}

	public String getRuleState() {
		return ruleState;
	}

	public void setRuleState(String ruleState) {
		this.ruleState = ruleState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
