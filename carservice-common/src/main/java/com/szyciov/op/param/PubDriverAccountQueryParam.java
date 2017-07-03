package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 司机账户管理  查询条件
 * 实体类
 * */
public class PubDriverAccountQueryParam extends QueryParam{
	//关联的租赁公司id
	private String leasescompanyid;
	
	//司机
	private String queryDriver;
	
	//司机类型
	private String queryType;

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getQueryDriver() {
		return queryDriver;
	}

	public void setQueryDriver(String queryDriver) {
		this.queryDriver = queryDriver;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	
}
