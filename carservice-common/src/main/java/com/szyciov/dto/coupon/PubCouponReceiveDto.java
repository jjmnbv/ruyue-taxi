package com.szyciov.dto.coupon;

/**
 * 优惠券领取记录传输对象
 * @author xuxxtr
 *
 */
public class PubCouponReceiveDto {

	private String account;         //账户
	private String organname;       //领取机构
	private String belongorgan;     //所属机构
	private Double money;           //优惠券金额
	private Integer couponstatus;    //优惠券状态(0-未使用，1-已使用，2-已过期)
	private String createtime;        //发放时间
	private String outimestart;       //有效期开始时间
	private String outtimeend;         //有效期结束时间
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getCouponstatus() {
		return couponstatus;
	}
	public void setCouponstatus(Integer couponstatus) {
		this.couponstatus = couponstatus;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOutimestart() {
		return outimestart;
	}
	public void setOutimestart(String outimestart) {
		this.outimestart = outimestart;
	}

	public String getOuttimeend() {
		return outtimeend;
	}
	public void setOuttimeend(String outtimeend) {
		this.outtimeend = outtimeend;
	}
	
	public String getOrganname() {
		return organname;
	}
	public void setOrganname(String organname) {
		this.organname = organname;
	}
	public String getBelongorgan() {
		return belongorgan;
	}
	public void setBelongorgan(String belongorgan) {
		this.belongorgan = belongorgan;
	}
	public PubCouponReceiveDto() {
		super();
	}
	
}
