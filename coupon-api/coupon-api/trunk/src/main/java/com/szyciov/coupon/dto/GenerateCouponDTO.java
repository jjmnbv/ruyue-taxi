package com.szyciov.coupon.dto;

import lombok.Data;

/**
 * 生成抵用券DTO
 *
 * @author LC
 * @date 2017/7/28
 */
public class GenerateCouponDTO {


    private String id;

    private Double money;

    private String name;

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
}
 