package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

/**
 * 结算管理页面查询参数
 * @author xuxxtr
 *
 */
public class BalanceManageQueryParam extends QueryParam {

	/**
	 * 租赁公司ID
	 */
	private String lecompanyid;   
	/**
	 * 订单号
	 */
	public String orderno;
	
	/**
	 * 车牌号
	 */
	public String plateno;
	
	/**
	 * 资格证号
	 */
	public String jobnum;
	
	/**
	 * 司机
	 */
	public String driverid;

	/**
	 * 结算状态
	 */
	public String paymentstatus;
	
	/**
	 * 所属企业
	 */
	public String companyid;
	
	/**
	 * 交易流水号
	 */
	public String tradeno;
	
	/**
	 * 开始时间
	 */
	public String starttime;
	
	/**
	 * 结束时间
	 */
	public String endtime;

	public String getLecompanyid() {
		return lecompanyid;
	}

	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getJobnum() {
		return jobnum;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
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

	public BalanceManageQueryParam() {
		super();
	}
	
}
