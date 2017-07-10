package com.supervision.api.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3.2.15	网约车乘客状态信息(CKZT)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerStatus extends BasicApi {

//    入黑名单日期
    private String inDate;
//  出黑名单日期
    private String outDate;
//  乘客电话
    private String passengerPhone;
//    原因
    private String cause;




}
