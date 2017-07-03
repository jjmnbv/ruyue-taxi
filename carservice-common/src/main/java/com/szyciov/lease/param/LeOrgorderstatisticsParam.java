package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeOrgorderstatisticsParam extends QueryParam  {
   public LeOrgorderstatisticsParam(){
	   
   }
   /**
	 * 所属租赁公司
	 */
	public String id;
   /**
	 * 所属租赁公司
	 */
	public String leasesCompanyId;
	
	/**
	 * 所属机构
	 */
	public String organId;
	public String shortname;
	/**
	 * 所属城市id
	 */
	public String cityid;
	public String city;
	/**
	 * 订单金额
	 */
	public String ordermoney;
	/**
	 * 约车订单数
	 */
	public String carorders;
	/**
	 * 接机订单数
	 */
	public String pickuporders;
	/**
	 * 送机订单数
	 */
	public String dropofforders;
	//总订单数
	public String allOrders;
	/**
	 * 异常已处理订单数
	 */
	public String confirmedorders;
	/**
	 * 异常未处理订单数
	 */
	public String processedorders;
	//总以常数
	public String alluporders;
	
	/**
	 * 起始时间
	 */
	public String startTime;
	
	/**
	 * 结束时间
	 */
	public String endTime;
	
	/**
	 * 状态
	 */
	public String billState;
	public String key;
	

	public String getAllOrders() {
		return allOrders;
	}

	public void setAllOrders(String allOrders) {
		this.allOrders = allOrders;
	}

	public String getAlluporders() {
		return alluporders;
	}

	public void setAlluporders(String alluporders) {
		this.alluporders = alluporders;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getOrdermoney() {
		return ordermoney;
	}

	public void setOrdermoney(String ordermoney) {
		this.ordermoney = ordermoney;
	}

	public String getCarorders() {
		return carorders;
	}

	public void setCarorders(String carorders) {
		this.carorders = carorders;
	}

	public String getPickuporders() {
		return pickuporders;
	}

	public void setPickuporders(String pickuporders) {
		this.pickuporders = pickuporders;
	}

	public String getDropofforders() {
		return dropofforders;
	}

	public void setDropofforders(String dropofforders) {
		this.dropofforders = dropofforders;
	}

	public String getConfirmedorders() {
		return confirmedorders;
	}

	public void setConfirmedorders(String confirmedorders) {
		this.confirmedorders = confirmedorders;
	}

	public String getProcessedorders() {
		return processedorders;
	}

	public void setProcessedorders(String processedorders) {
		this.processedorders = processedorders;
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

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LeFunctionmanagementParam [id=" + id + ", leasesCompanyId=" + leasesCompanyId + ", organId=" + organId
				+ ", shortname=" + shortname + ", cityid=" + cityid + ", city=" + city + ", ordermoney=" + ordermoney
				+ ", carorders=" + carorders + ", pickuporders=" + pickuporders + ", dropofforders=" + dropofforders
				+ ", allOrders=" + allOrders + ", confirmedorders=" + confirmedorders + ", processedorders="
				+ processedorders + ", alluporders=" + alluporders + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", billState=" + billState + "]";
	}

}
