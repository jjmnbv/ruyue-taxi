package com.szyciov.lease.entity;

public class OrganUserCouponInfo {

	private String id;           //抵用券活动id
	private String sendtime;     //发放时间
	private String name;         //抵用券名称
	private Integer couponstatus; //抵用券状态
	private Double amount;       //金额
	private String usestarttime;      //有效期
	private String useendtime;
	private String usecity;       //限用地点
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	public Integer getCouponstatus() {
		return couponstatus;
	}
	public void setCouponstatus(Integer couponstatus) {
		this.couponstatus = couponstatus;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getUsestarttime() {
		return usestarttime;
	}
	public void setUsestarttime(String usestarttime) {
		this.usestarttime = usestarttime;
	}
	public String getUseendtime() {
		return useendtime;
	}
	public void setUseendtime(String useendtime) {
		this.useendtime = useendtime;
	}
	public String getUsecity() {
		return usecity;
	}
	public void setUsecity(String usecity) {
		this.usecity = usecity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public OrganUserCouponInfo() {
		super();
	}
}
