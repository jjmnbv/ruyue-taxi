package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeAccountRulesQueryParam extends QueryParam {
	public LeAccountRulesQueryParam() {
	}

	/**
	 * 规则类型
	 */
	public String rulesType;
	
	/**
	 * 城市
	 */
	public String city;
	
	/**
	 * 服务车型
	 */
	public String carType;
	
	/**
	 * 规则状态
	 */
	public String rulesState;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	/**
	 * 回空里程(公里)
	 */
	public String deadheadmileage;
	/**
	 * 回空费价
	 */
	public String deadheadprice;
	/**
	 * 夜间服务开始时间
	 */
	public String nightstarttime;
	/**
	 * 夜间服务结束时间
	 */
	public String nightendtime;
	/**
	 * 夜间费价
	 */
	public String nighteprice;

	public String getDeadheadmileage() {
		return deadheadmileage;
	}

	public void setDeadheadmileage(String deadheadmileage) {
		this.deadheadmileage = deadheadmileage;
	}

	public String getDeadheadprice() {
		return deadheadprice;
	}

	public void setDeadheadprice(String deadheadprice) {
		this.deadheadprice = deadheadprice;
	}

	public String getNightstarttime() {
		return nightstarttime;
	}

	public void setNightstarttime(String nightstarttime) {
		this.nightstarttime = nightstarttime;
	}

	public String getNightendtime() {
		return nightendtime;
	}

	public void setNightendtime(String nightendtime) {
		this.nightendtime = nightendtime;
	}

	public String getNighteprice() {
		return nighteprice;
	}

	public void setNighteprice(String nighteprice) {
		this.nighteprice = nighteprice;
	}

	public String getRulesType() {
		return rulesType;
	}

	public void setRulesType(String rulesType) {
		this.rulesType = rulesType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getRulesState() {
		return rulesState;
	}

	public void setRulesState(String rulesState) {
		this.rulesState = rulesState;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
}
