package com.szyciov.org.entity;

import java.util.Date;

/**
 * 机构用户与用车规则关联表
 */
public class OrgUserRulesRef {
	/**
	 * id
	 */
	public String id;
	/**
	 * 所属用户
	 */
	public String userId;
	/**
	 * 所属规则
	 */
	public String useRulesId;
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUseRulesId() {
		return useRulesId;
	}
	public void setUseRulesId(String useRulesId) {
		this.useRulesId = useRulesId;
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
