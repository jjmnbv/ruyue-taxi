package com.szyciov.org.entity;

import java.util.Date;

/**
 * 用车规则表
 */
public class OrgUsecarrules {
	/**
	 * id
	 */
	public String id;
	/**
	 * 规则名称
	 */
	public String name;
	/**
	 * 1-约车、2-接机、3-送机
	 */
	public String userType;
	/**
	 * 
	 */
	public String leasesCompanyId;
	/**
	 * 租赁公司车型
	 */
	public String vehicleModels;
	/**
	 * '0-名称，1-用车方式，2-租赁公司，3-车型',
	 */
	public String type;
	/**
	 * 父ID
	 */
	public String parentId;
	/**
	 * 创建时间
	 */
	public Date createTime;
	/**
	 * 更新时间
	 */
	public Date updateTime;
	/**
	 * 数据状态
	 */
	public Integer status;
	/**
	 * 
	 */
	public String leasesCompanyName;
	/**
	 * 租赁公司车型
	 */
	public String vehicleModelsName;
	/**
	 * orgid
	 * */
	public String organId;
	
	private String[] queryId;
	
	/*private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}*/
	public String[] getQueryId() {
		return queryId;
	}
	public void setQueryId(String[] queryId) {
		this.queryId = queryId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getLeasesCompanyName() {
		return leasesCompanyName;
	}
	public void setLeasesCompanyName(String leasesCompanyName) {
		this.leasesCompanyName = leasesCompanyName;
	}
	public String getVehicleModelsName() {
		return vehicleModelsName;
	}
	public void setVehicleModelsName(String vehicleModelsName) {
		this.vehicleModelsName = vehicleModelsName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getVehicleModels() {
		return vehicleModels;
	}
	public void setVehicleModels(String vehicleModels) {
		this.vehicleModels = vehicleModels;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
