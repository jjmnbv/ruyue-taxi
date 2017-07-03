package com.szyciov.lease.entity;

import java.util.Date;

/**
 * 租赁公司信息
 */
public class LeLeasescompany {
    /**
     * 主键id
     */
    public String id;
    /**
     * 名称
     */
    public String name;
    /**
     * 简称
     */
    public String shortName;
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
    public String mail;
    /**
     * 客服电话
     */
    public String servicesPhone;
    /**
     * 所属地（市）
     */
    public String city;

    /**
     * 所属地（市）
     */
    public String cityShow;
    /**
     * 所属地（详细）
     */
    public String address;
    /**
     * 信用代码证图片
     */
    public String creditNoPic;
    /**
     * 信用代码证代码
     */
    public String creditNo;
    /**
     * 工商执照图片
     */
    public String bizlicPic;
    /**
     * 工商执照号码
     */
    public String bizlicNum;
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
    public String idCard;
    /**
     * toC业务状态 0-未加入，1-加入待审批，2-已加入，3-已退出
     */
    public String tocState;
    /**
     * 车牌号敏感词 0-显示，1-隐藏
     */
    public String busNumSensitive;
    /**
     * 司机敏感词 0-显示，1-隐藏
     */
    public String driverSensitive;
    /**
     * 微信账户
     */
    public String wechatAccount;
    /**
     * APPID（微信）
     */
    public String wechatappid;
    /**
     * 商户号（微信）
     */
    public String wechatmerchantno;
    /**
     * 商户密钥（微信）
     */
    public String wechatsecretkey;
    /**
     * 支付宝账户
     */
    public String alipayAccount;
    /**
     * APPID（支付宝）
     */
    public String alipayappid;
    /**
     * 应用私钥（支付宝）
     */
    public String alipayprivatekey;
    /**
     * 支付宝公钥（支付宝）
     */
    public String alipaypublickey;
    /**
     * 状态 0-正常，1-禁用
     */
    public String companyState;

    /**
     * 状态 0-正常，1-禁用
     */
    public String companyStateShow;
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
     * 账号（手机号） 账号
     */
    public String account;
    /**
     *
     * */
    public String userId;
    /**
     * 备注
     **/
    public String remarks;

    /**
     * 机构id
     */
    public String organId;

    public int firstTime;
    public String regorder;

    public String alipaypartnerid;
    public String alipaypartnerpublickey;
    public String alipaypartnerprivatekey;

    /**
     * 微信账户状态
     */
    public String wechatstatus;
    /**
     * 支付宝账户状态
     */
    public String alipaystatus;
    /**
     * 数据所属平台
     */
    public int platformtype;


    /**
     * 司机微信账户状态
     * '0-未开通,1-已开通',
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
    public String getRegorder() {
        return regorder;
    }
    public void setRegorder(String regorder) {
        this.regorder = regorder;
    }
    public int getFirstTime() {
        return firstTime;
    }
    public void setFirstTime(int firstTime) {
        this.firstTime = firstTime;
    }
//	public String getOrganId() {
//		return organId;
//	}
//	public void setOrganId(String organId) {
//		this.organId = organId;
//	}
//	public String getRemarks() {
//		return remarks;
//	}
//	public void setRemarks(String remarks) {
//		this.remarks = remarks;
//	}
//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}

    /**
     * 获取主键id
     *
     * @return id 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id
     *            主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name
     *            名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取简称
     *
     * @return shortName 简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置简称
     *
     * @param shortName
     *            简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取联系人
     *
     * @return contacts 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts
     *            联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取联系方式
     *
     * @return phone 联系方式
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系方式
     *
     * @param phone
     *            联系方式
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return mail 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail
     *            邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取客服电话
     *
     * @return servicesPhone 客服电话
     */
    public String getServicesPhone() {
        return servicesPhone;
    }

    /**
     * 设置客服电话
     *
     * @param servicesPhone
     *            客服电话
     */
    public void setServicesPhone(String servicesPhone) {
        this.servicesPhone = servicesPhone;
    }

    /**
     * 获取所属地（市）
     *
     * @return city 所属地（市）
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所属地（市）
     *
     * @param city
     *            所属地（市）
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取所属地（市）
     *
     * @return cityShow 所属地（市）
     */
    public String getCityShow() {
        return cityShow;
    }

    /**
     * 设置所属地（市）
     *
     * @param cityShow
     *            所属地（市）
     */
    public void setCityShow(String cityShow) {
        this.cityShow = cityShow;
    }

    /**
     * 获取所属地（详细）
     *
     * @return address 所属地（详细）
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置所属地（详细）
     *
     * @param address
     *            所属地（详细）
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取信用代码证图片
     *
     * @return creditNoPic 信用代码证图片
     */
    public String getCreditNoPic() {
        return creditNoPic;
    }

    /**
     * 设置信用代码证图片
     *
     * @param creditNoPic
     *            信用代码证图片
     */
    public void setCreditNoPic(String creditNoPic) {
        this.creditNoPic = creditNoPic;
    }

    /**
     * 获取信用代码证代码
     *
     * @return creditNo 信用代码证代码
     */
    public String getCreditNo() {
        return creditNo;
    }

