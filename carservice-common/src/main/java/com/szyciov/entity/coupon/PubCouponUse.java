package com.szyciov.entity.coupon;

import java.time.LocalDateTime;

/**
 * 优惠券使用信息表 实体
 * @author LC
 * @date 2017/8/8
 */
public class PubCouponUse {

    private String id;
    /**
     * 抵用券ID
     */
    private String couponidref;
    /**
     * 订单/账单ID
     */
    private String billingorderid;
    /**
     * 抵用券金额
     */
    private Double couponmoney;
    /**
     * 原抵扣金额
     */
    private Double discountamount;
    /**
     * 优惠券使用状态:锁定/使用
     */
    private Integer usestate;
    /**
     * 使用类型：账单/订单
     */
    private Integer usetype;
    /**
     * 实际抵扣金额
     */
    private Double actualamount;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
    private String creater;
    private String updater;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponidref() {
        return couponidref;
    }

    public void setCouponidref(String couponidref) {
        this.couponidref = couponidref;
    }

    public String getBillingorderid() {
        return billingorderid;
    }

    public void setBillingorderid(String billingorderid) {
        this.billingorderid = billingorderid;
    }

    public Double getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(Double couponmoney) {
        this.couponmoney = couponmoney;
    }

    public Double getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(Double discountamount) {
        this.discountamount = discountamount;
    }

    public Integer getUsestate() {
        return usestate;
    }

    public void setUsestate(Integer usestate) {
        this.usestate = usestate;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDateTime updatetime) {
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

    public Double getActualamount() {
        return actualamount;
    }

    public void setActualamount(Double actualamount) {
        this.actualamount = actualamount;
    }
}
