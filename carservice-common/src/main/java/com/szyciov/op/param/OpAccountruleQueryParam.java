package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class OpAccountruleQueryParam extends QueryParam {
	
	/**
	 * 规则类型
	 */
	private String rulestype;
	
	/**
	 * 城市id
	 */
	private String city;
	
	/**
	 * 服务车型
	 */
	private String vehiclemodelsid;
	
	/**
	 * 规则状态
	 */
	private String rulesstate;
	
	/**
	 * 时间类型
	 */
	private String timetype;

	public String getRulestype() {
		return rulestype;
	}

	public void setRulestype(String rulestype) {
		this.rulestype = rulestype;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVehiclemodelsid() {
		return vehiclemodelsid;
	}

	public void setVehiclemodelsid(String vehiclemodelsid) {
		this.vehiclemodelsid = vehiclemodelsid;
	}

	public String getRulesstate() {
		return rulesstate;
	}

	public void setRulesstate(String rulesstate) {
		this.rulesstate = rulesstate;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

}
