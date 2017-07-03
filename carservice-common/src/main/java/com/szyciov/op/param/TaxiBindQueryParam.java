package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class TaxiBindQueryParam extends QueryParam {
	public TaxiBindQueryParam() {
	}
	
	/**
	 * 品牌车系
	 */
	public String vehclineid;
	
	/**
	 * 车牌号
	 */
	public String plateno;
	
	/**
	 * 绑定状态
	 */
	public String boundstate;
	
	/**
	 * 服务状态
	 */
	public String workstatus;
	
	/**
	 * 登记城市
	 */
	public String city;
	
	/**
	 * 已绑定人数
	 */
	public int bindpersonnum;
	
	/**
	 * 当班司机
	 */
	public String driverid;
	
	/**
	 * 班次状态
	 */
	public String ondutystatus;
	
	/**
	 * 营运状态
	 */
	public String vehiclestatus;
	
	/**
	 * 车辆id
	 */
	public String vehicleid;

	public String getVehclineid() {
		return vehclineid;
	}

	public void setVehclineid(String vehclineid) {
		this.vehclineid = vehclineid;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getBoundstate() {
		return boundstate;
	}

	public void setBoundstate(String boundstate) {
		this.boundstate = boundstate;
	}

	public String getWorkstatus() {
		return workstatus;
	}

	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getBindpersonnum() {
		return bindpersonnum;
	}

	public void setBindpersonnum(int bindpersonnum) {
		this.bindpersonnum = bindpersonnum;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getOndutystatus() {
		return ondutystatus;
	}

	public void setOndutystatus(String ondutystatus) {
		this.ondutystatus = ondutystatus;
	}

	public String getVehiclestatus() {
		return vehiclestatus;
	}

	public void setVehiclestatus(String vehiclestatus) {
		this.vehiclestatus = vehiclestatus;
	}

	public String getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	
}
