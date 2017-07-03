package com.szyciov.driver.param;

/**
 * @ClassName ChangeOrderStateParam 
 * @author Efy Shu
 * @Description  变更订单状态参数类
 * @date 2016年9月26日 上午10:30:16
 */
public class ChangeOrderStateParam extends BaseParam {
	/**
	 * 订单号(兼容一期)
	 */
	private String orderid;
	/**
	 * 订单状态(兼容一期)
	 */
	private String state;
	/**
	 * 订单号
	 */
	private String orderno;
	/**
	 * 订单状态
	 */
	private String orderstate;
	/**
	 * 订单状态
	 */
	private String orderstatus;
	/**
	 * 经度
	 */
	private double lng;
	/**
	 * 纬度
	 */
	private double lat;
	/**  
	 * 获取订单号  
	 * @return orderid 订单号  
	 */
	public String getOrderid() {
		return orderid;
	}
	
	/**  
	 * 设置订单号  
	 * @param orderid 订单号  
	 */
	public void setOrderid(String orderid) {
		this.orderno = orderid;
		this.orderid = orderid;
	}
	
	/**  
	 * 获取订单状态  
	 * @return state 订单状态  
	 */
	public String getState() {
		return state;
	}
	
	/**  
	 * 设置订单状态  
	 * @param state 订单状态  
	 */
	public void setState(String state) {
		this.orderstate = state;
		this.orderstatus = state;
		this.state = state;
	}
	
	/**  
	 * 获取经度  
	 * @return lng 经度  
	 */
	public double getLng() {
		return lng;
	}
	
	/**  
	 * 设置经度  
	 * @param lng 经度  
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	/**  
	 * 获取纬度  
	 * @return lat 纬度  
	 */
	public double getLat() {
		return lat;
	}
	
	/**  
	 * 设置纬度  
	 * @param lat 纬度  
	 */
	public void setLat(double lat) {
		this.lat = lat;
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
		this.orderid = orderno;
		this.orderno = orderno;
	}
	

	/**  
	 * 获取订单状态  
	 * @return orderstate 订单状态  
	 */
	public String getOrderstate() {
		return orderstate;
	}
	
	/**  
	 * 设置订单状态  
	 * @param orderstate 订单状态  
	 */
	public void setOrderstate(String orderstate) {
		this.state = orderstate;
		this.orderstatus = orderstate;
		this.orderstate = orderstate;
	}

	/**  
	 * 获取订单状态  
	 * @return orderstatus 订单状态  
	 */
	public String getOrderstatus() {
		return orderstatus;
	}
	
	/**  
	 * 设置订单状态  
	 * @param orderstatus 订单状态  
	 */
	public void setOrderstatus(String orderstatus) {
		this.orderstate = orderstatus;
		this.orderstatus = orderstatus;
		this.state = orderstatus;
	}
	
}
