package com.szyciov.param;

import com.szyciov.driver.param.BaseParam;

public class TaxiDriverInBoundParam extends BaseParam{
	
	/**
	 * 租赁公司或者运管公司id
	 */
	private String companyid;
	
	/**
	 * 下单人ID
	 */
	private String userid;
	
	/**
	 * 订单所属城市
	 */
	private String city;

	/**
	 * 南侧经度
	 */
	private double minLng;
	
	/**
	 * 北侧经度
	 */
	private double maxLng;
	
	/**
	 * 西侧纬度
	 */
	private double minLat;
	
	/**
	 * 东侧纬度
	 */
	private double maxLat;
	
	/**
	 * 下车城市，获取司机的时候，上下车城市只要有一个在经营范围内就可以
	 */
	private String offcity;

	/** 服务车企 **/
	private String belongleasecompany;

    public String getBelongleasecompany() {
        return belongleasecompany;
    }

    public void setBelongleasecompany(String belongleasecompany) {
        this.belongleasecompany = belongleasecompany;
    }

    public double getMinLng() {
		return minLng;
	}

	public void setMinLng(double minLng) {
		this.minLng = minLng;
	}

	public double getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(double maxLng) {
		this.maxLng = maxLng;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOffcity() {
		return offcity;
	}

	public void setOffcity(String offcity) {
		this.offcity = offcity;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
