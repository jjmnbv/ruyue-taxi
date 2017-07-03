package com.szyciov.param;

public class BaiduApiQueryParam {
	
	private double orderStartLat;
	
	private double orderStartLng;
	
	private double orderEndLat;
	
	private double orderEndLng;
	
	private String orderStartCityName;
	
	private String orderEndCityName;
	
	private double driverLat;
	
	private double driverLng;

	/**
	 * 获取经纬度的地址
	 */
	private String address;
	/**
	 * 地址所属城市
	 */
	private String city;
	
	
	
	/**  
	 * 获取获取经纬度的地址  
	 * @return address 获取经纬度的地址  
	 */
	public String getAddress() {
		return address;
	}
	

	/**  
	 * 设置获取经纬度的地址  
	 * @param address 获取经纬度的地址  
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	

	/**  
	 * 获取地址所属城市  
	 * @return city 地址所属城市  
	 */
	public String getCity() {
		return city;
	}
	

	/**  
	 * 设置地址所属城市  
	 * @param city 地址所属城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}
	

	public double getOrderStartLat() {
		return orderStartLat;
	}

	public void setOrderStartLat(double orderStartLat) {
		this.orderStartLat = orderStartLat;
	}

	public double getOrderStartLng() {
		return orderStartLng;
	}

	public void setOrderStartLng(double orderStartLng) {
		this.orderStartLng = orderStartLng;
	}

	public double getOrderEndLat() {
		return orderEndLat;
	}

	public void setOrderEndLat(double orderEndLat) {
		this.orderEndLat = orderEndLat;
	}

	public double getOrderEndLng() {
		return orderEndLng;
	}

	public void setOrderEndLng(double orderEndLng) {
		this.orderEndLng = orderEndLng;
	}

	public double getDriverLat() {
		return driverLat;
	}

	public void setDriverLat(double driverLat) {
		this.driverLat = driverLat;
	}

	public double getDriverLng() {
		return driverLng;
	}

	public void setDriverLng(double driverLng) {
		this.driverLng = driverLng;
	}

	public String getOrderStartCityName() {
		return orderStartCityName;
	}

	public void setOrderStartCityName(String orderStartCityName) {
		this.orderStartCityName = orderStartCityName;
	}

	public String getOrderEndCityName() {
		return orderEndCityName;
	}

	public void setOrderEndCityName(String orderEndCityName) {
		this.orderEndCityName = orderEndCityName;
	}
	
}
