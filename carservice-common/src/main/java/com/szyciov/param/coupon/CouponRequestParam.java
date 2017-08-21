package com.szyciov.param.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 抵用券查询参数
 * @author LC
 * @date 2017/8/8
 */
public class CouponRequestParam {

    /**
     * 用户token
     */
    @ApiModelProperty("用户token")
    private String userToken;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;

    /**
     * 使用业务 1-出租车 2-网约车
     */
    @ApiModelProperty("使用业务 (1-出租车 2-网约车)")
    private Integer serviceType;

    /**
     * 租赁公司/运管公司ID
     */
    @ApiModelProperty("租赁公司/运管公司ID")
    private String companyId;

    /**
     * 城市code
     */
    @ApiModelProperty("城市code")
    private String cityCode;

    /**
     * 用户类型
     * 1-机构客户，2-机构用户，3个人用户
     */
    @ApiModelProperty("用户类型 (1机构客户，2机构用户，3个人用户)")
    private Integer userType;

    /**
     * 分页开始条数
     */
    @ApiModelProperty("分页开始条数")
    private Integer iDisplayStart;

    /**
     * 每页显示条数
     */
    @ApiModelProperty("每页显示条数")
    private Integer iDisplayLength;

    /**
     * 接口版本号，默认v4.0
     */
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

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
 