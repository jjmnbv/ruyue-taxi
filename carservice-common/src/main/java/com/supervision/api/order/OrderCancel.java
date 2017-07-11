package com.supervision.api.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3.3.3	订单撤销(DDCX) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancel extends OrderApi {


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




}
