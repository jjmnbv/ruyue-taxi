package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class QueryTrajectoryByEqpParam extends QueryParam {
	/**
	 * 授权码 (接口必须)
	 */
	private String apikey;

	/** 授权Id(不需要传) */
	private String authorizationId;
	/**
	 * 类型 1、根据设备 2、根据行程ID
	 */
	private Integer type;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 行程ID
	 */
	private String trackId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 纠偏选项1_不纠偏;2_百度纠偏;默认不纠偏
	 */
	private Integer processOption;
	/**
	 * 1_轨迹;2_轨迹+报警;默认轨迹
	 */
	private Integer returnResult;
	/**
	 * 动态 eqp_location 位置表名
	 */
	private String tableName;
	/**
	 * 登录用户 所属机构
	 */
	private String organizationId;

	/**
	 * 车辆ID
	 */
	private String vehcId;

	public String getVehcId() {
		return vehcId;
	}

	public void setVehcId(String vehcId) {
		this.vehcId = vehcId;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getProcessOption() {
		return processOption;
	}

	public void setProcessOption(Integer processOption) {
		this.processOption = processOption;
	}

	public Integer getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(Integer returnResult) {
		this.returnResult = returnResult;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
}
