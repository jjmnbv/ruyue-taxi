package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.14	网约车乘客基本信息(CKJB)
 * Created by 林志伟 on 2017/7/7.
 */

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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSex() {
        return passengerSex;
    }

    public void setPassengerSex(String passengerSex) {
        this.passengerSex = passengerSex;
    }

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "registerDate='" + registerDate + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengerSex='" + passengerSex + '\'' +
                "} " + super.toString();
    }
}
