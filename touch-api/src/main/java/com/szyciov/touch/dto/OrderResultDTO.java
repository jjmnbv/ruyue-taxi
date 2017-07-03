package com.szyciov.touch.dto;

/**
 * 订单实体类
 * @author chen
 *
 */
public class OrderResultDTO {
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 订单状态
	 */
	private String orderState;

	/**
	 * 下单人手机号
	 */
	private String orderPersonPhone;
	
	/**
	 * 乘车人手机号
	 */
	private String passengerPhone;
	
	/**
	 * 出发地经度
	 */
	private Double departureLon;
	
	/**
	 * 出发地纬度
	 */
	private Double departureLat;
	
	/**
	 * 出发地名称
	 */
	private String departureName;
	
	/**
	 * 出发地地址
	 */
	private String departureAddress;
	
	/**
	 * 目的地经度
	 */
	private Double destinationLon;
	
	/**
	 * 目的地纬度
	 */
	private Double destinationLat;
	
	/**
	 * 目的地名称
	 */
	private String destinationName;
	
	/**
	 * 目的地地址
	 */
	private String destinationAddress;
	
	/**
	 * 用车备注
	 */
	private String useRmark;
	
	/**
	 * 下单时间
	 */
	private String orderTime;
	
	/**
	 * 用车时间
	 */
	private String useTime;
	
	/**
	 * 取消时间
	 */
	private String cancelTime;
	
	/**
	 * 预估费用
	 */
	private Double estimateAmount;
	
	/**
	 * 预估里程
	 */
	private Double estimateDistance;
	
	/**
	 * 预估时长
	 */
	private Integer estimateDuration;
	
	/**
	 * 实际上车点名称
	 */
	private String realDepartureName;
	
	/**
	 * 实际上车点地址
	 */
	private String realDepartureAddress;
	
	/**
	 * 实际上车点经度
	 */
	private Double realDepartureLon;
	
	/**
	 * 实际上车点纬度
	 */
	private Double realDepartureLat;
	
	/**
	 * 实际下车点名称
	 */
	private String realDestinationName;
	
	/**
	 * 实际下车点地址
	 */
	private String realDestinationAddress;
	
	/**
	 * 实际下车点经度
	 */
	private Double realDestinationLon;
	
	/**
	 * 实际下车点纬度
	 */
	private Double realDestinationLat;
	
	/**
	 * 司机出发时间
	 */
	private String driverDepartureTime;
	
	/**
	 * 司机抵达时间
	 */
	private String driverArrivalTime;
	
	/**
	 * 开始服务时间
	 */
	private String driverStartTime;
	
	/**
	 * 结束行程时间
	 */
	private String driverEndTime;
	
	/**
	 * 行驶里程
	 */
	private Double realDistance;
	
	/**
	 * 行驶时长
	 */
	private Integer realDuration;
	
	/**
	 * 下单车型名称
	 */
	private String modelsName;
	
	/**
	 * 服务车型名称
	 */
	private String serviceModelsName;
	
	/**
	 * 计价车型名称
	 */
	private String priceModelsName;
	
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
	 * 支付时间
	 */
	private String payTime;
	
	/**
	 * 司机称呼
	 */
	private String driverNickName;
	
	/**
	 * 司机头像
	 */
	private String driverLogo;
	
	/**
	 * 司机手机号
	 */
	private String driverPhone;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 司机车型
	 */
	private String driverModelsName;

	/**
	 * 司机车牌
	 */
	private String driverPlateNo;
	
	/**
	 * 司机服务单数
	 */
	private Integer driverServiceOrderNum;
	
	/**
	 * 司机星级
	 */
	private String driverGrade;
	
	/**
	 * 司机实时经度
	 */
	private Double driverLon;
	
	/**
	 * 司机实时纬度
	 */
	private Double driverLat;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

    public String getOrderPersonPhone() {
        return orderPersonPhone;
    }

    public void setOrderPersonPhone(String orderPersonPhone) {
        this.orderPersonPhone = orderPersonPhone;
    }

    public String getPassengerPhone() {
		return passengerPhone;
	}

	public void setPassengerPhone(String passengerPhone) {
		this.passengerPhone = passengerPhone;
	}

	public Double getDepartureLon() {
		return departureLon;
	}

	public void setDepartureLon(Double departureLon) {
		this.departureLon = departureLon;
	}

	public Double getDepartureLat() {
		return departureLat;
	}

	public void setDepartureLat(Double departureLat) {
		this.departureLat = departureLat;
	}

	public String getDepartureName() {
		return departureName;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public String getDepartureAddress() {
		return departureAddress;
	}

	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}

