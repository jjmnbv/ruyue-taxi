package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class OpTaxiSendrulesQueryParam extends QueryParam {
	
	/**
	 * 所属城市
	 */
	private String city;
	
	/**
	 * 用车类型(0-预约用车,1-即刻用车)
	 */
	private String usetype;
	
	/**
	 * 派单方式(0-强派,1-抢派,2-抢单)
	 */
	private String sendtype;
	
	/**
	 * 派单模式(0-系统,1-系统+人工)
	 */
	private String sendmodel;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getSendmodel() {
		return sendmodel;
	}

	public void setSendmodel(String sendmodel) {
		this.sendmodel = sendmodel;
	}
	
}
