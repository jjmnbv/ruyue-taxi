package com.szyciov.entity;

import java.io.Serializable;
import java.util.Date;

public class PeDrivershiftPending implements Serializable {
	
	private String id;//
	  
	private String vehicleid;//	车辆ID
	  
	private String driverid;//与司机表主键关联
	  
	private String driverinfo;//当班司机信息
	  
	private Long onlinetime;//分钟为单位
	  
	private Date applytime;//申请时间
	  
	private String shifttype;//0-待处理
	  
	private String relievedtype;//0-自主交班,1-人工指派
	  
	private Date createtime;//创建时间
	  
	private Date updatetime;//修改时间
	  
	private String creater;//创建人
	  
	private String updater;//修改人
	  
	private Integer status;//数据状态
	  
	private String platformtype;//0-运管端，1-租赁端
	  
	private String leasescompanyid;//所属租赁ID

	private String processing;//是否正在处理 1正在处理
	
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
	
	public String getDriverid() {
		 return driverid;
	}
	
	public void setDriverid(String driverid) {
		 this.driverid = driverid;
	}
	
	public String getDriverinfo() {
		 return driverinfo;
	}
	
	public void setDriverinfo(String driverinfo) {
		 this.driverinfo = driverinfo;
	}

	public Long getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(Long onlinetime) {
		this.onlinetime = onlinetime;
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

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
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

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	/**  
	 * 获取overtimetime  
	 * @return overtimetime overtimetime  
	 */
	public Date getOvertimetime() {
		return overtimetime;
	}
	
	/**  
	 * 设置overtimetime  
	 * @param overtimetime overtimetime  
	 */
	public void setOvertimetime(Date overtimetime) {
		this.overtimetime = overtimetime;
	}
	
}