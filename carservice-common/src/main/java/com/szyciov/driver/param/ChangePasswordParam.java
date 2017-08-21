package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;
import com.szyciov.enums.DriverEnum;

public class ChangePasswordParam extends BaseParam {
	/**
	 * 司机ID
	 */
	private String id;
	/**
	 * 司机手机号
	 */
	@SzycValid(rules={"checkNull","isMobile","checkUserCanLogin","checkAuthToken"})
	private String mobile;
	/**
	 * 密码
	 */
	@SzycValid(rules={"checkNull"})
	private String password;
	/**
	 * 0-登录密码 1-提现密码
	 */
	@SzycValid(rules={"checkNull","checkPasswordType"})
	private String type = DriverEnum.PASSWORD_TYPE_LOGINPASS.code;
	/**
	 * 密码是否加密传输
	 */
	private boolean encrypted = false;
	
	/**  
	 * 获取司机ID  
	 * @return id 司机ID  
	 */
	public String getId() {
		return id;
	}
	
	/**  
	 * 设置司机ID  
	 * @param id 司机ID  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * 获取密码  
	 * @return password 密码  
	 */
	public String getPassword() {
		return password;
	}
	
	/**  
	 * 设置密码  
	 * @param password 密码  
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * 获取0-登录密码1-提现密码  
	 * @return type 0-登录密码1-提现密码  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置0-登录密码1-提现密码  
	 * @param type 0-登录密码1-提现密码  
	 */
	public void setType(String type) {
		this.type = type;
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

	/**  
	 * 获取司机手机号  
	 * @return mobile 司机手机号  
	 */
	public String getMobile() {
		return mobile;
	}
	

	/**  
	 * 设置司机手机号  
	 * @param mobile 司机手机号  
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
