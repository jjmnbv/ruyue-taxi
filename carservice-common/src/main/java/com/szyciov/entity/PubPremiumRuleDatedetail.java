package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/7/27.
 */
public class PubPremiumRuleDatedetail {

    /**
     * 主键
     */
    private String id;

    /**
     * 溢价规则主表ID
     */
    private String premiumruleid;

    /**
     * 开始日期
     */
    private Date startdate;

    /**
     * 结束日期
     */
    private Date enddate;

    /**
     * 起始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 溢价倍率
     */
    private Double premiumrate;

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

    public String getPremiumruleid() {
        return premiumruleid;
    }

    public void setPremiumruleid(String premiumruleid) {
        this.premiumruleid = premiumruleid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Double getPremiumrate() {
        return premiumrate;
    }

    public void setPremiumrate(Double premiumrate) {
        this.premiumrate = premiumrate;
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
