package com.szyciov.dto.pubPremiumRule;

import java.util.List;

public class PubPremiumModify {
	private String id;
	private String startdt;
	private String enddt;
	private String cityCode;
	private String rulename;
	private String cartype;
	private String ruletype;
	private List<PubPremiumDetail> week;
	private List<PubPremiumDetail> date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public List<PubPremiumDetail> getWeek() {
		return week;
	}
	public void setWeek(List<PubPremiumDetail> week) {
		this.week = week;
	}
	public List<PubPremiumDetail> getDate() {
		return date;
	}
	public void setDate(List<PubPremiumDetail> date) {
		this.date = date;
	}
	public String getRuletype() {
		return ruletype;
	}
	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
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

}
