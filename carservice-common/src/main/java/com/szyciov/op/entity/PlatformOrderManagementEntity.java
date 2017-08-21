package com.szyciov.op.entity;

public class PlatformOrderManagementEntity {
	private String ordersource;//订单来源
	private String orderno;//订单号
	private String usetype;//订单类型(用车类型)
	private String orderstatus;//订单状态
	private String paytype;//支付渠道
	private String orderamount;//订单金额(元)
	private String punishamount;//处罚金额(元)
	private String mileage;//里程(公里)
	private String runtime;//计费时长(分)
	private String orderpersonInfo;//下单人信息
	private String passengers;//乘车人信息
	private String driverInfo;//司机信息
	private String usetime;//用车时间
	private String onaddress;//上车地址
	private String offaddress;//下车地址
	private String leasecompany;//服务车企
	private String belongleasecompany;//订单归属
	private String ordernature;//订单性质
	private String canceler;//取消方
	private String tradeno;//交易流水号
	public String getOrdersource() {
		return ordersource;
	}
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getUsetype() {
		return usetype;
	}
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(String orderamount) {
		this.orderamount = orderamount;
	}
	public String getPunishamount() {
		return punishamount;
	}
	public void setPunishamount(String punishamount) {
		this.punishamount = punishamount;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getOrderpersonInfo() {
		return orderpersonInfo;
	}
	public void setOrderpersonInfo(String orderpersonInfo) {
		this.orderpersonInfo = orderpersonInfo;
	}
	public String getPassengers() {
		return passengers;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	public String getDriverInfo() {
		return driverInfo;
	}
	public void setDriverInfo(String driverInfo) {
		this.driverInfo = driverInfo;
	}
	public String getUsetime() {
		return usetime;
	}
	public void setUsetime(String usetime) {
		this.usetime = usetime;
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
	public String getLeasecompany() {
		return leasecompany;
	}
	public void setLeasecompany(String leasecompany) {
		this.leasecompany = leasecompany;
	}
	public String getBelongleasecompany() {
		return belongleasecompany;
	}
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}
	public String getOrdernature() {
		return ordernature;
	}
	public void setOrdernature(String ordernature) {
		this.ordernature = ordernature;
	}
	public String getCanceler() {
		return canceler;
	}
	public void setCanceler(String canceler) {
		this.canceler = canceler;
	}
	public String getTradeno() {
		return tradeno;
	}
	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}
	
}
