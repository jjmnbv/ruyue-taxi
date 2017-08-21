package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;


/**
 * 订单数据
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderApi extends BaseApi {
    public OrderApi(){
        setApiType(InterfaceType.ORDER);
    }

    //    行政区划编号
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderApi{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
