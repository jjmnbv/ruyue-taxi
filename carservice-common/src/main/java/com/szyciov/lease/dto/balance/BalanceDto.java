package com.szyciov.lease.dto.balance;

/**
 * 结算管理页面展示实体
 * @author xuxxtr
 *
 */
public class BalanceDto {

	private String orderno;              //订单号
	private String fullplateno;          //车牌号
	private String driverphone;          //司机电话
	private String drivername;           //司机姓名
	private String jobnum;               //资格证号
	private String shortname;            //服务车企简称
	private Double orderamount;          //行程费用
	private String paymentstatus;        //支付状态
	private String paymenttime;          //支付时间
	private String settlementtime;       //结算时间
	private String paymenttype;          //订单交易记录支付渠道
	private String tradeno;              //流水号
	 
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getFullplateno() {
		return fullplateno;
	}

	public void setFullplateno(String fullplateno) {
		this.fullplateno = fullplateno;
	}

	public String getDriverphone() {
		return driverphone;
	}

	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getJobnum() {
		return jobnum;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Double getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getPaymenttime() {
		return paymenttime;
	}

	public void setPaymenttime(String paymenttime) {
		this.paymenttime = paymenttime;
	}

	public String getSettlementtime() {
		return settlementtime;
	}

	public void setSettlementtime(String settlementtime) {
		this.settlementtime = settlementtime;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public BalanceDto() {
		super();
	}
	
}
