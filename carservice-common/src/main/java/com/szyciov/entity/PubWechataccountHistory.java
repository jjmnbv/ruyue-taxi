package com.szyciov.entity;

import java.util.Date;

public class PubWechataccountHistory {
	private String id;
	private String companyid;//所属公司
	private String wechataccount;//微信账户
	private String wechatappid;//APPID（微信）
	private String wechatmerchantno;//商户号（微信）
	private String wechatsecretkey;//商户密钥（微信）
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
	public String getWechataccount() {
		return wechataccount;
	}
	public void setWechataccount(String wechataccount) {
		this.wechataccount = wechataccount;
	}
	public String getWechatappid() {
		return wechatappid;
	}
	public void setWechatappid(String wechatappid) {
		this.wechatappid = wechatappid;
	}
	public String getWechatmerchantno() {
		return wechatmerchantno;
	}
	public void setWechatmerchantno(String wechatmerchantno) {
		this.wechatmerchantno = wechatmerchantno;
	}
	public String getWechatsecretkey() {
		return wechatsecretkey;
	}
	public void setWechatsecretkey(String wechatsecretkey) {
		this.wechatsecretkey = wechatsecretkey;
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
