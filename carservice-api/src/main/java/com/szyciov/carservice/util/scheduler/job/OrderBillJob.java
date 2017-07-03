package com.szyciov.carservice.util.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderBillService;

@DisallowConcurrentExecution
public class OrderBillJob  extends QuartzJobBean{

	private static final Logger ORDERBILLJOB_LOG = Logger.getLogger(OrderMessageJob.class);
	
	private OrderBillService service;
	
	public void setService(OrderBillService service) {
		this.service = service;
	}

 
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderBillService) schedulerCtx.get("orderBillService");
			service.batchCreateOrganBill();
		} catch (SchedulerException e) {
			ORDERBILLJOB_LOG.error("账单调度任务异常", e);
		}
	}

}
