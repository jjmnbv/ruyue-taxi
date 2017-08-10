package com.szyciov.op.entity;

import com.szyciov.param.QueryParam;

public class OrderExportEntity extends QueryParam {
	public String usetype;//定单类型
	public String paymentstatus;//支付状态
	public String starttime;
	public String endtime;
	public String passengers;//下单人
	public String driver;//司机
	public String ordertype;//用车类型
	public String organid;//所属机构
	public String leasescompany;//服务车企
	
	public String getUsetype() {
		return usetype;
	}
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	public String getOrganid() {
		return organid;
	}
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
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
	public String getPassengers() {
		return passengers;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getLeasescompany() {
		return leasescompany;
	}
	public void setLeasescompany(String leasescompany) {
		this.leasescompany = leasescompany;
	}
	@Override
	public String toString() {
		return "OrderExportEntity [usetype=" + usetype + ", paymentstatus=" + paymentstatus + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", passengers=" + passengers + ", driver=" + driver + ", ordertype="
				+ ordertype + ", organid=" + organid + "]";
	}
	
}
