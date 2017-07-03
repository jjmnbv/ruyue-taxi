package com.szyciov.op.entity;

import java.util.Date;

/**
 * 运管平台信息表
 */
public class OpPlatformInfo {
	/**
	 * 主键
	 */
	public String id;

	/**
	 * 客服电话
	 */
	public String servcieTel;

	/**
	 * 微信收款账户
	 */
	public String wechatPayAccount;

	/**
	 * APPID（微信）
	 */
	public String wechatAppId;

	/**
	 * 商户号（微信）
	 */
	public String wechatMerchantNo;

	/**
	 * 商户密钥（微信）
	 */
	public String wechatSecretKey;

	/**
	 * 支付宝收款账户
	 */
	public String aliPayAccount;

	/**
	 * APPID（支付宝）
	 */
	public String aliPayAppId;

	/**
	 * 应用私钥（支付宝）
	 */
	public String aliPayPrivateKey;

	/**
	 * 支付宝公钥（支付宝）
	 */
	public String aliPayPublicKey;

	/**
	 * 合作伙伴id
	 */
	public String aliPayPartnerId;

	/**
	 * 伙伴私钥
	 */
	public String aliPayPartnerPrivateKey;

	/**
	 * 伙伴公钥
	 */
	public String aliPayPartnerPublicKey;

	/**
	 * 创建时间
	 */
	public Date createTime;

	/**
	 * 更新时间
	 */
	public Date updateTime;

	/**
	 * 数据状态
	 */
	public int status;
	/**
	 *
	 * 公司名称
	 */
	public String companyname;
	/**
	 * 公司简称
	 */
	public String companyshortname;
	/**
	 * 所属城市
	 */
	public String city;

	/**
	 * 微信账户状态(0-未开通，1-已开通)
	 */
	private String wechatstatus;

	/**
	 * 支付宝账户状态(0-未开通，1-已开通)
	 */
	private String alipaystatus;

	/**
	 * 更新人
	 */
	private String updater;


	/**
	 * 司机微信账户状态
	 * 0-未开通,1-已开通',
	 */
	private String driverwechatstatus;
	/**
	 * 司机支付宝账户状态
	 * '0-未开通,1-已开通'
	 */
	private String driveralipaystatus;
	/**
	 * 司机微信收款账户
	 */
	private String driverwechatpayaccount;
	/**
	 * 司机APPID（微信）
	 */
	private String driverwechatappid;
	/**
	 * 司机商户号（微信）
	 */
	private String driverwechatmerchantno;
	/**
	 * 司机商户密钥（微信）
	 */
	private String driverwechatsecretkey;
	/**
	 * 司机支付宝收款账户
	 */
	private String driveralipayaccount;
	/**
	 * 司机APPID（支付宝）
	 */
	private String driveralipayappid;
	/**
	 * 司机应用私钥（支付宝）
	 */
	private String driveralipayprivatekey;
	/**
	 * 司机支付宝公钥（支付宝）
	 */
	private String driveralipaypublickey;
	/**
	 * 司机合作伙伴ID
	 */
	private String driveralipaypartnerid;
	/**
	 * 司机合作伙伴公钥
	 */
	private String driveralipaypartnerpublickey;
	/**
	 * 司机合作伙伴私钥
	 */
	private String driveralipaypartnerprivatekey;

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyshortname() {
		return companyshortname;
	}

