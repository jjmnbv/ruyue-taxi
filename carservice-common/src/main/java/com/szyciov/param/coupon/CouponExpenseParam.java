package com.szyciov.param.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 抵用券消费请求参数
 * @author LC
 * @date 2017/8/8
 */
public class CouponExpenseParam {

    /**
     * 使用ID
     */
    @ApiModelProperty("预约记录ID")
    private String useId;

    @ApiModelProperty("用户token")
    private String userToken;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;

    /**
     * 使用类型
     */
    @ApiModelProperty("使用类型 (1账单 2订单)")
    private Integer useType;

    /**
     * 抵用券金额
     */
    @ApiModelProperty("抵用券金额")
    private Double money;

    /**
     * 实际抵用金额
     */
    @ApiModelProperty("实际抵用金额")
    private Double discountamount;


    /**
     * 预约过的抵用券ID
     */
    @ApiModelProperty("预约过的抵用券ID")
    private String oldCouponId;

    /**
     * 新的支付抵用券ID
     */
    @ApiModelProperty("新选择的抵用券ID")
    private String couponId;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private String orderId;

    /**
     * 使用城市
     */
    @ApiModelProperty("使用城市")
    private String city;

    @ApiModelProperty("接口版本号")
    private String version;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOldCouponId() {
        return oldCouponId;
    }

    public void setOldCouponId(String oldCouponId) {
        this.oldCouponId = oldCouponId;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public Double getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(Double discountamount) {
        this.discountamount = discountamount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
 