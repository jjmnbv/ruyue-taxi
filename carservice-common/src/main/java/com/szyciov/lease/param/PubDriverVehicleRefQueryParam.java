package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

/**
 * 绑定模糊检索
 */
public class PubDriverVehicleRefQueryParam extends QueryParam{
	/**
	 * 品牌车系
	 */
	public String queryBrandCars;
	/**
	 * 车牌
	 */
	public String queryPlateNo;
	
	public String leasesCompanyId;
	
	
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getQueryBrandCars() {
		return queryBrandCars;
	}
	public void setQueryBrandCars(String queryBrandCars) {
		this.queryBrandCars = queryBrandCars;
	}
	public String getQueryPlateNo() {
		return queryPlateNo;
	}
	public void setQueryPlateNo(String queryPlateNo) {
		this.queryPlateNo = queryPlateNo;
	}
	
}
