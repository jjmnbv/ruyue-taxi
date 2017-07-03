package com.szyciov.carservice.util.scheduler.job;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderApiService;

@DisallowConcurrentExecution
public class OrganBalanceNewsJob extends QuartzJobBean {

	private static final Logger LOGGER = Logger.getLogger(OrganBalanceNewsJob.class);
	
	private OrderApiService service;

	public void setOrderApiService(OrderApiService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderApiService) schedulerCtx.get("OrderApiService");
			
			Map<String, Object> result = service.createOrganBalanceNews();
			LOGGER.info("---------------------------------------------");
			LOGGER.info("机构可用额度不足消息推送数量: " + result.get("message"));
			LOGGER.info("---------------------------------------------");
		} catch (SchedulerException e) {
			LOGGER.error("机构可用额度不足消息推送任务执行失败", e);
		}
	}
	
}
