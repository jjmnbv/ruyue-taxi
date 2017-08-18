package com.szyciov.dto.coupon;

import java.time.LocalDate;

/**
 * 抵用券DTO
 * @author LC
 * @date 2017/8/8
 */
public class CouponInfoDTO {

    private String id;
    private String name;
    private Double money;
    private String servicetype;
    private LocalDate outimestart;
    private LocalDate outtimeend;
    private String usetype;
    private String cityStr;
    private String lecompanyid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public LocalDate getOutimestart() {
        return outimestart;
    }

    public void setOutimestart(LocalDate outimestart) {
        this.outimestart = outimestart;
    }

    public LocalDate getOuttimeend() {
        return outtimeend;
    }

    public void setOuttimeend(LocalDate outtimeend) {
        this.outtimeend = outtimeend;
    }

    public String getUsetype() {
        return usetype;
    }

    public void setUsetype(String usetype) {
        this.usetype = usetype;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getLecompanyid() {
        return lecompanyid;
    }

    public void setLecompanyid(String lecompanyid) {
        this.lecompanyid = lecompanyid;
    }
}
