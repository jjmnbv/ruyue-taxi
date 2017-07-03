package com.szyciov.entity;

import java.util.Date;

public class PubAlipayaccountHistory {
	private String id;
	private String companyid;
	private String alipayaccount;//支付宝账户
	private String alipayappid;//APPID（支付宝）
	private String alipayprivatekey;//应用私钥（支付宝）
	private String alipaypublickey;//支付宝公钥（支付宝）
	private String alipaypartnerid;//合作伙伴ID
	private String alipaypartnerpublickey;//合作伙伴公钥
	private String alipaypartnerprivatekey;//合作伙伴私钥
	private Date createtime;
	private Date updatetime;
	private String creater;
	private String updater;
	private int status;
	private int platformtype;//1-租赁端，0-运管端，数据默认为1
	/**
	 * 用户类型
	 * '0-乘客端，1-司机端',
	 */
	private int usertype;

	
	public String getAlipayaccount() {
		return alipayaccount;
	}
	public void setAlipayaccount(String alipayaccount) {
		this.alipayaccount = alipayaccount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getAlipayappid() {
		return alipayappid;
	}
	public void setAlipayappid(String alipayappid) {
		this.alipayappid = alipayappid;
	}
	public String getAlipayprivatekey() {
		return alipayprivatekey;
	}
	public void setAlipayprivatekey(String alipayprivatekey) {
		this.alipayprivatekey = alipayprivatekey;
	}
	public String getAlipaypublickey() {
		return alipaypublickey;
	}
	public void setAlipaypublickey(String alipaypublickey) {
		this.alipaypublickey = alipaypublickey;
	}
	public String getAlipaypartnerid() {
		return alipaypartnerid;
	}
	public void setAlipaypartnerid(String alipaypartnerid) {
		this.alipaypartnerid = alipaypartnerid;
	}
	public String getAlipaypartnerpublickey() {
		return alipaypartnerpublickey;
	}
	public void setAlipaypartnerpublickey(String alipaypartnerpublickey) {
		this.alipaypartnerpublickey = alipaypartnerpublickey;
	}
	public String getAlipaypartnerprivatekey() {
		return alipaypartnerprivatekey;
	}
	public void setAlipaypartnerprivatekey(String alipaypartnerprivatekey) {
		this.alipaypartnerprivatekey = alipaypartnerprivatekey;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(int platformtype) {
		this.platformtype = platformtype;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
}
