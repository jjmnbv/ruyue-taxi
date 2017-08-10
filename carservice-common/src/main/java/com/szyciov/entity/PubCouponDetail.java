package com.szyciov.entity;

import java.util.Date;

public class PubCouponDetail {
    /**
    主键
     */
    private String id;

    /**
   机构id或者用户id
     */
    private String userid;

    /**
   抵用券
     */
    private String couponidref;

    /**
  类型（1-充值，2-扣款，3-注册，4-活动，5提现，6-人工发券）
     */
    private Integer usetype;

    /**
  金额
     */
    private Double amount;

    /**
    抵用券余额
     */
    private Double balance;
    /**
    创建人
     */
    private String remark;
    /**
    更新时间
     */
    private Date updatetime;
    /**
    更新时间
     */
    private Date createtime;
    /**
    数据状态
     */
    private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCouponidref() {
		return couponidref;
	}
	public void setCouponidref(String couponidref) {
		this.couponidref = couponidref;
	}
	public Integer getUsetype() {
		return usetype;
	}
	public void setUsetype(Integer usetype) {
		this.usetype = usetype;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}