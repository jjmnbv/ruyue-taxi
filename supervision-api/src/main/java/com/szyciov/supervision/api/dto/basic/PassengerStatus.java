package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.15	网约车乘客状态信息(CKZT)
 * Created by 林志伟 on 2017/7/7.
 */

public class PassengerStatus extends BasicApi {
    public PassengerStatus(){
        super();
        setCommand(CommandEnum.PassengerStatus);
    }
//    入黑名单日期
    private String inDate;
//  出黑名单日期
    private String outDate;
//  乘客电话
    private String passengerPhone;
//    原因
    private String cause;

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "PassengerStatus{" +
                "inDate='" + inDate + '\'' +
                ", outDate='" + outDate + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", cause='" + cause + '\'' +
                "} " + super.toString();
    }
}
