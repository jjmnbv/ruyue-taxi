package com.szyciov.entity.coupon;

import java.time.LocalDateTime;

/**
 * 优惠卷业务城市使用表
 * @author xuxxtr
 *
 */
public class PubCouponUseCity {

	private String id;
	private String couponidref;        //优惠卷活动ID
	private String city;                     //城市ID
	private LocalDateTime createtime;
	private LocalDateTime updatetime;
	private String creater;
	private String updater;
	private Integer status;
	
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

	public LocalDateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}

	public LocalDateTime getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
