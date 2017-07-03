package com.szyciov.lease.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 租赁计费规则修改历史
 */
public class LeAccountRulesModiLog {
	
	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 所属计费规则
	 */
	public String accountRulesId;
	
	/**
	 * 城市
	 */
	public String city;
	
	/**
	 * 规则类型
	 */
	public String rulesType;
	
	/**
	 * 服务车型
	 */
	public String carType;
	
	/**
	 * 起步价
	 */
	public BigDecimal startPrice;
	
	/**
	 * 里程价
	 */
	public BigDecimal rangePrice;
	
	/**
	 * 时间价
	 */
	public BigDecimal timePrice;
	
	/**
	 * 时间类型
	 */
	public String timeType;
	
	/**
	 * 时速
	 */
	public BigDecimal perhour;
	
	/**
	 * 是否通用规则
	 */
	public String type;
	
	/**
	 * 规则状态
	 */
	public String rulesState;
	
	/**
	 * 操作类型
	 */
	public String modiType;

	/**
	 * 禁用原因
	 */
	public String reason;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
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
	 * 操作类型名称
	 */
	public String modiTypeName;
	
	/**
	 * 时间类型名称
	 */
	public String timeTypeName;
	
	/**
	 * 规则状态名称
	 */
	public String rulesStateName;
	
	/**
	 * 时速显示
	 */
	public String perhourVisual;
	
	/**
	 * 创建人显示
	 */
	public String createrVisual;
	/**
	 * 回空里程
	 * 
	 */
	public String deadheadmileage;
	
	/**
	 * 回空费价
	 * 
	 */
	public String deadheadprice;
	/**
	 * 夜间征收时段
	 */
	public String nightstarttime;
	/**
	 * 夜间征收时段
	 */
	public String nightendtime;
	/**
	 * 夜间费价
	 * 
	 */
	public String nighteprice;
	/**
	 * 夜间征收时段
	 */
	public String nighttimes;
	

	public String getNighttimes() {
		return nighttimes;
	}

	public void setNighttimes(String nighttimes) {
		this.nighttimes = nighttimes;
	}

	public String getDeadheadmileage() {
		return deadheadmileage;
	}

	public void setDeadheadmileage(String deadheadmileage) {
		this.deadheadmileage = deadheadmileage;
	}

	public String getDeadheadprice() {
		return deadheadprice;
	}

	public void setDeadheadprice(String deadheadprice) {
		this.deadheadprice = deadheadprice;
	}

	public String getNighteprice() {
		return nighteprice;
	}

	public void setNighteprice(String nighteprice) {
		this.nighteprice = nighteprice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountRulesId() {
		return accountRulesId;
	}

	public void setAccountRulesId(String accountRulesId) {
		this.accountRulesId = accountRulesId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRulesType() {
		return rulesType;
	}

	public void setRulesType(String rulesType) {
		this.rulesType = rulesType;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getRangePrice() {
		return rangePrice;
	}

	public void setRangePrice(BigDecimal rangePrice) {
		this.rangePrice = rangePrice;
	}

	public BigDecimal getTimePrice() {
		return timePrice;
	}

	public void setTimePrice(BigDecimal timePrice) {
		this.timePrice = timePrice;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public BigDecimal getPerhour() {
		return perhour;
	}

	public void setPerhour(BigDecimal perhour) {
		this.perhour = perhour;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRulesState() {
		return rulesState;
	}

	public void setRulesState(String rulesState) {
		this.rulesState = rulesState;
	}

	public String getModiType() {
		return modiType;
	}

	public void setModiType(String modiType) {
		this.modiType = modiType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getModiTypeName() {
		return modiTypeName;
	}

	public void setModiTypeName(String modiTypeName) {
		this.modiTypeName = modiTypeName;
	}

	public String getTimeTypeName() {
		return timeTypeName;
	}

	public void setTimeTypeName(String timeTypeName) {
		this.timeTypeName = timeTypeName;
	}

	public String getRulesStateName() {
		return rulesStateName;
	}

	public void setRulesStateName(String rulesStateName) {
		this.rulesStateName = rulesStateName;
	}

	public String getPerhourVisual() {
		return perhourVisual;
	}

	public void setPerhourVisual(String perhourVisual) {
		this.perhourVisual = perhourVisual;
	}

	public String getCreaterVisual() {
		return createrVisual;
	}

	public void setCreaterVisual(String createrVisual) {
		this.createrVisual = createrVisual;
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

	@Override
	public String toString() {
		return "LeAccountRulesModiLog [id=" + id + ", accountRulesId=" + accountRulesId + ", city=" + city
				+ ", rulesType=" + rulesType + ", carType=" + carType + ", startPrice=" + startPrice + ", rangePrice="
				+ rangePrice + ", timePrice=" + timePrice + ", timeType=" + timeType + ", perhour=" + perhour
				+ ", type=" + type + ", rulesState=" + rulesState + ", modiType=" + modiType + ", reason=" + reason
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", creater=" + creater + ", updater="
				+ updater + ", status=" + status + ", modiTypeName=" + modiTypeName + ", timeTypeName=" + timeTypeName
				+ ", rulesStateName=" + rulesStateName + ", perhourVisual=" + perhourVisual + ", createrVisual="
				+ createrVisual + ", deadheadmileage=" + deadheadmileage + ", deadheadprice=" + deadheadprice
				+ ", nightstarttime=" + nightstarttime + ", nightendtime=" + nightendtime + ", nighteprice="
				+ nighteprice + ", nighttimes=" + nighttimes + "]";
	}


}
