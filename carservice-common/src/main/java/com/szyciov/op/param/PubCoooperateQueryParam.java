package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 模糊检索 条件
 */
public class PubCoooperateQueryParam extends QueryParam{
	private String queryCoono;
	
	private String queryCompanyname;
	
	private String queryServicetype;
	
	private String queryPlateno;
	
	private String queryDriverInformation;
	
	private String queryJobnum;
	//用于 查询资源信息  加盟的业务
	private String servicetype; 
	//用于查询联盟资源  的 服务车型
	private String queryModels;
	//
	private String leasescompanyid;
	
	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getQueryModels() {
		return queryModels;
	}

	public void setQueryModels(String queryModels) {
		this.queryModels = queryModels;
	}

	public String getQueryPlateno() {
		return queryPlateno;
	}

	public void setQueryPlateno(String queryPlateno) {
		this.queryPlateno = queryPlateno;
	}

	public String getQueryDriverInformation() {
		return queryDriverInformation;
	}

	public void setQueryDriverInformation(String queryDriverInformation) {
		this.queryDriverInformation = queryDriverInformation;
	}

	public String getQueryJobnum() {
		return queryJobnum;
	}

	public void setQueryJobnum(String queryJobnum) {
		this.queryJobnum = queryJobnum;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getQueryCoono() {
		return queryCoono;
	}

	public void setQueryCoono(String queryCoono) {
		this.queryCoono = queryCoono;
	}

	public String getQueryCompanyname() {
		return queryCompanyname;
	}

	public void setQueryCompanyname(String queryCompanyname) {
		this.queryCompanyname = queryCompanyname;
	}

	public String getQueryServicetype() {
		return queryServicetype;
	}

	public void setQueryServicetype(String queryServicetype) {
		this.queryServicetype = queryServicetype;
	}
	
	
}
