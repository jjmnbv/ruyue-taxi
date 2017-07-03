package com.szyciov.org.entity;

import java.util.Date;

/**
 * 机构支付记录表
 */
public class OrgOrganPaymentRecord {
	/**
	 * 支付订单号（主键）
	 */
	public String outtradeno;
	
	/**
	 * 所属机构
	 */
	public String organid;
	
	/**
	 * 所属租赁公司
	 */
	public String leasescompanyid;
	
	/**
	 * 支付方式 包括：0-支付宝，1-微信
	 */
	public String paymenttype;
	
	/**
	 * 流水号
	 */
	public String tradeno;
	
	/**
	 * 支付密钥
	 */
	public String privatekey;
	
	/**
	 * 操作结果 包括：0-成功，1-失败 
	 */
	public String operateresult;
	
	/**
	 * 创建时间
	 */
	public Date createtime;
	
	/**
	 * 更新时间
	 */
	public Date updatetime;
	
	/**
	 * 数据状态
	 */
	public int status;
	
	/**
	 * 创建人
	 */
	public String creater;
	
	/**
	 * 更新人
	 */
	public String updater;
	
	/**
	 * 金额
	 */
	public double amount; 

	public String getOuttradeno() {
		return outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno = outtradeno;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getLeasescompanyid() {
		return leasescompanyid;
	}

	public void setLeasescompanyid(String leasescompanyid) {
		this.leasescompanyid = leasescompanyid;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
