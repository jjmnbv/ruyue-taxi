package com.szyciov.lease.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 机构账单
 */
public class OrgOrganBill {
	
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
	 * 账单来源
	 */
	public String source;
	
	/**
	 * 账单名称
	 */
	public String name;
	
	/**
	 * 账单金额
	 */
	public BigDecimal money;
	
	/**
	 * 备注
	 */
	public String remark;
	
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
	public String status;
	
	/**
	 * 机构简称
	 */
	public String shortName;

	/**
	 * 账单状态
	 */
	public String billState;
	
	/**
	 * 账单状态名称
	 */
	public String billStateName;
	
	/**
	 * 最后更新时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
	public Date operationTime;
	
	/**
	 * 账单来源名称
	 */
	public String sourceName;

	/**
	 * 订单Id
	 */
	public String orderId;
	
	/**
	 * 起始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 租赁公司简称
	 */
	public String leasesCompanyShortName;
	
	/**
	 * 订单数量
	 */
	public int orderCount;

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillStateName() {
		return billStateName;
	}

	public void setBillStateName(String billStateName) {
		this.billStateName = billStateName;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getLeasesCompanyShortName() {
		return leasesCompanyShortName;
	}

	public void setLeasesCompanyShortName(String leasesCompanyShortName) {
		this.leasesCompanyShortName = leasesCompanyShortName;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

}
