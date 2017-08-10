package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/7/31.
 */
public class PubOrderCancelRule {

    /**
     * 主键
     */
    private String id;

    /**
     * 所属城市
     */
    private String citycode;

    /**
     * 服务业务(0-网约车,1-出租车)
     */
    private Integer cartype;

    /**
     * 免责取消时限(分)
     */
    private Integer cancelcount;

    /**
     * 免责取消时限(分)
     */
    private Integer latecount;

    /**
     * 免责等待时限(分)
     */
    private Integer watingcount;

    /**
     * 取消金额
     */
    private Integer price;

    /**
     * 系统类型(0-运管端,1-租赁端)
     */
    private Integer platformtype;

    /**
     * 租赁公司id
     */
    private String leasescompanyid;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public Integer getCartype() {
        return cartype;
    }

    public void setCartype(Integer cartype) {
        this.cartype = cartype;
    }

    public Integer getCancelcount() {
        return cancelcount;
    }

    public void setCancelcount(Integer cancelcount) {
        this.cancelcount = cancelcount;
    }

    public Integer getLatecount() {
        return latecount;
    }

    public void setLatecount(Integer latecount) {
        this.latecount = latecount;
    }

    public Integer getWatingcount() {
        return watingcount;
    }

    public void setWatingcount(Integer watingcount) {
        this.watingcount = watingcount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
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
}
