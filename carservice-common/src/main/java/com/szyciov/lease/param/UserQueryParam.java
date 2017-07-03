package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class UserQueryParam extends QueryParam {

	private String leasescompanyid;
	
	/**
	 * 通过账户得到的租赁用户id
	 */
	private String accountid;
	
	/**
	 * 通过姓名/电话得到的租赁用户id
	 */
	private String nicknameid;
	
	/**
	 * 角色id
	 */
	private String roleid;

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getNicknameid() {
		return nicknameid;
	}

	public void setNicknameid(String nicknameid) {
		this.nicknameid = nicknameid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
