package com.szyciov.touch.dto;

/**
 * 欠款明细
 * Created by shikang on 2017/5/10.
 */
public class DebtAccordDTO {

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 用车业务，1-出租车，2-约车，3-接机，4-送机
     */
    private String useType;

    /**
     * 欠款订单量
     */
    private Integer orderNum;

    /**
     * 全款金额
     */
    private Double amount;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
