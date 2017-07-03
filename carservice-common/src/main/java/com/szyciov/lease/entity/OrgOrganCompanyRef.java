package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 机构、人员、租赁公司关联
 */
public class OrgOrganCompanyRef {
	/**
	 * 主键id
	 */
	public String id;
	/**
	 * 所属机构
	 */
	public String organId;
	/**
	 * 所属租赁公司
	 */
	public String companyId;
	/**
	 * 所属用户
	 */
	public String userId;
	/**
	 * 是否主账号
	 */
	public String mainAccount;
	/**
	 * 所属主账号
	 */
	public String parentId;
	/**
	 * 可用余额
	 */
	public double balance;
	/**
	 * 账户状态 0-限制用车，1-正常用车
	 */
	public String accountStatus;
	/**
	 * 实际余额
	 */
	public Double actualBalance;
	/**
	 * 信用额度
	 */
	public Double lineOfCredit;
	/**
	 * 下期信用额度  nextlineofcredit
	 */
	public Double nextLineOfCredit;
	/**
	 * 0-未提供，1-已提供  约车服务
	 */
	public String carService;
	/**
	 * 0-未提供，1-已提供 接机服务
	 */
	public String pickUpService;
	/**
	 * 0-未提供，1-已提供  送机服务
	 */
	public String dropOffService;
	/**
	 * 0-未关联，1-已关联 关联状态
	 */
	public String bindState;
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
	 * 是否是第一次登陆
	 * */
	public String firstTime;
	/**
	 * 租赁公司名称
	 * */
	public String name;
	/**
	 * 未结算金额
	 */
	public Double unBalanced;
	/**  
	 * 获取主键id  
	 * @return id 主键id  
	 */
	public String rulesType;
	
	public String getRulesType() {
		return rulesType;
	}

	public void setRulesType(String rulesType) {
		this.rulesType = rulesType;
	}

	public String getId() {
		return id;
	}
	
	/**  
	 * 设置主键id  
	 * @param id 主键id  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * 获取所属机构  
	 * @return organId 所属机构  
	 */
	public String getOrganId() {
		return organId;
	}
	
	/**  
	 * 设置所属机构  
	 * @param organId 所属机构  
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	/**  
	 * 获取所属租赁公司  
	 * @return companyId 所属租赁公司  
	 */
	public String getCompanyId() {
		return companyId;
	}
	
	/**  
	 * 设置所属租赁公司  
	 * @param companyId 所属租赁公司  
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	/**  
	 * 获取所属用户  
	 * @return userId 所属用户  
	 */
	public String getUserId() {
		return userId;
	}
	
	/**  
	 * 设置所属用户  
	 * @param userId 所属用户  
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**  
	 * 获取是否主账号  
	 * @return mainAccount 是否主账号  
	 */
	public String getMainAccount() {
		return mainAccount;
	}
	
	/**  
	 * 设置是否主账号  
	 * @param mainAccount 是否主账号  
	 */
	public void setMainAccount(String mainAccount) {
		this.mainAccount = mainAccount;
	}
	
	/**  
	 * 获取所属主账号  
	 * @return parentId 所属主账号  
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**  
	 * 设置所属主账号  
	 * @param parentId 所属主账号  
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**  
	 * 获取可用余额  
	 * @return balance 可用余额  
	 */
	public double getBalance() {
		return balance;
	}
	
	/**  
	 * 设置可用余额  
	 * @param balance 可用余额  
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**  
	 * 获取账户状态0-限制用车，1-正常用车  
	 * @return accountStatus 账户状态0-限制用车，1-正常用车  
	 */
	public String getAccountStatus() {
		return accountStatus;
	}
	
	/**  
	 * 设置账户状态0-限制用车，1-正常用车  
	 * @param accountStatus 账户状态0-限制用车，1-正常用车  
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	/**  
	 * 获取实际余额  
	 * @return actualBalance 实际余额  
	 */
	public Double getActualBalance() {
		return actualBalance;
	}
	
