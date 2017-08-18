package com.szyciov.entity;

import java.util.Date;

public class PubVehcline {
    /**
     * 主键
     */
    private String id;

    /**
     * 品牌ID
     */
    private String vehcbrandid;

    /**
     * 所属租赁公司
     */
    private String leasescompanyid;

    /**
     * 车系名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

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
     * 所属平台  0-运管端，1-租赁端
     */
    private Integer platformtype;

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
     * get 品牌ID
     */
    public String getVehcbrandid() {
        return vehcbrandid;
    }

    /**
     * set 品牌ID
     */
    public void setVehcbrandid(String vehcbrandid) {
        this.vehcbrandid = vehcbrandid == null ? null : vehcbrandid.trim();
    }

    /**
     * get 所属租赁公司
     */
    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    /**
     * set 所属租赁公司
     */
    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid == null ? null : leasescompanyid.trim();
    }

    /**
     * get 车系名称
     */
    public String getName() {
        return name;
    }

    /**
     * set 车系名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * get 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * set 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    /**
     * get 所属平台  0-运管端，1-租赁端
     */
    public Integer getPlatformtype() {
        return platformtype;
    }

    /**
     * set 所属平台  0-运管端，1-租赁端
     */
    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }
}