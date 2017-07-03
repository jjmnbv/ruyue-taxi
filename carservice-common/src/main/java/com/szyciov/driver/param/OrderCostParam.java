package com.szyciov.driver.param;

import com.szyciov.param.OrderApiParam;

/**
 * @ClassName OrderCostParam 
 * @author Efy Shu
 * @Description 获取订单费用参数类(预估,实时,完成都使用此类)
 * @date 2016年9月22日 下午5:39:06
 */
public class OrderCostParam extends OrderApiParam{
	/**
	 * 车型
	 */
	private String cartype;
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 机构ID
	 */
	private String organid;
	/**
	 * 租赁公司
	 */
	private String companyid;
	/**
	 * 订单类型
	 */
	private String ordertype;
	/**
	 * 所属城市
	 */
	private String city;
	/**
	 * 用车类型(0-因公用车 1-因私用车)
	 */
	private String usetype;
	/**
	 * 计费规则(0-标准 1-个性化)
	 */
	private String rulestype;
	/**
	 * 距离(公里)
	 */
	private double distance;
	
	/**
	 * 时长(分钟)
	 */
	private int duration;
	
	/**
	 * 上车经度
	 */
	private double onaddrlng;
	/**
	 * 上车纬度
	 */
	private double onaddrlat;
	/**
	 * 下车经度
	 */
	private double offaddrlng;
	/**
	 * 下车纬度
	 */
	private double offaddrlat;
	
	/**
	 * 返回结果是否带上单位(自动换算)
	 */
	private boolean hasunit = true;
	
	/**
	 * 调度费
	 */
	private Integer schedulefee;
	
	/**
	 * 打表来接里程
	 */
	private Integer meterrange;
	
	/**  
	 * 获取计费规则(0-标准1-个性化)  
	 * @return rulestype 计费规则(0-标准1-个性化)  
	 */
	public String getRulestype() {
		return rulestype;
	}

	/**  
	 * 设置计费规则(0-标准1-个性化)  
	 * @param rulestype 计费规则(0-标准1-个性化)  
	 */
	public void setRulestype(String rulestype) {
		this.rulestype = rulestype;
	}

	/**  
	 * 获取用户ID  
	 * @return userid 用户ID  
	 */
	public String getUserid() {
		return userid;
	}

	/**  
	 * 设置用户ID  
	 * @param userid 用户ID  
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**  
	 * 获取返回结果是否带上单位  
	 * @return hasunit 返回结果是否带上单位  
	 */
	public boolean isHasunit() {
		return hasunit;
	}

	/**  
	 * 设置返回结果是否带上单位  
	 * @param hasunit 返回结果是否带上单位  
	 */
	public void setHasunit(boolean hasunit) {
		this.hasunit = hasunit;
	}
	
	/**  
	 * 获取距离(公里)  
	 * @return distance 距离(公里)  
	 */
	public double getDistance() {
		return distance;
	}

	/**  
	 * 设置距离(公里)  
	 * @param distance 距离(公里)  
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**  
	 * 获取时长(分钟)  
	 * @return duration 时长(分钟)  
	 */
	public int getDuration() {
		return duration;
	}
	
	/**  
	 * 设置时长(分钟)  
	 * @param duration 时长(分钟)  
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	

	/**  
	 * 获取机构ID  
	 * @return organid 机构ID  
	 */
	public String getOrganid() {
		return organid;
	}
	
	/**  
	 * 设置机构ID  
	 * @param organid 机构ID  
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**  
	 * 获取车型  
	 * @return cartype 车型  
	 */
	public String getCartype() {
		return cartype;
	}
	
	/**  
	 * 设置车型  
	 * @param cartype 车型  
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	
	/**  
	 * 获取租赁公司  
	 * @return companyid 租赁公司  
	 */
	public String getCompanyid() {
		return companyid;
	}
	
	/**  
	 * 设置租赁公司  
	 * @param companyid 租赁公司  
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	/**  
	 * 获取订单类型  
	 * @return ordertype 订单类型  
	 */
	public String getOrdertype() {
		return ordertype;
	}
	
	/**  
	 * 设置订单类型  
	 * @param ordertype 订单类型  
	 */
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	
	/**  
	 * 获取所属城市  
	 * @return city 所属城市  
	 */
	public String getCity() {
		return city;
	}
	
	/**  
	 * 设置所属城市  
	 * @param city 所属城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**  
	 * 获取用车类型(0-因公用车1-因私用车)  
	 * @return usetype 用车类型(0-因公用车1-因私用车)  
	 */
	public String getUsetype() {
		return usetype;
	}
	
	/**  
	 * 设置用车类型(0-因公用车1-因私用车)  
	 * @param usetype 用车类型(0-因公用车1-因私用车)  
	 */
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	/**  
	 * 获取上车经度  
	 * @return onaddrlng 上车经度  
	 */
	public double getOnaddrlng() {
		return onaddrlng;
	}
	
	/**  
	 * 设置上车经度  
	 * @param onaddrlng 上车经度  
	 */
	public void setOnaddrlng(double onaddrlng) {
		this.onaddrlng = onaddrlng;
	}
	
	/**  
	 * 获取上车纬度  
	 * @return onaddrlat 上车纬度  
	 */
	public double getOnaddrlat() {
		return onaddrlat;
	}
	
	/**  
	 * 设置上车纬度  
	 * @param onaddrlat 上车纬度  
	 */
	public void setOnaddrlat(double onaddrlat) {
		this.onaddrlat = onaddrlat;
	}
	
	/**  
	 * 获取下车经度  
	 * @return offaddrlng 下车经度  
	 */
	public double getOffaddrlng() {
		return offaddrlng;
	}
	
	/**  
	 * 设置下车经度  
	 * @param offaddrlng 下车经度  
	 */
	public void setOffaddrlng(double offaddrlng) {
		this.offaddrlng = offaddrlng;
	}
	
	/**  
	 * 获取下车纬度  
	 * @return offaddrlat 下车纬度  
	 */
	public double getOffaddrlat() {
		return offaddrlat;
	}
	
	/**  
	 * 设置下车纬度  
	 * @param offaddrlat 下车纬度  
	 */
	public void setOffaddrlat(double offaddrlat) {
		this.offaddrlat = offaddrlat;
	}

	public Integer getSchedulefee() {
		return schedulefee;
	}

	public void setSchedulefee(Integer schedulefee) {
		this.schedulefee = schedulefee;
	}

	public Integer getMeterrange() {
		return meterrange;
	}

	public void setMeterrange(Integer meterrange) {
		this.meterrange = meterrange;
	}
}
