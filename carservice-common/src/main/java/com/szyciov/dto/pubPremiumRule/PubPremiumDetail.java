package com.szyciov.dto.pubPremiumRule;

import java.util.Date;

import com.szyciov.param.QueryParam;

public class PubPremiumDetail extends QueryParam{
	 /**
     * 主键
     */
    private String id;

    /**
     * 溢价规则主表ID
     */
    private String premiumruleid;

    /**
     * 星期(一、二、三、四、五、六、日)
     */
    private String weekday;

    /**
     * 起始时间
     */
    private String startdt;

    /**
     * 结束时间
     */
    private String enddt;

    /**
     * 溢价倍率
     */
    private String premiumrate;

    /**
     * 更新时间
     */
    private String updatetime;
    private String startTime;
    private String endTime;
    private String creater;
    private String updater;
//时间拼接
    private String theTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPremiumruleid() {
		return premiumruleid;
	}
	public void setPremiumruleid(String premiumruleid) {
		this.premiumruleid = premiumruleid;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getStartdt() {
		return startdt;
	}
	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getPremiumrate() {
		return premiumrate;
	}
	public void setPremiumrate(String premiumrate) {
		this.premiumrate = premiumrate;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getTheTime() {
		return theTime;
	}
	public void setTheTime(String theTime) {
		this.theTime = theTime;
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
	
    
}
