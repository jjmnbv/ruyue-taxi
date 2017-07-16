package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.2.15	网约车乘客状态信息(CKZT)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
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




}
