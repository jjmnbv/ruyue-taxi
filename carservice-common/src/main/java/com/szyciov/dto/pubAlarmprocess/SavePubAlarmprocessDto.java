package com.szyciov.dto.pubAlarmprocess;

public class SavePubAlarmprocessDto {
	private String platformtype; //数据所属平台
	private String leasecompanyid; //所属租赁公司
	private String usertype; //用户类型
	private String ordertype; //订单类型
	private String userid; //所属用户
	private String alarmsource; //报警来源
	private String alarmtype; //报警类型
	private String alarmtime; //报警时间
	private String orderno; //所属订单
	private String driverid; //所属司机id
	private String lng; //经度
	private String lat; //纬度
	private String creater;
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}
	public String getLeasecompanyid() {
		return leasecompanyid;
	}
	public void setLeasecompanyid(String leasecompanyid) {
		this.leasecompanyid = leasecompanyid;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAlarmsource() {
		return alarmsource;
	}
	public void setAlarmsource(String alarmsource) {
		this.alarmsource = alarmsource;
	}
	public String getAlarmtype() {
		return alarmtype;
	}
	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}
	public String getAlarmtime() {
		return alarmtime;
	}
	public void setAlarmtime(String alarmtime) {
		this.alarmtime = alarmtime;
	}
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
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	

}
