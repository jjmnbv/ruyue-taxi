package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PubDriverVehicleBindQueryParam extends QueryParam {
	public PubDriverVehicleBindQueryParam() {
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
	public String bindstate;
	
	/**
	 * 开始时间
	 */
	public String starttime;
	
	/**
	 * 结束时间
	 */
	public String endtime;
	
	/**
	 * 车牌号
	 */
	public String plateno;
	
	/**
	 * 车架号
	 */
	public String vin;

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

	public String getBindstate() {
		return bindstate;
	}

	public void setBindstate(String bindstate) {
		this.bindstate = bindstate;
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

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
	
}
