package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 运管端  模糊检索 条件
 */
public class PeUserQueryParam extends QueryParam{
	private String queryAccount; //账号
	private String startTime; //开始时间
	private String endTime; //结束时间
	private String queryCompanystate; //状态
	public String getQueryAccount() {
		return queryAccount;
	}
	public void setQueryAccount(String queryAccount) {
		this.queryAccount = queryAccount;
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
	public String getQueryCompanystate() {
		return queryCompanystate;
	}
	public void setQueryCompanystate(String queryCompanystate) {
		this.queryCompanystate = queryCompanystate;
	}
	
}
