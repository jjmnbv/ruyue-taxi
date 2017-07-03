package com.szyciov.op.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 运营计费规则
 */
public class OpAccountrules {
	
	private String id;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 规则类型
	 */
	private String rulestype;
	
	/**
	 * 服务车型
	 */
	private String vehiclemodelsid;
	
	/**
	 * 起步价
	 */
	private BigDecimal startprice;
	
	/**
	 * 里程价
	 */
	private BigDecimal rangeprice;
	
	/**
	 * 时间价
	 */
	private BigDecimal timeprice;
	
	/**
	 * 时间类型
	 */
	private String timetype;
	
	/**
	 * 时速
	 */
	private BigDecimal perhour;
	
	/**
	 * 规则状态
	 */
	private String rulesstate;
	
	/**
	 * 禁用原因
	 */
	private String reason;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
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
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 规则类型名称
	 */
	private String rulestypeName;
	
	/**
	 * 服务车型名称
	 */
	private String vehiclemodelsName;
	
	/**
	 * 时间类型名称
	 */
	private String timetypeName;
	
	/**
	 * 规则状态名称
	 */
	private String rulesstateName;
	
	/**
	 * 页面展示的时速
	 */
	private String perhourVisual;
	
	/**
	 * 回空里程
	 * 
	 */
	public BigDecimal deadheadmileage;
	
	/**
	 * 回空费价
	 * 
	 */
	public BigDecimal deadheadprice;
	
	/**
	 * 夜间费价
	 * 
	 */
	public BigDecimal nighteprice;
	/**
	 * 夜间征收时段
	 */
	public String nightetimes;
	public String nightstarttime;//夜间服务开始时间
	public String nightendtime;//夜间服务结束时间
	
	public BigDecimal getDeadheadmileage() {
		return deadheadmileage;
	}

	public void setDeadheadmileage(BigDecimal deadheadmileage) {
		this.deadheadmileage = deadheadmileage;
	}

	public BigDecimal getDeadheadprice() {
		return deadheadprice;
	}

	public void setDeadheadprice(BigDecimal deadheadprice) {
		this.deadheadprice = deadheadprice;
	}

	public BigDecimal getNighteprice() {
		return nighteprice;
	}

	public void setNighteprice(BigDecimal nighteprice) {
		this.nighteprice = nighteprice;
	}

	public String getNightetimes() {
		return nightetimes;
	}

	public void setNightetimes(String nightetimes) {
		this.nightetimes = nightetimes;
	}

	public String getNightstarttime() {
		return nightstarttime;
	}

	public void setNightstarttime(String nightstarttime) {
		this.nightstarttime = nightstarttime;
	}

	public String getNightendtime() {
		return nightendtime;
	}

	public void setNightendtime(String nightendtime) {
		this.nightendtime = nightendtime;
	}

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

	public String getRulestype() {
		return rulestype;
	}

	public void setRulestype(String rulestype) {
		this.rulestype = rulestype;
	}

	public String getVehiclemodelsid() {
		return vehiclemodelsid;
	}

	public void setVehiclemodelsid(String vehiclemodelsid) {
		this.vehiclemodelsid = vehiclemodelsid;
	}

	public BigDecimal getStartprice() {
		return startprice;
	}

	public void setStartprice(BigDecimal startprice) {
		this.startprice = startprice;
	}

	public BigDecimal getRangeprice() {
		return rangeprice;
	}

	public void setRangeprice(BigDecimal rangeprice) {
		this.rangeprice = rangeprice;
	}

	public BigDecimal getTimeprice() {
		return timeprice;
	}

	public void setTimeprice(BigDecimal timeprice) {
		this.timeprice = timeprice;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public BigDecimal getPerhour() {
		return perhour;
	}

	public void setPerhour(BigDecimal perhour) {
		this.perhour = perhour;
	}

	public String getRulesstate() {
		return rulesstate;
	}

	public void setRulesstate(String rulesstate) {
		this.rulesstate = rulesstate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRulestypeName() {
		return rulestypeName;
	}

	public void setRulestypeName(String rulestypeName) {
		this.rulestypeName = rulestypeName;
	}

	public String getVehiclemodelsName() {
		return vehiclemodelsName;
	}

	public void setVehiclemodelsName(String vehiclemodelsName) {
		this.vehiclemodelsName = vehiclemodelsName;
	}

	public String getTimetypeName() {
		return timetypeName;
	}

	public void setTimetypeName(String timetypeName) {
		this.timetypeName = timetypeName;
	}

	public String getRulesstateName() {
		return rulesstateName;
	}

	public void setRulesstateName(String rulesstateName) {
		this.rulesstateName = rulesstateName;
	}

	public String getPerhourVisual() {
		return perhourVisual;
	}

	public void setPerhourVisual(String perhourVisual) {
		this.perhourVisual = perhourVisual;
	}
}
