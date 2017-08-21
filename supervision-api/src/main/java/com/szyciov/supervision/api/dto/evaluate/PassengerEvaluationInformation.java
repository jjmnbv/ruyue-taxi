package com.szyciov.supervision.api.dto.evaluate;

import com.supervision.enums.CommandEnum;

/**
 * 3.6.1	乘客评价信息(CKPJ) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class PassengerEvaluationInformation extends EvaluateApi {
    public PassengerEvaluationInformation() {
        super();
        setCommand(CommandEnum.PassengerEvaluationInformation);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 评价时间	YYYYMMDDHHMMSS
     */
    private String evaluateTime;
    /**
     * 服务满意度	五分制
     */
    private String serviceScore;
    /**
     * 驾驶员满意度	五分制
     */
    private String driverScore;
    /**
     * 车辆满意度	五分制
     */
    private String vehicleScore;
    /**
     * 评价文字内容
     */
    private String detail;
    /**
     * 评价图片	以Base64进行编码，格式jpeg,照片大小在500k以内
     */
    private String photoDetail;
    /**
     * 补充评价内容
     */
    private String supDetail;
    /**
     * 补充评价时间	YYYYMMDDHHMMSS
     */
    private String supTime;
    /**
     * 行政区划编号	见GB/T 2260
     */
    private String address;
    /**
     * 整体满意度	分数，0-100
     */
    private String totalScore;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(String serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getDriverScore() {
        return driverScore;
    }

    public void setDriverScore(String driverScore) {
        this.driverScore = driverScore;
    }

    public String getVehicleScore() {
        return vehicleScore;
    }

    public void setVehicleScore(String vehicleScore) {
        this.vehicleScore = vehicleScore;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhotoDetail() {
        return photoDetail;
    }

    public void setPhotoDetail(String photoDetail) {
        this.photoDetail = photoDetail;
    }

    public String getSupDetail() {
        return supDetail;
    }

    public void setSupDetail(String supDetail) {
        this.supDetail = supDetail;
    }

    public String getSupTime() {
        return supTime;
    }

    public void setSupTime(String supTime) {
        this.supTime = supTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "PassengerEvaluationInformation{" +
                "orderId='" + orderId + '\'' +
                ", evaluateTime='" + evaluateTime + '\'' +
                ", serviceScore='" + serviceScore + '\'' +
                ", driverScore='" + driverScore + '\'' +
                ", vehicleScore='" + vehicleScore + '\'' +
                ", detail='" + detail + '\'' +
                ", photoDetail='" + photoDetail + '\'' +
                ", supDetail='" + supDetail + '\'' +
                ", supTime='" + supTime + '\'' +
                ", address='" + address + '\'' +
                ", totalScore='" + totalScore + '\'' +
                "} " + super.toString();
    }
}
