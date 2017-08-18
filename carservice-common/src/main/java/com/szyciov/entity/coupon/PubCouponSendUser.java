package com.szyciov.entity.coupon;

/**
 * 人工发券的用户
 * @author xuxxtr
 *
 */
public class PubCouponSendUser {

	private String id;
	private String couponactivityidref;
	private String phone;
	private String userid;
	private String lecompanyid;
	private Integer platformtype;
	private String createtime;
	private String updatetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCouponactivityidref() {
		return couponactivityidref;
	}
	public void setCouponactivityidref(String couponactivityidref) {
		this.couponactivityidref = couponactivityidref;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLecompanyid() {
		return lecompanyid;
	}
	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}
 
	public Integer getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(Integer platformtype) {
		this.platformtype = platformtype;
	}
	public PubCouponSendUser() {
		super();
	}
	
}
