package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeDriverorderstatisticsParam  extends QueryParam{
	public LeDriverorderstatisticsParam(){
		
	}
	private String id;
	private String leasesCompanyId;
	//工号
	private String jobnum;
	//姓名
	private String name;
	//手机号码
	private String phone;
	//车牌号
	private String plateno;
	 //品牌车系
	private String vehcBrand;
	//服务车型
	private String cartype;
	//城市名称
	private String cityName;
	//总金额
	private String ordermoney;
	//总订单数
	private String allOrders;
	//约车
	private String carorders;
	//接机
	private String pickuporders;
	//送机
	private String dropofforders;
	//异常订单
	private String reviewstatus;
	private String startTime;
	private String endTime;
	//星评
	private String userrate;
	//差异订单
	private String orderreview;
	//差异金额
	private String orderreviewPrice;
	//服务机构订单
	private String oragnAgency;
	//服务个人
	private String personOrders;
	public String key;
	//出租车
	private String taxiOrders;
	//异常已处理
	private String confirmedorders;
    //异常未处理
	private String processedorders;
	
	
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
	
	public String getTaxiOrders() {
		return taxiOrders;
	}
	public void setTaxiOrders(String taxiOrders) {
		this.taxiOrders = taxiOrders;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPersonOrders() {
		return personOrders;
	}
	public void setPersonOrders(String personOrders) {
		this.personOrders = personOrders;
	}
	public String getOrderreview() {
		return orderreview;
	}
	public void setOrderreview(String orderreview) {
		this.orderreview = orderreview;
	}
	public String getOrderreviewPrice() {
		return orderreviewPrice;
	}
	public void setOrderreviewPrice(String orderreviewPrice) {
		this.orderreviewPrice = orderreviewPrice;
	}
	public String getOragnAgency() {
		return oragnAgency;
	}
	public void setOragnAgency(String oragnAgency) {
		this.oragnAgency = oragnAgency;
	}
	public String getUserrate() {
		return userrate;
	}
	public void setUserrate(String userrate) {
		this.userrate = userrate;
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
	public String getReviewstatus() {
		return reviewstatus;
	}
	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}
	public String getJobnum() {
		return jobnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPlateno() {
		return plateno;
	}
	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}
	public String getVehcBrand() {
		return vehcBrand;
	}
	public void setVehcBrand(String vehcBrand) {
		this.vehcBrand = vehcBrand;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	
	public String getAllOrders() {
		return allOrders;
	}
	public void setAllOrders(String allOrders) {
		this.allOrders = allOrders;
	}
	@Override
	public String toString() {
		return "LeDriverorderstatisticsParam [id=" + id + ", leasesCompanyId=" + leasesCompanyId + ", jobnum=" + jobnum
				+ ", name=" + name + ", phone=" + phone + ", plateno=" + plateno + ", vehcBrand=" + vehcBrand
				+ ", cartype=" + cartype + ", cityName=" + cityName + ", ordermoney=" + ordermoney + ", allOrders="
				+ allOrders + ", carorders=" + carorders + ", pickuporders=" + pickuporders + ", dropofforders="
				+ dropofforders + ", reviewstatus=" + reviewstatus + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", userrate=" + userrate + ", orderreview=" + orderreview + ", orderreviewPrice=" + orderreviewPrice
				+ ", oragnAgency=" + oragnAgency + ", personOrders=" + personOrders + ", key=" + key + ", taxiOrders="
				+ taxiOrders + ", confirmedorders=" + confirmedorders + ", processedorders=" + processedorders + "]";
	}

	
	
}
