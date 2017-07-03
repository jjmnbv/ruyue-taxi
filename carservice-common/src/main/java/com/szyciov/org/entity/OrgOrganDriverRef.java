package com.szyciov.org.entity;

import java.util.Date;

/**
 * 司机 跟 机构 关联表
 */
public class OrgOrganDriverRef {
	/**
	 * id
	 */
	public String id;
	/**
	 * 所属机构
	 */
	public String organId;
	/**
	 * 司机id
	 */
	public String driverId;
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
	 * 机构名称
	 * */
	public String fullName;
	/**
	 * 机构简称
	 * */
	public String shortName;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
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
