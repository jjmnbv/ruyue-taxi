package com.szyciov.coupon.scheduler.job;

import java.time.LocalDate;

import javax.annotation.Resource;

import com.szyciov.coupon.service.PubCouponService;
import com.szyciov.coupon.service.generate.expense.AutoExpenseGenerateCoupon;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.enums.coupon.CouponEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 抵用券超时任务
 * @author LC
 * @date 2017/8/4
 */
@Component
public class CouponTimOutJob implements Job {

    @Resource
    private PubCouponService couponService;

    private Logger logger = LoggerFactory.getLogger(CouponSendJob.class);




    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("------------------设置抵用券超时开始---------------------");
        PubCoupon pubCoupon = new PubCoupon();
        pubCoupon.setOuttimeend(LocalDate.now());
        pubCoupon.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
        couponService.timeOutCouponByTime(pubCoupon);
        logger.info("------------------设置抵用券超时结束---------------------");
    }
}
 