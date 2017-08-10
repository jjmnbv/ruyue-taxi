package com.szyciov.enums.coupon;

/**
 * 抵用券活动
 * Created by LC on 2017/7/25.
 */
public enum CouponActivityEnum {

    /**服务类型**/
    SERVICE_TYPE_TAXI(1,"出租车"),
    SERVICE_TYPE_CAR(2,"网约车"),

    /******规则对象******/
    TARGET_ORGAN_CLIENT(1,"机构客户"),
    TARGET_ORGAN_USER(2,"机构用户"),
    TARGET_USER(3,"个人用户"),
    
    /*****发放类型*****/
    SEND_RULE_REGISTER(1,"注册返券"),
    SEND_RULE_RECHARGE(2,"充值返券"),
    SEND_RULE_CONSUME(3,"消费返券"),
    SEND_RULE_ACTIVITY(4,"活动返券"),
    SEND_RULE_INVITATE(5,"邀请返券"),
    SEND_RULE_ARTIFICIAL(6,"人工返券"),

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


    public Integer code;
    public String msg;
    CouponActivityEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
