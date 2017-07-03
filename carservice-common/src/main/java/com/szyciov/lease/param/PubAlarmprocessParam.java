package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class PubAlarmprocessParam extends QueryParam{
		public PubAlarmprocessParam(){
	}
		private String id; //id
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
		private String driver; //所属司机
		private String processstatus;//处理状态
		private String processresult;//处理结果
		private String processrecord;//处理记录
		private String processtime;//处理时间
		private String processor;//处理人
		private String createtime;//创建时间
		private String updatetime;//更新时间
		private String status;//数据状态
		private String creater;//创建人
		private String updater;//更新人
		private String passenger; //乘客
		private String startTime; //开始时间
		private String endTime; //结束时间
		private String plateno; //车牌号
		private String code; //编号
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getPassenger() {
			return passenger;
		}
		public String getPlateno() {
			return plateno;
		}
		public void setPlateno(String plateno) {
			this.plateno = plateno;
		}
		public void setPassenger(String passenger) {
			this.passenger = passenger;
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
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getProcessstatus() {
			return processstatus;
		}
		public void setProcessstatus(String processstatus) {
			this.processstatus = processstatus;
		}
		public String getProcessresult() {
			return processresult;
		}
		public void setProcessresult(String processresult) {
			this.processresult = processresult;
		}
		public String getProcessrecord() {
			return processrecord;
		}
		public void setProcessrecord(String processrecord) {
			this.processrecord = processrecord;
		}
		public String getProcesstime() {
			return processtime;
		}
		public void setProcesstime(String processtime) {
			this.processtime = processtime;
		}
		public String getProcessor() {
			return processor;
		}
		public void setProcessor(String processor) {
			this.processor = processor;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCreater() {
			return creater;
		}
		public void setCreater(String creater) {
			this.creater = creater;
		}
		public String getUpdater() {
			return updater;
		}
		public void setUpdater(String updater) {
			this.updater = updater;
		}
		@Override
		public String toString() {
			return "PubAlarmprocessParam [id=" + id + ", platformtype=" + platformtype + ", leasecompanyid="
					+ leasecompanyid + ", usertype=" + usertype + ", ordertype=" + ordertype + ", userid=" + userid
					+ ", alarmsource=" + alarmsource + ", alarmtype=" + alarmtype + ", alarmtime=" + alarmtime
					+ ", orderno=" + orderno + ", driverid=" + driverid + ", processstatus=" + processstatus
					+ ", processresult=" + processresult + ", processrecord=" + processrecord + ", processtime="
					+ processtime + ", processor=" + processor + ", createtime=" + createtime + ", updatetime="
					+ updatetime + ", status=" + status + ", creater=" + creater + ", updater=" + updater
					+ ", passenger=" + passenger + ", startTime=" + startTime + ", endTime=" + endTime + "]";
		}
		
}
