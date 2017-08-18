package com.szyciov.coupon.scheduler.job;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.ScheduleParamDTO;
import com.szyciov.coupon.scheduler.ScheduleTask;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 调度任务类
 * @author LC
 * @date 2017/8/4
 */
@Component
public class ScheduleJobs {

    private Logger logger = LoggerFactory.getLogger(ScheduleJobs.class);
    @Resource
    private ScheduleTask scheduleTask;

    public void initSchedules(){
        this.addCouponJob();
        this.addCouponTimeOutJob();
    }

    /**
     * 新增抵用券自动生成调度
     */
    private void addCouponJob(){
        ScheduleParamDTO dto = new ScheduleParamDTO();
        dto.setCronSchedule("0/20 * * * *  ?");
        dto.setJobClass(CouponSendJob.class);
        dto.setJobName("couponSendJob");
        dto.setJobGroup("group1");
        dto.setTriggerName("couponTrigger");
        dto.setTriggerGroup("group1");

        try {
            scheduleTask.addCroTrigger(dto);
            logger.info("-------添加消费类型优惠券派发调度结束-------");
        } catch (SchedulerException e) {
            logger.error("-------添加消费类型优惠券派发调度失败-------",e);
        }
    }

    /**
     * 抵用券超时调度
     */
    private void addCouponTimeOutJob(){
        ScheduleParamDTO dto = new ScheduleParamDTO();
        dto.setCronSchedule("0/20 * * * *  ?");
        dto.setJobClass(CouponTimOutJob.class);
        dto.setJobName("CouponTimOutJob");
        dto.setJobGroup("group1");
        dto.setTriggerName("couponOutTimeTrigger");
        dto.setTriggerGroup("group1");

        try {
            scheduleTask.addCroTrigger(dto);
            logger.info("-------添加抵用券超时调度结束-------");
        } catch (SchedulerException e) {
            logger.error("-------添加抵用券超时调度失败-------",e);
        }
    }
}

 