package com.szyciov.lease.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 租赁公司与计费规则关联
 */
public class LeCompanyRulesRef {
	
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 所属规则
	 */
	public String rulesId;
	
	/**
	 * 启用状态
	 */
	public String ruleState;
	
	/**
	 * 有效起始时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public String startTime;
	
	/**
	 * 有效截止时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "GMT+8")
	public String endTime;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date createTime;
	
	/**
	 * 更新时间
	 */
	public Date updateTime;
	
	/**
	 * 创建人
	 */
	public String creater;
	
	/**
	 * 更新人
	 */
	public String updater;
	
	/**
	 * 数据状态
	 */
	public int status;
	
	/**
	 * 机构名称
	 */
	public String shortName;
	
	/**
	 * 状态名称
	 */
	public String ruleStateName;
	
	/**
	 * 创建人
	 */
	public String createrVisual;
	
	/**
	 * 计费规则list
	 */
	public List<String> ruleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getRulesId() {
		return rulesId;
	}

	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
	}

	public String getRuleState() {
		return ruleState;
	}

	public void setRuleState(String ruleState) {
		this.ruleState = ruleState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getRuleStateName() {
		return ruleStateName;
	}

	public void setRuleStateName(String ruleStateName) {
		this.ruleStateName = ruleStateName;
	}

	public String getCreaterVisual() {
		return createrVisual;
	}

	public void setCreaterVisual(String createrVisual) {
		this.createrVisual = createrVisual;
	}

	public List<String> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<String> ruleList) {
		this.ruleList = ruleList;
	}
	
}
