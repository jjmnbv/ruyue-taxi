package com.szyciov.dto.pubCoooperate;


import java.util.Date;

/**
  * 司机信息表
  */ 
public class DriverInformationDto {

	public String id;
	/**
	 * 所属租赁公司
	 */
	public String leasescompanyid;
	
	public String name;

	/**
	 * city name
	 * */
	public String jobnum;
	
	/**
	 * 司机类型 0-网约车，1-出租车
	 */
	public String vehicletype;
	/**
	 * 车辆信息
	 * */
	public String vehicleInfo;
	public String distributionVelId;
	public String distributionVel;
	public Date distributionVelTime;
	
	public String originalName;//原来的服务车型
	
	public String nowName;//现在的服务车型
	
	
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getNowName() {
		return nowName;
	}
	public void setNowName(String nowName) {
		this.nowName = nowName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobnum() {
		return jobnum;
	}
	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public String getVehicleInfo() {
		return vehicleInfo;
	}
	public void setVehicleInfo(String vehicleInfo) {
		this.vehicleInfo = vehicleInfo;
	}
	public String getDistributionVelId() {
		return distributionVelId;
	}
	public void setDistributionVelId(String distributionVelId) {
		this.distributionVelId = distributionVelId;
	}
	public String getDistributionVel() {
		return distributionVel;
	}
	public void setDistributionVel(String distributionVel) {
		this.distributionVel = distributionVel;
	}
	public Date getDistributionVelTime() {
		return distributionVelTime;
	}
	public void setDistributionVelTime(Date distributionVelTime) {
		this.distributionVelTime = distributionVelTime;
	}

	
}
