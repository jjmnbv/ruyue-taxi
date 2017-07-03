package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

/**
 * 绑定模糊检索
 */
public class PubVehicleQueryParam extends QueryParam{

	/**
	 * 车辆id
	 */
	private  String vehicleId;
	/**
	 * 品牌车系
	 */
	public String queryBrandCars;
	/**
	 * 车牌
	 */
	public String queryPlateNo;
	/**
	 * 服务车型
	 * */
	public String queryServiceCars;
	/**
	 * 城市
	 * */
	public String queryCity;
	/**
	 * 当前状态
	 * */
	public String queryWorkStatus;
	/**
	 * 租赁公司id
	 * */
	public String leasesCompanyId;

	/**
	 * 所属平台 0-运管端，1-租赁端
	 */
	private String platformtype;

	/**
	 * 车辆类型 0 网约车 1 出租车
	 */
	private String vehicletype;

	/**
	 * 绑定司机ID
	 */
	private String driverid;

	/**
	 * 当前服务状态 0/空闲 1/服务中 2/下线 3/未绑定
	 */
	private String  driverState;
	
	//车辆类型
	public String queryVehicleType;
	//营运状态
	public String queryVehicleStatus;
	//绑定状态
	public String queryBoundState;
	
	public String belongleasecompanyQuery;
	
	public String getBelongleasecompanyQuery() {
		return belongleasecompanyQuery;
	}
	public void setBelongleasecompanyQuery(String belongleasecompanyQuery) {
		this.belongleasecompanyQuery = belongleasecompanyQuery;
	}
	public String getQueryBoundState() {
		return queryBoundState;
	}
	public void setQueryBoundState(String queryBoundState) {
		this.queryBoundState = queryBoundState;
	}
	public String getQueryVehicleType() {
		return queryVehicleType;
	}
	public void setQueryVehicleType(String queryVehicleType) {
		this.queryVehicleType = queryVehicleType;
	}
	public String getQueryVehicleStatus() {
		return queryVehicleStatus;
	}
	public void setQueryVehicleStatus(String queryVehicleStatus) {
		this.queryVehicleStatus = queryVehicleStatus;
	}
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
	public String getQueryServiceCars() {
		return queryServiceCars;
	}
	public void setQueryServiceCars(String queryServiceCars) {
		this.queryServiceCars = queryServiceCars;
	}
	public String getQueryCity() {
		return queryCity;
	}
	public void setQueryCity(String queryCity) {
		this.queryCity = queryCity;
	}
	public String getQueryWorkStatus() {
		return queryWorkStatus;
	}
	public void setQueryWorkStatus(String queryWorkStatus) {
		this.queryWorkStatus = queryWorkStatus;
	}

	public String getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}


	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getDriverState() {
		return driverState;
	}

	public void setDriverState(String driverState) {
		this.driverState = driverState;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}
}
