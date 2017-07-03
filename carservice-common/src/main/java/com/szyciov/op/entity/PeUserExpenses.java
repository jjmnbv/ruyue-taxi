package com.szyciov.op.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 运管用户消费记录
 */
public class PeUserExpenses {
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 所属用户
	 */
	private String userid;
	
	/**
	 * 所属租赁公司
	 */
	private String leasescompanyid;
	
	/**
	 * 交易渠道
	 */
	private String expensetype;
	
	/**
	 * 交易时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date expensetime;
	
	/**
	 * 消费金额
	 */
	private BigDecimal amount;
	
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 创建人
	 */
	private String creater;
	
	/**
	 * 更新人
	 */
	private String updater;
	
	/**
	 * 数据状态
	 */
	private String status;
	
	/**
	 * 交易类型
	 */
	private String tradetype;
	
	/**
	 * 明细类型
	 */
	private String detailtype;

	/**
	 * 交易结果
	 */
	private String operateresult;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(String expensetype) {
		this.expensetype = expensetype;
	}

	public Date getExpensetime() {
		return expensetime;
	}

	public void setExpensetime(Date expensetime) {
		this.expensetime = expensetime;
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

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getDetailtype() {
		return detailtype;
	}

	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
	}
}
