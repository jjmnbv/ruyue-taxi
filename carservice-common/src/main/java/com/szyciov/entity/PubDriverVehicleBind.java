package com.szyciov.entity;

import java.io.Serializable;
import java.util.Date;

public class PubDriverVehicleBind implements Serializable {
	
	private String id;//
	  
	private String vehicleid;//车辆
	  
	private String driverid;//司机
	  
	private String drivertype;//司机类型 0-网约车，1-出租车
	  
	private String bindstate;//绑定状态 0 - 绑定， 1 - 解绑
	  
	private String unbindreason;//解绑原因
	  
	private Integer bindpersonnum;//已经绑定人数
	  
	private String binddirverinfo;//已经绑定司机信息
	  
	private Date createtime;//创建时间
	  
	private Date updatetime;//更新时间
	  
	private String operator;//操作人
	  
	private String creater;//创建人
	  
	private String updater;//更新人
	  
	private Integer status;//数据状态
	
	private Integer unbindpersonnum;//解绑人数
	
	private String unbinddirverinfo;//解绑司机信息

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
	
	public String getDrivertype() {
		 return drivertype;
	}
	
	public void setDrivertype(String drivertype) {
		 this.drivertype = drivertype;
	}
	
	public String getBindstate() {
		 return bindstate;
	}
	
	public void setBindstate(String bindstate) {
		 this.bindstate = bindstate;
	}
	
	public String getUnbindreason() {
		 return unbindreason;
	}
	
	public void setUnbindreason(String unbindreason) {
		 this.unbindreason = unbindreason;
	}
	
	public Integer getBindpersonnum() {
		 return bindpersonnum;
	}
	
	public void setBindpersonnum(Integer bindpersonnum) {
		 this.bindpersonnum = bindpersonnum;
	}
	
	public String getBinddirverinfo() {
		 return binddirverinfo;
	}
	
	public void setBinddirverinfo(String binddirverinfo) {
		 this.binddirverinfo = binddirverinfo;
	}
	

	public String getOperator() {
		 return operator;
	}
	
	public void setOperator(String operator) {
		 this.operator = operator;
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

	public Integer getUnbindpersonnum() {
		return unbindpersonnum;
	}

	public void setUnbindpersonnum(Integer unbindpersonnum) {
		this.unbindpersonnum = unbindpersonnum;
	}

	public String getUnbinddirverinfo() {
		return unbinddirverinfo;
	}

	public void setUnbinddirverinfo(String unbinddirverinfo) {
		this.unbinddirverinfo = unbinddirverinfo;
	}
}