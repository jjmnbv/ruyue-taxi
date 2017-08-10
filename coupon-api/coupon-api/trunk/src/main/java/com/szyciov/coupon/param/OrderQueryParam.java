package com.szyciov.coupon.param;

import java.time.LocalDateTime;

/**
 * 订单查询参数
 *
 * @author LC
 * @date 2017/8/1
 */
public class OrderQueryParam {

    //开始时间
    private LocalDateTime startDt;

    //结束时间
    private LocalDateTime endDt;

    //订单归属公司ID
    private String companyid;

    //上车城市
    private String cityCode;

    //用户ID
    private String userId;

    //数据表名
    private String tbName;

    public LocalDateTime getStartDt() {
        return startDt;
    }

    public void setStartDt(LocalDateTime startDt) {
        this.startDt = startDt;
    }

    public LocalDateTime getEndDt() {
        return endDt;
    }

    public void setEndDt(LocalDateTime endDt) {
        this.endDt = endDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
 