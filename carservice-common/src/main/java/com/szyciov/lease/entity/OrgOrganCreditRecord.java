package com.szyciov.lease.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构信用额度变更记录
 */
public class OrgOrganCreditRecord {
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 所属机构
	 */
	public String organId;
	
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 原信用额度
	 */
	public Double oldCredit;
	
	/**
	 * 变更后信用额度
	 */
	public Double currentCredit;
	
	/**
	 * 变更时间
	 */
	public Date changeTime;
	
	/**
	 * 生效时间
	 */
	public Date effectiveTime;
	
	/**
	 * 创建时间
	 */
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
	public String status;
	
	/**
	 * 操作人
	 */
	public String operator;

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

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public Double getOldCredit() {
		return oldCredit;
	}

	public void setOldCredit(Double oldCredit) {
		this.oldCredit = oldCredit;
	}

	public Double getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(Double currentCredit) {
		this.currentCredit = currentCredit;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
