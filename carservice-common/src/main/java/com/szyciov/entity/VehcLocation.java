package com.szyciov.entity;

import java.math.BigDecimal;

/**
 * 车辆位置对应实体对象 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class VehcLocation {
	/**
	 * 行程Id
	 */
	private String trackId;
	/**
	 * 设备Id
	 */
	private String eqpId;
	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 所属部门
	 */
	private String department;
	/**
	 * 设备工作状态
	 */
	private Integer workStatus;
	/**
	 * 设备工作状态文本
	 */
	private String workStatusText;

	/**
	 * 设备状态时长
	 */
	private String longTime;
	/**
	 * 定位状态
	 */
	private String locState;
	/**
	 * 方向
	 */
	private String direction;
	/**
	 * 速度
	 */
	private BigDecimal speed;
	/**
	 * 定位时设备的时间
	 */
	private String locTime;
	/**
	 * 经度
	 */
	private BigDecimal longitude;
	/**
	 * 纬度
	 */
	private BigDecimal latitude;
	/**
	 * 纠偏后经度
	 */
	private BigDecimal longitudeOffSet;
	/**
	 * 就偏后纬度
	 */
	private BigDecimal latitudeOffSet;
	
	private String startTime;
	
	private BigDecimal cumulativeOil;
	
	private BigDecimal totalMileage;

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkStatusText() {
		return workStatusText;
	}

	public void setWorkStatusText(String workStatusText) {
		this.workStatusText = workStatusText;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public String getLongTime() {
		return longTime;
	}

	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}

	public String getLocState() {
		return locState;
	}

	public void setLocState(String locState) {
		this.locState = locState;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BigDecimal getSpeed() {
		return speed;
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}

	public String getLocTime() {
		return locTime;
	}

	public void setLocTime(String locTime) {
		this.locTime = locTime;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitudeOffSet() {
		return longitudeOffSet;
	}

	public void setLongitudeOffSet(BigDecimal longitudeOffSet) {
		this.longitudeOffSet = longitudeOffSet;
	}

	public BigDecimal getLatitudeOffSet() {
		return latitudeOffSet;
	}

	public void setLatitudeOffSet(BigDecimal latitudeOffSet) {
		this.latitudeOffSet = latitudeOffSet;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getCumulativeOil() {
		return cumulativeOil;
	}

	public void setCumulativeOil(BigDecimal cumulativeOil) {
		this.cumulativeOil = cumulativeOil;
	}

	public BigDecimal getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(BigDecimal totalMileage) {
		this.totalMileage = totalMileage;
	}
}
