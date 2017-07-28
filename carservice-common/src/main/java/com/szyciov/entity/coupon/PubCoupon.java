package com.szyciov.entity.coupon;

import java.util.Date;

/**
 * 抵用券实体
 * Created by LC on 2017/7/25.
 */
public class PubCoupon {

    /**
     * 主键
     */
    private String id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券所属活动
     */
    private String couponactivyidref;
    /**
     * 优惠券可用业务
     * 1-出租车，2-网约车
     */
    private Integer servicetype;
    /**
     * 优惠券可使用对象
     * 1-机构客户，2机构用户，3-个人用户
     */
    private Integer target;
    /**
     * 优惠券持有人
     */
    private String useid;
    /**
     * 优惠券金额
     */
    private Double money;
    /**
     * 使用区域
     * 1-发放区域有效，2-开通业务城市有效(2即不限制区域)
     */
    private Integer usetype;
    /**
     * 锁定状态
     * 0-未锁定，1-锁定
     */
    private Integer lockstate;
    /**
     * 优惠券状态
     * 0-未使用，1-已使用，2-已过期
     */
    private Integer couponstatus;
    /**
     * 有效期开始时间
     */
    private Date outimestart;
    /**
     * 有效期结束时间
     */
    private Date outtimeend;
    /**
     * 租赁公司
     */
    private String lecompanyid;
    /**
     * 平台类型
     * 0-运管端，1-租赁端
     */
    private Integer platformtype;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouponactivyidref() {
        return couponactivyidref;
    }

    public void setCouponactivyidref(String couponactivyidref) {
        this.couponactivyidref = couponactivyidref;
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getUseid() {
        return useid;
    }

    public void setUseid(String useid) {
        this.useid = useid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
    }

    public Integer getLockstate() {
        return lockstate;
    }

    public void setLockstate(Integer lockstate) {
        this.lockstate = lockstate;
    }

    public Integer getCouponstatus() {
        return couponstatus;
    }

    public void setCouponstatus(Integer couponstatus) {
        this.couponstatus = couponstatus;
    }

    public Date getOutimestart() {
        return outimestart;
    }

    public void setOutimestart(Date outimestart) {
        this.outimestart = outimestart;
    }

    public Date getOuttimeend() {
        return outtimeend;
    }

    public void setOuttimeend(Date outtimeend) {
        this.outtimeend = outtimeend;
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
 