package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class CashManageQueryParam extends QueryParam {
	
	private String account;
	
	private String nickname;
	
	private String creditcardname;
	
	private String minUseTime;
	
	private String maxUseTime;
	
	private String leasescompanyid;
	
	private String usertype;
	
	private String processstatus;

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

	public String getCreditcardname() {
		return creditcardname;
	}

	public void setCreditcardname(String creditcardname) {
		this.creditcardname = creditcardname;
	}

	public String getMinUseTime() {
		return minUseTime;
	}

	public void setMinUseTime(String minUseTime) {
		this.minUseTime = minUseTime;
	}

	public String getMaxUseTime() {
		return maxUseTime;
	}

	public void setMaxUseTime(String maxUseTime) {
		this.maxUseTime = maxUseTime;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getProcessstatus() {
		return processstatus;
	}

	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}
}
