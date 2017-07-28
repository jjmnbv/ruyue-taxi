package com.szyciov.org.entity;

import com.szyciov.entity.AbstractOrder;

/**
 * @ClassName OrgOrder
 * @author Efy
 * @Description 机构订单表
 * @date 2016年9月22日 17:56:22
 */
public class OrgOrder extends AbstractOrder {

	/**
	 * 用车事由类型 包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 */
	private String vehiclessubjecttype;

	/**
	 * 用车事由
	 */
	private String vehiclessubject;

	/**
	 * 机构ID
	 */
	private String organid;

	/**
	 * 支付方式
	 */
	private String paymethod;

	/**
	 * 计费规则(0-标准 1-个性化)
	 */
	private String rulestype;
	
	//支付方式 1-钱包支付，2-微信支付，3-支付宝支付
	private String payTypeShow;
	//订单状态
	private String orderStatusShow;
	
	private String userMessage;//乘车人信息
	
	private String companyName;//租赁公司名字
	
	private String nickname ;//下单人名字
	private String account;//下单人电话
	private String deptid;//部门
	private String modelName; //车型名字
	private String deptname;//部门名称
	//实际车型
	private String factmodel;
	//计费车型
	private String pricemodel;
	//累积时间
	private String cumulativeTime;
	private String jobnum; //资格证号
	//司机信息
	private String driverName;
	public String name;//司机姓名
	public String phone;//司机电话
	public String plateno;//车牌号
    private String manualDriver; // 手动选择司机 0: 否    1:是
    private String usetimeShow;
    
    public String getUsetimeShow() {
		return usetimeShow;
	}

	public void setUsetimeShow(String usetimeShow) {
		this.usetimeShow = usetimeShow;
	}

	public String getManualDriver() {
        return manualDriver;
    }

    public void setManualDriver(String manualDriver) {
        this.manualDriver = manualDriver;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getJobnum() {
		return jobnum;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}

	/**
	 * 获取用车事由类型包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 * 
	 * @return vehiclessubjecttype 用车事由类型包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 */
	public String getVehiclessubjecttype() {
		return vehiclessubjecttype;
	}

	/**
	 * 设置用车事由类型包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 * 
	 * @param vehiclessubjecttype
	 *            用车事由类型包括：公务出行、接待客户、会议用车、商务差旅、其它选项，从字典中读取加载
	 */
	public void setVehiclessubjecttype(String vehiclessubjecttype) {
		this.vehiclessubjecttype = vehiclessubjecttype;
	}

	/**
	 * 获取用车事由
	 * 
	 * @return vehiclessubject 用车事由
	 */
	public String getVehiclessubject() {
		return vehiclessubject;
	}

	/**
	 * 设置用车事由
	 * 
	 * @param vehiclessubject
	 *            用车事由
	 */
	public void setVehiclessubject(String vehiclessubject) {
		this.vehiclessubject = vehiclessubject;
	}

	/**
	 * 获取机构ID
	 * 
	 * @return organid 机构ID
	 */
	public String getOrganid() {
		return organid;
	}

	/**
	 * 设置机构ID
	 * 
	 * @param organid
	 *            机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}

	/**
	 * 获取支付方式
	 * 
	 * @return paymethod 支付方式
	 */
	public String getPaymethod() {
		return paymethod;
	}

	/**
	 * 设置支付方式
	 * 
	 * @param paymethod
	 *            支付方式
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getPayTypeShow() {
		return payTypeShow;
	}

	public void setPayTypeShow(String payTypeShow) {
		this.payTypeShow = payTypeShow;
	}

	public String getOrderStatusShow() {
		return orderStatusShow;
	}

	public void setOrderStatusShow(String orderStatusShow) {
		this.orderStatusShow = orderStatusShow;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**  
	 * 获取计费规则(0-标准1-个性化)  
	 * @return rulestype 计费规则(0-标准1-个性化)  
	 */
	public String getRulestype() {
		return rulestype;
	}
	

	/**  
	 * 设置计费规则(0-标准1-个性化)  
	 * @param rulestype 计费规则(0-标准1-个性化)  
	 */
	public void setRulestype(String rulestype) {
		this.rulestype = rulestype;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getFactmodel() {
		return factmodel;
	}

	public void setFactmodel(String factmodel) {
		this.factmodel = factmodel;
	}

	public String getPricemodel() {
		return pricemodel;
	}

	public void setPricemodel(String pricemodel) {
		this.pricemodel = pricemodel;
	}

	public String getCumulativeTime() {
		return cumulativeTime;
	}

	public void setCumulativeTime(String cumulativeTime) {
		this.cumulativeTime = cumulativeTime;
	}

}
