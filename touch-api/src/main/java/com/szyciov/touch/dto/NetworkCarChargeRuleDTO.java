package com.szyciov.touch.dto;

/**
 * 网约车计费规则实体
 * @author chen
 *
 */
public class NetworkCarChargeRuleDTO {
	
	/**
	 * 起步价
	 */
	private Double startPrice;
	
	/**
	 * 起始里程
	 */
	private Double startMileage;
	
	/**
	 * 里程费价
	 */
	private Double mileagePrice;
	
	/**
	 * 时长费价
	 */
	private Double timePrice;
	
	/**
	 * 标准里程
	 */
	private Double standardMileage;
	
	/**
	 * 远途费价
	 */
	private Double longDistancePrice;
	
	/**
	 * 夜间费起征时间
	 */
	private String nightStartTime;
	
	/**
	 * 夜间费止征时间
	 */
	private String nightEndTime;
	
	/**
	 * 夜间费价
	 */
	private Double nightPrice;
	
	/**
	 * 低速标准车速
	 */
	private Double lowSpeed;
	
	/**
	 * 低速费价
	 */
	private Double lowSpeedPrice;
	
	/**
	 * 最低消费
	 */
	private Double lowestFee;

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(Double startMileage) {
		this.startMileage = startMileage;
	}

	public Double getMileagePrice() {
		return mileagePrice;
	}

	public void setMileagePrice(Double mileagePrice) {
		this.mileagePrice = mileagePrice;
	}

	public Double getTimePrice() {
		return timePrice;
	}

	public void setTimePrice(Double timePrice) {
		this.timePrice = timePrice;
	}

	public Double getStandardMileage() {
		return standardMileage;
	}

	public void setStandardMileage(Double standardMileage) {
		this.standardMileage = standardMileage;
	}

	public Double getLongDistancePrice() {
		return longDistancePrice;
	}

	public void setLongDistancePrice(Double longDistancePrice) {
		this.longDistancePrice = longDistancePrice;
	}

	public String getNightStartTime() {
		return nightStartTime;
	}

	public void setNightStartTime(String nightStartTime) {
		this.nightStartTime = nightStartTime;
	}

	public String getNightEndTime() {
		return nightEndTime;
	}

	public void setNightEndTime(String nightEndTime) {
		this.nightEndTime = nightEndTime;
	}

	public Double getNightPrice() {
		return nightPrice;
	}

	public void setNightPrice(Double nightPrice) {
		this.nightPrice = nightPrice;
	}

	public Double getLowSpeed() {
		return lowSpeed;
	}

	public void setLowSpeed(Double lowSpeed) {
		this.lowSpeed = lowSpeed;
	}

	public Double getLowSpeedPrice() {
		return lowSpeedPrice;
	}

	public void setLowSpeedPrice(Double lowSpeedPrice) {
		this.lowSpeedPrice = lowSpeedPrice;
	}

	public Double getLowestFee() {
		return lowestFee;
	}

	public void setLowestFee(Double lowestFee) {
		this.lowestFee = lowestFee;
	}

}
