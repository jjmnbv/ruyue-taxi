package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 租赁端车系与车型关联表
 */
public class LeVehclineModelsRef {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 所属车型
	 */
	public String vehicleModelsId;
	/**
	 * 所属车系
	 */
	public String vehclineid;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVehicleModelsId() {
		return vehicleModelsId;
	}
	public void setVehicleModelsId(String vehicleModelsId) {
		this.vehicleModelsId = vehicleModelsId;
	}
	public String getVehclineid() {
		return vehclineid;
	}
	public void setVehclineid(String vehclineid) {
		this.vehclineid = vehclineid;
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
}
