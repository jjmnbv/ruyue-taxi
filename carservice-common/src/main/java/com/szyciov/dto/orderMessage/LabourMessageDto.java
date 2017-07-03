package com.szyciov.dto.orderMessage;

/**
 * 人工派单消息dto
 * Created by LC on 2016/11/24.
 */
public class LabourMessageDto {

    /**
     * 标题
     */
    private String title = "待人工派单";   //默认值写这里

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 用车时间
     */
    private String timeStr;

    /**
     * 下单类型
     */
    private String carType;

    /**
     * 下单人
     */
    private String userName;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 超时时间(分)
     */
    private int outTime;

    /**
     * 租赁公司ID
     */
    private String companyId;

    /**
     * 机构公司ID
     */
    private String organId;

    /**
     * 系统类型
     */
    private String toSystem;

    /**
     * 结算方式
     */
    private String paymethod;

    /**
     * 上车城市编码
     */
    private String cityCode;

    /**
     * 订单类型：0网约车/1出租车
     */
    private String orderstyle;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOutTime() {
        return outTime;
    }

    public void setOutTime(int outTime) {
        this.outTime = outTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getToSystem() {
        return toSystem;
    }

    public void setToSystem(String toSystem) {
        this.toSystem = toSystem;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
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

    public String getOrderstyle() {
        return orderstyle;
    }

    public void setOrderstyle(String orderstyle) {
        this.orderstyle = orderstyle;
    }
}
 