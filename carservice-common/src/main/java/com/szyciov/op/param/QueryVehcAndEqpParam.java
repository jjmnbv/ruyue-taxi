
/*
* 文 件 名:  QueryVehcAndEqpParam.java
* 版    权:  Szyciov Technologies Co., Ltd. Copyright 1993-2016,  All rights reserved
* 描    述:  <描述>
* 修 改 人:  Administrator
* 修改时间:  2017年3月29日
* 跟踪单号:  <跟踪单号>
* 修改单号:  <修改单号>
* 修改内容:  <修改内容>
*/

package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 查询车辆及设备IMEI参数对象 <功能详细描述>
 * 
 * @author 杨晋伟
 * @version [版本号, 2017年3月29日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class QueryVehcAndEqpParam extends QueryParam {
	/**
	 * 授权码 (接口必须)
	 */
	private String apikey;
	
	/** 授权Id(不需要传) */
	private String authorizationId;
	
	/** 车牌 */
	private String plate;

	/** 设备IMEI */
	private String imei;

	/** 型号名称 */
	private String entityName;

	/** 设备工作状态 */
	private Integer workStatus;
	/**
	 * 登录账户所属 机构ID
	 */
	private String organizationId;
	/**
	 * @return 返回 plate
	 */

	public String getPlate() {
		return plate;
	}

	/**
	 * @param 对plate进行赋值
	 */

	public void setPlate(String plate) {
		this.plate = plate;
	}

	/**
	 * @return 返回 imei
	 */

	public String getImei() {
		return imei;
	}

	/**
	 * @param 对imei进行赋值
	 */

	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return 返回 entityName
	 */

	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param 对entityName进行赋值
	 */

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return 返回 workStatus
	 */

	public Integer getWorkStatus() {
		return workStatus;
	}

	/**
	 * @param 对workStatus进行赋值
	 */

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
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
