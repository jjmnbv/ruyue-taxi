package com.szyciov.lease.entity;

import java.math.BigDecimal;

/**
 * 个人客户账户信息
 */
public class OrganUserAccountInfo {
	
	/**
	 * 昵称
	 */
	public String nickName;
	
	/**
	 * 电话号码
	 */
	public String account;
	
	/**
	 * 性别
	 */
	public String sex;
	
	/**
	 * 账户余额
	 */
	public BigDecimal balance;
	
	/**
	 * 机构简称
	 */
	public String shortName;
	
	/**
	 * 用户Id
	 */
	public String id;
	
	/**
	 * 交易明细数
	 */
	public int dealCount;
	
	/**
	 * 余额明细数
	 */
	public int balanceCount;
	
	/**
	 * 抵用券明细数
	 */
    public int couponCount; 
    
    /**
     * 可用的抵用券张数
     */
	public int validCoupon;
    
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public int getBalanceCount() {
		return balanceCount;
	}

	public void setBalanceCount(int balanceCount) {
		this.balanceCount = balanceCount;
	}

	public int getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}

	public int getValidCoupon() {
		return validCoupon;
	}

	public void setValidCoupon(int validCoupon) {
		this.validCoupon = validCoupon;
	}

	public OrganUserAccountInfo() {
		super();
	}
}
