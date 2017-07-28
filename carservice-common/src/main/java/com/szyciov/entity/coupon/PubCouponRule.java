package com.szyciov.entity.coupon;

import java.util.Date;

/**
 * 抵用券规则实体
 * Created by LC on 2017/7/27.
 */
public class PubCouponRule {

    private String id;
    /**
     * 规则名称
     */
    private String name;
    /**
     * 规则类型
     * 1-注册，2充值，3消费，4活动
     */
    private Integer ruletype;
    /**
     * 用户类型
     * 1-机构客户，2-机构用户，3个人用户
     */
    private Integer ruletarget;
    /**
     * 充值满多少钱
     */
    private Double rechargemoney;
    /**
     * 消费类型
     * 1-消费频次，2-消费金额
     */
    private Integer consumetype;
    /**
     * 周期天数
     */
    private Integer cycleday;
    /**
     * 消费频次类型
     */
    private Integer consumefrequencytype;
    /**
     * 消费频次高次
     */
    private Integer consumehightimes;
    /**
     * 消费频次低次
     */
    private Integer consumelowtimes;
    /**
     *  单次消费可用
     *  0-不可用，1-可用
     */
    private Integer consumemoneysingleable;
    /**
     * 单次消费满金额
     */
    private Integer consumemoneysingelfull;
    /**
     * 周期消费可用
     * 0-不可用，1-可用
     */
    private Integer consumemoneycycleable;
    /**
     * 周期消费金额类型
     * 1-满，2-满低，3低
     */
    private Integer consumemoneycycletype;
    /**
     * 周期消费金额满值
     */
    private Double consumemoneycyclefull;
    /**
     * 周期消费金额低值
     */
    private Double consumemoneycyclelow;
    /**
     * 租赁公司
     */
    private String lecompanyid;
    /**
     * 平台类型
     */
    private Integer platformtype;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新人
     */
    private String updater;
    /**
     * 创建人
     */
    private String creater;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRuletype() {
        return ruletype;
    }

    public void setRuletype(Integer ruletype) {
        this.ruletype = ruletype;
    }

    public Integer getRuletarget() {
        return ruletarget;
    }

    public void setRuletarget(Integer ruletarget) {
        this.ruletarget = ruletarget;
    }

    public Double getRechargemoney() {
        return rechargemoney;
    }

    public void setRechargemoney(Double rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public Integer getConsumetype() {
        return consumetype;
    }

    public void setConsumetype(Integer consumetype) {
        this.consumetype = consumetype;
    }

    public Integer getCycleday() {
        return cycleday;
    }

    public void setCycleday(Integer cycleday) {
        this.cycleday = cycleday;
    }

    public Integer getConsumefrequencytype() {
        return consumefrequencytype;
    }

    public void setConsumefrequencytype(Integer consumefrequencytype) {
        this.consumefrequencytype = consumefrequencytype;
    }

    public Integer getConsumehightimes() {
        return consumehightimes;
    }

    public void setConsumehightimes(Integer consumehightimes) {
        this.consumehightimes = consumehightimes;
    }

    public Integer getConsumelowtimes() {
        return consumelowtimes;
    }

    public void setConsumelowtimes(Integer consumelowtimes) {
        this.consumelowtimes = consumelowtimes;
    }

    public Integer getConsumemoneysingleable() {
        return consumemoneysingleable;
    }

    public void setConsumemoneysingleable(Integer consumemoneysingleable) {
        this.consumemoneysingleable = consumemoneysingleable;
    }

    public Integer getConsumemoneysingelfull() {
        return consumemoneysingelfull;
    }

    public void setConsumemoneysingelfull(Integer consumemoneysingelfull) {
        this.consumemoneysingelfull = consumemoneysingelfull;
    }

    public Integer getConsumemoneycycleable() {
        return consumemoneycycleable;
    }

    public void setConsumemoneycycleable(Integer consumemoneycycleable) {
        this.consumemoneycycleable = consumemoneycycleable;
    }

    public Integer getConsumemoneycycletype() {
        return consumemoneycycletype;
    }

    public void setConsumemoneycycletype(Integer consumemoneycycletype) {
        this.consumemoneycycletype = consumemoneycycletype;
    }

    public Double getConsumemoneycyclefull() {
        return consumemoneycyclefull;
    }

    public void setConsumemoneycyclefull(Double consumemoneycyclefull) {
        this.consumemoneycyclefull = consumemoneycyclefull;
    }

    public Double getConsumemoneycyclelow() {
        return consumemoneycyclelow;
    }

    public void setConsumemoneycyclelow(Double consumemoneycyclelow) {
        this.consumemoneycyclelow = consumemoneycyclelow;
    }

    public String getLecompanyid() {
        return lecompanyid;
    }

    public void setLecompanyid(String lecompanyid) {
        this.lecompanyid = lecompanyid;
    }

    public Integer getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(Integer platformtype) {
        this.platformtype = platformtype;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
 