package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PubDriverExpensesQuerryParam extends QueryParam{
	
	
	private String driverid;

	//0-余额明细,1-交易明细  明细类型
	private String detailtype;
	
	private String startTime; //开始时间
	
	private String endTime; //结束时间
	
	private String queryTradetype;

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getDetailtype() {
		return detailtype;
	}

	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
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

	public String getQueryTradetype() {
		return queryTradetype;
	}

	public void setQueryTradetype(String queryTradetype) {
		this.queryTradetype = queryTradetype;
	}

}
