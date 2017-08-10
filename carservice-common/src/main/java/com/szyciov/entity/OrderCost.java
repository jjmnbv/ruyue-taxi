package com.szyciov.entity;

import com.szyciov.util.StringUtil;

/**
 * @ClassName GetCost
 * @author Efy Shu
 * @Description 计算费用实体类
 * <p>计算公式:</p>
 * <p>起步价+时长*每分钟单价+行驶公里数*每公里单价</p>
 * @date 2016年9月21日 下午4:12:29
 */
public class OrderCost{
	/**
	 * 订单号
	 */
	private String orderno;
	/**
	 * 时长
	 */
	private int times;
	/**
	 * 行驶公里数
	 */
	private double mileage;
	/**
	 * 起步价
	 */
	private double startprice;
	/**
	 * 每公里单价
	 */
	private double rangeprice;
	/**
	 * 每分钟单价
	 */
	private double timeprice;
	/**
	 * 总费用
	 */
	private double cost;
	/**
	 * 公里数总费用
	 */
	private double rangecost;
	/**
	 * 时长总费用
	 */
	private double timecost;
	
	/**
	 * 夜间费
	 */
	private double nightcost;
	
	/**
	 * 空驶费
	 */
	private double deadheadcost;
	
	/**
	 * 计费时间类型(0-总用时 1-低速用时)
	 */
	private int timetype;
	
	/**
	 * 低速用时限定时速
	 */
	private double perhour;
	
	/**
	 * 低速累计时长(分钟)
	 */
	private int slowtimes;
	
	/**
	 * 里程类型
	 */
	private Integer costtype;
	
	/**
	 * 回空里程(公里)
	 */
	private double deadheadmileage;
	
	/**
	 * 实际参与计费的回空里程
	 */
	private double realdeadheadmileage;
	
	/**
	 * 回空费价
	 */
	private double deadheadprice;
	/**
	 * 夜间服务开始时间
	 */
	private String nightstarttime;
	/**
	 * 夜间服务结束时间
	 */
	private String nightendtime;
	/**
	 * 夜间费价
	 */
	private double nighteprice;
	/**
	 * 预约附加费
	 */
	private double reversefee;
	/**
	 * 订单折扣
	 */
	private double discount;
	/**
	 * 溢价倍率
	 */
	private double premiumrate = 1.0D;
	
	/**  
	 * 获取低速用时限定时速  
	 * @return perhour 低速用时限定时速  
	 */
	public double getPerhour() {
		return perhour;
	}

	/**  
	 * 设置低速用时限定时速  
	 * @param perhour 低速用时限定时速  
	 */
	public void setPerhour(double perhour) {
		this.perhour = perhour;
	}
	
	/**  
	 * 获取低速累计时长(分钟)  
	 * @return slowtimes 低速累计时长(分钟)  
	 */
	public int getSlowtimes() {
		return slowtimes;
	}
	
	/**  
	 * 设置低速累计时长(分钟)  
	 * @param slowtimes 低速累计时长(分钟)  
	 */
	public void setSlowtimes(int slowtimes) {
		this.slowtimes = slowtimes;
	}

	/**  
	 * 获取计费时间类型(0-总用时1-低速用时)  
	 * @return timetype 计费时间类型(0-总用时1-低速用时)  
	 */
	public int getTimetype() {
		return timetype;
	}

	/**  
	 * 设置计费时间类型(0-总用时1-低速用时)  
	 * @param timetype 计费时间类型(0-总用时1-低速用时)  
	 */
	public void setTimetype(int timetype) {
		this.timetype = timetype;
	}
	
	/**  
	 * 获取订单号  
	 * @return orderno 订单号  
	 */
	public String getOrderno() {
		return orderno;
	}
	
	/**  
	 * 设置订单号  
	 * @param orderno 订单号  
	 */
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	/**  
	 * 获取时长  
	 * @return times 时长  
	 */
	public int getTimes() {
		return times;
	}
	
	/**  
	 * 设置时长  
	 * @param times 时长  
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	
	/**  
	 * 获取行驶公里数  
	 * @return mileage 行驶公里数  
	 */
	public double getMileage() {
		mileage = StringUtil.formatNum(mileage, 1);
		return mileage;
	}
	
	/**  
	 * 设置行驶公里数  
	 * @param mileage 行驶公里数  
	 */
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	
	/**  
	 * 获取起步价  
	 * @return startprice 起步价  
	 */
	public double getStartprice() {
		startprice = StringUtil.formatNum(startprice, 1);
		return startprice;
	}
	