	public void setCompanyshortname(String companyshortname) {
		this.companyshortname = companyshortname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServcieTel() {
		return servcieTel;
	}

	public void setServcieTel(String servcieTel) {
		this.servcieTel = servcieTel;
	}

	public String getWechatPayAccount() {
		return wechatPayAccount;
	}

	public void setWechatPayAccount(String wechatPayAccount) {
		this.wechatPayAccount = wechatPayAccount;
	}

	public String getWechatAppId() {
		return wechatAppId;
	}

	public void setWechatAppId(String wechatAppId) {
		this.wechatAppId = wechatAppId;
	}

	public String getWechatMerchantNo() {
		return wechatMerchantNo;
	}

	public void setWechatMerchantNo(String wechatMerchantNo) {
		this.wechatMerchantNo = wechatMerchantNo;
	}

	public String getWechatSecretKey() {
		return wechatSecretKey;
	}

	public void setWechatSecretKey(String wechatSecretKey) {
		this.wechatSecretKey = wechatSecretKey;
	}

	public String getAliPayAccount() {
		return aliPayAccount;
	}

	public void setAliPayAccount(String aliPayAccount) {
		this.aliPayAccount = aliPayAccount;
	}

	public String getAliPayAppId() {
		return aliPayAppId;
	}

	public void setAliPayAppId(String aliPayAppId) {
		this.aliPayAppId = aliPayAppId;
	}

	public String getAliPayPrivateKey() {
		return aliPayPrivateKey;
	}

	public void setAliPayPrivateKey(String aliPayPrivateKey) {
		this.aliPayPrivateKey = aliPayPrivateKey;
	}

	public String getAliPayPublicKey() {
		return aliPayPublicKey;
	}

	public void setAliPayPublicKey(String aliPayPublicKey) {
		this.aliPayPublicKey = aliPayPublicKey;
	}

	public String getAliPayPartnerId() {
		return aliPayPartnerId;
	}

	public void setAliPayPartnerId(String aliPayPartnerId) {
		this.aliPayPartnerId = aliPayPartnerId;
	}

	public String getAliPayPartnerPrivateKey() {
		return aliPayPartnerPrivateKey;
	}

	public void setAliPayPartnerPrivateKey(String aliPayPartnerPrivateKey) {
		this.aliPayPartnerPrivateKey = aliPayPartnerPrivateKey;
	}

	public String getAliPayPartnerPublicKey() {
		return aliPayPartnerPublicKey;
	}

	public void setAliPayPartnerPublicKey(String aliPayPartnerPublicKey) {
		this.aliPayPartnerPublicKey = aliPayPartnerPublicKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWechatstatus() {
		return wechatstatus;
	}

	public void setWechatstatus(String wechatstatus) {
		this.wechatstatus = wechatstatus;
	}

	public String getAlipaystatus() {
		return alipaystatus;
	}

	public void setAlipaystatus(String alipaystatus) {
		this.alipaystatus = alipaystatus;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getDriverwechatstatus() {
		return driverwechatstatus;
	}

	public void setDriverwechatstatus(String driverwechatstatus) {
		this.driverwechatstatus = driverwechatstatus;
	}

	public String getDriveralipaystatus() {
		return driveralipaystatus;
	}

	public void setDriveralipaystatus(String driveralipaystatus) {
		this.driveralipaystatus = driveralipaystatus;
	}

	public String getDriverwechatpayaccount() {
		return driverwechatpayaccount;
	}

	public void setDriverwechatpayaccount(String driverwechatpayaccount) {
		this.driverwechatpayaccount = driverwechatpayaccount;
	}

	public String getDriverwechatappid() {
		return driverwechatappid;
	}

	public void setDriverwechatappid(String driverwechatappid) {
		this.driverwechatappid = driverwechatappid;
	}

	public String getDriverwechatmerchantno() {
		return driverwechatmerchantno;
	}

	public void setDriverwechatmerchantno(String driverwechatmerchantno) {
		this.driverwechatmerchantno = driverwechatmerchantno;
	}

	public String getDriverwechatsecretkey() {
		return driverwechatsecretkey;
	}

	public void setDriverwechatsecretkey(String driverwechatsecretkey) {
		this.driverwechatsecretkey = driverwechatsecretkey;
	}

	public String getDriveralipayaccount() {
		return driveralipayaccount;
	}

	public void setDriveralipayaccount(String driveralipayaccount) {
		this.driveralipayaccount = driveralipayaccount;
	}

	public String getDriveralipayappid() {
		return driveralipayappid;
	}

	public void setDriveralipayappid(String driveralipayappid) {
		this.driveralipayappid = driveralipayappid;
	}

	public String getDriveralipayprivatekey() {
		return driveralipayprivatekey;
	}

	public void setDriveralipayprivatekey(String driveralipayprivatekey) {
		this.driveralipayprivatekey = driveralipayprivatekey;
	}

	public String getDriveralipaypublickey() {
		return driveralipaypublickey;
	}

	public void setDriveralipaypublickey(String driveralipaypublickey) {
		this.driveralipaypublickey = driveralipaypublickey;
	}

	public String getDriveralipaypartnerid() {
		return driveralipaypartnerid;
	}

	public void setDriveralipaypartnerid(String driveralipaypartnerid) {
		this.driveralipaypartnerid = driveralipaypartnerid;
	}

	public String getDriveralipaypartnerpublickey() {
		return driveralipaypartnerpublickey;
	}

	public void setDriveralipaypartnerpublickey(String driveralipaypartnerpublickey) {
		this.driveralipaypartnerpublickey = driveralipaypartnerpublickey;
	}

	public String getDriveralipaypartnerprivatekey() {
		return driveralipaypartnerprivatekey;
	}

	public void setDriveralipaypartnerprivatekey(String driveralipaypartnerprivatekey) {
		this.driveralipaypartnerprivatekey = driveralipaypartnerprivatekey;
	}
}
