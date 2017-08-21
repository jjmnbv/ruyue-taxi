package com.szyciov.supervision.api.dto.evaluate;

import com.supervision.enums.CommandEnum;

/**
 * 3.6.2	乘客投诉处理信息*（CKTSCL）
 * Created by 林志伟 on 2017/7/7.
 */

public class PassengerComplaintInfo extends EvaluateApi {
    public PassengerComplaintInfo(){
        super();
        setCommand(CommandEnum.PassengerComplaintInfo);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 是否投诉	1:是;
     0:否;

     */
    private String isComplaint;
    /**
     * 投诉时间	YYYYMMDDHHMMSS
     */
    private String complaintTime;
    /**
     * 投诉图片	以Base64进行编码，格式jpeg,照片大小在500k以内
     */
    private String photo;
    /**
     * 投诉处理状态	1:未处理;
     2:处理中;
     3:已办结.

     */
    private String dealState;
    /**
     * 投诉文字内容
     */
    private String detail;
    /**
     * 投诉处理结果
     */
    private String result;

    /**
     * 投诉回访结果
     */
    private String visitTime;
    /**
     * 投诉回访结果
     */
    private String visitResult;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIsComplaint() {
        return isComplaint;
    }

    public void setIsComplaint(String isComplaint) {
        this.isComplaint = isComplaint;
    }

    public String getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(String complaintTime) {
        this.complaintTime = complaintTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDealState() {
        return dealState;
    }

    public void setDealState(String dealState) {
        this.dealState = dealState;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }
}
