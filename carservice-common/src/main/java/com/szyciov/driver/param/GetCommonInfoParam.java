package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

public class GetCommonInfoParam extends BaseParam {
	/**
	 * 客户端版本号
	 */
	private int appversion;
	
	/**
	 * 系统类型(0-iOS 1-Android)
	 */
	@SzycValid(rules={"checkNull","checkSystemType"})
	private String systemtype;
	
	/**
	 * 引导页版本
	 */
	private String version;
	
	/**  
	 * 获取系统类型(0-iOS1-Android)  
	 * @return systemtype 系统类型(0-iOS1-Android)  
	 */
	public String getSystemtype() {
		return systemtype;
	}

	/**  
	 * 设置系统类型(0-iOS1-Android)  
	 * @param systemtype 系统类型(0-iOS1-Android)  
	 */
	public void setSystemtype(String systemtype) {
		this.systemtype = systemtype;
	}

	/**  
	 * 获取客户端版本号  
	 * @return appversion 客户端版本号  
	 */
	public int getAppversion() {
		return appversion;
	}
	
	/**  
	 * 设置客户端版本号  
	 * @param appversion 客户端版本号  
	 */
	public void setAppversion(int appversion) {
		this.appversion = appversion;
	}

	/**  
	 * 获取引导页版本  
	 * @return version 引导页版本  
	 */
	public String getVersion() {
		return version;
	}
	
	/**  
	 * 设置引导页版本  
	 * @param version 引导页版本  
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
}
