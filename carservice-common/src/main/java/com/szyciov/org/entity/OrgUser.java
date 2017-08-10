package com.szyciov.org.entity;

import java.util.Date;

/**
 * 机构用户表
 */
public class OrgUser {
	/**
	 * 机构用户id
	 */
	public String id;
	/**
	 * 所属部门
	 */
	public String dpetId;
	/**
	 * 用户类型
	 */
	public String userType;
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
	public String nickName;
	/**
	 * 用户密码
	 */
	public String userPassword;
	/**
	 * 性别
	 */
	public String sex;
	/**
	 * 特殊状态 0-普通，1-特殊
	 */
	public String specialState;
	/**
	 * 头像（小）
	 */
	public String headPortraitMin;
	/**
	 * 头像（大）
	 */
	public String headPortraitMax;
	/**
	 * 注册时间
	 */
	public Date registerTime;
	/**
	 * 禁用状态
	 */
	public String disableState;
	/**
	 * 禁用历史
	 */
	public String disableHis;

	/**
	 * 用车次数
	 */
	public Integer useTimes;
	/**
	 * GPS速度
	 */
	public Double gpsSpeed;
	/**
	 * GPS方向
	 */
	public Double gpsDirection;
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
	public Date createTime;
	/**
	 * 更新时间
	 */
	public Date updateTime;
	/**
	 * 数据状态
	 */
	public Integer status;
	/**
	 * 机构id
	 **/
	public String organId;

	/**
	 * 机构名称
	 */
	private String orgcaption;
	/**
	 * 部门名称
	 */
	private String deptcation;
	/**
	 * 0-主账号，1-子账号 是否主账号
	 */
	private String mainaccount;

	/**
	 * 0-未关联，1-已关联 关联状态
	 */
	private String bindstate;
	/**
	 * 租赁公司名字
	 */
	private String name;
	/**
	 * k开通时间
	 */
	private String openTime;
	/**
	 * org_organ_company_ref id
	 */
	private String orgOrganCompanyRefId;

	/**
	 * 是否存在未完成订单
	 */
	private boolean notpay;
	
	/**
	 * 是否分配了用车规则
	 */
	private boolean hasrule;
	
	/**
	 * 未完成订单数(此数值不能>5,如果>=5则不能继续下单)
	 */
	private int notdone;
	
	/**
	 * 登陆次数
	 * */
	private int logontimes;
	
	/**
	 * 所在机构所属城市
	 */
	private String citycaption;
	
	/**
	 * 提现密码
	 */
	private String withdrawpwd;
	
	/**
	 * 提现密码修改过的标识
	 */
	private int wdpwdchangestate;
	
	/**
	 * 客户类型
	 * @author xuxxtr
	 */
	private String customerType;
	
	/**
	 * 注册城市
	 */
	private String registercity;
	
	/**  
	 * 获取是否分配了用车规则  
	 * @return hasrule 是否分配了用车规则  
	 */
	public boolean isHasrule() {
		return hasrule;
	}
	
	/**  
	 * 设置是否分配了用车规则  
	 * @param hasrule 是否分配了用车规则  
	 */
	public void setHasrule(boolean hasrule) {
		this.hasrule = hasrule;
	}
	
	/**  
	 * 获取未完成订单数(此数值不能>5如果>=5则不能继续下单)  
	 * @return notdone 未完成订单数(此数值不能>5如果>=5则不能继续下单)  
	 */
	public int getNotdone() {
		return notdone;
	}
	
	
	
	/**  
	 * 设置未完成订单数(此数值不能>5如果>=5则不能继续下单)  
	 * @param notdone 未完成订单数(此数值不能>5如果>=5则不能继续下单)  
	 */
	public void setNotdone(int notdone) {
		this.notdone = notdone;
	}

	/**  
	 * 获取所在机构所属城市  
	 * @return citycaption 所在机构所属城市  
	 */
	public String getCitycaption() {
		return citycaption;
	}
	
