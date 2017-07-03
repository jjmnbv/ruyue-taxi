package com.szyciov.op.param;

import java.math.BigDecimal;

/**
 * 设备行程数据列表 <一句话功能简述> <功能详细描述>
 * 
 * @author DELL
 * @version [版本号, 2017年3月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryTrackData {
	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 所属部门
	 */
	private String department;
	/**
	 * 总里程 单位：km
	 */
	private Integer totalMileage;
	/**
	 * 总油耗量 单位：L
	 */
	private Integer totalFuel;
	/**
	 * 总行程数 单位：次
	 */
	private Integer numberOfDays;
	/**
	 * 总行程时长
	 */
	private BigDecimal runTimeSum;
	/**
	 * 总行程时长
	 */
	private String totalTrackTime;
	/**
	 * 总怠速时长
	 */
	private BigDecimal idleTimeSum;
	/**
	 * 总怠速时长
	 */
	private String totalIdleTime;
	/**
	 * 最后行驶时间
	 */
	private String finalTrackTime;
	
	private String ZDY;
	
	

	public String getZDY() {
		return ZDY;
	}

	public void setZDY(String zDY) {
		ZDY = zDY;
	}
	
	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
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

	public Integer getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(Integer totalMileage) {
		this.totalMileage = totalMileage;
	}

	public Integer getTotalFuel() {
		return totalFuel;
	}

	public void setTotalFuel(Integer totalFuel) {
		this.totalFuel = totalFuel;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getTotalTrackTime() {
		return totalTrackTime;
	}

	public void setTotalTrackTime(String totalTrackTime) {
		this.totalTrackTime = totalTrackTime;
	}

	public String getTotalIdleTime() {
		return totalIdleTime;
	}

	public void setTotalIdleTime(String totalIdleTime) {
		this.totalIdleTime = totalIdleTime;
	}

	public String getFinalTrackTime() {
		return finalTrackTime;
	}

	public void setFinalTrackTime(String finalTrackTime) {
		this.finalTrackTime = finalTrackTime;
	}

	public BigDecimal getRunTimeSum() {
		return runTimeSum;
	}

	public void setRunTimeSum(BigDecimal runTimeSum) {
		this.runTimeSum = runTimeSum;
	}

	public BigDecimal getIdleTimeSum() {
		return idleTimeSum;
	}

	public void setIdleTimeSum(BigDecimal idleTimeSum) {
		this.idleTimeSum = idleTimeSum;
	}

	
}
