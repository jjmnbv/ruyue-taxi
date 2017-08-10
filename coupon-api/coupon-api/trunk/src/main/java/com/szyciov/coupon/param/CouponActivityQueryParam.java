package com.szyciov.coupon.param;

import java.time.LocalDate;

/**
 * 抵用券活动查询参数对象
 * @author LC
 * @date 2017/8/4
 */
public class CouponActivityQueryParam {

    //当前时间
    private LocalDate nowDt;

    //活动规则类别
    private Integer ruleType;

    public LocalDate getNowDt() {
        return nowDt;
    }

    public void setNowDt(LocalDate nowDt) {
        this.nowDt = nowDt;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }
}
 