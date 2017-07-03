package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 司机信息模糊检索
 */
public class PubDriverQueryParam extends QueryParam{
	/**
	 * 关键字
	 */
	public String queryKeyword;
	/**
	 * 0-空闲，1-服务中，2-下线  工作状态
	 */
	public String queryWorkStatus;
	/**
	 * 关联字典表获取数据  所属城市
	 */
	public String queryCity;
	/**
	 * 0-在职，1-离职 在职状态
	 */
	public String queryJobStatus;
	/**
	 * 0-普通司机，1-特殊司机  司机身份类型
	 */
	public String queryIdEntityType;
	/**
	 * 机构地址（市）
	 */
	public String queryServiceOrg;
	/**
	 * 租赁公司id
	 * */
	public String leasesCompanyId;


	/**
	 * 资格证号
	 */
	private String jobNum;


	/**
	 * 司机ID
	 */
	private String driverId;

	/**
	 * 经度
	 */
	public Double lng;

	/**
	 * 纬度
	 */
	public Double lat;
	/**
	 * 绑定状态
	 * */
	public String queryBoundState;
	/**
	 * 司机类型
	 * */
	public String queryVehicleType;

	/**
	 * 系统类型
	 */
	private String platformType;


	public String getQueryVehicleType() {
		return queryVehicleType;
	}
	public void setQueryVehicleType(String queryVehicleType) {
		this.queryVehicleType = queryVehicleType;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getQueryBoundState() {
		return queryBoundState;
	}
	public void setQueryBoundState(String queryBoundState) {
		this.queryBoundState = queryBoundState;
	}
	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}
	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}
	public String getQueryKeyword() {
		return queryKeyword;
	}
	public void setQueryKeyword(String queryKeyword) {
		this.queryKeyword = queryKeyword;
	}
	public String getQueryWorkStatus() {
		return queryWorkStatus;
	}
	public void setQueryWorkStatus(String queryWorkStatus) {
		this.queryWorkStatus = queryWorkStatus;
	}
	public String getQueryCity() {
		return queryCity;
	}
	public void setQueryCity(String queryCity) {
		this.queryCity = queryCity;
	}
	public String getQueryJobStatus() {
		return queryJobStatus;
	}
	public void setQueryJobStatus(String queryJobStatus) {
		this.queryJobStatus = queryJobStatus;
	}
	public String getQueryIdEntityType() {
		return queryIdEntityType;
	}
	public void setQueryIdEntityType(String queryIdEntityType) {
		this.queryIdEntityType = queryIdEntityType;
	}
	public String getQueryServiceOrg() {
		return queryServiceOrg;
	}
	public void setQueryServiceOrg(String queryServiceOrg) {
		this.queryServiceOrg = queryServiceOrg;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
}
