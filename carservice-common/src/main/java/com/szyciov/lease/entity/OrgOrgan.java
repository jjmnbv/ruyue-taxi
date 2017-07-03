package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 机构信息
 */
public class OrgOrgan {
	/**
	 * 主键id
	 */
	public String id;
	/**
	 * 机构全称
	 */
	public String fullName;
	/**
	 * 机构简称
	 */
	public String shortName;
	/**
	 *合作状态 0 停止 1  正常
	 */
	public String cooperationStatus;
	/**
	 * 联系人
	 */
	public String contacts;
	/**
	 * 联系方式
	 */
	public String phone;
	/**
	 * 邮箱
	 */
	public String email;
	/**
	 * 机构地址（市）
	 */
	public String city;
	/**
	 * 机构地址
	 */
	public String address;
	/**
	 * 结算类型  0-月结，1-季结
	 */
	public String billType;
	/**
	 * 结算日期
	 */
	public Integer billDate;
	/**
	 * 结算日期
	 */
	public String billDateShow;
	/**
	 * 机构信用代码证图片
	 */
	public String creditCodePic;
	/**
	 * 机构信用代码证代码
	 */
	public String creditCode;
	/**
	 * 工商执照图片
	 */
	public String businessLicensePic;
	/**
	 * 工商执照号码
	 */
	public String businessLicense;
	/**
	 * 法人身份证正面
	 */
	public String idCardFront;
	/**
	 * 法人身份证背面
	 */
	public String idCardBack;
	/**
	 * 法人身份证号码
	 */
	public String idCardNo;
	/**
	 * 创建时间
	 */
	public Date createTime;
	/**
	 * 更新时间
	 */
	public Date updateTime;
	/**
	 * 
	 */
	public String creater;
	/**
	 * 
	 */
	public String updater;
	/**
	 * 数据状态
	 */
	public Integer status;
	/**
	 * 账号（手机号） 机构账号
	 */
	public String account;
	/**
	 * 信用额度
	 */
	public Double lineOfCredit;
	/**
	 * 下期信用额度  nextlineofcredit
	 */
	public Double nextLineOfCredit;
	/**
	 * 租赁公司id
	 * */
	public String companyId;
	/**
	 * city name
	 * */
	private String citycaption;
	/**
	 * 机构名称首字母
	 */
	public String initials;
	/**
	 * userid
	 * 
	 * */
	public String userId;
	
	public String regorder;
	
	/**
	 * 开户账号
	 */
	public String creditcardNum;
	
	/**
	 * 开户名称
	 */
	public String creditcardName;
	
	/**
	 * 开户银行
	 */
	public String bankName;
	/**
	 * 客户类型(0-非渠道客户，1-渠道客户)
	 * */
	public String customertype;
	
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	public String getRegorder() {
		return regorder;
	}
	public void setRegorder(String regorder) {
		this.regorder = regorder;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Double getLineOfCredit() {
		return lineOfCredit;
	}
	public void setLineOfCredit(Double lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCooperationStatus() {
		return cooperationStatus;
	}
	public void setCooperationStatus(String cooperationStatus) {
		this.cooperationStatus = cooperationStatus;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Integer getBillDate() {
		return billDate;
	}
	public void setBillDate(Integer billDate) {
		this.billDate = billDate;
	}
	public String getCreditCodePic() {
		return creditCodePic;
	}
	public void setCreditCodePic(String creditCodePic) {
		this.creditCodePic = creditCodePic;
	}
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getBusinessLicensePic() {
		return businessLicensePic;
	}
	public void setBusinessLicensePic(String businessLicensePic) {
		this.businessLicensePic = businessLicensePic;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getIdCardFront() {
		return idCardFront;
	}
	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}
	public String getIdCardBack() {
		return idCardBack;
	}
	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCitycaption() {
		return citycaption;
	}
	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getBillDateShow() {
		return billDateShow;
	}
	public void setBillDateShow(String billDateShow) {
		this.billDateShow = billDateShow;
	}
	public Double getNextLineOfCredit() {
		return nextLineOfCredit;
	}
	public void setNextLineOfCredit(Double nextLineOfCredit) {
		this.nextLineOfCredit = nextLineOfCredit;
	}
	public String getCreditcardNum() {
		return creditcardNum;
	}
	public void setCreditcardNum(String creditcardNum) {
		this.creditcardNum = creditcardNum;
	}
	public String getCreditcardName() {
		return creditcardName;
	}
	public void setCreditcardName(String creditcardName) {
		this.creditcardName = creditcardName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
