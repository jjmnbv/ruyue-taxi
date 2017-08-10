package com.szyciov.entity;

import java.util.Date;

public class PubCoooperate {
    /**
     * 主键
     */
    private String id;

    /**
     * 所属租赁,数据归属
     */
    private String companyid;

    /**
     * 租赁公司(合作方)
     */
    private String leasecompanyid;

    /**
     * 合作编号
     */
    private String coono;

    /**
     * 合作类型(0-B2B联盟，1-B2C联营)
     */
    private Integer cootype;

    /**
     * 加盟业务(0-网约车,1-出租车)
     */
    private Integer servicetype;

    /**
     * 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
     */
    private Integer coostate;

    /**
     * 合作开始时间
     */
    private Date coostarttime;

    /**
     * 合作截止时间
     */
    private Date cooendtime;

    /**
     * 申请时间
     */
    private Date applicationtime;

    /**
     * 数据所属平台(0-运管端，1-租赁端)
     */
    private Integer platformtype;

    /**
     * 审核时间
     */
    private Date reviewtime;

    /**
     * 审核意见
     */
    private String reviewtext;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 数据状态
     */
    private Integer status;
    /**
     * 战略伙伴 name
     */
    private String companyName;
    /**
     * 开放资源个数
     */
    private int allCount;
    /**
     * 可用资源个数
     */
    private int availableCount;

    /**
     * 车牌号
     */
    private String plateno;
    /**
     * 司机信息
     */
    private String driverInformation;
    /**
     * 资格证号
     */
    private String jobnum;
    /**
     * 合作协议id
     */
    private String cooagreementid;

    public String getCooagreementid() {
        return cooagreementid;
    }

    public void setCooagreementid(String cooagreementid) {
        this.cooagreementid = cooagreementid;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getDriverInformation() {
        return driverInformation;
    }

    public void setDriverInformation(String driverInformation) {
        this.driverInformation = driverInformation;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * get 主键
     */
    public String getId() {
        return id;
    }

    /**
     * set 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * get 所属租赁,数据归属
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * set 所属租赁,数据归属
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    /**
     * get 租赁公司(合作方)
     */
    public String getLeasecompanyid() {
        return leasecompanyid;
    }

    /**
     * set 租赁公司(合作方)
     */
    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid == null ? null : leasecompanyid.trim();
    }

    /**
     * get 合作编号
     */
    public String getCoono() {
        return coono;
    }

    /**
     * set 合作编号
     */
    public void setCoono(String coono) {
        this.coono = coono == null ? null : coono.trim();
    }

    /**
     * get 合作类型(0-B2B联盟，1-B2C联营)
     */
    public Integer getCootype() {
        return cootype;
    }

    /**
     * set 合作类型(0-B2B联盟，1-B2C联营)
     */
    public void setCootype(Integer cootype) {
        this.cootype = cootype;
    }

    /**
     * get 加盟业务(0-网约车,1-出租车)
     */
    public Integer getServicetype() {
        return servicetype;
    }

    /**
     * set 加盟业务(0-网约车,1-出租车)
     */
    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    /**
     * get 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
     */
    public Integer getCoostate() {
        return coostate;
    }

    /**
     * set 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
     */
    public void setCoostate(Integer coostate) {
        this.coostate = coostate;
    }

    /**
     * get 合作开始时间
     */
    public Date getCoostarttime() {
        return coostarttime;
    }

    /**
     * set 合作开始时间
     */
    public void setCoostarttime(Date coostarttime) {
        this.coostarttime = coostarttime;
    }

    /**
     * get 合作截止时间
     */
    public Date getCooendtime() {
        return cooendtime;
    }

    /**
     * set 合作截止时间
     */
    public void setCooendtime(Date cooendtime) {
        this.cooendtime = cooendtime;
    }

    /**
     * get 申请时间
     */
    public Date getApplicationtime() {
        return applicationtime;
    }

    /**
     * set 申请时间
     */
    public void setApplicationtime(Date applicationtime) {
        this.applicationtime = applicationtime;
    }

    /**
     * get 数据所属平台(0-运管端，1-租赁端)
     */
    public Integer getPlatformtype() {
        return platformtype;
    }

    /**
     * set 数据所属平台(0-运管端，1-租赁端)
     */
    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

    /**
     * get 审核时间
     */
    public Date getReviewtime() {
        return reviewtime;
    }

    /**
     * set 审核时间
     */
    public void setReviewtime(Date reviewtime) {
        this.reviewtime = reviewtime;
    }

    /**
     * get 审核意见
     */
    public String getReviewtext() {
        return reviewtext;
    }

    /**
     * set 审核意见
     */
    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext == null ? null : reviewtext.trim();
    }

    /**
     * get 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * set 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * get 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * set 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * get 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * set 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * get 更新人
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * set 更新人
     */
    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    /**
     * get 数据状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * set 数据状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}