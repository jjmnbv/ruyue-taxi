package com.szyciov.dto.coupon;

public class CouponUsageDto {

	private String id;
	private String city;
	private Integer totalcount;
	private Integer usedcount;
	private Double usedpercent;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public Integer getUsedcount() {
		return usedcount;
	}
	public void setUsedcount(Integer usedcount) {
		this.usedcount = usedcount;
	}
	public Double getUsedpercent() {
		return usedpercent;
	}
	public void setUsedpercent(Double usedpercent) {
		this.usedpercent = usedpercent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CouponUsageDto() {
		super();
	}
	
}