	/**  
	 * 设置所在机构所属城市  
	 * @param citycaption 所在机构所属城市  
	 */
	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}
	

	/**
	 * 获取机构用户id
	 * 
	 * @return id 机构用户id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置机构用户id
	 * 
	 * @param id
	 *            机构用户id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取所属部门
	 * 
	 * @return dpetId 所属部门
	 */
	public String getDpetId() {
		return dpetId;
	}

	/**
	 * 设置所属部门
	 * 
	 * @param dpetId
	 *            所属部门
	 */
	public void setDpetId(String dpetId) {
		this.dpetId = dpetId;
	}

	/**
	 * 获取用户类型
	 * 
	 * @return userType 用户类型
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设置用户类型
	 * 
	 * @param userType
	 *            用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 获取账号（手机号）
	 * 
	 * @return account 账号（手机号）
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置账号（手机号）
	 * 
	 * @param account
	 *            账号（手机号）
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取邮箱
	 * 
	 * @return email 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 * 
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取昵称
	 * 
	 * @return nickName 昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 设置昵称
	 * 
	 * @param nickName
	 *            昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 获取用户密码
	 * 
	 * @return userPassword 用户密码
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * 设置用户密码
	 * 
	 * @param userPassword
	 *            用户密码
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * 获取性别
	 * 
	 * @return sex 性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别
	 * 
	 * @param sex
	 *            性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**  
	 * 获取特殊状态0-普通，1-特殊  
	 * @return specialState 特殊状态0-普通，1-特殊  
	 */
	public String getSpecialState() {
		return specialState;
	}

	/**  
	 * 设置特殊状态0-普通，1-特殊  
	 * @param specialState 特殊状态0-普通，1-特殊  
	 */
	public void setSpecialState(String specialState) {
		this.specialState = specialState;
	}
	
	/**
	 * 获取头像（小）
	 * 
	 * @return headPortraitMin 头像（小）
	 */
	public String getHeadPortraitMin() {
		return headPortraitMin;
	}

	/**
	 * 设置头像（小）
	 * 
	 * @param headPortraitMin
	 *            头像（小）
	 */
	public void setHeadPortraitMin(String headPortraitMin) {
		this.headPortraitMin = headPortraitMin;
	}

	/**
	 * 获取头像（大）
	 * 
	 * @return headPortraitMax 头像（大）
	 */
	public String getHeadPortraitMax() {
		return headPortraitMax;
	}

	/**
	 * 设置头像（大）
	 * 
	 * @param headPortraitMax
	 *            头像（大）
	 */
	public void setHeadPortraitMax(String headPortraitMax) {
		this.headPortraitMax = headPortraitMax;
	}

	/**
	 * 获取注册时间
	 * 
	 * @return registerTime 注册时间
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * 设置注册时间
	 * 
	 * @param registerTime
	 *            注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * 获取禁用状态
	 * 
	 * @return disableState 禁用状态
	 */
	public String getDisableState() {
		return disableState;
	}

	/**
	 * 设置禁用状态
	 * 
	 * @param disableState
	 *            禁用状态
	 */
	public void setDisableState(String disableState) {
		this.disableState = disableState;
	}

	/**
	 * 获取禁用历史
	 * 
	 * @return disableHis 禁用历史
	 */
	public String getDisableHis() {
		return disableHis;
	}

	/**
	 * 设置禁用历史
	 * 
	 * @param disableHis
	 *            禁用历史
	 */
	public void setDisableHis(String disableHis) {
		this.disableHis = disableHis;
	}

	/**
	 * 获取用车次数
	 * 
	 * @return useTimes 用车次数
	 */
	public Integer getUseTimes() {
		return useTimes;
	}

	/**
	 * 设置用车次数
	 * 
	 * @param useTimes
	 *            用车次数
	 */
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}

	/**
	 * 获取GPS速度
	 * 
	 * @return gpsSpeed GPS速度
	 */
	public Double getGpsSpeed() {
		return gpsSpeed;
	}

	/**
	 * 设置GPS速度
	 * 
	 * @param gpsSpeed
	 *            GPS速度
	 */
	public void setGpsSpeed(Double gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}

	/**
	 * 获取GPS方向
	 * 
	 * @return gpsDirection GPS方向
	 */
	public Double getGpsDirection() {
		return gpsDirection;
	}

	/**
	 * 设置GPS方向
	 * 
	 * @param gpsDirection
	 *            GPS方向
	 */
	public void setGpsDirection(Double gpsDirection) {
		this.gpsDirection = gpsDirection;
	}

	/**
	 * 获取经度
	 * 
	 * @return lng 经度
	 */
	public Double getLng() {
		return lng;
	}

	/**
	 * 设置经度
	 * 
	 * @param lng
	 *            经度
	 */
	public void setLng(Double lng) {
		this.lng = lng;
	}

	/**
	 * 获取纬度
	 * 
	 * @return lat 纬度
	 */
	public Double getLat() {
		return lat;
	}

	/**
	 * 设置纬度
	 * 
	 * @param lat
	 *            纬度
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return createTime 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取更新时间
	 * 
	 * @return updateTime 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param updateTime
	 *            更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取数据状态
	 * 
	 * @return status 数据状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置数据状态
	 * 
	 * @param status
	 *            数据状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取机构id
	 * 
	 * @return organId 机构id
	 */
	public String getOrganId() {
		return organId;
	}

	/**
	 * 设置机构id
	 * 
	 * @param organId
	 *            机构id
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}

	/**
	 * 获取机构名称
	 * 
	 * @return orgcaption 机构名称
	 */
	public String getOrgcaption() {
		return orgcaption;
	}

	/**
	 * 设置机构名称
	 * 
	 * @param orgcaption
	 *            机构名称
	 */
	public void setOrgcaption(String orgcaption) {
		this.orgcaption = orgcaption;
	}

	/**
	 * 获取部门名称
	 * 
	 * @return deptcation 部门名称
	 */
	public String getDeptcation() {
		return deptcation;
	}

	/**
	 * 设置部门名称
	 * 
	 * @param deptcation
	 *            部门名称
	 */
	public void setDeptcation(String deptcation) {
		this.deptcation = deptcation;
	}

	/**
	 * 获取0-主账号，1-子账号是否主账号
	 * 
	 * @return mainaccount 0-主账号，1-子账号是否主账号
	 */
	public String getMainaccount() {
		return mainaccount;
	}

	/**
	 * 设置0-主账号，1-子账号是否主账号
	 * 
	 * @param mainaccount
	 *            0-主账号，1-子账号是否主账号
	 */
	public void setMainaccount(String mainaccount) {
		this.mainaccount = mainaccount;
	}

	/**
	 * 获取0-未关联，1-已关联关联状态
	 * 
	 * @return bindstate 0-未关联，1-已关联关联状态
	 */
	public String getBindstate() {
		return bindstate;
	}

	/**
	 * 设置0-未关联，1-已关联关联状态
	 * 
	 * @param bindstate
	 *            0-未关联，1-已关联关联状态
	 */
	public void setBindstate(String bindstate) {
		this.bindstate = bindstate;
	}

	/**
	 * 获取租赁公司名字
	 * 
	 * @return name 租赁公司名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置租赁公司名字
	 * 
	 * @param name
	 *            租赁公司名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取k开通时间
	 * 
	 * @return openTime k开通时间
	 */
	public String getOpenTime() {
		return openTime;
	}

	/**
	 * 设置k开通时间
	 * 
	 * @param openTime
	 *            k开通时间
	 */
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	/**
	 * 获取org_organ_company_refid
	 * 
	 * @return orgOrganCompanyRefId org_organ_company_refid
	 */
	public String getOrgOrganCompanyRefId() {
		return orgOrganCompanyRefId;
	}

	/**
	 * 设置org_organ_company_refid
	 * 
	 * @param orgOrganCompanyRefId
	 *            org_organ_company_refid
	 */
	public void setOrgOrganCompanyRefId(String orgOrganCompanyRefId) {
		this.orgOrganCompanyRefId = orgOrganCompanyRefId;
	}

	/**
	 * 获取是否存在未完成订单
	 * 
	 * @return notpay 是否存在未完成订单
	 */
	public boolean isNotpay() {
		return notpay;
	}

	/**
	 * 设置是否存在未完成订单
	 * 
	 * @param notpay
	 *            是否存在未完成订单
	 */
	public void setNotpay(boolean notpay) {
		this.notpay = notpay;
	}

	public int getLogontimes() {
		return logontimes;
	}

	public void setLogontimes(int logontimes) {
		this.logontimes = logontimes;
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

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getRegistercity() {
		return registercity;
	}

	public void setRegistercity(String registercity) {
		this.registercity = registercity;
	}


}
