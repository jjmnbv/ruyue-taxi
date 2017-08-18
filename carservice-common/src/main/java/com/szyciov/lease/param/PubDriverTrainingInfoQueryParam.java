package com.szyciov.lease.param;

import java.util.Date;

import com.szyciov.param.QueryParam;

public class PubDriverTrainingInfoQueryParam extends QueryParam{
	
	private String id;
	
	/**
	 * 培训课程
	 */
	private String courseName;
	
	/**
	 * 培训类型
	 */
	private String type;
	
	/**
	 * 培训日期
	 */
	private Date courseDate;
	
	/**
	 * 培训时间开始时间
	 */
	private Date startTime;
	
	/**
	 * 培训时间结束时间
	 */
	private Date stopTime;
	
	/**
	 * 培训时长
	 */
	private int duration;
	
	/**
	 * 司机姓名
	 */
	private String name;
	
	/**
	 * 网约车资格证号（工号）
	 */
	private String jobnum;
	
	/**
	 * 出租车类型（司机类型）目前是没有合乘车
	 */
	private int vehicletype;
	
	/**
	 * 司机ID
	 */
	private String driverid;
	
	/**
	 * 租凭公司的ID
	 */
	private String leasescompanyid;
	
	/**
	 * 所属平台
	 */
	private String platformtype;
	
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public int getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(int vehicletype) {
		this.vehicletype = vehicletype;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
