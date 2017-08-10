package com.szyciov.param;

/**
 * 成效分析，用户充值率查询参数
 * @author xuxxtr
 *
 */
public class UserRechargeQueryParam extends QueryParam{

	private String lecompanyid;
	private String rdatetype;      //0-按日；1-按月
	private String starttime;
	private String endtime;
	public String getLecompanyid() {
		return lecompanyid;
	}
	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
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
	public String getRdatetype() {
		return rdatetype;
	}
	public void setRdatetype(String rdatetype) {
		this.rdatetype = rdatetype;
	}
	public UserRechargeQueryParam() {
		super();
	}
	
	
}
