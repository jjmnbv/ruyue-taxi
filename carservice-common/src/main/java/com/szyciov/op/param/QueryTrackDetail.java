package com.szyciov.op.param;

import java.math.BigDecimal;

/**
 * 单行程详情 返回实体 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月27日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryTrackDetail {
	/**
	 * 车牌
	 */
	private String plate;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 行程里程
	 */
	private BigDecimal trackMileage;
	/**
	 * 最高速度
	 */
	private BigDecimal maxSpeed;
	/**
	 * 行程油耗
	 */
	private BigDecimal strokeFuel;
	/**
	 * 行程平均速度
	 */
	private BigDecimal avgTrackSpeed;
	/**
	 * 行程开始时间
	 */
	private String trackStartTime;
	/**
	 * 行程结束时间
	 */
	private String strokeEndTime;
	/**
	 * 行程开始经度
	 */
	private BigDecimal startLongitude;
	/**
	 * 行程开始纬度
	 */
	private BigDecimal startLatitude;
	/**
	 * 行程开始地址
	 */
	private String trackStartAddress;
	/**
	 * 行程结束经度
	 */
	private BigDecimal endLongitude;
	/**
	 * 行程结束纬度
	 */
	private BigDecimal endLatitude;
	/**
	 * 行程结束地址
	 */
	private String endAddress;
	/**
	 * 行程总行驶时间(s)
	 */
	private Integer totalTime;
	/**
	 * 行程总行驶时间(s)
	 */
	private String totalTimeText;
	/**
	 * 行程百公里油耗(L)
	 */
	private BigDecimal fuelConsumption;
	
	/**
	 * 行程累计耗油(ml)
	 */
	private Integer cumulativeOil;
	/**
	 * 怠速时长(s)
	 */
	private Integer idleTime;
	/**
	 * 怠速时长(s)
	 */
	private String idleTimeText;
	/**
	 * 分段速度里程(0-20)
	 */
	private BigDecimal mileage0020;
	/**
	 * 分段速度里程(20-40)
	 */
	private BigDecimal mileage2040;
	/**
	 * 分段速度里程(40-60)
	 */
	private BigDecimal mileage4060;
	/**
	 * 分段速度里程(60-90)
	 */
	private BigDecimal mileage6090;
	/**
	 * 分段速度里程(90-120)
	 */
	private BigDecimal mileage90120;
	/**
	 * 分段速度里程(120以上)
	 */
	private BigDecimal mileage120;
	
	/**
	 * 行程怠速耗油(ml)
	 */
	private BigDecimal idleFuel;
	/**
	 * 行驶时长(s)
	 */
	private Integer runTime;
	/**
	 * 行驶时长(s)
	 */
	private String runTimeText;
	/**
	 * 服务车企
	 */
	private String departmentName;
	/**
	 * 超速报警次数
	 */
	private Integer speedingCount;
	/**
	 * 怠速报警次数
	 */
	private Integer idlingCount;
	/**
	 * 碰撞报警次数
	 */
	private Integer collisionCount;
	/**
	 * 断电报警次数
	 */
	private Integer powerfailureCount;
	/**
	 * 水温报警次数
	 */
	private Integer watertempCount;
	/**
	 * 时间栅栏次数
	 */
	private Integer timefenceCount;
	/**
	 * 区域栅栏次数
	 */
	private Integer regionalfenceCount;
	/**
	 * 电子栅栏次数
	 */
	private Integer elefenceCount;

	/**
	 * 当前状态
	 */
	private Integer workStatus;
	/**
	 * 当前状态文本
	 */
	private String workStatusText;
	/**
	 * 当前转速
	 */
	private BigDecimal rotation;
	/**
	 * 当前速度
	 */
	private BigDecimal speed;
	/**
	 * 当前温度
	 */
	private BigDecimal temperatrue;
	/**
	 * 当前电压
	 */
	private BigDecimal voltage;
	/**
	 * 当前里程
	 */
	private BigDecimal totalMileage;
	/**
	 * 当前错误代码
	 */
	private String faultCode;

    /**
     * 更新时间
	 */
	private String updateTime;

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkStatusText() {
		return workStatusText;
	}

	public void setWorkStatusText(String workStatusText) {
		this.workStatusText = workStatusText;
	}

	public BigDecimal getRotation() {
		return rotation;
	}

	public void setRotation(BigDecimal rotation) {
		this.rotation = rotation;
	}

	public BigDecimal getSpeed() {
		return speed;
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}

	public BigDecimal getTemperatrue() {
		return temperatrue;
	}

	public void setTemperatrue(BigDecimal temperatrue) {
		this.temperatrue = temperatrue;
	}

	public BigDecimal getVoltage() {
		return voltage;
	}

	public void setVoltage(BigDecimal voltage) {
		this.voltage = voltage;
	}

	public BigDecimal getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(BigDecimal totalMileage) {
		this.totalMileage = totalMileage;
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public Integer getSpeedingCount() {
		return speedingCount;
	}

	public void setSpeedingCount(Integer speedingCount) {
		this.speedingCount = speedingCount;
	}

	public Integer getIdlingCount() {
		return idlingCount;
	}

	public void setIdlingCount(Integer idlingCount) {
		this.idlingCount = idlingCount;
	}

	public Integer getCollisionCount() {
		return collisionCount;
	}

	public void setCollisionCount(Integer collisionCount) {
		this.collisionCount = collisionCount;
	}

	public Integer getPowerfailureCount() {
		return powerfailureCount;
	}

	public void setPowerfailureCount(Integer powerfailureCount) {
		this.powerfailureCount = powerfailureCount;
	}

	public Integer getWatertempCount() {
		return watertempCount;
	}

	public void setWatertempCount(Integer watertempCount) {
		this.watertempCount = watertempCount;
	}

	public Integer getTimefenceCount() {
		return timefenceCount;
	}

	public void setTimefenceCount(Integer timefenceCount) {
		this.timefenceCount = timefenceCount;
	}

	public Integer getRegionalfenceCount() {
		return regionalfenceCount;
	}

	public void setRegionalfenceCount(Integer regionalfenceCount) {
		this.regionalfenceCount = regionalfenceCount;
	}

	public Integer getElefenceCount() {
		return elefenceCount;
	}

	public void setElefenceCount(Integer elefenceCount) {
		this.elefenceCount = elefenceCount;
	}

	public BigDecimal getTrackMileage() {
		return trackMileage;
	}

	public void setTrackMileage(BigDecimal trackMileage) {
		this.trackMileage = trackMileage;
	}

	public BigDecimal getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(BigDecimal maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public BigDecimal getStrokeFuel() {
		return strokeFuel;
	}

	public void setStrokeFuel(BigDecimal strokeFuel) {
		this.strokeFuel = strokeFuel;
	}

	public BigDecimal getAvgTrackSpeed() {
		return avgTrackSpeed;
	}

	public void setAvgTrackSpeed(BigDecimal avgTrackSpeed) {
		this.avgTrackSpeed = avgTrackSpeed;
	}

	public String getTrackStartTime() {
		return trackStartTime;
	}

	public void setTrackStartTime(String trackStartTime) {
		this.trackStartTime = trackStartTime;
	}

	public String getStrokeEndTime() {
		return strokeEndTime;
	}

	public void setStrokeEndTime(String strokeEndTime) {
		this.strokeEndTime = strokeEndTime;
	}

	public BigDecimal getStartLongitude() {
		return startLongitude;
	}

	public void setStartLongitude(BigDecimal startLongitude) {
		this.startLongitude = startLongitude;
	}

	public BigDecimal getStartLatitude() {
		return startLatitude;
	}

	public void setStartLatitude(BigDecimal startLatitude) {
		this.startLatitude = startLatitude;
	}

	public String getTrackStartAddress() {
		return trackStartAddress;
	}

	public void setTrackStartAddress(String trackStartAddress) {
		this.trackStartAddress = trackStartAddress;
	}

	public BigDecimal getEndLongitude() {
		return endLongitude;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public BigDecimal getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(BigDecimal fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public Integer getCumulativeOil() {
		return cumulativeOil;
	}

	public void setCumulativeOil(Integer cumulativeOil) {
		this.cumulativeOil = cumulativeOil;
	}

	public Integer getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}

	public BigDecimal getMileage0020() {
		return mileage0020;
	}

	public String getTotalTimeText() {
		return totalTimeText;
	}

	public void setTotalTimeText(String totalTimeText) {
		this.totalTimeText = totalTimeText;
	}

	public String getIdleTimeText() {
		return idleTimeText;
	}

	public void setIdleTimeText(String idleTimeText) {
		this.idleTimeText = idleTimeText;
	}

	public String getRunTimeText() {
		return runTimeText;
	}

	public void setRunTimeText(String runTimeText) {
		this.runTimeText = runTimeText;
	}

	public void setMileage0020(BigDecimal mileage0020) {
		this.mileage0020 = mileage0020;
	}

	public BigDecimal getMileage2040() {
		return mileage2040;
	}

	public void setMileage2040(BigDecimal mileage2040) {
		this.mileage2040 = mileage2040;
	}

	public BigDecimal getMileage4060() {
		return mileage4060;
	}

	public void setMileage4060(BigDecimal mileage4060) {
		this.mileage4060 = mileage4060;
	}

	public BigDecimal getMileage6090() {
		return mileage6090;
	}

	public void setMileage6090(BigDecimal mileage6090) {
		this.mileage6090 = mileage6090;
	}

	public BigDecimal getMileage90120() {
		return mileage90120;
	}

	public void setMileage90120(BigDecimal mileage90120) {
		this.mileage90120 = mileage90120;
	}

	public BigDecimal getMileage120() {
		return mileage120;
	}

	public void setMileage120(BigDecimal mileage120) {
		this.mileage120 = mileage120;
	}

	public BigDecimal getIdleFuel() {
		return idleFuel;
	}

	public void setIdleFuel(BigDecimal idleFuel) {
		this.idleFuel = idleFuel;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public void setEndLongitude(BigDecimal endLongitude) {
		this.endLongitude = endLongitude;
	}

	public BigDecimal getEndLatitude() {
		return endLatitude;
	}

	public void setEndLatitude(BigDecimal endLatitude) {
		this.endLatitude = endLatitude;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
