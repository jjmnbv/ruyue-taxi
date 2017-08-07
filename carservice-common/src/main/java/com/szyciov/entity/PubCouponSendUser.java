package com.szyciov.entity;

/**
 * 人工发券的用户
 * @author xuxxtr
 *
 */
public class PubCouponSendUser {

	private String id;
	private String couponactivityidref;
	private String phone;
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
	public PubCouponSendUser() {
		super();
	}
	
}
