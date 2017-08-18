package com.szyciov.lease.entity;

import java.math.BigDecimal;

/**
 * 机构客户账户信息
 */
public class OrganAccountInfo {
	
	/**
	 * 机构简称
	 */
	public String shortName;
	
	/**
	 * 机构全称
	 */
	public String fullName;
	
	/**
	 * 所属城市
	 */
	public String city;
	
	/**
	 * 账户状态
	 */
	public String accountStatus;
	
	/**
	 * 账户余额
	 */
	public BigDecimal actualBalance;
	
	/**
	 * 可用额度
	 */
	public BigDecimal balance;
	
	/**
	 * 信用额度
	 */
	public BigDecimal lineOfCredit;
	
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 城市名称
	 */
	public String cityName;
	
	/**
	 * 账户状态名称
	 */
	public String accountStatusName;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 未结算金额
	 */
	public BigDecimal unbalance;
	
	/**
	 * 抵用券余额
	 */
	public Double couponamount;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getActualBalance() {
		return actualBalance;
	}

	public void setActualBalance(BigDecimal actualBalance) {
		this.actualBalance = actualBalance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(BigDecimal lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAccountStatusName() {
		return accountStatusName;
	}

	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public BigDecimal getUnbalance() {
		return unbalance;
	}

	public void setUnbalance(BigDecimal unbalance) {
		this.unbalance = unbalance;
	}

	public Double getCouponamount() {
		return couponamount;
	}

	public void setCouponamount(Double couponamount) {
		this.couponamount = couponamount;
	}

}
