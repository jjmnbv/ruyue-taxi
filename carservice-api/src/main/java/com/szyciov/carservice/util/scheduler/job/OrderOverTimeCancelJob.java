package com.szyciov.carservice.util.scheduler.job;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.param.OrderApiParam;

/**
 * 人工派单超时取消订单调度任务
 * @author Fisher
 *
 */
@DisallowConcurrentExecution
public class OrderOverTimeCancelJob extends QuartzJobBean{
	
	private static final Logger ORDERCANCELJOB_LOGGER = Logger.getLogger(OrderOverTimeCancelJob.class);
	
	private OrderApiService service;

	public void setOrderApiService(OrderApiService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderApiService) schedulerCtx.get("OrderApiService");
			
			OrderApiParam param = new OrderApiParam();
			Map<String, Object> resultMap = service.cancelOverTimeOrder(param);
			ORDERCANCELJOB_LOGGER.info("---------------------------------------------");
			ORDERCANCELJOB_LOGGER.info("超时取消订单数量: " + resultMap.get("message"));
			ORDERCANCELJOB_LOGGER.info("---------------------------------------------");
		} catch (Exception e) {
			ORDERCANCELJOB_LOGGER.error("超时取消订单调度任务异常：", e);
		}
	}

}
