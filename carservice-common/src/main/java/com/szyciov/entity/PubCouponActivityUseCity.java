package com.szyciov.entity;

/**
 * 优惠卷活动发放城市表
 * @author xuxxtr
 *
 */
public class PubCouponActivityUseCity {

	private String id;
	private String couponactivyidref;        //优惠卷活动ID
	private String city;                     //城市ID
	private String createtime;
	private String updatetime;
	private String creater;
	private String updater;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCouponactivyidref() {
		return couponactivyidref;
	}
	public void setCouponactivyidref(String couponactivyidref) {
		this.couponactivyidref = couponactivyidref;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public PubCouponActivityUseCity() {
		super();
	}
	
	
}
