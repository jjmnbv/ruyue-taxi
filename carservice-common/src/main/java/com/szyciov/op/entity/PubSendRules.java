package com.szyciov.op.entity;

import java.util.Date;

/**
 * 网约车派单规则
 * @author xuxxtr
 *
 */
public class PubSendRules {

	private String id;
	
	/**
	 * 所属租赁公司
	 */
	private String leasesCompanyId;
	
	/**
	 * 城市id
	 */
	private String city;
	
	
	/**
	 * 所属城市
	 */
	private String cityName;
	
	/**
	 * 城市简称
	 */
	private String shortName;
	
	/**
	 * 车型升级
	 * 0-当前级别    1-升级级别
	 */
	private Integer vehicleUpgrade;
	
	/**
	 * 用车类型(0-预约用车,1-即刻用车)
	 * 0-预约用车,1-即刻用车
	 */
	private Integer useType;
	
	/**
	 * 派单方式(0-强派,1-抢派,2-抢单，3-纯人工)
	 */
	private Integer sendType;
	
	/**
	 * 派单模式(0-系统,1-系统+人工)
	 */
	private Integer sendModel;
	
	/**
	 * 系统派单时限
	 */
	private Integer systemSendInterval;
	
	/**
	 * 司机抢单时限
	 */
	private Integer driverSendInterval;
	
	/**
	 * 特殊派单时限
	 */
	private Integer specialInterval;
	
	/**
	 * 人工派单时限
	 */
	private Integer personSendInterval;
	
	/**
	 * 初始化半径
	 */
	private Double initSendRadius;
	
	/**
	 * 最大半径
	 */
	private Double maxSendRadius;
	
	/**
	 * 半径递增比
	 */
	private Integer increRatio;
	
	/**
	 * 推送数量限制
	 * 0-不限制  1-限制
	 */
	private String pushNumLimit;
	
	/**
	 * 推送数量
	 */
	private Integer pushNum;
	
	/**
	 * 推送限制
	 * 0-存在抢单弹窗不推单  1-..推单
	 */
	private String pushLimit;

	/**
	 * 约车时限
	 */
	private Integer carsInterval;
	
	/**
	 * 所属平台
	 * 0-运管端  1-租赁端
	 */
	private String platformType;
	
	/**
	 * 车型
	 * 0-网约车  1-出租车
	 */
	private Integer vehicleType;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新人
	 */
	private String updater;
	
	/**
	 * 数据状态
	 */
	private Integer status;
	
	/**
	 * 规则状态
	 * 0-启用，1-禁用
	 */
	private String rulesState;

	public PubSendRules() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getVehicleUpgrade() {
		return vehicleUpgrade;
	}

	public void setVehicleUpgrade(Integer vehicleUpgrade) {
		this.vehicleUpgrade = vehicleUpgrade;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getSendModel() {
		return sendModel;
	}

	public void setSendModel(Integer sendModel) {
		this.sendModel = sendModel;
	}

	public Integer getSystemSendInterval() {
		return systemSendInterval;
	}

	public void setSystemSendInterval(Integer systemSendInterval) {
		this.systemSendInterval = systemSendInterval;
	}

	public Integer getDriverSendInterval() {
		return driverSendInterval;
	}

	public void setDriverSendInterval(Integer driverSendInterval) {
		this.driverSendInterval = driverSendInterval;
	}

	public Integer getSpecialInterval() {
		return specialInterval;
	}

	public void setSpecialInterval(Integer specialInterval) {
		this.specialInterval = specialInterval;
	}

	public Integer getPersonSendInterval() {
		return personSendInterval;
	}

	public void setPersonSendInterval(Integer personSendInterval) {
		this.personSendInterval = personSendInterval;
	}

	public Double getInitSendRadius() {
		return initSendRadius;
	}

	public void setInitSendRadius(Double initSendRadius) {
		this.initSendRadius = initSendRadius;
	}

	public Double getMaxSendRadius() {
		return maxSendRadius;
	}

	public void setMaxSendRadius(Double maxSendRadius) {
		this.maxSendRadius = maxSendRadius;
	}

	public Integer getIncreRatio() {
		return increRatio;
	}

	public void setIncreRatio(Integer increRatio) {
		this.increRatio = increRatio;
	}

	public String getPushNumLimit() {
		return pushNumLimit;
	}

	public void setPushNumLimit(String pushNumLimit) {
		this.pushNumLimit = pushNumLimit;
	}

	public Integer getPushNum() {
		return pushNum;
	}

	public void setPushNum(Integer pushNum) {
		this.pushNum = pushNum;
	}

	public String getPushLimit() {
		return pushLimit;
	}

	public void setPushLimit(String pushLimit) {
		this.pushLimit = pushLimit;
	}

	public Integer getCarsInterval() {
		return carsInterval;
	}

	public void setCarsInterval(Integer carsInterval) {
		this.carsInterval = carsInterval;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRulesState() {
		return rulesState;
	}

	public void setRulesState(String rulesState) {
		this.rulesState = rulesState;
	}
}
