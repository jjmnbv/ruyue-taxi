package com.szyciov.passenger.param;

public class LoginParam {

	/**
	 * 用户类型
	 * 1-机构用户
	 * 2-个人用户
	 */
	private String usertype;
	
	/**
	 * 登录类型
	 * 1-密码登录
	 * 2-验证码登录
	 */
	private String logintype;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 登录凭证
	 * 当时密码登录时是密码，当验证码登录时是验证码
	 */
	private String validatecode;
	
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
	 * app的版本号
	 */
	private String appversion;

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
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
