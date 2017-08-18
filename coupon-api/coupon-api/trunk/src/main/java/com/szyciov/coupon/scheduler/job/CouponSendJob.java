package com.szyciov.coupon.scheduler.job;

import javax.annotation.Resource;

import com.szyciov.coupon.service.generate.expense.AutoExpenseGenerateCoupon;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消费类型抵用券派发任务
 * @author LC
 * @date 2017/8/4
 */
@Component
public class CouponSendJob implements Job {

    private Logger logger = LoggerFactory.getLogger(CouponSendJob.class);

    @Resource
    private AutoExpenseGenerateCoupon autoExpenseGenerateCoupon;




    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("------------------生成消费类型抵用券开始---------------------");
        autoExpenseGenerateCoupon.autoGenerateCoupon();
        logger.info("------------------生成消费类型抵用券结束---------------------");
    }
}
 