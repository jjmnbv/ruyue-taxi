package com.szyciov.param.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 抵用券预约请求参数
 * @author LC
 * @date 2017/8/8
 */
public class CouponReserveParam {


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
     * 抵用券ID
     */
    @ApiModelProperty("抵用券ID")
    private String couponId;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private String orderId;

    /**
     * 使用城市
     */
    @ApiModelProperty("使用城市code")
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
 