package com.szyciov.passenger.entity;

public class MostAddress {
	
	private String id;
	
	private String addresstype;
	
	private String address;
	
	/**
	  *地址经度
	  */
	private double lng;

	/**
	  *地址纬度
	  */
	private double lat;
	
	/**
	 * 地址的标题
	 */
	private String title;
	
	/**
	 * 常用地址城市
	 */
	private String city;

	public String getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(String addresstype) {
		this.addresstype = addresstype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
