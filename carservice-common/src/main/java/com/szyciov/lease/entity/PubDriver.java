package com.szyciov.lease.entity;


import java.util.Date;

/**
  * @ClassName PubDriver
  * @author Efy
  * @Description 司机数据表
  * @date 2016年9月29日 13:06:29
  */ 
public class PubDriver{

	public String id;
	
	public String jobNum;

	public String name;

	public String phone;
	
	public String sex;

	public String driversType;
	
	public String driversTypeName;
	
	public Date cardTime;

	public String city;

	public String driversNum;

	public String driverPhoto;

	public String idCardNum;
	
	public String idCardFront;

	public String idCardBack;

	public String workStatus;

	public Double gpsSpeed;
	
	public Double gpsDirection;
	
	public Double lng;

	public Double lat;

	public Double avgRate;

	public Integer orderCount;
	
	public Date createTime;

	public Date updateTime;
	/**
	 * 
	 */
	public String creater;
	/**
	 * 
	 */
	public String updater;
	
	public Integer status;

	public String headPortraitMin;
	
	public String headPortraitMax;
	
	public String driveSeniority;
	
	public String workStatusName;

	public String driverNamePhone;
	
	public String plateNo;

	public String brandCars;
	
	public Double driverYears;
	
	/**
	 * city name
	 * */
	public String cityName;
	/**
	 * 服务机构
	 * */
	public String serviceOrgId;
	/**
	 * 服务机构
	 * */
	public String serviceOrgName;
	/**
	 * 0-普通司机，1-特殊司机  司机身份类型
	 */
	public String idEntityType;
	
	/**
	 * 用户密码
	 */
	public String userPassword;
	
	/**
	 * 车辆信息
	 * */
	public String vehicleInfo;
	
	/**
	 * 判断绑定
	 * */
	public boolean judgeBinding;
	/**
	 * 判断 解绑记录
	 * */
	public boolean judgeUnwrapRecord;
	
	/**
	 * 0-在职，1-离职 在职状态
	 */
	public String jobStatus;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	//经营范围
	public String pubVehicleScope;
	
	//车辆id
	public String vehicleId;
	public String distributionVelId;
	public String distributionVel;
	public Date distributionVelTime;
	/**
	 * 所属平台 0-运管端，1-租赁端
	 */
	private String platformType;

	/**
	 *锁定状态 0-未锁定，1-已锁定
	 */
	private String lockStatus;
	/**
	 * 交接班状态 0-无对班,1-当班,2-歇班,3-交班中
	 */
	private String passworkStatus;

	/**
	 * 司机类型 0-网约车，1-出租车
	 */
	public String vehicleType;
	/**
	 * 绑定状态 0-未绑定,1-已绑定
	 */
	public String boundState;
	
	private String citymarkId;

	//上线时间 秒
	private Date uptime;
	//在线时长(单位：秒)
	private int onlinetime;
	//类型 0 上班 1 下班
	private String type;
	/**归属车企,从字典表中读取*/
	private String belongleasecompany;

    /**
     * gps来源
     */
    private Integer gpssource;
    /**
     * gps时间
     */
    private Date gpstime;

	//服务车企
	private String belongleasecompanyName;
	