    /**
     * 设置信用代码证代码
     *
     * @param creditNo
     *            信用代码证代码
     */
    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    /**
     * 获取工商执照图片
     *
     * @return bizlicPic 工商执照图片
     */
    public String getBizlicPic() {
        return bizlicPic;
    }

    /**
     * 设置工商执照图片
     *
     * @param bizlicPic
     *            工商执照图片
     */
    public void setBizlicPic(String bizlicPic) {
        this.bizlicPic = bizlicPic;
    }

    /**
     * 获取工商执照号码
     *
     * @return bizlicNum 工商执照号码
     */
    public String getBizlicNum() {
        return bizlicNum;
    }

    /**
     * 设置工商执照号码
     *
     * @param bizlicNum
     *            工商执照号码
     */
    public void setBizlicNum(String bizlicNum) {
        this.bizlicNum = bizlicNum;
    }

    /**
     * 获取法人身份证正面
     *
     * @return idCardFront 法人身份证正面
     */
    public String getIdCardFront() {
        return idCardFront;
    }

    /**
     * 设置法人身份证正面
     *
     * @param idCardFront
     *            法人身份证正面
     */
    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    /**
     * 获取法人身份证背面
     *
     * @return idCardBack 法人身份证背面
     */
    public String getIdCardBack() {
        return idCardBack;
    }

    /**
     * 设置法人身份证背面
     *
     * @param idCardBack
     *            法人身份证背面
     */
    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    /**
     * 获取法人身份证号码
     *
     * @return idCard 法人身份证号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置法人身份证号码
     *
     * @param idCard
     *            法人身份证号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取toC业务状态0-未加入，1-加入待审批，2-已加入，3-已退出
     *
     * @return tocState toC业务状态0-未加入，1-加入待审批，2-已加入，3-已退出
     */
    public String getTocState() {
        return tocState;
    }

    /**
     * 设置toC业务状态0-未加入，1-加入待审批，2-已加入，3-已退出
     *
     * @param tocState
     *            toC业务状态0-未加入，1-加入待审批，2-已加入，3-已退出
     */
    public void setTocState(String tocState) {
        this.tocState = tocState;
    }

    /**
     * 获取车牌号敏感词0-显示，1-隐藏
     *
     * @return busNumSensitive 车牌号敏感词0-显示，1-隐藏
     */
    public String getBusNumSensitive() {
        return busNumSensitive;
    }

    /**
     * 设置车牌号敏感词0-显示，1-隐藏
     *
     * @param busNumSensitive
     *            车牌号敏感词0-显示，1-隐藏
     */
    public void setBusNumSensitive(String busNumSensitive) {
        this.busNumSensitive = busNumSensitive;
    }

    /**
     * 获取司机敏感词0-显示，1-隐藏
     *
     * @return driverSensitive 司机敏感词0-显示，1-隐藏
     */
    public String getDriverSensitive() {
        return driverSensitive;
    }

    /**
     * 设置司机敏感词0-显示，1-隐藏
     *
     * @param driverSensitive
     *            司机敏感词0-显示，1-隐藏
     */
    public void setDriverSensitive(String driverSensitive) {
        this.driverSensitive = driverSensitive;
    }

    /**
     * 获取微信账户
     *
     * @return wechatAccount 微信账户
     */
    public String getWechatAccount() {
        return wechatAccount;
    }

    /**
     * 设置微信账户
     *
     * @param wechatAccount
     *            微信账户
     */
    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    /**
     * 获取支付宝账户
     *
     * @return alipayAccount 支付宝账户
     */
    public String getAlipayAccount() {
        return alipayAccount;
    }

    /**
     * 设置支付宝账户
     *
     * @param alipayAccount
     *            支付宝账户
     */
    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    /**
     * 获取状态0-正常，1-禁用
     *
     * @return companyState 状态0-正常，1-禁用
     */
    public String getCompanyState() {
        return companyState;
    }

    /**
     * 设置状态0-正常，1-禁用
     *
     * @param companyState
     *            状态0-正常，1-禁用
     */
    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    /**
     * 获取状态0-正常，1-禁用
     *
     * @return companyStateShow 状态0-正常，1-禁用
     */
    public String getCompanyStateShow() {
        return companyStateShow;
    }

    /**
     * 设置状态0-正常，1-禁用
     *
     * @param companyStateShow
     *            状态0-正常，1-禁用
     */
    public void setCompanyStateShow(String companyStateShow) {
        this.companyStateShow = companyStateShow;
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
     * 获取
     *
     * @return creater
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置
     *
     * @param creater
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取
     *
     * @return updater
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * 设置
     *
     * @param updater
     */
    public void setUpdater(String updater) {
        this.updater = updater;
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
     * 获取账号（手机号）账号
     *
     * @return account 账号（手机号）账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号（手机号）账号
     *
     * @param account
     *            账号（手机号）账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取备注
     *
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks
     *            备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    public int getPlatformtype() {
        return platformtype;
    }
    public void setPlatformtype(int platformtype) {
        this.platformtype = platformtype;
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
