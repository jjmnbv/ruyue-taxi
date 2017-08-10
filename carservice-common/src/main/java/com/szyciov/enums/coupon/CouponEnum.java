package com.szyciov.enums.coupon;

/**
 * 抵用券活动
 * Created by LC on 2017/7/25.
 */
public enum CouponEnum {

    /**服务类型**/
    SERVICE_TYPE_TAXI(1,"出租车"),
    SERVICE_TYPE_CAR(2,"网约车"),

    /******规则对象******/
    TARGET_ORGAN_CLIENT(1,"机构客户"),
    TARGET_ORGAN_USER(2,"机构用户"),
    TARGET_USER(3,"个人用户"),

    /**********使用城市区域************/
    USE_TYPE_DESIGNATE(1,"选定的发放区域"),
    USE_TYPE_FIXED(2,"开通业务城市有效"),

    /***********锁定状态************/
    LOCK_STATE_LOCKED(0,"锁定"),
    LOCK_STATE_UN_LOCKE(1,"未锁定"),

    /***********优惠券状态************/
    COUPON_STATUS_UN_USE(0,"未使用"),
    COUPON_STATUS_USED(1,"已使用"),
    COUPON_STATUS_EXPIRE(2,"已过期");


    public Integer code;
    public String msg;
    CouponEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
