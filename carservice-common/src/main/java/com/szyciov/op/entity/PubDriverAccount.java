package com.szyciov.op.entity;

import java.util.Date;

/**
 * 司机账户管理 
 * 实体类
 * */
public class PubDriverAccount {
	private String id;
	// 关联的司机id
	private String driverid;
	//关联的租赁公司id
	private String leasescompanyid;
	//司机的账户余额
	private double balance;
	
	private Date createtime;
	
	private Date updatetime;
	
	private int status;
	//所属平台
	private int platformtype;
	//司机账号
	private String driverAccount;
	//司机姓名
	private String driverName;
	//司机类型
	private String driverType;
	// 0 kong 1 you
	private int isNull;
	// 余额明细数
	private int balanceCount;
	// 交易明细数
	private int dealCount;
	
	public int getIsNull() {
		return isNull;
	}
	public void setIsNull(int isNull) {
		this.isNull = isNull;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDriverid() {
		return driverid;
	}
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(int platformtype) {
		this.platformtype = platformtype;
	}
	public String getDriverAccount() {
		return driverAccount;
	}
	public void setDriverAccount(String driverAccount) {
		this.driverAccount = driverAccount;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	public int getBalanceCount() {
		return balanceCount;
	}
	public void setBalanceCount(int balanceCount) {
		this.balanceCount = balanceCount;
	}
	public int getDealCount() {
		return dealCount;
	}
	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
	
}
