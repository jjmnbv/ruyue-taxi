package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PubDriverVehicleRefQueryParam extends QueryParam {
	public PubDriverVehicleRefQueryParam(){
	}
	
	/**
	 * 司机
	 */
	public String driver;
	
	/**
	 * 资格证号
	 */
	public String jobnum;
	
	/**
	 * 绑定状态
	 */
	public String boundstate;
	
	/**
	 * 服务状态
	 */
	public String workstatus;
	
	/**
	 * 服务车型
	 */
	public String vehiclemodelsid;
	
	/**
	 * 登记城市
	 */
	public String city;
	
	/**
	 * 品牌车系
	 */
	public String vehclineid;
	
	/**
	 * 车牌号
	 */
	public String plateno;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getJobnum() {
		return jobnum;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
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

	public String getVehiclemodelsid() {
		return vehiclemodelsid;
	}

	public void setVehiclemodelsid(String vehiclemodelsid) {
		this.vehiclemodelsid = vehiclemodelsid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

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

}
