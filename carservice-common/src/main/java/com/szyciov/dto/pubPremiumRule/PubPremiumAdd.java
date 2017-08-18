package com.szyciov.dto.pubPremiumRule;

import java.util.Date;

public class PubPremiumAdd {
	private String id;
	private String cartype;//用车类型
	private String ruletype;//规则类型
	private String cityname;//城市名称
	private String rulename;//规则名称
	private String tableInfo;//表格信息
	private String checkVal;//表格星期选择信息
	private Date startdate;//开始时间
	private Date enddate;//结束时间
	private String creater;
	private String updater;
	private String isoperated;
	private String leasescompanyid;
	private String rulestatus;
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getTableInfo() {
		return tableInfo;
	}
	public void setTableInfo(String tableInfo) {
		this.tableInfo = tableInfo;
	}
	public String getCheckVal() {
		return checkVal;
	}
	public void setCheckVal(String checkVal) {
		this.checkVal = checkVal;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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
	public String getIsoperated() {
		return isoperated;
	}
	public void setIsoperated(String isoperated) {
		this.isoperated = isoperated;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getRulestatus() {
		return rulestatus;
	}
	public void setRulestatus(String rulestatus) {
		this.rulestatus = rulestatus;
	}
	
	
	
}
