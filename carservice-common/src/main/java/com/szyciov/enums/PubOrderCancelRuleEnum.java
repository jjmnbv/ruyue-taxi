package com.szyciov.enums;

/**
 * Created by shikang on 2017/7/31.
 */
public enum PubOrderCancelRuleEnum {

    CARTYPE_NET(0, "网约车"),
    CARTYPE_TAXI(1, "出租车"),

    PLATFORMTYPE_OPERATE(0, "运管端"),
    PLATFORMTYPE_LEASE(1, "租赁端"),

    PRICEREASON_EXEMPTION(1, "免责取消"),
    PRICEREASON_NOTDRIVER_EXEMPTION(2, "没有司机接单，免责取消"),
    PRICEREASON_NOTRULE_EXEMPTION(3, "没有取消规则，免责取消"),
    PRICEREASON_DRIVERLATE_EXEMPTION(4, "司机迟到，免责取消"),
    PRICEREASON_OVERCANCEL_DUTY(5, "司机未抵达，司机未迟到，已过免责取消时限，有责取消"),
    PRICEREASON_DRIVERARRIVAL_DUTY(6, "司机已抵达，司机未迟到，乘客未迟到，有责取消"),
    PRICEREASON_PASSENGERLATE_DUTY(7, "司机已抵达，司机未迟到，乘客迟到，有责取消");

    public int code;

    public String msg;

    PubOrderCancelRuleEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
