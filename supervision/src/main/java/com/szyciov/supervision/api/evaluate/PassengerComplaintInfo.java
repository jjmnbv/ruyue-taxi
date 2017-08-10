package com.szyciov.supervision.api.evaluate;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.6.2	乘客投诉处理信息*（CKTSCL）
 * Created by 林志伟 on 2017/7/7.
 */
@Data
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


}
