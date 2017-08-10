package com.szyciov.enums;

/**
 * 优惠券规则类型枚举
 * Created by CJF on 2017/7/25.
 */
public enum CouponRuleTypeEnum {
	/**
     * 规则类型
     */
    REGISTER(1, "注册"),
    RECHARGE(2, "充值"),
    EXPENSE(3, "消费"),
    ACTIVITY(4, "活动"),
    INVITE(5, "邀请"),
    
    /**
     * 规则对象
     */
    ORGAN_CONSUMER(1, "机构客户"),
    ORGAN_USER(2, "机构用户"),
    PERSONAL_USER(3, "个人用户"),
    
    /**
     * 消费类型
     */
    FRENQUENCY(1, "消费频次"),
    MONETARY(2, "消费金额"),
    
    /**
     * 消费频次类型  周期消费金额类型
     */
    FULL(1, "满"),
    FULL_LOW(2, "满低"),
    LOW(3, "低"),
    
    /**
     * 单次消费可用  周期消费可用
     */
    DISABLE(0, "不可用"),
    USABLE(1, "可用");


    public Integer value;
    public String name;

    CouponRuleTypeEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }
}
