package com.szyciov.coupon.dto;

/**
 * 订单信息DTO
 *
 * @author LC
 * @date 2017/8/4
 */
public class OrderInfoDTO {

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 订单归属ID
     */
    private String companyid;


    private String oncity;



    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOncity() {
        return oncity;
    }

    public void setOncity(String oncity) {
        this.oncity = oncity;
    }
}
 