	public String getBelongleasecompanyName() {
		return belongleasecompanyName;
	}
	public void setBelongleasecompanyName(String belongleasecompanyName) {
		this.belongleasecompanyName = belongleasecompanyName;
	}
	public String getCitymarkId() {
		return citymarkId;
	}
	public void setCitymarkId(String citymarkId) {
		this.citymarkId = citymarkId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getBoundState() {
		return boundState;
	}
	public void setBoundState(String boundState) {
		this.boundState = boundState;
	}
	public String getDistributionVelId() {
		return distributionVelId;
	}
	public void setDistributionVelId(String distributionVelId) {
		this.distributionVelId = distributionVelId;
	}
	public Date getDistributionVelTime() {
		return distributionVelTime;
	}
	public void setDistributionVelTime(Date distributionVelTime) {
		this.distributionVelTime = distributionVelTime;
	}
	public String getDistributionVel() {
		return distributionVel;
	}
	public void setDistributionVel(String distributionVel) {
		this.distributionVel = distributionVel;
	}
	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getPubVehicleScope() {
		return pubVehicleScope;
	}

	public void setPubVehicleScope(String pubVehicleScope) {
		this.pubVehicleScope = pubVehicleScope;
	}

	/**  
	 * 获取所属租赁公司  
	 * @return leasesCompanyId 所属租赁公司  
	 */
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	/**  
	 * 设置所属租赁公司  
	 * @param leasesCompanyId 所属租赁公司  
	 */
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	


	/**  
	 * 获取0-普通司机，1-特殊司机司机身份类型  
	 * @return idEntityType 0-普通司机，1-特殊司机司机身份类型  
	 */
	public String getIdEntityType() {
		return idEntityType;
	}
	

	/**  
	 * 设置0-普通司机，1-特殊司机司机身份类型  
	 * @param idEntityType 0-普通司机，1-特殊司机司机身份类型  
	 */
	public void setIdEntityType(String idEntityType) {
		this.idEntityType = idEntityType;
	}
	

	/**  
	 * 获取用户密码  
	 * @return userPassword 用户密码  
	 */
	public String getUserPassword() {
		return userPassword;
	}
	

	/**  
	 * 设置用户密码  
	 * @param userPassword 用户密码  
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	

	/**  
	 * 获取车辆信息  
	 * @return vehicleInfo 车辆信息  
	 */
	public String getVehicleInfo() {
		return vehicleInfo;
	}
	

	/**  
	 * 设置车辆信息  
	 * @param vehicleInfo 车辆信息  
	 */
	public void setVehicleInfo(String vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	

	/**  
	 * 获取判断绑定  
	 * @return judgeBinding 判断绑定  
	 */
	public boolean isJudgeBinding() {
		return judgeBinding;
	}
	

	/**  
	 * 设置判断绑定  
	 * @param judgeBinding 判断绑定  
	 */
	public void setJudgeBinding(boolean judgeBinding) {
		this.judgeBinding = judgeBinding;
	}
	

	/**  
	 * 获取判断解绑记录  
	 * @return judgeUnwrapRecord 判断解绑记录  
	 */
	public boolean isJudgeUnwrapRecord() {
		return judgeUnwrapRecord;
	}
	

	/**  
	 * 设置判断解绑记录  
	 * @param judgeUnwrapRecord 判断解绑记录  
	 */
	public void setJudgeUnwrapRecord(boolean judgeUnwrapRecord) {
		this.judgeUnwrapRecord = judgeUnwrapRecord;
	}
	

	/**  
	 * 获取0-在职，1-离职在职状态  
	 * @return jobStatus 0-在职，1-离职在职状态  
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	

	/**  
	 * 设置0-在职，1-离职在职状态  
	 * @param jobStatus 0-在职，1-离职在职状态  
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	

	/**  
	 * 获取cityname  
	 * @return cityName cityname  
	 */
	public String getCityName() {
		return cityName;
	}

	/**  
	 * 设置cityname  
	 * @param cityName cityname  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	

	/**  
	 * 获取服务机构  
	 * @return serviceOrgId 服务机构  
	 */
	public String getServiceOrgId() {
		return serviceOrgId;
	}

	/**  
	 * 设置服务机构  
	 * @param serviceOrgId 服务机构  
	 */
	public void setServiceOrgId(String serviceOrgId) {
		this.serviceOrgId = serviceOrgId;
	}
	

	/**  
	 * 获取服务机构  
	 * @return serviceOrgName 服务机构  
	 */
	public String getServiceOrgName() {
		return serviceOrgName;
	}
	

	/**  
	 * 设置服务机构  
	 * @param serviceOrgName 服务机构  
	 */
	public void setServiceOrgName(String serviceOrgName) {
		this.serviceOrgName = serviceOrgName;
	}
	

	/**
	  *设置主键
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取主键
	  */
	public String getId(){
		return id;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDriversType() {
		return driversType;
	}

	public void setDriversType(String driversType) {
		this.driversType = driversType;
	}

	public String getDriversTypeName() {
		return driversTypeName;
	}

	public void setDriversTypeName(String driversTypeName) {
		this.driversTypeName = driversTypeName;
	}

	public Date getCardTime() {
		return cardTime;
	}

	public void setCardTime(Date cardTime) {
		this.cardTime = cardTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDriversNum() {
		return driversNum;
	}

	public void setDriversNum(String driversNum) {
		this.driversNum = driversNum;
	}

	public String getDriverPhoto() {
		return driverPhoto;
	}

	public void setDriverPhoto(String driverPhoto) {
		this.driverPhoto = driverPhoto;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public Double getGpsSpeed() {
		return gpsSpeed;
	}

	public void setGpsSpeed(Double gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}

	public Double getGpsDirection() {
		return gpsDirection;
	}

	public void setGpsDirection(Double gpsDirection) {
		this.gpsDirection = gpsDirection;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(Double avgRate) {
		this.avgRate = avgRate;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
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

	public String getHeadPortraitMin() {
		return headPortraitMin;
	}

	public void setHeadPortraitMin(String headPortraitMin) {
		this.headPortraitMin = headPortraitMin;
	}

	public String getHeadPortraitMax() {
		return headPortraitMax;
	}

	public void setHeadPortraitMax(String headPortraitMax) {
		this.headPortraitMax = headPortraitMax;
	}

	public String getDriveSeniority() {
		return driveSeniority;
	}

	public void setDriveSeniority(String driveSeniority) {
		this.driveSeniority = driveSeniority;
	}

	public String getWorkStatusName() {
		return workStatusName;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	public String getDriverNamePhone() {
		return driverNamePhone;
	}

	public void setDriverNamePhone(String driverNamePhone) {
		this.driverNamePhone = driverNamePhone;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getBrandCars() {
		return brandCars;
	}

	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}

	public Double getDriverYears() {
		return driverYears;
	}

	public void setDriverYears(Double driverYears) {
		this.driverYears = driverYears;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getPassworkStatus() {
		return passworkStatus;
	}

	public void setPassworkStatus(String passworkStatus) {
		this.passworkStatus = passworkStatus;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public int getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(int onlinetime) {
		this.onlinetime = onlinetime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	/**  
	 * 获取归属车企从字典表中读取  
	 * @return belongleasecompany 归属车企从字典表中读取  
	 */
	public String getBelongleasecompany() {
		return belongleasecompany;
	}
	
	/**  
	 * 设置归属车企从字典表中读取  
	 * @param belongleasecompany 归属车企从字典表中读取  
	 */
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}

    public Integer getGpssource() {
        return gpssource;
    }

    public void setGpssource(Integer gpssource) {
        this.gpssource = gpssource;
    }

    public Date getGpstime() {
        return gpstime;
    }

    public void setGpstime(Date gpstime) {
        this.gpstime = gpstime;
    }
}
