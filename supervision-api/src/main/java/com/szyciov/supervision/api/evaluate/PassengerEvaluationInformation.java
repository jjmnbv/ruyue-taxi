package com.szyciov.supervision.api.evaluate;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.6.1	乘客评价信息(CKPJ) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
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

}
