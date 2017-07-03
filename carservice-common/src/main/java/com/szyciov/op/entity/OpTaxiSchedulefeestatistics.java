package com.szyciov.op.entity;

import com.szyciov.param.QueryParam;

public class OpTaxiSchedulefeestatistics extends QueryParam{
	private String id;
	private String timeType;//时间类型 0 按日 1按月
	private String startTime;//开始时间 精确到 月
	private String startTime1;//开始时间 精确到 日
	private String endTime; //结束时间 精确到月
	private String endTime1;//结束时间 精确到日
	private String customer;//客户
	private String accounttype;//入账状态
	private String key;//查询初始化值key
	private String feetype;//费用类型，0 调度费，1行程费
	/**
	 * 返回值
	 */
	private String time;
	private String orders;
	private String money;
	private String driver;
	private String runMoney;
	
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	public String getRunMoney() {
		return runMoney;
	}
	public void setRunMoney(String runMoney) {
		this.runMoney = runMoney;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	@Override
	public String toString() {
		return "OpTaxiSchedulefeestatistics [id=" + id + ", timeType=" + timeType + ", startTime=" + startTime
				+ ", startTime1=" + startTime1 + ", endTime=" + endTime + ", endTime1=" + endTime1 + ", customer="
				+ customer + ", accounttype=" + accounttype + ", key=" + key + ", time=" + time + ", orders=" + orders
				+ ", money=" + money + ", driver=" + driver + ", runMoney=" + runMoney + "]";
	}
	
}
