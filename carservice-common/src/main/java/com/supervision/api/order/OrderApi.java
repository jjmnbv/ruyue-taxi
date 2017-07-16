package com.supervision.api.order;

import com.supervision.api.BaseApi;
import com.supervision.enums.InterfaceType;
import lombok.Data;


/**
 * 订单数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderApi extends BaseApi {
    public OrderApi(){
        setApiType(InterfaceType.ORDER);
    }

    //    行政区划编号
    private String address;


}
