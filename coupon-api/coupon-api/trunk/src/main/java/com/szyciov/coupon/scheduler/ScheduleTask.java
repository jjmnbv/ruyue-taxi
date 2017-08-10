package com.szyciov.coupon.scheduler;

import com.szyciov.coupon.dto.ScheduleParamDTO;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 调度任务处理
 * @author LC
 * @date 2017/8/4
 */
@Component
public class ScheduleTask {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 新增调度任务
     * @param scheduleParam
     * @throws SchedulerException
     */
    public void addCroTrigger(ScheduleParamDTO scheduleParam) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(scheduleParam.getJobClass()).withIdentity(scheduleParam.getJobName(), scheduleParam.getJobGroup()).build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleParam.getCronSchedule());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(scheduleParam.getTriggerName(),scheduleParam.getJobGroup()) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
 