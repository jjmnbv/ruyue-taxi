package com.szyciov.op.entity;

import java.util.Date;

/**
 * 运营端车系与车型关联表
 */
public class OpVehiclemodelsVehicleRef {
	
	private String id;
	
	/**
	 * 所属车型
	 */
	private String vehiclemodelsid;
	
	/**
	 * 所属车辆
	 */
	private String vehicleid;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehiclemodelsid() {
		return vehiclemodelsid;
	}

	public void setVehiclemodelsid(String vehiclemodelsid) {
		this.vehiclemodelsid = vehiclemodelsid;
	}

	public String getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
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
}
