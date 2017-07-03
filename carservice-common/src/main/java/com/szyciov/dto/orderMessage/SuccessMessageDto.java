package com.szyciov.dto.orderMessage;

/**
 * 接单成功消息dto
 * Created by LC on 2016/11/26.
 */
public class SuccessMessageDto {

    /**
     * 标题 默认接单成功
     */
    private String title = "接单成功";

    /**
     *  接单司机 姓名(电话)
     */
    private String driverName;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 汽车类型
     */
    private String carType;

    /**
     * 汽车品牌
     */
    private String carBrand;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 系统类型
     */
    private String toSystem;

    /**
     * 租赁公司ID
     */
    private String companyId;

    /**
     * 机构公司ID
     */
    private String organId;

    /**
     * 结算方式
     */
    private String paymethod;

    /**
     * 上车城市编码
     */
    private String cityCode;

    /**
     * 订单类型：网约车/出租车
     */
    private String orderstyle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getToSystem() {
        return toSystem;
    }

    public void setToSystem(String toSystem) {
        this.toSystem = toSystem;
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

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getOrderstyle() {
        return orderstyle;
    }

    public void setOrderstyle(String orderstyle) {
        this.orderstyle = orderstyle;
    }
}
 