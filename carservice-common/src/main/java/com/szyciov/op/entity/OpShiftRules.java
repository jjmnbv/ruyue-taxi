package com.szyciov.op.entity;

import java.util.Date;

public class OpShiftRules {

	private String id;
	
	private int autoshifttime;
	
	private int manualshifttime;
	
	private String city;
	
	private String cityname;
	
	private Date createtime;
	
	private Date updatetime;
	
	private String creater;
	
	private String updater;
	
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAutoshifttime() {
		return autoshifttime;
	}

	public void setAutoshifttime(int autoshifttime) {
		this.autoshifttime = autoshifttime;
	}

	public int getManualshifttime() {
		return manualshifttime;
	}

	public void setManualshifttime(int manualshifttime) {
		this.manualshifttime = manualshifttime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
