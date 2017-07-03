package com.szyciov.org.param;

import com.szyciov.param.QueryParam;

public class UserQueryParam extends QueryParam {

	private String organid;
	
	private String deptid;

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
}
