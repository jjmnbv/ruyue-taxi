package com.szyciov.lease.param;

import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName GetCarTypesParam
 * @author Efy Shu
 * @Description 获取机构车型参数类
 * @date 2016年9月26日 下午4:03:31
 */
public class GetCarTypesParam extends BaseParam {
	/**
	 * 所属机构ID
	 */
	private String organid;
	/**
	 * 租赁公司ID
	 */
	private String companyid;

	/**
	 * 用户ID
	 */
	private String userid;

	/**
	 * 所选城市
	 */
	private String city;


	/**
	 * 获取所选城市
	 * 
	 * @return city 所选城市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置所选城市
	 * 
	 * @param city
	 *            所选城市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return userid 用户ID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 设置用户ID
	 * 
	 * @param userid
	 *            用户ID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 获取所属机构ID
	 * 
	 * @return organid 所属机构ID
	 */
	public String getOrganid() {
		return organid;
	}

	/**
	 * 设置所属机构ID
	 * 
	 * @param organid
	 *            所属机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**
	 * 获取租赁公司ID
	 * 
	 * @return companyid 租赁公司ID
	 */
	public String getCompanyid() {
		return companyid;
	}

	/**
	 * 设置租赁公司ID
	 * 
	 * @param companyid
	 *            租赁公司ID
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
}
