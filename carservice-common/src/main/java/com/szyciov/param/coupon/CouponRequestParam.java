package com.szyciov.param.coupon;

/**
 * 抵用券查询参数
 * @author LC
 * @date 2017/8/8
 */
public class CouponRequestParam {

    private String userToken;

    private String userId;

    private Integer serviceType;

    private String companyId;

    private String cityCode;

    private Integer userType;

    private Integer iDisplayStart;

    private Integer iDisplayLength;

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
 