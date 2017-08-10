package com.szyciov.coupon.scheduler.job;

import javax.annotation.Resource;

import com.szyciov.coupon.service.OrderService;
import com.szyciov.coupon.service.generate.expense.AutoExpenseGenerateCoupon;
import com.szyciov.enums.coupon.CouponActivityEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 消费类型抵用券派发任务
 * @author LC
 * @date 2017/8/4
 */
@Component
public class CouponSendJob implements Job {

    @Resource
    private AutoExpenseGenerateCoupon autoExpenseGenerateCoupon;




    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        autoExpenseGenerateCoupon.autoGenerateCoupon();
    }
}
 