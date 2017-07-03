package com.szyciov.entity;

import java.math.BigDecimal;

public class OneTrafficData {
	/**
	 * 行程ID
	 */
	private String trackId;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 总运行时长
	 */
	private BigDecimal totalTime;
	/**
	 * 运行时长
	 */
	private String runLength;
	/**
	 * 里程 单位：
	 */
	private Double mileage;
	/**
	 * 耗油量 单位：L
	 */
	private Double fuelConspt;
	/**
	 * 总怠速时长
	 */
	private BigDecimal totalIdleTime;
	/**
	 * 怠速时长
	 */
	private String idleTime;
	/**
	 * 怠速耗油量 单位：L
	 */
	private Double idleFuel;
	/**
	 * 百公里 油耗 (L/100km)
	 */
	private Double cumulativeOil;
	/**
	 * 平均速度 单位：km/h
	 */
	private Double avgSpeed;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRunLength() {
		return runLength;
	}

	public void setRunLength(String runLength) {
		this.runLength = runLength;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Double getFuelConspt() {
		return fuelConspt;
	}

	public void setFuelConspt(Double fuelConspt) {
		this.fuelConspt = fuelConspt;
	}

	public String getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(String idleTime) {
		this.idleTime = idleTime;
	}

	public Double getIdleFuel() {
		return idleFuel;
	}

	public void setIdleFuel(Double idleFuel) {
		this.idleFuel = idleFuel;
	}

	public Double getCumulativeOil() {
		return cumulativeOil;
	}

	public void setCumulativeOil(Double cumulativeOil) {
		this.cumulativeOil = cumulativeOil;
	}

	public Double getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public BigDecimal getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(BigDecimal totalTime) {
		this.totalTime = totalTime;
	}

	public BigDecimal getTotalIdleTime() {
		return totalIdleTime;
	}

	public void setTotalIdleTime(BigDecimal totalIdleTime) {
		this.totalIdleTime = totalIdleTime;
	}

}
