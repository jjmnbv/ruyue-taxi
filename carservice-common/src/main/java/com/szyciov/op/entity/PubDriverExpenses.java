package com.szyciov.op.entity;

import java.util.Date;

public class PubDriverExpenses {
	
	private String id;
	
	private String driverid;
	//0-充值,1-提现,2-订单结算
	private String tradetype;
	//1-微信支付，2-支付宝支付，3-余额支付  交易渠道
	private String expensetype;
	//交易时间
	private Date expensetime;
	//消费金额
	private double amount;
	//账户余额
	private double balance;
	//0-余额明细,1-交易明细  明细类型
	private String detailtype;
	
	private String remark;
	
	private Date createtime;
	
	private Date updatetime;
	
	private String creater;
	
	private String updater;
	
	private int status;
	//交易结果 包括：0-成功,1-失败
	private String operateresult;
	//所属平台  0-运管端，1-租赁端
	private String platformtype;
	//交易时间
	private String expensetimevisual;

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

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(String expensetype) {
		this.expensetype = expensetype;
	}

	public Date getExpensetime() {
		return expensetime;
	}

	public void setExpensetime(Date expensetime) {
		this.expensetime = expensetime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDetailtype() {
		return detailtype;
	}

	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getExpensetimevisual() {
		return expensetimevisual;
	}

	public void setExpensetimevisual(String expensetimevisual) {
		this.expensetimevisual = expensetimevisual;
	}

}
