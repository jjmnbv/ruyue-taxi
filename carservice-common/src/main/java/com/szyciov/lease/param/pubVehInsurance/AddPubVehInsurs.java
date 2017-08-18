/**
 * 
 */
package com.szyciov.lease.param.pubVehInsurance;

import java.util.List;

import com.szyciov.entity.PubVehInsur;

/**
 * @Title:AddPubVehInsurs.java
 * @Package com.szyciov.lease.param.pubVehInsurance
 * @Description
 * @author zhangdd
 * @date 2017年8月15日 上午11:48:31
 * @version 
 *
 */
public class AddPubVehInsurs {
	/**
	 * 创建者
	 */
	private String creater;
	
    /**
     * 车牌号
     */
	private String fullplateno;
	
	/**
	 * 车辆识别码
	 */
	private String vin;
	
	/**
	 * 发动机号
	 */
	private String engineid;
	
	/**
	 * 车辆id
	 */
	private String vehicleid;
	
	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;
	
	/**
	 * 保险列表
	 */
	private List<PubVehInsur> insurList;
	
	
	
	public String getCreater() {
		return creater;
	}


	public void setCreater(String creater) {
		this.creater = creater;
	}

	
	public String getFullplateno() {
		return fullplateno;
	}


	public void setFullplateno(String fullplateno) {
		this.fullplateno = fullplateno;
	}


	public String getVin() {
		return vin;
	}


	/**
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}


	public String getEngineid() {
		return engineid;
	}


	public void setEngineid(String engineid) {
		this.engineid = engineid;
	}


	public List<PubVehInsur> getInsurList() {
		return insurList;
	}


	public void setInsurList(List<PubVehInsur> insurList) {
		this.insurList = insurList;
	}


	public String getVehicleid() {
		return vehicleid;
	}


	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}


	public String getLeasescompanyid() {
		return leasescompanyid;
	}


	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	

}
