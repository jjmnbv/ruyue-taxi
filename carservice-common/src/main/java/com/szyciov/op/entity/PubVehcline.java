package com.szyciov.op.entity;

import java.util.Date;

/**
 * 车系表
 */
public class PubVehcline {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 品牌ID
	 */
	public String vehcBrandID;
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	/**
	 * 车系名称
	 */
	public String name;
	/**
	 * 备注
	 */
	public String remark;
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
	/**
	 * 品牌名称
	 * */
	public String vehcBrandName;
	/**
	 * 品牌车系
	 * */
	public String brandCars;
	/**
	 * 服务车型
	 * */
	public String vehicleModelsName;
	
	public String initials;
	
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getVehicleModelsName() {
		return vehicleModelsName;
	}
	public void setVehicleModelsName(String vehicleModelsName) {
		this.vehicleModelsName = vehicleModelsName;
	}
	public String getBrandCars() {
		return brandCars;
	}
	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}
	
	public String getVehcBrandName() {
		return vehcBrandName;
	}
	public void setVehcBrandName(String vehcBrandName) {
		this.vehcBrandName = vehcBrandName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVehcBrandID() {
		return vehcBrandID;
	}
	public void setVehcBrandID(String vehcBrandID) {
		this.vehcBrandID = vehcBrandID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
