package com.szyciov.passenger.param;

public class RegisterParam {

	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 登录设备
	 * 0-pc
	 * 1-Android
	 * 2-ios
	 */
	private String device;
	
	/**
	 * 登录版本
	 */
	private String version;
	
	/**
	 * 登录手机品牌
	 */
	private String phonebrand;
	
	/**
	 * 登录手机型号
	 */
	private String phonemodel;
	
	/**
	 * 浏览器版本
	 */
	private String browserversion;
	
	/**
	 * 浏览器类型
	 */
	private String browsertype;
	
	/**
	 * 设备唯一id
	 */
	private String uuid;
	
	/**
	 * app版本号
	 */
	private String appversion;

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPhonebrand() {
		return phonebrand;
	}

	public void setPhonebrand(String phonebrand) {
		this.phonebrand = phonebrand;
	}

	public String getPhonemodel() {
		return phonemodel;
	}

	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}

	public String getBrowserversion() {
		return browserversion;
	}

	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}

	public String getBrowsertype() {
		return browsertype;
	}

	public void setBrowsertype(String browsertype) {
		this.browsertype = browsertype;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
