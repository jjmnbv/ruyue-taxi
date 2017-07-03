package com.szyciov.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 交接班管理历史记录
 */
public class PeDrivershiftProcessed implements Serializable {
	
	private String id;//
	  
	private String vehicleid;//车辆ID
	  
	private String ondutydriverid;//与司机表主键关联
	  
	private String ondutydriverinfo;//
	  
	private String relieveddriverid;//与司机表主键关联
	  
	private String relieveddriverinfo;//
	  
	private Long onlinetime;//分钟为单位
	  
	private Date applytime;//
	  
	private String shifttype;//0-交班成功，1-车辆回收，2-交班超时，3-指派当班
	  
	private String relievedtype;//0-自主交班,1-人工指派
	  
	private String processperson;//

	private String processpersonname;//
	  
	private Date processtime;//
	  
	private Date createtime;//
	  
	private Date updatetime;//
	  
	private String creater;//
	  
	private String updater;//
	  
	private Integer status;//
	  
	private String platformtype;//0-运管端，1-租赁端
	  
	private String leasescompanyid;//

	private Date overtimetime;//超时时间
	  
	public String getId() {
		 return id;
	}
	
	public void setId(String id) {
		 this.id = id;
	}
	
	public String getVehicleid() {
		 return vehicleid;
	}
	
	public void setVehicleid(String vehicleid) {
		 this.vehicleid = vehicleid;
	}
	
	public String getOndutydriverid() {
		 return ondutydriverid;
	}
	
	public void setOndutydriverid(String ondutydriverid) {
		 this.ondutydriverid = ondutydriverid;
	}

	public String getOndutydriverinfo() {
		return ondutydriverinfo;
	}

	public void setOndutydriverinfo(String ondutydriverinfo) {
		this.ondutydriverinfo = ondutydriverinfo;
	}

	public String getRelieveddriverid() {
		return relieveddriverid;
	}

	public void setRelieveddriverid(String relieveddriverid) {
		this.relieveddriverid = relieveddriverid;
	}

	public String getRelieveddriverinfo() {
		return relieveddriverinfo;
	}

	public void setRelieveddriverinfo(String relieveddriverinfo) {
		this.relieveddriverinfo = relieveddriverinfo;
	}

	public Long getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(Long onlinetime) {
		this.onlinetime = onlinetime;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public String getShifttype() {
		return shifttype;
	}

	public void setShifttype(String shifttype) {
		this.shifttype = shifttype;
	}

	public String getRelievedtype() {
		return relievedtype;
	}

	public void setRelievedtype(String relievedtype) {
		this.relievedtype = relievedtype;
	}

	public String getProcessperson() {
		return processperson;
	}

	public void setProcessperson(String processperson) {
		this.processperson = processperson;
	}

	public Date getProcesstime() {
		return processtime;
	}

	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
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

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getProcesspersonname() {
		return processpersonname;
	}

	public void setProcesspersonname(String processpersonname) {
		this.processpersonname = processpersonname;
	}

	public Date getOvertimetime() {
		return overtimetime;
	}

	public void setOvertimetime(Date overtimetime) {
		this.overtimetime = overtimetime;
	}
}