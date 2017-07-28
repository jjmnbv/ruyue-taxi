package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/7/27.
 */
public class PubPremiumRuleWeekdetail {

    /**
     * 主键
     */
    private String id;

    /**
     * 溢价规则主表ID
     */
    private String premiumruleid;

    /**
     * 星期(一、二、三、四、五、六、日)
     */
    private Integer weekday;

    /**
     * 起始时间
     */
    private String startdt;

    /**
     * 结束时间
     */
    private String enddt;

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

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getStartdt() {
        return startdt;
    }

    public void setStartdt(String startdt) {
        this.startdt = startdt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
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
