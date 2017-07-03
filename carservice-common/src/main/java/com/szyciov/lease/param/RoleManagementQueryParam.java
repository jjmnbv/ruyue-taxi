package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class RoleManagementQueryParam extends QueryParam {

	private String leasescompanyid;
	
	/**
	 * 角色id
	 */
	private String id;
	
	/**
	 * 角色类型
	 */
	private String roletype;

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

}
