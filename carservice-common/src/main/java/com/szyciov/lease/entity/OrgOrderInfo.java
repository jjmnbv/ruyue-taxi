package com.szyciov.lease.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构账单详情
 */
public class OrgOrderInfo {
	
	/**
	 * 订单类型
	 */
	public String orderType;
	
	/**
	 * 订单号
	 */
	public String orderNo;
	
	/**
	 * 订单状态
	 */
	public String orderStatus;
	
	/**
	 * 订单金额
	 */
	public BigDecimal orderAmount;
	
	/**
	 * 里程
	 */
	public Double mileage;
	
	/**
	 * 累计时间
	 */
	public String cumulativeTime;
	
	/**
	 * 下单人
	 */
	public String nickName;
	
	/**
	 * 下单人手机号
	 */
	public String account;
	
	/**
	 * 乘车人
	 */
	public String passengers;
	
	/**
	 * 乘车人手机号
	 */
	public String passengerPhone;
	
	/**
	 * 司机姓名
	 */
	public String name;
	
	/**
	 * 司机手机号
	 */
	public String phone;
	
	/**
	 * 订单结束时间
	 */
	public Date endTime;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getCumulativeTime() {
		return cumulativeTime;
	}

	public void setCumulativeTime(String cumulativeTime) {
		this.cumulativeTime = cumulativeTime;
	}

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

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getPassengerPhone() {
		return passengerPhone;
	}

	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
