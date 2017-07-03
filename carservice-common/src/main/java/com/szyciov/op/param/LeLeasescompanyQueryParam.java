package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 运管端  模糊检索 条件
 */
public class LeLeasescompanyQueryParam extends QueryParam{
	private String queryName;
	
	private String queryCity;
	
	private String queryCompanystate;
	
	private String usertype;
	
	//时间   开始
	private String startTime;
	//时间  结束
	private String endTime;
	private String queryVehicletype;

	public String getQueryVehicletype() {
		return queryVehicletype;
	}
	public void setQueryVehicletype(String queryVehicletype) {
		this.queryVehicletype = queryVehicletype;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQueryCity() {
		return queryCity;
	}
	public void setQueryCity(String queryCity) {
		this.queryCity = queryCity;
	}
	public String getQueryCompanystate() {
		return queryCompanystate;
	}
	public void setQueryCompanystate(String queryCompanystate) {
		this.queryCompanystate = queryCompanystate;
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
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	
}
