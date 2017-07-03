package com.szyciov.op.param;

import com.szyciov.param.QueryParam;
/**
 * 销售管理
 * @author zhouwu
 *
 */
public class OpOrderstatisticsParam extends QueryParam {
	private String id;
	/**
	 * 所属机构
	 */
	private String leasescompanyid;
	/**
	 *  公司名称
	 */
	private String compayName;
	/**
	 * 订单类型 1-约车，2-接机，3-送机
	 */
	private String ordertype;
	/**
	 * 订单种类 0-月度，1-季度，2-年度
	 */
	private String type;
	/**
	 * 已支付
	 */
	private String paidorders;
	/**
	 * 未支付
	 */
	private String bepaidorders;
	/**
	 * 订单总量
	 */
	private String orders;
	/**
	 * 约车
	 */
	private String carorders;
	/**
	 * 接机
	 */
	private String pickuporders;
    /**
     * 送机
     */
	private String dropofforders;
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
	 * 用户id
	 * 
	 */
	private String userid;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPaidorders() {
		return paidorders;
	}
	public void setPaidorders(String paidorders) {
		this.paidorders = paidorders;
	}
	public String getBepaidorders() {
		return bepaidorders;
	}
	public void setBepaidorders(String bepaidorders) {
		this.bepaidorders = bepaidorders;
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
	
	public String getLeasescompanyid() {
		return leasescompanyid;
	}
	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}
	public String getCompayName() {
		return compayName;
	}
	public void setCompayName(String compayName) {
		this.compayName = compayName;
	}
	
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getCarorders() {
		return carorders;
	}
	public void setCarorders(String carorders) {
		this.carorders = carorders;
	}
	public String getPickuporders() {
		return pickuporders;
	}
	public void setPickuporders(String pickuporders) {
		this.pickuporders = pickuporders;
	}
	public String getDropofforders() {
		return dropofforders;
	}
	public void setDropofforders(String dropofforders) {
		this.dropofforders = dropofforders;
	}
	@Override
	public String toString() {
		return "OpOrderstatisticsParam [id=" + id + ", leasescompanyid=" + leasescompanyid + ", compayName="
				+ compayName + ", ordertype=" + ordertype + ", type=" + type + ", paidorders=" + paidorders
				+ ", bepaidorders=" + bepaidorders + ", orders=" + orders + ", carorders=" + carorders
				+ ", pickuporders=" + pickuporders + ", dropofforders=" + dropofforders + ", ordermoney=" + ordermoney
				+ ", starttime=" + starttime + ", endtime=" + endtime + "]";
	}
	

}
