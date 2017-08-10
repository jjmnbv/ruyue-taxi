package com.szyciov.enums;

/**
 * Created by shikang on 2017/8/3.
 */
public enum PubOrdercancelEnum {

    /***************************责任方***************************/
    DUTYPARTY_PASSENGER(1, "乘客"),

    DUTYPARTY_DRIVER(2, "司机"),

    DUTYPARTY_SERVICE(3, "客服"),

    DUTYPARTY_PLATFORM(4, "平台"),

    /***************************取消性质***************************/
    CANCELNATURE_DUTY(1, "有责"),

    CANCELNATURE_NODUTY(2, "免责"),

    /***************************取消原因***************************/
    CANCELREASON_PASSENGER_UNUSE(1, "不再需要用车"),

    CANCELREASON_PASSENGER_LATE(2, "乘客迟到违约"),

    CANCELREASON_DRIVER_LATE(3, "司机迟到违约"),

    CANCELREASON_DRIVER_UNSERVICE(4, "司机不愿接乘客"),

    CANCELREASON_BUSINESS_ERROR(5, "业务操作错误"),

    CANCELREASON_STOP_SERVICE(6, "暂停供车服务"),

    CANCELREASON_SYSTEM_FAIL(7, "系统派单失败"),

    CANCELREASON_PASSENGER_CHANGETRAVEL(8, "行程有变，不再需要用车"),

    CANCELREASON_DRIVER_CANNOTCONTACT(9, "司机没来接我，联系不上司机"),

    /***************************取消费用产生原因***************************/
    PRICEREASON_NODUTY(1, "免责取消"),

    PRICEREASON_NODRIVER_NODUTY(2, "没有司机接单,免责取消"),

    PRICEREASON_NORULE_NODUTY(3, "没有取消规则,免责取消"),

    PRICEREASON_DRIVERLATE_NODUTY(4, "司机迟到,免责取消"),

    PRICEREASON_OVERCANCELTIME_DUTY(5, "司机未抵达,司机未迟到,已过免责取消时限,有责取消"),

    PRICEREASON_DRIVERARRIVAL_DUTY(6, "司机已抵达,司机未迟到,乘客未迟到,有责取消"),

    PRICEREASON_PASSENGERLATE_DUTY(7, "司机已抵达,司机未迟到,乘客迟到,有责取消");


    public int code;

    public String msg;

    PubOrdercancelEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据取消费用产生原因获取取消性质
     * @param pricereason
     * @return
     */
    public static PubOrdercancelEnum getCancelnature(int pricereason) {
        if(pricereason == PRICEREASON_OVERCANCELTIME_DUTY.code || pricereason == PRICEREASON_DRIVERARRIVAL_DUTY.code
            || pricereason == PRICEREASON_PASSENGERLATE_DUTY.code) {
            return CANCELNATURE_DUTY;
        }
        return CANCELNATURE_NODUTY;
    }

}
