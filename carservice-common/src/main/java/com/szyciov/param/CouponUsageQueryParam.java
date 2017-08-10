package com.szyciov.param;

/**
 * 优惠券使用率查询参数
 * @author xuxxtr
 *
 */
public class CouponUsageQueryParam extends QueryParam {

	private String lecompanyid;      //租赁公司ID
	private String cdatetype;        //0-按日；1-按月
	private String usedstarttime;    //开始使用时间
	private String usedendtime;      //截止使用时间
	private String city;             //城市
	public String getUsedstarttime() {
		return usedstarttime;
	}
	public void setUsedstarttime(String usedstarttime) {
		this.usedstarttime = usedstarttime;
	}
	public String getUsedendtime() {
		return usedendtime;
	}
	public void setUsedendtime(String usedendtime) {
		this.usedendtime = usedendtime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLecompanyid() {
		return lecompanyid;
	}
	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}
	public String getCdatetype() {
		return cdatetype;
	}
	public void setCdatetype(String cdatetype) {
		this.cdatetype = cdatetype;
	}
	public CouponUsageQueryParam() {
		super();
	}
}
