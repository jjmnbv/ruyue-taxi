package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PlatformOrderManagementParam extends QueryParam{
	private String orderSource;//订单来源
	private String companyId;//订单归属
	private String orderType;//订单类型(用车类型)
	private String orderStatus;//订单状态
	private String driver;//司机信息
	private String orderPerson;//下单人信息
	private String payType;//支付渠道
	private String cancelParty;//取消方
	private String orderNo;//订单号
	private String tradeNo;//交易流水号
	private String minUseTime;//用车开始时间
	private String maxUseTime;//用车结束时间
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getOrderPerson() {
		return orderPerson;
	}
	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCancelParty() {
		return cancelParty;
	}
	public void setCancelParty(String cancelParty) {
		this.cancelParty = cancelParty;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getMinUseTime() {
		return minUseTime;
	}
	public void setMinUseTime(String minUseTime) {
		this.minUseTime = minUseTime;
	}
	public String getMaxUseTime() {
		return maxUseTime;
	}
	public void setMaxUseTime(String maxUseTime) {
		this.maxUseTime = maxUseTime;
	}
	@Override
	public String toString() {
		return "PlatformOrderManagementParam [orderSource=" + orderSource + ", companyId=" + companyId
				+ ", orderType=" + orderType + ", orderStatus=" + orderStatus + ", driver=" + driver + ", orderPerson="
				+ orderPerson + ", payType=" + payType + ", cancelParty=" + cancelParty + ", orderNo=" + orderNo
				+ ", tradeNo=" + tradeNo + ", minUseTime=" + minUseTime + ", maxUseTime=" + maxUseTime + "]";
	}
	
}
