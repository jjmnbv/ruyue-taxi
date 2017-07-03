package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class LeBaseQueryParam extends QueryParam{

	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;

	/**
	 * 所属部门
	 */
	private String dpetid;

	/**
	 * 用戶id
	 */
	private String userid;

	/**
	 * 账号（手机号）
	 */
	private String account;

	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 用户类型(0-普通，1-超管)
	 */
	private String specialstate;

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getDpetid() {
		return dpetid;
	}

	public void setDpetid(String dpetid) {
		this.dpetid = dpetid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSpecialstate() {
		return specialstate;
	}

	public void setSpecialstate(String specialstate) {
		this.specialstate = specialstate;
	}
}
