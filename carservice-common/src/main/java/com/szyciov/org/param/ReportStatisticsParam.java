package com.szyciov.org.param;

import com.szyciov.param.QueryParam;

public class ReportStatisticsParam extends QueryParam {
	private String rownum;
	/**
	 * 所属机构id
	 *
	 */
	private String organid;
	/**
	 * 所属租赁公司id
	 */
	private String leasescompanyid;
	/**
	 * 所属租赁公司名称
	 */
	private String leasescompanyName;
	/**
	 * 订单类型
	 */
	private String ordertype;
	/**
	 * 订单数量
	 */
	private String ordernum;
	/**
	 * 订单金额
	 */
	private String ordermoney;
	/**
	 * 开始时间
	 */
	private String starttime;
    /**
     * 结束时间
     */
	private String endtime;
	/**
	 * 部门id
	 */
	private String deptid;
	/**
	 * 部门名称
	 */
	private String deptName;
	private String key;
	
	public String getRownum() {
		return rownum;
	}
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOrganid() {
		return organid;
	}
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getLeasescompanyName() {
		return leasescompanyName;
	}
	public void setLeasescompanyName(String leasescompanyName) {
		this.leasescompanyName = leasescompanyName;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getOrdermoney() {
		return ordermoney;
	}
	public void setOrdermoney(String ordermoney) {
		this.ordermoney = ordermoney;
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
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	

}
