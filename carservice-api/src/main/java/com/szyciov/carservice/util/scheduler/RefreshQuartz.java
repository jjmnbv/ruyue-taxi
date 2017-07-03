package com.szyciov.carservice.util.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;
import java.util.Set;

public class RefreshQuartz {
	
	@Autowired
	private Scheduler scheduler;
	
	private Map<String, TriggerBean> triggers;
	
	public void run() {

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            if (null != jobKeys && !jobKeys.isEmpty()) {
                for (JobKey jobKey : jobKeys) {
                    TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                    scheduler.pauseTrigger(triggerKey);
                    scheduler.unscheduleJob(triggerKey);
                    scheduler.deleteJob(jobKey);
                }
            }
            // 添加新任务
            addQuartz();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

//		try {
//			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
//			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//			// 更新原任务
//			if (null != jobKeys && !jobKeys.isEmpty()) {
//				for (JobKey jobKey : jobKeys) {
//					List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
//					if (null != triggerList && !triggerList.isEmpty()) {
//						Trigger trigger = triggerList.get(0);
//						String triggerName = trigger.getKey().getName();
//						if (triggers.containsKey(triggerName)) { //当该任务已存在任务列表 时，更新当前任务的表达式，否则删除
//							TriggerBean triggerBean = triggers.remove(triggerName);
//							ScheduleBuilder<? extends Trigger> scheduleBuilder = null;
//							if (triggerBean instanceof CronTriggerBean) { // cron定时任务
//								CronTriggerBean cronTriggerbean = (CronTriggerBean) triggerBean;
//								scheduleBuilder = CronScheduleBuilder.cronSchedule(cronTriggerbean.getCronExpression());
//							} else if (triggerBean instanceof SimpleTriggerBean) { // simple定时任务
//								SimpleTriggerBean simpleTriggerBean = (SimpleTriggerBean) triggerBean;
//								scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//										.withIntervalInMilliseconds(simpleTriggerBean.getRepeatInterval())
//										.withRepeatCount(simpleTriggerBean.getTriggerRepeatCount());
//							}
//							trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, JobKey.DEFAULT_GROUP)
//									.withSchedule(scheduleBuilder).build();
//							scheduler.rescheduleJob(trigger.getKey(), trigger);
//						} else {
//							scheduler.deleteJob(jobKey);
//						}
//					}
//				}
//			}
//			// 添加新任务
//			addQuartz();
//		} catch (SchedulerException e) {
//			try {
//				GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
//				Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//				if (null != jobKeys && !jobKeys.isEmpty()) {
//					for (JobKey jobKey : jobKeys) {
//						TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
//						scheduler.pauseTrigger(triggerKey);
//				        scheduler.unscheduleJob(triggerKey);
//						scheduler.deleteJob(jobKey);
//					}
//				}
//				// 添加新任务
//				addQuartz();
//			} catch (SchedulerException e1) {
//				e1.printStackTrace();
//			}
//		}
	}

	/**
	 * 添加新任务
	 * @throws SchedulerException
	 */
	private void addQuartz() throws SchedulerException {
		for (Map.Entry<String, TriggerBean> entry : triggers.entrySet()) {
			TriggerBean triggerBean = entry.getValue();
			Class<? extends QuartzJobBean> jobClass = triggerBean.getJobClass(); //自定义的任务类，需要实现QuartzJobBean类
			String triggerName = entry.getKey(); //调度任务的名称，每个调度任务的名称保持唯一
			
			ScheduleBuilder<? extends Trigger> scheduleBuilder = null;
			if (triggerBean instanceof CronTriggerBean) {
				CronTriggerBean cronTriggerbean = (CronTriggerBean) triggerBean;
				String cronExpression = cronTriggerbean.getCronExpression(); // 调度任务表达式
				scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			} else if (triggerBean instanceof SimpleTriggerBean) {
				SimpleTriggerBean simpleTriggerBean = (SimpleTriggerBean) triggerBean;
				scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInMilliseconds(simpleTriggerBean.getRepeatInterval())
						.withRepeatCount(simpleTriggerBean.getTriggerRepeatCount());
			}
			JobDetail jobDetail = JobBuilder.newJob(jobClass)
					.withIdentity(entry.getKey(), JobKey.DEFAULT_GROUP).build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, JobKey.DEFAULT_GROUP)
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		}
	}

	public Map<String, TriggerBean> getTriggers() {
		return triggers;
	}

	public void setTriggers(Map<String, TriggerBean> triggers) {
		this.triggers = triggers;
	}
}
