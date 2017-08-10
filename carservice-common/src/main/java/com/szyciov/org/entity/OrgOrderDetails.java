package com.szyciov.org.entity;

import java.util.Date;

/**
  * 机构详情
  */ 
public class OrgOrderDetails{
	/**
	  *主键(订单编号)
	  */
	private String orderno;
	private String userid;
	private String companyid;
	/**
	  *支付状态 包括(0-未支付，1-已支付，2-结算中，3-已结算)
	  */
	private String paymentstatus;

	/**
	  *所属租赁公司 与租赁公司id关联 
	  */
	private String servicesphone;

	/**
	  *公司名字
	  */
	private String companyName;

	/**
	  * 乘车人
	  */
	private String passengerCar;

	/**
	  *用车时间
	  */
	private Date usetime;
	private String cancelparty;
	private Date canceltime;
	private String canceltimeShow;
	private String usetype;
	private Integer cancelnature;
	private Integer cancelamount;
	
	public Integer getCancelamount() {
		return cancelamount;
	}

	public void setCancelamount(Integer cancelamount) {
		this.cancelamount = cancelamount;
	}

	public Integer getCancelnature() {
		return cancelnature;
	}

	public void setCancelnature(Integer cancelnature) {
		this.cancelnature = cancelnature;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getCanceltimeShow() {
		return canceltimeShow;
	}

	public void setCanceltimeShow(String canceltimeShow) {
		this.canceltimeShow = canceltimeShow;
	}

	public String getCancelparty() {
		return cancelparty;
	}

	public void setCancelparty(String cancelparty) {
		this.cancelparty = cancelparty;
	}

	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	public Date getUsetime() {
		return usetime;
	}

	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	public String getUsetimeShow() {
		return usetimeShow;
	}

	public void setUsetimeShow(String usetimeShow) {
		this.usetimeShow = usetimeShow;
	}

	/**
	  *用车时间
	  */
	private String usetimeShow;
	/**
	  * 上车地址
	  */
	private String onaddress;

	/**
	  * 下车地址
	  */
	private String offaddress;

	/**
	  * 用车方式
	  */
	private String ordertype;

	/**
	  * 服务车型
	  */
	private String modelName;

	/**
	  * 司机
	  */
	private String driverName;

	/**
	  * 车牌
	  */
	private String plateNo;

	/**
	  * 品牌车系
	  */
	private String vehcBrandName;

	/**
	  *订单结算方式 包括：0-个人支付，1-个人垫付，2-机构支付
	  */
	private String paymethod;

	/**
	  *实际金额
	  */
	private String orderamount;
	/**
	 * 用车事由类型 包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 * */
	private String vehiclessubjecttype;
	/**
	 * 用车事由
	 * */
	private String vehiclessubject;
	/***/
	private String pricecopy;
	
	private String orderstatus;
	
	private String orderStatusShow;
	
	private String estimatedtime;
	private String estimatedmileage;
	private String estimatedcost;
	private String userrate;
	private String usercomment;
	
	public String getUsercomment() {
		return usercomment;
	}

	public void setUsercomment(String usercomment) {
		this.usercomment = usercomment;
	}

	public String getUserrate() {
		return userrate;
	}

	public void setUserrate(String userrate) {
		this.userrate = userrate;
	}

	public String getEstimatedtime() {
		return estimatedtime;
	}

	public void setEstimatedtime(String estimatedtime) {
		this.estimatedtime = estimatedtime;
	}

	public String getEstimatedmileage() {
		return estimatedmileage;
	}

	public void setEstimatedmileage(String estimatedmileage) {
		this.estimatedmileage = estimatedmileage;
	}

	public String getEstimatedcost() {
		return estimatedcost;
	}

	public void setEstimatedcost(String estimatedcost) {
		this.estimatedcost = estimatedcost;
	}

	public String getOrderStatusShow() {
		return orderStatusShow;
	}

	public void setOrderStatusShow(String orderStatusShow) {
		this.orderStatusShow = orderStatusShow;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPricecopy() {
		return pricecopy;
	}

	public void setPricecopy(String pricecopy) {
		this.pricecopy = pricecopy;
	}

	public String getVehiclessubjecttype() {
		return vehiclessubjecttype;
	}

	public void setVehiclessubjecttype(String vehiclessubjecttype) {
		this.vehiclessubjecttype = vehiclessubjecttype;
	}

	public String getVehiclessubject() {
		return vehiclessubject;
	}

	public void setVehiclessubject(String vehiclessubject) {
		this.vehiclessubject = vehiclessubject;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getServicesphone() {
		return servicesphone;
	}

	public void setServicesphone(String servicesphone) {
		this.servicesphone = servicesphone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassengerCar() {
		return passengerCar;
	}

	public void setPassengerCar(String passengerCar) {
		this.passengerCar = passengerCar;
	}

	public String getOnaddress() {
		return onaddress;
	}

	public void setOnaddress(String onaddress) {
		this.onaddress = onaddress;
	}

	public String getOffaddress() {
		return offaddress;
	}

	public void setOffaddress(String offaddress) {
		this.offaddress = offaddress;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehcBrandName() {
		return vehcBrandName;
	}

	public void setVehcBrandName(String vehcBrandName) {
		this.vehcBrandName = vehcBrandName;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(String orderamount) {
		this.orderamount = orderamount;
	}

	
}
