package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 司机培训类
 * 
 */
public class PubDriverTraininginfo {
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;
	
	/**
	 * 所属平台
	 */
	private String platformtype;
	
	/**
	 * 所属司机
	 */
	private String driverid;
	
	/**
	 * 培训类型,GQ:岗前,RC:日常
	 */
	private String type;
	
	/**
	 * 培训课程
	 */
	private String coursename;
	
	/**
	 * 培训日期
	 */
	private Date coursedate;
	
	/**
	 * 培训时间开始时间
	 */
	private Date starttime;
	
	/**
	 * 培训时间结束时间
	 */
	private Date stoptime;
	
	/**
	 * 培训时长（秒）
	 */
	private int duration;
	
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
	private int status;
	
	

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

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public Date getCoursedate() {
		return coursedate;
	}

	public void setCoursedate(Date coursedate) {
		this.coursedate = coursedate;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getStoptime() {
		return stoptime;
	}

	public void setStoptime(Date stoptime) {
		this.stoptime = stoptime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	

}
