package com.szyciov.enums.coupon;

/**
 * 抵用券明细类型
 * Created by CJF on 2017/8/11.
 */
public enum CouponUsetypeEnum {
    /*****类型*****/
	RECHARGE(1,"充值返券"),
    SETTLEMENT(2,"账单结算扣款"),
    REGISTER(3,"注册返券"),
    ACTIVITY(4,"活动返券"),
    RESET(5,"违约清零"),
    ARTIFICIAL(6,"人工返券");

    public Integer code;
    public String msg;
    CouponUsetypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
