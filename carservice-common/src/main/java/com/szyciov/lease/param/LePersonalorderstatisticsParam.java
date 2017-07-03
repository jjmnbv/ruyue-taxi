package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LePersonalorderstatisticsParam extends QueryParam{
	public LePersonalorderstatisticsParam(){
		
	}
	public String id;
	//所属租赁公司
	public String leasescompanyid;
	//所属城市
	public String cityid;
	//城市
	public String city;
	//业务分类
	public String ordertype;
	//支付类型
	public String paymethod;
	//订单数
	public String ordernum;
	//异常已处理订单
	public String confirmedorders;
	//异常未处理订单
	public String processedorders;
	//异常订单
	public String alluporders;
	//订单金额
	public String ordermoney;
	//差异金额
	public String diffmoney;
	//时间
	public String time;
	//起始时间
	public String startTime;
	//结束时间
	public String endTime;
	//数据状态
	public String status;
	//约车单数
	public String carorders;
    //送机单数
	public String dropofforders;
	//接机单数
	public String pickuporders;
	//差异订单
	public String difforders;
	//业务类型
	public String type;
	//业务类型
	public String key;
	public String runfee;
	public String schedulefee;
	public String paymentstatus;
	
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getRunfee() {
		return runfee;
	}
	public void setRunfee(String runfee) {
		this.runfee = runfee;
	}
	public String getSchedulefee() {
		return schedulefee;
	}
	public void setSchedulefee(String schedulefee) {
		this.schedulefee = schedulefee;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDifforders() {
		return difforders;
	}
	public void setDifforders(String difforders) {
		this.difforders = difforders;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCarorders() {
		return carorders;
	}
	public void setCarorders(String carorders) {
		this.carorders = carorders;
	}
	public String getDropofforders() {
		return dropofforders;
	}
	public void setDropofforders(String dropofforders) {
		this.dropofforders = dropofforders;
	}
	public String getPickuporders() {
		return pickuporders;
	}
	public void setPickuporders(String pickuporders) {
		this.pickuporders = pickuporders;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAlluporders() {
		return alluporders;
	}
	public void setAlluporders(String alluporders) {
		this.alluporders = alluporders;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
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
	public String getOrdermoney() {
		return ordermoney;
	}
	public void setOrdermoney(String ordermoney) {
		this.ordermoney = ordermoney;
	}
	public String getDiffmoney() {
		return diffmoney;
	}
	public void setDiffmoney(String diffmoney) {
		this.diffmoney = diffmoney;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LePersonalorderstatisticsParam [id=" + id + ", leasescompanyid=" + leasescompanyid + ", cityid="
				+ cityid + ", city=" + city + ", ordertype=" + ordertype + ", paymethod=" + paymethod + ", ordernum="
				+ ordernum + ", confirmedorders=" + confirmedorders + ", processedorders=" + processedorders
				+ ", alluporders=" + alluporders + ", ordermoney=" + ordermoney + ", diffmoney=" + diffmoney + ", time="
				+ time + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + ", carorders="
				+ carorders + ", dropofforders=" + dropofforders + ", pickuporders=" + pickuporders + ", difforders="
				+ difforders + ", type=" + type + "]";
	}
	
}
