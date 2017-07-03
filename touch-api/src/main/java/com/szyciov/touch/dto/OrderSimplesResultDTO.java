package com.szyciov.touch.dto;

/**
 * 订单信息
 * Created by shikang on 2017/5/10.
 */
public class OrderSimplesResultDTO {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 车型名称
     */
    private String modelsName;

    /**
     * 下单人手机号
     */
    private String orderPersonPhone;

    /**
     * 乘车人手机号
     */
    private String passengerPhone;

    /**
     * 出发地名称
     */
    private String departureName;

    /**
     * 出发地地址
     */
    private String departureAddress;

    /**
     * 目的地名称
     */
    private String destinationName;

    /**
     * 目的地地址
     */
    private String destinationAddress;

    /**
     * 用车备注
     */
    private String useRmark;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 用车时间
     */
    private String useTime;

    /**
     * 取消时间
     */
    private String cancelTime;

    /**
     * 订单金额
     */
    private Double realAmount;

    /**
     * 优惠金额
     */
    private Double couponAmount;

    /**
     * 实付金额
     */
    private Double actuallyAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getModelsName() {
        return modelsName;
    }

    public void setModelsName(String modelsName) {
        this.modelsName = modelsName;
    }

    public String getOrderPersonPhone() {
        return orderPersonPhone;
    }

    public void setOrderPersonPhone(String orderPersonPhone) {
        this.orderPersonPhone = orderPersonPhone;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getDepartureName() {
        return departureName;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getUseRmark() {
        return useRmark;
    }

    public void setUseRmark(String useRmark) {
        this.useRmark = useRmark;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Double getActuallyAmount() {
        return actuallyAmount;
    }

    public void setActuallyAmount(Double actuallyAmount) {
        this.actuallyAmount = actuallyAmount;
    }
}
