package com.szyciov.entity;

/**
 * 城市信息表
 * @author admin
 *
 */
public class City {

	private String id;
	
	private String city;
	
	private String cityInitials;
	
	private String createtime;
	
	private String updatetime;
	
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityInitials() {
		return cityInitials;
	}

	public void setCityInitials(String cityInitials) {
		this.cityInitials = cityInitials;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