	public Double getDestinationLon() {
		return destinationLon;
	}

	public void setDestinationLon(Double destinationLon) {
		this.destinationLon = destinationLon;
	}

	public Double getDestinationLat() {
		return destinationLat;
	}

	public void setDestinationLat(Double destinationLat) {
		this.destinationLat = destinationLat;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getUseRmark() {
		return useRmark;
	}

	public void setUseRmark(String useRmark) {
		this.useRmark = useRmark;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Double getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public Double getEstimateDistance() {
		return estimateDistance;
	}

	public void setEstimateDistance(Double estimateDistance) {
		this.estimateDistance = estimateDistance;
	}

	public Integer getEstimateDuration() {
		return estimateDuration;
	}

	public void setEstimateDuration(Integer estimateDuration) {
		this.estimateDuration = estimateDuration;
	}

	public String getRealDepartureName() {
		return realDepartureName;
	}

	public void setRealDepartureName(String realDepartureName) {
		this.realDepartureName = realDepartureName;
	}

	public String getRealDepartureAddress() {
		return realDepartureAddress;
	}

	public void setRealDepartureAddress(String realDepartureAddress) {
		this.realDepartureAddress = realDepartureAddress;
	}

	public Double getRealDepartureLon() {
		return realDepartureLon;
	}

	public void setRealDepartureLon(Double realDepartureLon) {
		this.realDepartureLon = realDepartureLon;
	}

	public Double getRealDepartureLat() {
		return realDepartureLat;
	}

	public void setRealDepartureLat(Double realDepartureLat) {
		this.realDepartureLat = realDepartureLat;
	}

	public String getRealDestinationName() {
		return realDestinationName;
	}

	public void setRealDestinationName(String realDestinationName) {
		this.realDestinationName = realDestinationName;
	}

	public String getRealDestinationAddress() {
		return realDestinationAddress;
	}

	public void setRealDestinationAddress(String realDestinationAddress) {
		this.realDestinationAddress = realDestinationAddress;
	}

	public Double getRealDestinationLon() {
		return realDestinationLon;
	}

	public void setRealDestinationLon(Double realDestinationLon) {
		this.realDestinationLon = realDestinationLon;
	}

	public Double getRealDestinationLat() {
		return realDestinationLat;
	}

	public void setRealDestinationLat(Double realDestinationLat) {
		this.realDestinationLat = realDestinationLat;
	}

	public String getDriverDepartureTime() {
		return driverDepartureTime;
	}

	public void setDriverDepartureTime(String driverDepartureTime) {
		this.driverDepartureTime = driverDepartureTime;
	}

	public String getDriverArrivalTime() {
		return driverArrivalTime;
	}

	public void setDriverArrivalTime(String driverArrivalTime) {
		this.driverArrivalTime = driverArrivalTime;
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

	public String getModelsName() {
		return modelsName;
	}

	public void setModelsName(String modelsName) {
		this.modelsName = modelsName;
	}

	public String getServiceModelsName() {
		return serviceModelsName;
	}

	public void setServiceModelsName(String serviceModelsName) {
		this.serviceModelsName = serviceModelsName;
	}

	public String getPriceModelsName() {
		return priceModelsName;
	}

	public void setPriceModelsName(String priceModelsName) {
		this.priceModelsName = priceModelsName;
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

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getDriverNickName() {
		return driverNickName;
	}

	public void setDriverNickName(String driverNickName) {
		this.driverNickName = driverNickName;
	}

	public String getDriverLogo() {
		return driverLogo;
	}

	public void setDriverLogo(String driverLogo) {
		this.driverLogo = driverLogo;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverModelsName() {
		return driverModelsName;
	}

	public void setDriverModelsName(String driverModelsName) {
		this.driverModelsName = driverModelsName;
	}

	public String getDriverPlateNo() {
		return driverPlateNo;
	}

	public void setDriverPlateNo(String driverPlateNo) {
		this.driverPlateNo = driverPlateNo;
	}

	public Integer getDriverServiceOrderNum() {
		return driverServiceOrderNum;
	}

	public void setDriverServiceOrderNum(Integer driverServiceOrderNum) {
		this.driverServiceOrderNum = driverServiceOrderNum;
	}

	public String getDriverGrade() {
		return driverGrade;
	}

	public void setDriverGrade(String driverGrade) {
		this.driverGrade = driverGrade;
	}

	public Double getDriverLon() {
		return driverLon;
	}

	public void setDriverLon(Double driverLon) {
		this.driverLon = driverLon;
	}

	public Double getDriverLat() {
		return driverLat;
	}

	public void setDriverLat(Double driverLat) {
		this.driverLat = driverLat;
	}
	
	
}
