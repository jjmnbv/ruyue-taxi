package com.szyciov.coupon.factory.generate;

import com.szyciov.coupon.service.generate.activity.ActivityGenerateCoupon;
import com.szyciov.coupon.service.generate.expense.ExpenseGenerateCoupon;
import com.szyciov.coupon.service.generate.recharge.RechargeGenerateCoupon;
import com.szyciov.coupon.service.generate.register.RegisterGenerateCoupon;
import com.szyciov.coupon.util.SpringUtil;

/**
 * 抵用券生成工厂，用于生成抵用券
 * @author LC
 * @date 2017/7/28
 */
public class GenerateCouponFactory {


    public static RegisterGenerateCoupon registerGenerateCoupon(){
        return SpringUtil.getBean("registerGenerateCoupon",RegisterGenerateCoupon.class);
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
 