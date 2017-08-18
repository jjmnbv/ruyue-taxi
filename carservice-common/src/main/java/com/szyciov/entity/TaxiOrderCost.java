package com.szyciov.entity;

public class TaxiOrderCost {
	
	/**
	 * 订单号
	 */
	private String orderno;
	 /**
	  * 起步价
	  */
	private Double startprice;
	
	/**
	 * 起租里程（公里）
	 */
	private Double startrange;
	
	/**
	 * 附加费
	 */
	private Double surcharge;
	
	/**
	 * 空驶费率
	 */
	private Double emptytravelrate;
	
	/**
	 * 标准里程（公里）
	 */
	private Double standardrange;
	
	/**
	 * 续租价
	 */
	private Double renewalprice;
	
	/**
	 * 行程费
	 */
	private Double rangecost;
	
	/**
	 * 调度费
	 */
	private Integer schedulefee;
	
	/**
	 * 总费用
	 */
	private Double cost;
	
	/**
	 * 行驶里程
	 */
	private Integer mileage;
	
	/**
	 * 时长
	 */
	private Integer times;
	
	/**
	 * 预约单附加费
	 */
	private Integer reversefee = 0;
	
	/**
	 * 优惠券面额
	 */
	private Integer couponprice = 0;
	
	/**
	 * 优惠券ID
	 */
	private String couponid;
	
	/**
	 * 溢价倍率
	 */
	private Double premiumrate = 1.0D;
	
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Double getStartprice() {
		return startprice;
	}

	public void setStartprice(Double startprice) {
		this.startprice = startprice;
	}

	public Double getStartrange() {
		return startrange;
	}

	public void setStartrange(Double startrange) {
		this.startrange = startrange;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public Double getEmptytravelrate() {
		return emptytravelrate;
	}

	public void setEmptytravelrate(Double emptytravelrate) {
		this.emptytravelrate = emptytravelrate;
	}

	public Double getStandardrange() {
		return standardrange;
	}

	public void setStandardrange(Double standardrange) {
		this.standardrange = standardrange;
	}

	public Double getRenewalprice() {
		return renewalprice;
	}

	public void setRenewalprice(Double renewalprice) {
		this.renewalprice = renewalprice;
	}

	public Double getRangecost() {
		return rangecost;
	}

	public void setRangecost(Double rangecost) {
		this.rangecost = rangecost;
	}

	public Integer getSchedulefee() {
		return schedulefee;
	}

	public void setSchedulefee(Integer schedulefee) {
		this.schedulefee = schedulefee;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	/**  
	 * 获取预约单附加费  
	 * @return reversefee 预约单附加费  
	 */
	public Integer getReversefee() {
		return reversefee;
	}
	
	/**  
	 * 设置预约单附加费  
	 * @param reversefee 预约单附加费  
	 */
	public void setReversefee(Integer reversefee) {
		this.reversefee = reversefee;
	}
	
	/**  
	 * 获取优惠券面额  
	 * @return couponprice 优惠券面额  
	 */
	public Integer getCouponprice() {
		return couponprice;
	}

	/**  
	 * 设置优惠券面额  
	 * @param couponprice 优惠券面额  
	 */
	public void setCouponprice(Integer couponprice) {
		this.couponprice = couponprice;
	}

	/**  
	 * 获取溢价倍率  
	 * @return premiumrate 溢价倍率  
	 */
	public Double getPremiumrate() {
		return premiumrate;
	}

	/**  
	 * 设置溢价倍率  
	 * @param premiumrate 溢价倍率  
	 */
	public void setPremiumrate(Double premiumrate) {
		this.premiumrate = premiumrate;
	}

	/**  
	 * 获取优惠券ID  
	 * @return couponid 优惠券ID  
	 */
	public String getCouponid() {
		return couponid;
	}

	/**  
	 * 设置优惠券ID  
	 * @param couponid 优惠券ID  
	 */
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	
}
