package com.szyciov.carservice.util.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderStatisticsService;

@DisallowConcurrentExecution
public class OrderStatisticsJob extends QuartzJobBean{
	private static final Logger ORDERSTATISTICSJOB_LOG = Logger.getLogger(OrderMessageJob.class);
	private OrderStatisticsService service;
	
	public void setService(OrderStatisticsService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderStatisticsService) schedulerCtx.get("orderStatisticsService");
//			OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
//			service.leIndexOrderStatistics(queryParam,"1");
//			service.leOrgOrderStatistics(queryParam,"1");
//			service.lePersonalOrderStatistics(queryParam,"1");
//			service.opIndexorderstatistics(queryParam, "1");
//			service.opOrderstatistics(queryParam, "1");
//			service.orgCompanystatistics(queryParam, "1");
//			service.orgDeptstatistics(queryParam, "1");
			service.getAllDatas();
		} catch (SchedulerException e) {
			ORDERSTATISTICSJOB_LOG.error("订单统计调度任务异常", e);
		}
	}

}
