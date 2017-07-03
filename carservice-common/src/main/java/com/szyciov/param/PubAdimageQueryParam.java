package com.szyciov.param;

public class PubAdimageQueryParam extends QueryParam {
	
	/**
	 * 图片类型
	 */
	private String imgtype;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 开始时间
	 */
	private String starttime;
	
	/**
	 * 结束时间
	 */
	private String endtime;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * App类型
	 */
	private String apptype;

	public String getImgtype() {
		return imgtype;
	}

	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

}
