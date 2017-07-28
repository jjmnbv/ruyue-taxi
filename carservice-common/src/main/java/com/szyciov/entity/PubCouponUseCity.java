package com.szyciov.entity;

/**
 * 优惠卷业务城市使用表
 * @author xuxxtr
 *
 */
public class PubCouponUseCity {

	private String id;
	private String couponidref;        //优惠卷活动ID
	private String city;                     //城市ID
	private String createtime;
	private String updatetime;
	private String creater;
	private String updater;
	
	public PubCouponUseCity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCouponidref() {
		return couponidref;
	}

	public void setCouponidref(String couponidref) {
		this.couponidref = couponidref;
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
	
}
