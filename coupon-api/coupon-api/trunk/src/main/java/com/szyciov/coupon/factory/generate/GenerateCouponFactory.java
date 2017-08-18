package com.szyciov.coupon.factory.generate;

import com.szyciov.coupon.service.generate.activity.ActivityGenerateCoupon;
import com.szyciov.coupon.service.generate.expense.ExpenseGenerateCoupon;
import com.szyciov.coupon.service.generate.manual.ManualGenerateCoupon;
import com.szyciov.coupon.service.generate.recharge.RechargeGenerateCoupon;
import com.szyciov.coupon.service.generate.register.RegisterGenerateCoupon;
import com.szyciov.coupon.util.SpringUtil;
import com.szyciov.enums.CouponRuleTypeEnum;

/**
 * 抵用券生成工厂，用于生成抵用券
 * @author LC
 * @date 2017/7/28
 */
public class GenerateCouponFactory {


    public static GenerateCoupon generateCoupon(Integer type){
        //注册、邀请类型
        if (CouponRuleTypeEnum.REGISTER.value.equals(type) ||
            CouponRuleTypeEnum.INVITE.value.equals(type)) {
            return GenerateCouponFactory.registerGenerateCoupon();
        }

        //充值类型
        if (CouponRuleTypeEnum.RECHARGE.value.equals(type)) {
            return GenerateCouponFactory.rechargeGenerateCoupon();
        }
        //消费类型
        if (CouponRuleTypeEnum.EXPENSE.value.equals(type)) {
            return GenerateCouponFactory.expenseGenerateCoupon();
        }

        //活动类型
        if (CouponRuleTypeEnum.ACTIVITY.value.equals(type)) {
            return GenerateCouponFactory.activityGenerateCoupon();
        }

        //人工
        if (CouponRuleTypeEnum.MANUAL.value.equals(type)) {
            return GenerateCouponFactory.manualGenerateCoupon();
        }
        return null;
    }

    public static RegisterGenerateCoupon registerGenerateCoupon(){
        return SpringUtil.getBean("registerGenerateCoupon",RegisterGenerateCoupon.class);
    }


    public static ManualGenerateCoupon manualGenerateCoupon(){
        return SpringUtil.getBean("manualGenerateCoupon",ManualGenerateCoupon.class);
    }


    public static RechargeGenerateCoupon rechargeGenerateCoupon(){
        return SpringUtil.getBean("rechargeGenerateCoupon",RechargeGenerateCoupon.class);
    }

    public static ExpenseGenerateCoupon expenseGenerateCoupon(){
        return SpringUtil.getBean("expenseGenerateCoupon",ExpenseGenerateCoupon.class);
    }

    public static ActivityGenerateCoupon activityGenerateCoupon(){
        return SpringUtil.getBean("activityGenerateCoupon",ActivityGenerateCoupon.class);
    }

}
 