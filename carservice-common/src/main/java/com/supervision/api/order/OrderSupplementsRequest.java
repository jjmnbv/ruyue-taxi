package com.supervision.api.order;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.3.4	订单补传请求*(DDBCQQ)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderSupplementsRequest extends OrderApi {
    public OrderSupplementsRequest(){
        super();
        setCommand(CommandEnum.OrderSupplementsRequest);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;


}
