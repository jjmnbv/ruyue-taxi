package com.szyciov.op.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PeUser {
	
	/**
	 * 个人用户id
	 */
	public String id;
	/**
	 * 账号（手机号）
	 */
	public String account;
	/**
	 * 邮箱
	 */
	public String email;
	/**
	 * 昵称
	 */
	public String nickname;
	/**
	 * 用户密码
	 */
	public String userpassword;
	/**
	 * 性别
	 */
	public String sex;
	/**
	 * 特殊状态
	 */
	public String specialstate;
	/**
	 * 头像（小）
	 */
	public String headportraitmin;
	/**
	 * 头像（大）
	 */
	public String headPortraitmax;
	/**
	 * 注册时间
	 */
	public Date registertime;
	/**
	 * 禁用状态
	 */
	public String disablestate;
	/**
	 * 禁用状态 0 启用 1 禁用
	 */
	public String disablestateShow;
	/**
	 * 禁用历史
	 */
	public String disableHis;
	
	/**
	 * 用车次数
	 */
	public Integer usetimes;
	/**
	 * GPS速度
	 */
	public Double gpsspeed;
	/**
	 * GPS方向
	 */
	public Double gpsdirection;
	/**
	 * 经度
	 */
	public Double lng;
	/**
	 * 纬度
	 */
	public Double lat;
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
	public Integer status;
	/**
	 * 账户余额
	 */
	public BigDecimal balance;

	/**
	 * 是否有未支付订单
	 */
	private boolean notpay;
	
	/**
	 * 未完成订单数
	 */
	private int notdone;
	
	public int disableRecord;
	
	/**
	 * 提现密码
	 */
	private String withdrawpwd;
	
	/**
	 * 提现密码修改过的标识
	 */
	private int wdpwdchangestate;
	/**
	 * 交易明细数
	 */
	public int dealCount;
	
	/**
	 * 余额明细数
	 */
	public int balanceCount;
	
	//org_user account 
	public String orgUserAccount;
	
	
	public String getOrgUserAccount() {
		return orgUserAccount;
	}

	public void setOrgUserAccount(String orgUserAccount) {
		this.orgUserAccount = orgUserAccount;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public int getBalanceCount() {
		return balanceCount;
	}

	public void setBalanceCount(int balanceCount) {
		this.balanceCount = balanceCount;
	}

	/**  
	 * 获取是否有未支付订单  
	 * @return notpay 是否有未支付订单  
	 */
	public boolean isNotpay() {
		return notpay;
	}
	
	/**  
	 * 设置是否有未支付订单  
	 * @param notpay 是否有未支付订单  
	 */
	public void setNotpay(boolean notpay) {
		this.notpay = notpay;
	}
	
	/**  
	 * 获取未完成订单数  
	 * @return notdone 未完成订单数  
	 */
	public int getNotdone() {
		return notdone;
	}
	
	/**  
	 * 设置未完成订单数  
	 * @param notdone 未完成订单数  
	 */
	public void setNotdone(int notdone) {
		this.notdone = notdone;
	}
	
	public int getDisableRecord() {
		return disableRecord;
	}
	public void setDisableRecord(int disableRecord) {
		this.disableRecord = disableRecord;
	}
	public String getDisablestateShow() {
		return disablestateShow;
	}
	public void setDisablestateShow(String disablestateShow) {
		this.disablestateShow = disablestateShow;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSpecialstate() {
		return specialstate;
	}
	public void setSpecialstate(String specialstate) {
		this.specialstate = specialstate;
	}
	public String getHeadportraitmin() {
		return headportraitmin;
	}
	public void setHeadportraitmin(String headportraitmin) {
		this.headportraitmin = headportraitmin;
	}
	public String getHeadPortraitmax() {
		return headPortraitmax;
	}
	public void setHeadPortraitmax(String headPortraitmax) {
		this.headPortraitmax = headPortraitmax;
	}
	public Date getRegistertime() {
		return registertime;
	}
	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}
	public String getDisablestate() {
		return disablestate;
	}
	public void setDisablestate(String disablestate) {
		this.disablestate = disablestate;
	}
	public String getDisableHis() {
		return disableHis;
	}
	public void setDisableHis(String disableHis) {
		this.disableHis = disableHis;
	}
	public Integer getUsetimes() {
		return usetimes;
	}
	public void setUsetimes(Integer usetimes) {
		this.usetimes = usetimes;
	}
	public Double getGpsspeed() {
		return gpsspeed;
	}
	public void setGpsspeed(Double gpsspeed) {
		this.gpsspeed = gpsspeed;
	}
	public Double getGpsdirection() {
		return gpsdirection;
	}
	public void setGpsdirection(Double gpsdirection) {
		this.gpsdirection = gpsdirection;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getWithdrawpwd() {
		return withdrawpwd;
	}

	public void setWithdrawpwd(String withdrawpwd) {
		this.withdrawpwd = withdrawpwd;
	}

	public int getWdpwdchangestate() {
		return wdpwdchangestate;
	}

	public void setWdpwdchangestate(int wdpwdchangestate) {
		this.wdpwdchangestate = wdpwdchangestate;
	}
}
