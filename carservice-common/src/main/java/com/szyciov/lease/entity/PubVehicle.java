package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 车辆表
 */
public class PubVehicle {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	/**
	 * 品牌车系
	 */
	public String vehclineId;
	/**
	 * 从字典表中读取  车牌号码(省)
	 */
	public String plateNoProvince;
	/**
	 * 从字典表中读取（与车牌号省形成多对一关联）  车牌号码(市)
	 */
	public String plateNoCity;
	/**
	 * 车牌号码
	 */
	public String plateNo;
	/**
	 * 车架号
	 */
	public String vin;
	/**
	 * 从字典表中读取 颜色
	 */
	public String color;
	/**
	 * 关联字典表获取数据  所属城市
	 */
	public String city;
	/**
	 * 荷载人数
	 */
	public Integer loads;
	
	/**
	 * 创建时间
	 */
	public Date createTime;
	/**
	 * 更新时间
	 */
	public Date updateTime;
	/**
	 * 
	 */
	public String creater;
	/**
	 * 
	 */
	public String updater;
	/**
	 * 数据状态
	 */
	public Integer status;
	/**
	 * 组合后的车牌号
	 * */
	public String showPlateNo;
	/**
	 * 品牌车系
	 * */
	public String brandCars;
	/**
	 * 服务车型
	 * */
	public String serviceCars;
	/**
	 * 司机信息
	 * */
	public String driverMessage;
	/**
	 * 当前状态
	 * */
	public String workStatus;
	
	/**
	 * 经营区域
	 * */
	public String  businessScope;
	/**
	 * 
	 * */
	public String setAsDefault;
	
	/**
	 * 经营区域 temp
	 * */
	public String  businessScopeTemp;
	
	/**
	 * 从字典表中读取  车牌号码(省) name
	 */
	public String plateNoProvinceName;
	/**
	 * 从字典表中读取（与车牌号省形成多对一关联）  车牌号码(市) name
	 */
	public String plateNoCityName;
	
	public String cityName;
	
	/**
	 * vehicleType 车辆类型
	 * */
	public String vehicleType;
	
	/**
	 * vehicleType 车辆类型  用于修改的时候   判断是出租 修改  营运状态 为维修中  且为出租车的时候
	 * */
	public String vehicleTypes;
	/**
	 * vehicleStatus 营运状态
	 * */
	public String vehicleStatus;
	/**
	 * 绑定状态
	 * **/
	public String boundState;

	/**
	 * 所属平台 0-运管端，1-租赁端
	 */
	private String platformType;
	/**
	 * 当班司机id
	 */
	private String driverId;
	/**
	 * 已绑定人数
	 */
	private Integer bindPersonNum;
	
	private String citymarkid;
	
	//服务车企
	private String belongleasecompany;
	//服务车企
	private String belongleasecompanyName;
	private String  identitytype;//特殊司机
	private String fullplateno;
	
	public String getFullplateno() {
		return fullplateno;
	}
	public void setFullplateno(String fullplateno) {
		this.fullplateno = fullplateno;
	}
	public String getIdentitytype() {
		return identitytype;
	}
	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}
	public String getBelongleasecompany() {
		return belongleasecompany;
	}
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}
	public String getBelongleasecompanyName() {
		return belongleasecompanyName;
	}
	public void setBelongleasecompanyName(String belongleasecompanyName) {
		this.belongleasecompanyName = belongleasecompanyName;
	}
	public String getCitymarkid() {
		return citymarkid;
	}
	public void setCitymarkid(String citymarkid) {
		this.citymarkid = citymarkid;
	}
	public String getVehicleTypes() {
		return vehicleTypes;
	}
	public void setVehicleTypes(String vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}
	public String getBoundState() {
		return boundState;
	}
	public void setBoundState(String boundState) {
		this.boundState = boundState;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPlateNoProvinceName() {
		return plateNoProvinceName;
	}
	public void setPlateNoProvinceName(String plateNoProvinceName) {
		this.plateNoProvinceName = plateNoProvinceName;
	}
	public String getPlateNoCityName() {
		return plateNoCityName;
	}
	public void setPlateNoCityName(String plateNoCityName) {
		this.plateNoCityName = plateNoCityName;
	}
	public String getBusinessScopeTemp() {
		return businessScopeTemp;
	}
	public void setBusinessScopeTemp(String businessScopeTemp) {
		this.businessScopeTemp = businessScopeTemp;
	}
	public String getSetAsDefault() {
		return setAsDefault;
	}
	public void setSetAsDefault(String setAsDefault) {
		this.setAsDefault = setAsDefault;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getShowPlateNo() {
		return showPlateNo;
	}
	public void setShowPlateNo(String showPlateNo) {
		this.showPlateNo = showPlateNo;
	}
	public String getBrandCars() {
		return brandCars;
	}
	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}
	public String getServiceCars() {
		return serviceCars;
	}
	public void setServiceCars(String serviceCars) {
		this.serviceCars = serviceCars;
	}
	public String getDriverMessage() {
		return driverMessage;
	}
	public void setDriverMessage(String driverMessage) {
		this.driverMessage = driverMessage;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
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
	public String getVehclineId() {
		return vehclineId;
	}
	public void setVehclineId(String vehclineId) {
		this.vehclineId = vehclineId;
	}
	public String getPlateNoProvince() {
		return plateNoProvince;
	}
	public void setPlateNoProvince(String plateNoProvince) {
		this.plateNoProvince = plateNoProvince;
	}
	public String getPlateNoCity() {
		return plateNoCity;
	}
	public void setPlateNoCity(String plateNoCity) {
		this.plateNoCity = plateNoCity;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getLoads() {
		return loads;
	}
	public void setLoads(Integer loads) {
		this.loads = loads;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public Integer getBindPersonNum() {
		return bindPersonNum;
	}

	public void setBindPersonNum(Integer bindPersonNum) {
		this.bindPersonNum = bindPersonNum;
	}
}
