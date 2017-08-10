package com.szyciov.org.param;

import com.szyciov.param.QueryParam;

public class OrgOrderQueryParam extends QueryParam{
	//租赁公司
	private String queryCompany;
	//下单人 信息 or 下单人
	private String queryUserMessage;
	// 订单号
	private String queryOrderNo;
	
	//用车时间   开始
	private String startTime;
	//用车时间  结束
	private String endTime;	
	
	private String queryOrderTemp;
	
	private String usertype;
	
	private String userid;
	
	private String querySearch;
	
	private String queryExpensetype;
	
	public String getQueryExpensetype() {
		return queryExpensetype;
	}

	public void setQueryExpensetype(String queryExpensetype) {
		this.queryExpensetype = queryExpensetype;
	}

	public String getQuerySearch() {
		return querySearch;
	}

	public void setQuerySearch(String querySearch) {
		this.querySearch = querySearch;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getQueryOrderTemp() {
		return queryOrderTemp;
	}

	public void setQueryOrderTemp(String queryOrderTemp) {
		this.queryOrderTemp = queryOrderTemp;
	}

	private String[] queryOrder;
	
	private String queryVehicleMode;
	
	private String queryPaymentMethod;
	
	public String[] getQueryOrder() {
		return queryOrder;
	}

	public void setQueryOrder(String[] queryOrder) {
		this.queryOrder = queryOrder;
	}

	public String getQueryVehicleMode() {
		return queryVehicleMode;
	}

	public void setQueryVehicleMode(String queryVehicleMode) {
		this.queryVehicleMode = queryVehicleMode;
	}

	public String getQueryPaymentMethod() {
		return queryPaymentMethod;
	}

	public void setQueryPaymentMethod(String queryPaymentMethod) {
		this.queryPaymentMethod = queryPaymentMethod;
	}

	public String getQueryUserMessage() {
		return queryUserMessage;
	}

	public void setQueryUserMessage(String queryUserMessage) {
		this.queryUserMessage = queryUserMessage;
	}

	public String getQueryOrderNo() {
		return queryOrderNo;
	}

	public void setQueryOrderNo(String queryOrderNo) {
		this.queryOrderNo = queryOrderNo;
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

	public String getQueryCompany() {
		return queryCompany;
	}

	public void setQueryCompany(String queryCompany) {
		this.queryCompany = queryCompany;
	}
	
}
