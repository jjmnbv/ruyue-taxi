package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.4	订单补传请求*(DDBCQQ)
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderSupplementsRequest extends OrderApi {
    public OrderSupplementsRequest(){
        super();
        setCommand(CommandEnum.OrderSupplementsRequest);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

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

    @Override
    public String toString() {
        return "OrderSupplementsRequest{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                "} " + super.toString();
    }
}
