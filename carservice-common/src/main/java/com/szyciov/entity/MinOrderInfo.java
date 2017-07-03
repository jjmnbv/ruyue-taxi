/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName MinOrderInfo 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年12月3日 下午8:30:36 
 */
public class MinOrderInfo {
	
	/**
	 * 司机姓名
	 */
	private String drivername;
	/**
	 * 司机电话
	 */
	private String driverphone;
	/**
	 * 品牌车系
	 */
	private String carbrand;
	/**
	 * 车牌号
	 */
	private String plateno;
	/**
	 * 车型
	 */
	private String cartype;
	
	/**
	 * 支付方式
	 */
	private String paymethod;
	/**
	 * 订单类型
	 */
	private String ordertype;
	
	/**
	 * 用车类型
	 */
	private String usetype;
	
	/**  
	 * 获取司机姓名  
	 * @return drivername 司机姓名  
	 */
	public String getDrivername() {
		return drivername;
	}
	
	/**  
	 * 设置司机姓名  
	 * @param drivername 司机姓名  
	 */
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	
	/**  
	 * 获取司机电话  
	 * @return driverphone 司机电话  
	 */
	public String getDriverphone() {
		return driverphone;
	}
	
	/**  
	 * 设置司机电话  
	 * @param driverphone 司机电话  
	 */
	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}
	
	/**  
	 * 获取品牌车系  
	 * @return carbrand 品牌车系  
	 */
	public String getCarbrand() {
		return carbrand;
	}
	
	/**  
	 * 设置品牌车系  
	 * @param carbrand 品牌车系  
	 */
	public void setCarbrand(String carbrand) {
		this.carbrand = carbrand;
	}
	
	/**  
	 * 获取车牌号  
	 * @return plateno 车牌号  
	 */
	public String getPlateno() {
		return plateno;
	}
	
	/**  
	 * 设置车牌号  
	 * @param plateno 车牌号  
	 */
	public void setPlateno(String plateno) {
		this.plateno = plateno;
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
	 * 获取支付方式  
	 * @return paymethod 支付方式  
	 */
	public String getPaymethod() {
		return paymethod;
	}

	/**  
	 * 设置支付方式  
	 * @param paymethod 支付方式  
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
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
	 * 获取用车类型  
	 * @return usetype 用车类型  
	 */
	public String getUsetype() {
		return usetype;
	}
	

	/**  
	 * 设置用车类型  
	 * @param usetype 用车类型  
	 */
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	
}
