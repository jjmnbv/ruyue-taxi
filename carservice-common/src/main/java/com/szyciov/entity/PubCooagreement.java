package com.szyciov.entity;

import java.util.Date;

public class PubCooagreement {
    /**
    主键
     */
    private String id;

    /**
    所属租赁,数据归属
     */
    private String companyid;

    /**
    租赁公司(合作方)
     */
    private String leasecompanyid;

    /**
    协议名称
     */
    private String cooname;

    /**
    业务类型(0-网约车,1-出租车)
     */
    private Integer servicetype;

    /**
    数据所属平台(0-运管端，1-租赁端)
     */
    private Integer platformtype;

    /**
    创建时间
     */
    private Date createtime;

    /**
    更新时间
     */
    private Date updatetime;

    /**
    创建人
     */
    private String creater;

    /**
    更新人
     */
    private String updater;

    /**
    数据状态
     */
    private Integer status;

    /**
    协议内容
     */
    private String coocontent;

    /**
     * get 主键
     */
    public String getId() {
        return id;
    }

    /**
     *
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
     *
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
     *
     * set 租赁公司(合作方)
     */
    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid == null ? null : leasecompanyid.trim();
    }

    /**
     * get 协议名称
     */
    public String getCooname() {
        return cooname;
    }

    /**
     *
     * set 协议名称
     */
    public void setCooname(String cooname) {
        this.cooname = cooname == null ? null : cooname.trim();
    }

    /**
     * get 业务类型(0-网约车,1-出租车)
     */
    public Integer getServicetype() {
        return servicetype;
    }

    /**
     *
     * set 业务类型(0-网约车,1-出租车)
     */
    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    /**
     * get 数据所属平台(0-运管端，1-租赁端)
     */
    public Integer getPlatformtype() {
        return platformtype;
    }

    /**
     *
     * set 数据所属平台(0-运管端，1-租赁端)
     */
    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

    /**
     * get 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     *
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
     *
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
     *
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
     *
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
     *
     * set 数据状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * get 协议内容
     */
    public String getCoocontent() {
        return coocontent;
    }

    /**
     *
     * set 协议内容
     */
    public void setCoocontent(String coocontent) {
        this.coocontent = coocontent == null ? null : coocontent.trim();
    }
}