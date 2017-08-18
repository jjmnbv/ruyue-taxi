package com.szyciov.coupon.dto;

import lombok.Data;

/**
 * 生成抵用券DTO
 *
 * @author LC
 * @date 2017/7/28
 */
public class GenerateCouponDTO {

    //抵用券ID
    private String id;
    //抵用券金额
    private Double money;
    //用户ID
    private String userId;
    //活动名称
    private String name;
    //租赁公司Id
    private String companyid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
 