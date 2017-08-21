package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.3	订单撤销(DDCX) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderCancel extends OrderApi {
    public OrderCancel(){
        super();
        setCommand(CommandEnum.OrderCancel);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

//    撤单时间
    private String cancelTime;
//      撤销发起方1：乘客2：驾驶员3：平台公司
    private String initiator;
    /**
     * 撤销类型代码
     * 1：乘客提前撤销
     2：驾驶员提前撤销
     3：平台公司撤销
     4：乘客违约撤销
     5：驾驶员违约撤销

     */
    private String cancelTypeCode;
    //撤销或违约原因
    private String cancelReason;

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

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getCancelTypeCode() {
        return cancelTypeCode;
    }

    public void setCancelTypeCode(String cancelTypeCode) {
        this.cancelTypeCode = cancelTypeCode;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String toString() {
        return "OrderCancel{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", cancelTime='" + cancelTime + '\'' +
                ", initiator='" + initiator + '\'' +
                ", cancelTypeCode='" + cancelTypeCode + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                "} " + super.toString();
    }
}
