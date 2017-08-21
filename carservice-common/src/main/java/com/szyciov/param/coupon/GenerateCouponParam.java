package com.szyciov.param.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 生成抵用券请求参数对象
 *
 * @author LC
 * @date 2017/7/25
 */
public class GenerateCouponParam {

    /**
     * 优惠券类型
     * 1-注册，2充值，3消费，4活动，5邀请，6人工
     */
    @ApiModelProperty("优惠券类型 (1注册，2充值，3消费，4活动，5邀请，6人工)")
    private Integer type;

    /**
     * 用户类型
     * 1-机构客户，2-机构用户，3个人用户
     */
    @ApiModelProperty("用户类型 (1机构客户，2机构用户，3个人用户)")
    private Integer userType;

    /**
     * 租赁公司id/运管id
     */
    @ApiModelProperty("租赁公司id/运管id")
    private String companyId;

    /**
     * 用户token
     */
    @ApiModelProperty("用户token")
    private String userToken;

    /**
     * 城市ID
     * 定位城市ID
     */
    @ApiModelProperty("城市ID")
    private String cityCode;

    /**
     * 个人用户/机构用户/机构客户ID
     */
    @ApiModelProperty("个人用户/机构用户/机构客户ID")
    private String userId;

    /**
     * 用户手机
     */
    @ApiModelProperty("用户手机号")
    private String userPhone;

    /**
     * 充值金额
     */
    @ApiModelProperty("充值金额")
    private Double money;

    /**
     * 接口版本
     */
    @ApiModelProperty("接口版本")
    private String version;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
 