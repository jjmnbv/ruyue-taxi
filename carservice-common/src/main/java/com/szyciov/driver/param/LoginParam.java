package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

public class LoginParam  extends BaseParam {
	
	/**
	 * 手机号
	 */
	@SzycValid(rules={"checkNull","isMobile","checkUserCanLogin"})
	private String mobile;
	/**
	 * 登录密码
	 */
	@SzycValid(rules={"checkNull"})
	private String password;
	/**
	 * 登录类型 0-密码登录 1 短信登录
	 */
	@SzycValid(rules={"checkLoginType"})
	private int logintype;
	/**
	 * 登录设备 0-Web 1-Android 2-iOS
	 */
	private int device;
	/**
	 * 设备版本号
	 */
	private String deviceversion;
	/**
	 * app版本
	 */
	private String appversion;
	/**
	 * 设备品牌
	 */
	private String phonebrand;
	/**
	 * 设备型号
	 */
	private String phonemodel;
	
	/**
	 * 登录IP
	 */
	private String ipaddr;

	/**
	 * 设备唯一标识
	 */
	private String uuid;
	
	/**
	 * 密码是否加密传输
	 */
	private boolean encrypted = false;
	
	/**
	 * 极光推送消息ID
	 */
	@SzycValid(rules={"checkNull"})
	private String regid;
	
	/**
	 * 验证码错误次数校验标识
	 */
	@SzycValid(rules={"checkErrorTimes"})
	private boolean errorTimesSign;
	
	/**  
	 * 获取设备唯一标识  
	 * @return uuid 设备唯一标识  
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**  
	 * 设置设备唯一标识  
	 * @param uuid 设备唯一标识  
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**  
	 * 获取app版本  
	 * @return appversion app版本  
	 */
	public String getAppversion() {
		return appversion;
	}
	
	/**  
	 * 设置app版本  
	 * @param appversion app版本  
	 */
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	/**  
	 * 获取手机号  
	 * @return mobile 手机号  
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**  
	 * 设置手机号  
	 * @param mobile 手机号  
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	/**  
	 * 获取登录密码  
	 * @return password 登录密码  
	 */
	public String getPassword() {
		return password;
	}
	

	/**  
	 * 设置登录密码  
	 * @param password 登录密码  
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	/**  
	 * 获取登录类型0-密码登录1短信登录  
	 * @return logintype 登录类型0-密码登录1短信登录  
	 */
	public int getLogintype() {
		return logintype;
	}
	

	/**  
	 * 设置登录类型0-密码登录1短信登录  
	 * @param logintype 登录类型0-密码登录1短信登录  
	 */
	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}
	

	/**  
	 * 获取登录设备0-Web1-Android2-iOS  
	 * @return device 登录设备0-Web1-Android2-iOS  
	 */
	public int getDevice() {
		return device;
	}
	

	/**  
	 * 设置登录设备0-Web1-Android2-iOS  
	 * @param device 登录设备0-Web1-Android2-iOS  
	 */
	public void setDevice(int device) {
		this.device = device;
	}
	

	/**  
	 * 获取设备版本号  
	 * @return deviceversion 设备版本号  
	 */
	public String getDeviceversion() {
		return deviceversion;
	}
	

	/**  
	 * 设置设备版本号  
	 * @param deviceversion 设备版本号  
	 */
	public void setDeviceversion(String deviceversion) {
		this.deviceversion = deviceversion;
	}
	

	/**  
	 * 获取设备品牌  
	 * @return phonebrand 设备品牌  
	 */
	public String getPhonebrand() {
		return phonebrand;
	}
	

	/**  
	 * 设置设备品牌  
	 * @param phonebrand 设备品牌  
	 */
	public void setPhonebrand(String phonebrand) {
		this.phonebrand = phonebrand;
	}
	

	/**  
	 * 获取设备型号  
	 * @return phonemodel 设备型号  
	 */
	public String getPhonemodel() {
		return phonemodel;
	}
	

	/**  
	 * 设置设备型号  
	 * @param phonemodel 设备型号  
	 */
	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}

	/**  
	 * 获取登录IP  
	 * @return ipaddr 登录IP  
	 */
	public String getIpaddr() {
		return ipaddr;
	}

	/**  
	 * 设置登录IP  
	 * @param ipaddr 登录IP  
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	/**  
	 * 获取极光推送消息ID  
	 * @return regid 极光推送消息ID  
	 */
	public String getRegid() {
		return regid;
	}
	
	/**  
	 * 设置极光推送消息ID  
	 * @param regid 极光推送消息ID  
	 */
	public void setRegid(String regid) {
		this.regid = regid;
	}

	/**  
	 * 获取验证码错误次数校验标识  
	 * @return errorTimesSign 验证码错误次数校验标识  
	 */
	public boolean isErrorTimesSign() {
		return errorTimesSign;
	}
	
	/**  
	 * 设置验证码错误次数校验标识  
	 * @param errorTimesSign 验证码错误次数校验标识  
	 */
	public void setErrorTimesSign(boolean errorTimesSign) {
		this.errorTimesSign = errorTimesSign;
	}

	/**  
	 * 获取密码是否加密传输  
	 * @return encrypted 密码是否加密传输  
	 */
	public boolean isEncrypted() {
		return encrypted;
	}

	/**  
	 * 设置密码是否加密传输  
	 * @param encrypted 密码是否加密传输  
	 */
	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}
	
}
