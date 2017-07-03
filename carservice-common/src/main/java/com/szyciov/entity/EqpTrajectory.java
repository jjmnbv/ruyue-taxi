package com.szyciov.entity;

import java.math.BigDecimal;

/**
 * 设备轨迹对象 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月23日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 * 袁金林 20170426
 * 增加trackId;
 */
public class EqpTrajectory {
	private String trackId;
	/**
	 * 定位状态 对应值：1_是;2_否
	 */
	private String locState;
	/**
	 * 方向 (向北，东北，向东，东南，向南，西南，向西，西北)
	 */
	private String direction;
	/**
	 * 速度
	 */
	private BigDecimal speed;
	/**
	 * 定位时间
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

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
}
