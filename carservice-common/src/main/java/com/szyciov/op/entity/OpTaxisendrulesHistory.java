package com.szyciov.op.entity;

import java.util.Date;

public class OpTaxisendrulesHistory {
	
	private String id;
	
	/**
	 * 所属城市
	 */
	private String city;
	
	/**
	 * 用车类型(0-预约用车,1-即刻用车)
	 */
	private String usetype;
	
	/**
	 * 派单方式(0-强派,1-抢派,2-抢单)
	 */
	private String sendtype;
	
	/**
	 * 派单模式(0-系统,1-系统+人工)
	 */
	private String sendmodel;
	
	/**
	 * 系统派单时限(分钟)
	 */
	private Integer systemsendinterval;
	
	/**
	 * 司机抢单时限(分钟)
	 */
	private Integer driversendinterval;
	
	/**
	 * 人工派单时限(分钟)
	 */
	private Integer personsendinterval;
	
	/**
	 * 初始派单半径(公里)
	 */
	private Double initsendradius;
	
	/**
	 * 最大派单半径(公里)
	 */
	private Double maxsendradius;
	
	/**
	 * 半径递增比
	 */
	private Double increratio;
	
	/**
	 * 推送数量(0-不限制,1-限制)
	 */
	private String pushnumlimit;
	
	/**
	 * 推送数量大小
	 */
	private Integer pushnum;
	
	/**
	 * 推送限制(0-存在抢单弹窗不推单,1-存在抢单弹窗推单)
	 */
	private String pushlimit;
	
	/**
	 * 城市首字母简称
	 */
	private String shortname;
	
	/**
	 * 约车时限(分钟)
	 */
	private Integer carsinterval;
	
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
	
	/**
	 * 操作类型(0-新增操作，1-修改操作，2-启用操作，3-禁用操作)
	 */
	private String operatetype;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 所属规则
	 */
	private String taxisendrulesid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getSendmodel() {
		return sendmodel;
	}

	public void setSendmodel(String sendmodel) {
		this.sendmodel = sendmodel;
	}

	public Integer getSystemsendinterval() {
		return systemsendinterval;
	}

	public void setSystemsendinterval(Integer systemsendinterval) {
		this.systemsendinterval = systemsendinterval;
	}

	public Integer getDriversendinterval() {
		return driversendinterval;
	}

	public void setDriversendinterval(Integer driversendinterval) {
		this.driversendinterval = driversendinterval;
	}

	public Integer getPersonsendinterval() {
		return personsendinterval;
	}

	public void setPersonsendinterval(Integer personsendinterval) {
		this.personsendinterval = personsendinterval;
	}

	public Double getInitsendradius() {
		return initsendradius;
	}

	public void setInitsendradius(Double initsendradius) {
		this.initsendradius = initsendradius;
	}

	public Double getMaxsendradius() {
		return maxsendradius;
	}

	public void setMaxsendradius(Double maxsendradius) {
		this.maxsendradius = maxsendradius;
	}

	public Double getIncreratio() {
		return increratio;
	}

	public void setIncreratio(Double increratio) {
		this.increratio = increratio;
	}

	public String getPushnumlimit() {
		return pushnumlimit;
	}

	public void setPushnumlimit(String pushnumlimit) {
		this.pushnumlimit = pushnumlimit;
	}

	public Integer getPushnum() {
		return pushnum;
	}

	public void setPushnum(Integer pushnum) {
		this.pushnum = pushnum;
	}

	public String getPushlimit() {
		return pushlimit;
	}

	public void setPushlimit(String pushlimit) {
		this.pushlimit = pushlimit;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Integer getCarsinterval() {
		return carsinterval;
	}

	public void setCarsinterval(Integer carsinterval) {
		this.carsinterval = carsinterval;
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

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTaxisendrulesid() {
		return taxisendrulesid;
	}

	public void setTaxisendrulesid(String taxisendrulesid) {
		this.taxisendrulesid = taxisendrulesid;
	}
}
