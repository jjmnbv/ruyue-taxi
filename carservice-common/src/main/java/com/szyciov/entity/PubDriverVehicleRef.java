package com.szyciov.entity;

import java.io.Serializable;
import java.util.Date;

public class PubDriverVehicleRef implements Serializable {
	
	private String id;//
	  
	private String vehicleid;//车辆
	  
	private String driverid;//司机
	  
	private Date createtime;//创建时间
	  
	private Date updatetime;//更新时间
	  
	private String creater;//创建人
	  
	private String updater;//更新人
	  
	private Integer status;//数据状态

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
}