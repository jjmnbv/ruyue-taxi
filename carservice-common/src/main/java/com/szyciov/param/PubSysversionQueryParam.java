package com.szyciov.param;

public class PubSysversionQueryParam extends QueryParam {
	
	/**
	 * 终端类型
	 */
	private String platformtype;
	
	/**
	 * 当前版本号
	 */
	private String curversion;
	
	/**
	 * 适用系统
	 */
	private String systemtype;
	
	/**
	 * 开始查询时间
	 */
	private String startTime;
	
	/**
	 * 结束查询时间
	 */
	private String endTime;

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getCurversion() {
		return curversion;
	}

	public void setCurversion(String curversion) {
		this.curversion = curversion;
	}

	public String getSystemtype() {
		return systemtype;
	}

	public void setSystemtype(String systemtype) {
		this.systemtype = systemtype;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
