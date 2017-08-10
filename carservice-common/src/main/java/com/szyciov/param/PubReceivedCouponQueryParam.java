package com.szyciov.param;

/**
 * 优惠券发放记录查询参数
 * @author xuxxtr
 *
 */
public class PubReceivedCouponQueryParam extends QueryParam{

	private String id;
	private String account;
	private String sendstarttime;
	private String sendendtime;
	private Integer couponstatus;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSendstarttime() {
		return sendstarttime;
	}
	public void setSendstarttime(String sendstarttime) {
		this.sendstarttime = sendstarttime;
	}
	public String getSendendtime() {
		return sendendtime;
	}
	public void setSendendtime(String sendendtime) {
		this.sendendtime = sendendtime;
	}
	public Integer getCouponstatus() {
		return couponstatus;
	}
	public void setCouponstatus(Integer couponstatus) {
		this.couponstatus = couponstatus;
	}
	public PubReceivedCouponQueryParam() {
		super();
	}
	
}
