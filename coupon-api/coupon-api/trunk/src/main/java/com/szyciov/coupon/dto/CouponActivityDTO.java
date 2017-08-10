package com.szyciov.coupon.dto;

import lombok.Data;

/**
 * 抵用券活动信息DTO
 * @author LC
 * @date 2017/8/4
 */
public class CouponActivityDTO {

    private String id;

    private String lecompanyid;

    private Integer sendservicetype;

    private Integer sendruletarget;

    /**
     * 规则字符串
     */
    private String couponrule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLecompanyid() {
        return lecompanyid;
    }

    public void setLecompanyid(String lecompanyid) {
        this.lecompanyid = lecompanyid;
    }

    public Integer getSendservicetype() {
        return sendservicetype;
    }

    public void setSendservicetype(Integer sendservicetype) {
        this.sendservicetype = sendservicetype;
    }

    public Integer getSendruletarget() {
        return sendruletarget;
    }

    public void setSendruletarget(Integer sendruletarget) {
        this.sendruletarget = sendruletarget;
    }

    public String getCouponrule() {
        return couponrule;
    }

    public void setCouponrule(String couponrule) {
        this.couponrule = couponrule;
    }
}
 