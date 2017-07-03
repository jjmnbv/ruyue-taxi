package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 
 * 行程记录查询参数<一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryTrackRecordParam extends QueryParam {
	/**
	 * 授权码 (接口必须)
	 */
	private String apikey;
	
	/** 授权Id(不需要传) */
	private String authorizationId;
	/**
	 * 设备ID
	 */
	private String eqpId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 公司ID
	 */
	private String departmentId;
	/**
	 * 登录用户 所属机构
	 */
	private String organizationId;
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
