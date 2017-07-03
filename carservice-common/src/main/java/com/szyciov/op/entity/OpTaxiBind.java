package com.szyciov.op.entity;

import java.util.List;

import com.szyciov.entity.PubDriverVehicleBind;

/**
 * 出租车绑定
 */
public class OpTaxiBind extends PubDriverVehicleBind {
	/**
	 * 绑定的司机
	 */
	public List<String> driverids;
	
	/**
	 * 车牌号
	 */
	public String plateno;

	public List<String> getDriverids() {
		return driverids;
	}

	public void setDriverids(List<String> driverids) {
		this.driverids = driverids;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}
	
}
