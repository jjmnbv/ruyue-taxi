package com.szyciov.enums;

/**
 * Created by shikang on 2017/7/27.
 */
public enum PubPremiumRuleEnum {

    CARTYPE_NET(0, "网约车"),
    CARTYPE_TAXI(1, "出租车"),

    RULETYPE_WEEK(0, "按星期"),
    RULETYPE_DATE(1, "按日期"),

    RULESTATUS_DISABLE(0, "禁用"),
    RULESTATUS_ENABLE(1, "启用"),

    ISPERPETUAL_UNPERPETUAL(0, "非永久"),
    ISPERPETUAL_PERPETUAL(1, "永久"),

    ISOPERATED_UNOPERATE(0, "未操作"),
    ISOPERATED_OPERATED(1, "已操作"),

    PLATFORMTYPE_OPERATE(0, "运管端"),
    PLATFORMTYPE_LEASE(1, "租赁端"),

    WEEKDAY_SUN(7, "星期天"),
    WEEKDAY_MON(1, "星期一"),
    WEEKDAY_TUES(2, "星期二"),
    WEEKDAY_WED(3, "星期三"),
    WEEKDAY_THUR(4, "星期四"),
    WEEKDAY_FRI(5, "星期五"),
    WEEKDAY_SAT(6, "星期六"),

    OPERATIONTYPE_ENABLE(0, "启用"),
    OPERATIONTYPE_DISABLE(1, "禁用"),
    OPERATIONTYPE_UPDATE(2, "修改");

    public int code;

    public String msg;

    PubPremiumRuleEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
