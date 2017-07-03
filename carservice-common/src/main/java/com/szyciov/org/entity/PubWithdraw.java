package com.szyciov.org.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现管理表
 */
public class PubWithdraw {

	/**
	 * 主键
	 */
	public String id;
	
	/**
	 * 数据所属平台
	 */
	public String platformtype;
	
	/**
	 * 所属租赁公司
	 */
	public String leasescompanyid;
	
	/**
	 * 用户类型
	 */
	public String usertype;
	
	/**
	 * 所属用户
	 */
	public String userid;
	
	/**
	 * 提现金额
	 */
	public BigDecimal amount;
	
	/**
	 * 设备唯一标识
	 */
	public String uuid;
	
	/**
	 * 银行账户
	 */
	public String creditcardnum;
	
	/**
	 * 账户名称
	 */
	public String creditcardname;
	
	/**
	 * 开户银行
	 */
	public String bankname;
	 
	/**
	 * 申请时间
	 */
	public Date applytime;
	
	/**
	 * 处理状态
	 */
	public String processstatus;
	
	/**
	 * 处理结果
	 */
	public String processresult;
	
	/**
	 * 处理原因
	 */
	public String processreason;
	 
	/**
	 * 处理时间
	 */
	public Date processtime;
	
	/**
	 * 创建时间
	 */
	public Date createtime;
	
	/**
	 * 更新时间
	 */
	public Date updatetime;
	
	/**
	 * 创建人
	 */
	public String creater;
	
	/**
	 * 更新人
	 */
	public String updater;
	
	/**
	 * 操作人
	 */
	public String processuser;
	
	/**
	 * 数据状态
	 */
	public int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCreditcardnum() {
		return creditcardnum;
	}

	public void setCreditcardnum(String creditcardnum) {
		this.creditcardnum = creditcardnum;
	}

	public String getCreditcardname() {
		return creditcardname;
	}

	public void setCreditcardname(String creditcardname) {
		this.creditcardname = creditcardname;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public String getProcessstatus() {
		return processstatus;
	}

	public void setProcessstatus(String processstatus) {
		this.processstatus = processstatus;
	}

	public String getProcessresult() {
		return processresult;
	}

	public void setProcessresult(String processresult) {
		this.processresult = processresult;
	}

	public String getProcessreason() {
		return processreason;
	}

	public void setProcessreason(String processreason) {
		this.processreason = processreason;
	}

	public Date getProcesstime() {
		return processtime;
	}

	public void setProcesstime(Date processtime) {
		this.processtime = processtime;
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

	public String getProcessuser() {
		return processuser;
	}

	public void setProcessuser(String processuser) {
		this.processuser = processuser;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
