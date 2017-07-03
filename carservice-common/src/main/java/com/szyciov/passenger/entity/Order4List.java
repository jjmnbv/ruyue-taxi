package com.szyciov.passenger.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Order4List {
	
	/**
	  *所属租赁公司 与租赁公司id关联
	  */
	private String companyid;
	
	private String orderno;
	
	private String driverid;
	
	private String selectedmodel;
	
	private String selectedmodelcaption;
	
	private String ordertype;
	
	private String usetype;
	
	private String onaddress;
	
	private String offaddress;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date usetime;
	
	/**
	  *订单状态 包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消)
	  */
	private String orderstatus;
	
	/**
	 * 0-未支付，1-已支付，2-结算中，3-已结算
	 */
	private String paymentstatus;
	
	/**
	 * 0-未复核，1-异常待复核，2-已复核
	 */
	private String reviewstatus;
	
	private String orderstatuscaption;
	
	private double estimatedtime;
	
	private double estimatedmileage;
	
	private double estimatedcost;
	
	private double mileage;
	
	private double orderamount;
	
	private String driverimg;
	
	private boolean hasComment;
	
	private String userrate;
	
	/**
	 * 订单类型显示值
	 */
	private String ordertypecaption;
	
	/**
	 * 是否是即刻用车
	 */
	private boolean isusenow;
	
	private String canceltime;
	
	private String mileagestr;
	
	/**
	 * 待接单订单超时时间
	 */
	private Date autocanceltime;
	
	/**
	 * 待接单订单的剩余时间
	 */
	private int lefttime;
	
	private String oncity;
	
	private String offcity;
	
	/**
	 * 0-网约车，1-出租车
	 */
	private String orderstyle;
	
	/**
	 * 0-运营端，1-乘客端，2-司机端
	 */
	private String cancelparty;
	
	private double schedulefee;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getSelectedmodel() {
		return selectedmodel;
	}

	public void setSelectedmodel(String selectedmodel) {
		this.selectedmodel = selectedmodel;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
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

	public Date getUsetime() {
		return usetime;
	}

	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public double getEstimatedtime() {
		return estimatedtime;
	}

	public void setEstimatedtime(double estimatedtime) {
		this.estimatedtime = estimatedtime;
	}

	public double getEstimatedmileage() {
		return estimatedmileage;
	}

	public void setEstimatedmileage(double estimatedmileage) {
		this.estimatedmileage = estimatedmileage;
	}

	public double getEstimatedcost() {
		return estimatedcost;
	}

	public void setEstimatedcost(double estimatedcost) {
		this.estimatedcost = estimatedcost;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public double getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(double orderamount) {
		this.orderamount = orderamount;
	}

	public String getDriverimg() {
		return driverimg;
	}

	public void setDriverimg(String driverimg) {
		this.driverimg = driverimg;
	}

	public String getOrderstatuscaption() {
		return orderstatuscaption;
	}

	public void setOrderstatuscaption(String orderstatuscaption) {
		this.orderstatuscaption = orderstatuscaption;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getReviewstatus() {
		return reviewstatus;
	}

	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}

	public String getSelectedmodelcaption() {
		return selectedmodelcaption;
	}

	public void setSelectedmodelcaption(String selectedmodelcaption) {
		this.selectedmodelcaption = selectedmodelcaption;
	}

	public boolean isHasComment() {
		return hasComment;
	}

	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}

	public String getUserrate() {
		return userrate;
	}

	public void setUserrate(String userrate) {
		this.userrate = userrate;
	}

	public boolean isIsusenow() {
		return isusenow;
	}

	public void setIsusenow(boolean isusenow) {
		this.isusenow = isusenow;
	}

	public String getOrdertypecaption() {
		return ordertypecaption;
	}

	public void setOrdertypecaption(String ordertypecaption) {
		this.ordertypecaption = ordertypecaption;
	}

	public String getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(String canceltime) {
		this.canceltime = canceltime;
	}

	public String getMileagestr() {
		return mileagestr;
	}

	public void setMileagestr(String mileagestr) {
		this.mileagestr = mileagestr;
	}

	public Date getAutocanceltime() {
		return autocanceltime;
	}

	public void setAutocanceltime(Date autocanceltime) {
		this.autocanceltime = autocanceltime;
	}

	public int getLefttime() {
		return lefttime;
	}

	public void setLefttime(int lefttime) {
		this.lefttime = lefttime;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOncity() {
		return oncity;
	}

	public void setOncity(String oncity) {
		this.oncity = oncity;
	}

	public String getOffcity() {
		return offcity;
	}

	public void setOffcity(String offcity) {
		this.offcity = offcity;
	}

	public String getOrderstyle() {
		return orderstyle;
	}

	public void setOrderstyle(String orderstyle) {
		this.orderstyle = orderstyle;
	}

	public String getCancelparty() {
		return cancelparty;
	}

	public void setCancelparty(String cancelparty) {
		this.cancelparty = cancelparty;
	}

	public double getSchedulefee() {
		return schedulefee;
	}

	public void setSchedulefee(double schedulefee) {
		this.schedulefee = schedulefee;
	}
}
