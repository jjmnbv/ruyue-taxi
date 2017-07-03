package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 租赁端车型信息表
 */
public class LeVehiclemodels {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	/**
	 * 车型LOGO
	 */
	public String logo;
	/**
	 * 车型名称
	 */
	public String name;
	/**
	 * 车型级别
	 */
	public Integer level;
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
	 * 对应车系
	 * */
	public String brandCars;
	
	/*************************************************附加字段********************************************************/
	/**
	 * 起步价
	 */
	private double startprice;
	/**
	 * 里程价
	 */
	private double rangeprice;
	/**
	 * 时长价
	 */
	private double timeprice;
	
	// 0 启用  1 禁用
	private String modelstatus;
	
 	public String getModelstatus() {
		return modelstatus;
	}

	public void setModelstatus(String modelstatus) {
		this.modelstatus = modelstatus;
	}

	/**  
	 * 获取机构用户id  
	 * @return id 机构用户id  
	 */
	public String getId() {
		return id;
	}
	
	/**  
	 * 设置机构用户id  
	 * @param id 机构用户id  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * 获取所属租赁公司  
	 * @return leasesCompanyId 所属租赁公司  
	 */
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	
	/**  
	 * 设置所属租赁公司  
	 * @param leasesCompanyId 所属租赁公司  
	 */
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	
	/**  
	 * 获取车型LOGO  
	 * @return logo 车型LOGO  
	 */
	public String getLogo() {
		return logo;
	}
	
	/**  
	 * 设置车型LOGO  
	 * @param logo 车型LOGO  
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	/**  
	 * 获取车型名称  
	 * @return name 车型名称  
	 */
	public String getName() {
		return name;
	}
	
	/**  
	 * 设置车型名称  
	 * @param name 车型名称  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 获取车型级别  
	 * @return level 车型级别  
	 */
	public Integer getLevel() {
		return level;
	}
	
	/**  
	 * 设置车型级别  
	 * @param level 车型级别  
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	/**  
	 * 获取备注  
	 * @return remark 备注  
	 */
	public String getRemark() {
		return remark;
	}
	
	/**  
	 * 设置备注  
	 * @param remark 备注  
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**  
	 * 获取创建时间  
	 * @return createTime 创建时间  
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**  
	 * 设置创建时间  
	 * @param createTime 创建时间  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**  
	 * 获取更新时间  
	 * @return updateTime 更新时间  
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**  
	 * 设置更新时间  
	 * @param updateTime 更新时间  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**  
	 * 获取  
	 * @return creater   
	 */
	public String getCreater() {
		return creater;
	}
	
	/**  
	 * 设置  
	 * @param creater   
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	/**  
	 * 获取  
	 * @return updater   
	 */
	public String getUpdater() {
		return updater;
	}
	
	/**  
	 * 设置  
	 * @param updater   
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
	/**  
	 * 获取数据状态  
	 * @return status 数据状态  
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**  
	 * 设置数据状态  
	 * @param status 数据状态  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**  
	 * 获取对应车系  
	 * @return brandCars 对应车系  
	 */
	public String getBrandCars() {
		return brandCars;
	}
	
	/**  
	 * 设置对应车系  
	 * @param brandCars 对应车系  
	 */
	public void setBrandCars(String brandCars) {
		this.brandCars = brandCars;
	}
	
	/**  
	 * 获取  
	 * @return startprice   
	 */
	public double getStartprice() {
		return startprice;
	}
	
	/**  
	 * 设置  
	 * @param startprice   
	 */
	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}
	
	/**  
	 * 获取里程价  
	 * @return rangeprice 里程价  
	 */
	public double getRangeprice() {
		return rangeprice;
	}
	
	/**  
	 * 设置里程价  
	 * @param rangeprice 里程价  
	 */
	public void setRangeprice(double rangeprice) {
		this.rangeprice = rangeprice;
	}
	
	/**  
	 * 获取时长价  
	 * @return timeprice 时长价  
	 */
	public double getTimeprice() {
		return timeprice;
	}
	
	/**  
	 * 设置时长价  
	 * @param timeprice 时长价  
	 */
	public void setTimeprice(double timeprice) {
		this.timeprice = timeprice;
	}
}
