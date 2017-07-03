package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeSendRulesQueryParam extends QueryParam {
	public LeSendRulesQueryParam() {
	}
	
	/**
	 * 所属租赁公司
	 */
	private String leasesCompanyId;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 用车类型
	 *  0-预约用车 1-即可用车
	 */
	private String useType;
	
	/**
	 * 派单模式
	 * 0-系统 1-系统+人工
	 */
	private String sendModel;
	
	/**
	 * 0-强派 1-抢派 2-抢单 3-纯人工
	 */
	private String sendType;

	public String getLeasesCompanyId() {
		return leasesCompanyId;
	}

	public void setLeasesCompanyId(String leasesCompanyId) {
		this.leasesCompanyId = leasesCompanyId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getSendModel() {
		return sendModel;
	}

	public void setSendModel(String sendModel) {
		this.sendModel = sendModel;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

}
