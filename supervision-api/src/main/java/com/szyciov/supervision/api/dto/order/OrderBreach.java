package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.6	订单违约*(DDWY) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderBreach extends OrderApi {
    public OrderBreach(){
        super();
        setCommand(CommandEnum.OrderBreach);
    }
    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;


    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private  String driCertNo;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private  String vehicleNo;
    /**
     * 违约方	0:驾驶员
     1:乘客

     */
    private  String breakPart;
//    违约原因
    private  String breakReason;
    /**
     * 乘客电话
     */
    private  String psgTel;
    /**
     * 订单违约时间	YYYYMMDDHHMMSS
     */
    private  String breakTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDriCertNo() {
        return driCertNo;
    }

    public void setDriCertNo(String driCertNo) {
        this.driCertNo = driCertNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getBreakPart() {
        return breakPart;
    }

    public void setBreakPart(String breakPart) {
        this.breakPart = breakPart;
    }

    public String getBreakReason() {
        return breakReason;
    }

    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    public String getPsgTel() {
        return psgTel;
    }

    public void setPsgTel(String psgTel) {
        this.psgTel = psgTel;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    @Override
    public String toString() {
        return "OrderBreach{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", driCertNo='" + driCertNo + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", breakPart='" + breakPart + '\'' +
                ", breakReason='" + breakReason + '\'' +
                ", psgTel='" + psgTel + '\'' +
                ", breakTime='" + breakTime + '\'' +
                "} " + super.toString();
    }
}