	/**  
	 * 设置实际余额  
	 * @param actualBalance 实际余额  
	 */
	public void setActualBalance(Double actualBalance) {
		this.actualBalance = actualBalance;
	}
	
	/**  
	 * 获取信用额度  
	 * @return lineOfCredit 信用额度  
	 */
	public Double getLineOfCredit() {
		return lineOfCredit;
	}
	
	/**  
	 * 设置信用额度  
	 * @param lineOfCredit 信用额度  
	 */
	public void setLineOfCredit(Double lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
	
	/**  
	 * 获取0-未提供，1-已提供约车服务  
	 * @return carService 0-未提供，1-已提供约车服务  
	 */
	public String getCarService() {
		return carService;
	}
	
	/**  
	 * 设置0-未提供，1-已提供约车服务  
	 * @param carService 0-未提供，1-已提供约车服务  
	 */
	public void setCarService(String carService) {
		this.carService = carService;
	}
	
	/**  
	 * 获取0-未提供，1-已提供接机服务  
	 * @return pickUpService 0-未提供，1-已提供接机服务  
	 */
	public String getPickUpService() {
		return pickUpService;
	}
	
	/**  
	 * 设置0-未提供，1-已提供接机服务  
	 * @param pickUpService 0-未提供，1-已提供接机服务  
	 */
	public void setPickUpService(String pickUpService) {
		this.pickUpService = pickUpService;
	}
	
	/**  
	 * 获取0-未提供，1-已提供送机服务  
	 * @return dropOffService 0-未提供，1-已提供送机服务  
	 */
	public String getDropOffService() {
		return dropOffService;
	}
	
	/**  
	 * 设置0-未提供，1-已提供送机服务  
	 * @param dropOffService 0-未提供，1-已提供送机服务  
	 */
	public void setDropOffService(String dropOffService) {
		this.dropOffService = dropOffService;
	}
	
	/**  
	 * 获取0-未关联，1-已关联关联状态  
	 * @return bindState 0-未关联，1-已关联关联状态  
	 */
	public String getBindState() {
		return bindState;
	}
	
	/**  
	 * 设置0-未关联，1-已关联关联状态  
	 * @param bindState 0-未关联，1-已关联关联状态  
	 */
	public void setBindState(String bindState) {
		this.bindState = bindState;
	}
	
	/**  
	 * 获取创建时间  
	 * @return createTime 创建时间  
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**  
	 * 设置创建时间  
	 * @param createTime 创建时间  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**  
	 * 获取更新时间  
	 * @return updateTime 更新时间  
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**  
	 * 设置更新时间  
	 * @param updateTime 更新时间  
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**  
	 * 获取数据状态  
	 * @return status 数据状态  
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**  
	 * 设置数据状态  
	 * @param status 数据状态  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**  
	 * 获取是否是第一次登陆  
	 * @return firstTime 是否是第一次登陆  
	 */
	public String getFirstTime() {
		return firstTime;
	}
	
	/**  
	 * 设置是否是第一次登陆  
	 * @param firstTime 是否是第一次登陆  
	 */
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	
	/**  
	 * 获取租赁公司名称  
	 * @return name 租赁公司名称  
	 */
	public String getName() {
		return name;
	}
	
	/**  
	 * 设置租赁公司名称  
	 * @param name 租赁公司名称  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 获取未结算金额  
	 * @return unBalanced 未结算金额  
	 */
	public Double getUnBalanced() {
		return unBalanced;
	}
	
	/**  
	 * 设置未结算金额  
	 * @param unBalanced 未结算金额  
	 */
	public void setUnBalanced(Double unBalanced) {
		this.unBalanced = unBalanced;
	}
	public Double getNextLineOfCredit() {
		return nextLineOfCredit;
	}
	public void setNextLineOfCredit(Double nextLineOfCredit) {
		this.nextLineOfCredit = nextLineOfCredit;
	}
}