	/**  
	 * 设置起步价  
	 * @param startprice 起步价  
	 */
	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}
	
	/**  
	 * 获取每公里单价  
	 * @return rangeprice 每公里单价  
	 */
	public double getRangeprice() {
		rangeprice = StringUtil.formatNum(rangeprice, 1);
		return rangeprice;
	}
	
	/**  
	 * 设置每公里单价  
	 * @param rangeprice 每公里单价  
	 */
	public void setRangeprice(double rangeprice) {
		this.rangeprice = rangeprice;
	}
	
	/**  
	 * 获取每分钟单价  
	 * @return timeprice 每分钟单价  
	 */
	public double getTimeprice() {
		timeprice = StringUtil.formatNum(timeprice, 1);
		return timeprice;
	}
	
	/**  
	 * 设置每分钟单价  
	 * @param timeprice 每分钟单价  
	 */
	public void setTimeprice(double timeprice) {
		this.timeprice = timeprice;
	}
	
	/**  
	 * 获取总费用  
	 * @return cost 总费用  
	 */
	public double getCost() {
		cost = StringUtil.formatNum(cost, 1);
		return cost;
	}
	
	/**  
	 * 设置总费用  
	 * @param cost 总费用  
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**  
	 * 获取公里数总费用  
	 * @return rangecost 公里数总费用  
	 */
	public double getRangecost() {
		rangecost = StringUtil.formatNum(rangecost, 1);
		return rangecost;
	}
	
	/**  
	 * 设置公里数总费用  
	 * @param rangecost 公里数总费用  
	 */
	public void setRangecost(double rangecost) {
		this.rangecost = rangecost;
	}
	
	/**  
	 * 获取时长总费用  
	 * @return timecost 时长总费用  
	 */
	public double getTimecost() {
		timecost = StringUtil.formatNum(timecost, 1);
		return timecost;
	}
	
	/**  
	 * 设置时长总费用  
	 * @param timecost 时长总费用  
	 */
	public void setTimecost(double timecost) {
		this.timecost = timecost;
	}

	/**  
	 * 获取里程类型  
	 * @return costtype 里程类型  
	 */
	public Integer getCosttype() {
		return costtype;
	}

	/**  
	 * 设置里程类型  
	 * @param costtype 里程类型  
	 */
	public void setCosttype(Integer costtype) {
		this.costtype = costtype;
	}

	/**  
	 * 获取回空里程(公里)  
	 * @return deadheadmileage 回空里程(公里)  
	 */
	public double getDeadheadmileage() {
		return deadheadmileage;
	}

	/**  
	 * 设置回空里程(公里)  
	 * @param deadheadmileage 回空里程(公里)  
	 */
	public void setDeadheadmileage(double deadheadmileage) {
		this.deadheadmileage = deadheadmileage;
	}

	/**  
	 * 获取实际参与计费的回空里程  
	 * @return realdeadheadmileage 实际参与计费的回空里程  
	 */
	public double getRealdeadheadmileage() {
		realdeadheadmileage = StringUtil.formatNum(realdeadheadmileage, 1);
		return realdeadheadmileage;
	}

	/**  
	 * 设置实际参与计费的回空里程  
	 * @param realdeadheadmileage 实际参与计费的回空里程  
	 */
	public void setRealdeadheadmileage(double realdeadheadmileage) {
		this.realdeadheadmileage = realdeadheadmileage;
	}
	
	/**  
	 * 获取回空费价  
	 * @return deadheadprice 回空费价  
	 */
	public double getDeadheadprice() {
		return deadheadprice;
	}
	
	/**  
	 * 设置回空费价  
	 * @param deadheadprice 回空费价  
	 */
	public void setDeadheadprice(double deadheadprice) {
		this.deadheadprice = deadheadprice;
	}

	/**  
	 * 获取夜间服务开始时间  
	 * @return nightstarttime 夜间服务开始时间  
	 */
	public String getNightstarttime() {
		return nightstarttime;
	}

	/**  
	 * 设置夜间服务开始时间  
	 * @param nightstarttime 夜间服务开始时间  
	 */
	public void setNightstarttime(String nightstarttime) {
		this.nightstarttime = nightstarttime;
	}

	/**  
	 * 获取夜间服务结束时间  
	 * @return nightendtime 夜间服务结束时间  
	 */
	public String getNightendtime() {
		return nightendtime;
	}
	
	/**  
	 * 设置夜间服务结束时间  
	 * @param nightendtime 夜间服务结束时间  
	 */
	public void setNightendtime(String nightendtime) {
		this.nightendtime = nightendtime;
	}
	
	/**  
	 * 获取夜间费价  
	 * @return nighteprice 夜间费价  
	 */
	public double getNighteprice() {
		return nighteprice;
	}
	
	/**  
	 * 设置夜间费价  
	 * @param nighteprice 夜间费价  
	 */
	public void setNighteprice(double nighteprice) {
		this.nighteprice = nighteprice;
	}

	/**  
	 * 获取夜间费  
	 * @return nightcost 夜间费  
	 */
	public double getNightcost() {
		nightcost = StringUtil.formatNum(nightcost, 1);
		return nightcost;
	}
	
	/**  
	 * 设置夜间费  
	 * @param nightcost 夜间费  
	 */
	public void setNightcost(double nightcost) {
		this.nightcost = nightcost;
	}

	/**  
	 * 获取空驶费  
	 * @return deadheadcost 空驶费  
	 */
	public double getDeadheadcost() {
		deadheadcost = StringUtil.formatNum(deadheadcost, 1);
		return deadheadcost;
	}
	
	/**  
	 * 设置空驶费  
	 * @param deadheadcost 空驶费  
	 */
	public void setDeadheadcost(double deadheadcost) {
		this.deadheadcost = deadheadcost;
	}

	/**  
	 * 获取预约附加费  
	 * @return reversefee 预约附加费  
	 */
	public double getReversefee() {
		return reversefee;
	}
	
	/**  
	 * 设置预约附加费  
	 * @param reversefee 预约附加费  
	 */
	public void setReversefee(double reversefee) {
		this.reversefee = reversefee;
	}

	/**  
	 * 获取订单折扣  
	 * @return discount 订单折扣  
	 */
	public double getDiscount() {
		return discount;
	}

	/**  
	 * 设置订单折扣  
	 * @param discount 订单折扣  
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**  
	 * 获取溢价倍率  
	 * @return premiumrate 溢价倍率  
	 */
	public double getPremiumrate() {
		return premiumrate;
	}

	/**  
	 * 设置溢价倍率  
	 * @param premiumrate 溢价倍率  
	 */
	public void setPremiumrate(double premiumrate) {
		this.premiumrate = premiumrate;
	}
	
}
