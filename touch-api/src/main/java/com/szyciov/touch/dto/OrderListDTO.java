package com.szyciov.touch.dto;

public class OrderListDTO {
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 用车业务，1-出租车，2-约车，3-接机，4-送机
	 */
	private String useType;
	
	/**
	 * 订单状态
	 */
	private Integer orderState;
	
	/**
	 * 用车时间
	 */
	private String useTime;
	
	/**
	 * 订单金额
	 */
	private Double realAmount;
	
	/**
	 * 优惠金额
	 */
	private Double couponAmount;
	
	/**
	 * 实付金额
	 */
	private Double actuallyAmount;
	
	/**
	 * 行驶里程
	 */
	private Double realDistance;
	
	/**
	 * 行驶时长
	 */
	private Integer realDuration;
	
	/**
	 * 下单人标识ID
	 */
	private String orderPersonId;
	
	/**
	 * 乘车人手机号
	 */
	private String passengerPhone;
	
	/**
	 * 司机姓名
	 */
	private String driverNickName;
	
	/**
	 * 司机手机号
	 */
	private String driverPhone;
	
	/**
	 * 服务开始时间
	 */
	private String driverStartTime;
	
	/**
	 * 服务结束时间
	 */
	private String driverEndTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Double getActuallyAmount() {
		return actuallyAmount;
	}

	public void setActuallyAmount(Double actuallyAmount) {
		this.actuallyAmount = actuallyAmount;
	}

	public Double getRealDistance() {
		return realDistance;
	}

	public void setRealDistance(Double realDistance) {
		this.realDistance = realDistance;
	}

	public Integer getRealDuration() {
		return realDuration;
	}

	public void setRealDuration(Integer realDuration) {
		this.realDuration = realDuration;
	}

	public String getOrderPersonId() {
		return orderPersonId;
	}

	public void setOrderPersonId(String orderPersonId) {
		this.orderPersonId = orderPersonId;
	}

	public String getPassengerPhone() {
		return passengerPhone;
	}

	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}

	public String getDriverNickName() {
		return driverNickName;
	}

	public void setDriverNickName(String driverNickName) {
		this.driverNickName = driverNickName;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverStartTime() {
		return driverStartTime;
	}

	public void setDriverStartTime(String driverStartTime) {
		this.driverStartTime = driverStartTime;
	}

	public String getDriverEndTime() {
		return driverEndTime;
	}

	public void setDriverEndTime(String driverEndTime) {
		this.driverEndTime = driverEndTime;
	}
}
