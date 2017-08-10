package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class OrderExportParam extends QueryParam {
	public String orderno;//订单号
	public String onaddress;//上车地址
	public String offaddress;//下车地址
	public String estimatedtime;//预估行使时长（分）
	public String estimatedmileage;//预估行使里程（公里）
	public String undertime;//下单时间
	public String usetime;//用车时间
	public String ordertime;//接单时间
	public String starttime;//服务开始时间
	public String endtime;//服务结束时间
	public String ordertype;//定单类型
	public String runtime;//行使时长
	public String mileage;//行使里程
	public String jobnum;//司机资格证号
	public String name;//司机姓名
	public String phone;//司机电话
	public String plateno;//车牌号
	public String companyid;//车企名称
	public String cartype;//服务车型
	public String passengerphone;//乘客账号
	public String passengers;//乘客姓名
	public String paymentstatus;//支付状态
	public String orderamount;//订单金额
	public String orderstatus;//订单状态
	public String ordersource;//订单来源
	public String usetype;//订单类型
	public String belongleasecompany;//服务车企
	
	public String getBelongleasecompany() {
		return belongleasecompany;
	}
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}
	public String getUsetype() {
		return usetype;
	}
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
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
	public String getUndertime() {
		return undertime;
	}
	public void setUndertime(String undertime) {
		this.undertime = undertime;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getJobnum() {
		return jobnum;
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
	
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getPassengerphone() {
		return passengerphone;
	}
	public void setPassengerphone(String passengerphone) {
		this.passengerphone = passengerphone;
	}
	public String getPassengers() {
		return passengers;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(String orderamount) {
		this.orderamount = orderamount;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getOrdersource() {
		return ordersource;
	}
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}
	
	
}
