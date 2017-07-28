package com.szyciov.enums.coupon;

/**
 * 抵用券活动
 * Created by LC on 2017/7/25.
 */
public enum CouponActivityEnum {

    /**规则类型**/
    SERVICE_TYPE_TAXI(0,"注册"),
    SERVICE_TYPE_CAR(1,"充值"),

    /******规则对象******/
    TARGET_ORGAN_CLIENT(1,"机构客户"),
    TARGET_ORGAN_USER(2,"机构用户"),
    TARGET_USER(3,"个人用户"),

    /**********派发状态***************/
    STATE_START_NOT(1,"待派发"),
    STATE_START_ING(2,"派发中"),
    STATE_EXPIRED(3,"已过期"),
    STATE_CANCEL(4,"已作废"),

    /**********派发金额类型************/
    MONEY_TYPE_RANDOM(2,"随机"),
    MONEY_TYPE_FIXED(1,"固定"),


    /**********使用城市区域************/
    USE_TYPE_DESIGNATE(1,"选定的发放区域"),
    USE_TYPE_FIXED(2,"开通业务城市有效"),

    /***********有效期类型************/
    OUT_TIME_TYPE_FIXED(2,"固定期限"),
    OUT_TIME_TYPE_DESIGNATE(1,"选定的发放日期");


    public int code;
    public String msg;
    CouponActivityEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
