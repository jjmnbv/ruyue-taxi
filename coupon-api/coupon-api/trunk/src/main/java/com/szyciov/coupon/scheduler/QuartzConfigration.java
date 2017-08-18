package com.szyciov.coupon.scheduler;

import javax.annotation.Resource;

import com.szyciov.coupon.scheduler.job.ScheduleJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * quartz配置
 * @author LC
 * @date 2017/8/4
 */
@Configuration
public class QuartzConfigration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    public ScheduleJobs myScheduler;

    @Resource
    private MyJobFactory myJobFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        myScheduler.initSchedules();
    }

    /**
     * attention:
     * Details：定义quartz调度工厂
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        bean.setJobFactory(myJobFactory);
        return bean;
    }
}
 