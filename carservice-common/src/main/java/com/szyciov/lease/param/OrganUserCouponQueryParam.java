package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class OrganUserCouponQueryParam extends QueryParam{
	
	/**
	 * 关键字(用户名称和手机号码)
	 */
	public String userid;
	
	/**
	 * 抵用券状态
	 */
	public Integer couponstatus;
	
	/**
	 * 起始时间
	 */
	public String starttime;
	
	/**
	 * 结束时间
	 */
	public String endtime;
	
	/**
	 * 所属租赁公司
	 */
	public String lecompanyid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getLecompanyid() {
		return lecompanyid;
	}

	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}

	public Integer getCouponstatus() {
		return couponstatus;
	}

	public void setCouponstatus(Integer couponstatus) {
		this.couponstatus = couponstatus;
	}

	public OrganUserCouponQueryParam() {
		super();
	}

	
}
