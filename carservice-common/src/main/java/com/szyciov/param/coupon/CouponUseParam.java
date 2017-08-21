package com.szyciov.param.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 抵用券使用请求
 * @author LC
 * @date 2017/8/9
 */
public class CouponUseParam {


    @ApiModelProperty("用户token")
    private String userToken;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;

    /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private String orderId;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    private Double money;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
 