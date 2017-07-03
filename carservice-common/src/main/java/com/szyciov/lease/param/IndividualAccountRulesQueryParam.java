package com.szyciov.lease.param;

import java.util.Date;

import com.szyciov.param.QueryParam;

public class IndividualAccountRulesQueryParam extends QueryParam {
	public IndividualAccountRulesQueryParam() {
	}
	
	/**
	 * 机构名称
	 */
	public String shortName;
	
	/**
	 * 有效起始时间
	 */
	public String startTime;
	
	/**
	 * 有效截止时间
	 */
	public String endTime;
	
	/**
	 * 状态
	 */
	public String ruleState;

	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 个性计费规则Id
	 */
	public String id;
	
	/**
	 * 用户类型  0-普通，1-超管
	 */
	public String specialState;
	
	/**
	 * 账号（手机号）
	 */
	public String account;
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public String getRuleState() {
		return ruleState;
	}

	public void setRuleState(String ruleState) {
		this.ruleState = ruleState;
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecialState() {
		return specialState;
	}

	public void setSpecialState(String specialState) {
		this.specialState = specialState;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
