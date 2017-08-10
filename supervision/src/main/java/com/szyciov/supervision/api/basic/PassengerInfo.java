package com.szyciov.supervision.api.basic;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.2.14	网约车乘客基本信息(CKJB)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class PassengerInfo extends BasicApi {
    public PassengerInfo(){
        super();
        setCommand(CommandEnum.PassengerInfo);
    }
//    注册时间
    private String registerDate;
//    乘客电话
    private String passengerPhone;
//    乘客称谓
    private String passengerName;
//    乘客性别
    private String passengerSex;



}
