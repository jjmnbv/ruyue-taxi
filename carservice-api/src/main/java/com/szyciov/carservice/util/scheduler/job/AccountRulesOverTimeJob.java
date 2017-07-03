package com.szyciov.carservice.util.scheduler.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.JobService;

@DisallowConcurrentExecution
public class AccountRulesOverTimeJob extends QuartzJobBean {
	
	private static final Logger ACCOUNTRULESOVERTIMEJOB_LOGGER = Logger.getLogger(OrderMessageJob.class);
	
	private JobService service;

	public void setService(JobService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (JobService) schedulerCtx.get("jobService");
			
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, Object> result = service.cancelOverTimeRules(param);
			
			if(result.containsKey("message")) {
				ACCOUNTRULESOVERTIMEJOB_LOGGER.info("---------------------------------------------");
				ACCOUNTRULESOVERTIMEJOB_LOGGER.info("过期个性化计费规则：" + result.get("message") + "条!");
				ACCOUNTRULESOVERTIMEJOB_LOGGER.info("---------------------------------------------");
			}
		} catch (SchedulerException e) {
			ACCOUNTRULESOVERTIMEJOB_LOGGER.error("过期个性化计费规则调度任务执行失败：", e);
		}
		
	}
	
	
	

}
