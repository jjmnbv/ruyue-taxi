package com.szyciov.carservice.util.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.szyciov.carservice.service.GiveIntegrationService;


@DisallowConcurrentExecution
public class GiveIntegrationJob extends QuartzJobBean{
	private static final Logger ORDERSTATISTICSJOB_LOG = Logger.getLogger(GiveIntegrationJob.class);
    private GiveIntegrationService service;
	
	public void setService(GiveIntegrationService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try{
		SchedulerContext schedulerCtx = context.getScheduler().getContext();
		service = (GiveIntegrationService) schedulerCtx.get("giveIntegrationService");
		service.giveIntegration();
		}catch (SchedulerException e) {
			ORDERSTATISTICSJOB_LOG.error("订单返还金额活动是否终止调度任务异常", e);
		}
	
	}
	
}
