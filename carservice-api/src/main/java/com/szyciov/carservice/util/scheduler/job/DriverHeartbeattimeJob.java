package com.szyciov.carservice.util.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.DriverHeartbeattimeService;

@DisallowConcurrentExecution
public class DriverHeartbeattimeJob  extends QuartzJobBean{
	private static final Logger ORDERSTATISTICSJOB_LOG = Logger.getLogger(DriverHeartbeattimeJob.class);
    private DriverHeartbeattimeService service;
	
	public void setService(DriverHeartbeattimeService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try{
		SchedulerContext schedulerCtx = context.getScheduler().getContext();
		service = (DriverHeartbeattimeService) schedulerCtx.get("driverHeartbeattimeService");
		service.setDriverHeartbeattime();
		}catch (SchedulerException e) {
			ORDERSTATISTICSJOB_LOG.error("司机心跳超时调度任务异常", e);
		}
	}

}
