package com.szyciov.lease.param;

import java.math.BigDecimal;

import com.szyciov.param.QueryParam;

public class OrganAccountQueryParam extends QueryParam {
	public OrganAccountQueryParam() {
	}
	
	/**
	 * 所属城市
	 */
	public String city;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 类型
	 */
	public String type;
	
	/**
	 * 起始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 查询次数
	 */
	public int num;
	
	/**
	 * 用户类型  0-普通，1-超管
	 */
	public String specialState;
	
	/**
	 * 账号（手机号）
	 */
	public String account;
	
	/**
	 * 未结算金额
	 */
	public BigDecimal unbalance;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public BigDecimal getUnbalance() {
		return unbalance;
	}

	public void setUnbalance(BigDecimal unbalance) {
		this.unbalance = unbalance;
	}
}
