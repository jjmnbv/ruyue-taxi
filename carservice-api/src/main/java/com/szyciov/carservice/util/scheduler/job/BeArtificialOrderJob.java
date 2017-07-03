package com.szyciov.carservice.util.scheduler.job;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.JobService;
import com.szyciov.carservice.util.OrderMessageUtil;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;

/**
 * 获取待人工派单订单信息
 * @author Fisher
 *
 */
@DisallowConcurrentExecution
public class BeArtificialOrderJob  extends QuartzJobBean{

	private static final Logger BEARTIFICIALORDERJOB_LOGGER = Logger.getLogger(OrderMessageJob.class);
	
	private JobService service;

	public void setService(JobService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (JobService) schedulerCtx.get("jobService");
			
			List<OrgOrder> orgOrderList = service.getBeArtificialOrgOrder();
			List<OpOrder> opOrderList = service.getBeArtificialOpOrder();
			Map<String, OrgOrder> orgOrderMap = OrderMessageUtil.getbeArtificialOrgOrderInstance();
			Map<String, OpOrder> opOrderMap = OrderMessageUtil.getbeArtificialOpOrderInstance();
			
			for(OrgOrder orgOrder: orgOrderList) {
				orgOrderMap.put(orgOrder.getOrderno(), orgOrder);
			}
			
			for(OpOrder opOrder : opOrderList) {
				opOrderMap.put(opOrder.getOrderno(), opOrder);
			}
			
			BEARTIFICIALORDERJOB_LOGGER.info("---------------------------------------------");
			BEARTIFICIALORDERJOB_LOGGER.info("待提醒待机构人工派单量" + orgOrderMap.size() + "条!");
			BEARTIFICIALORDERJOB_LOGGER.info("待提醒待运管人工派单量：" + opOrderMap.size() + "条!");
			BEARTIFICIALORDERJOB_LOGGER.info("---------------------------------------------");
		} catch (SchedulerException e) {
			BEARTIFICIALORDERJOB_LOGGER.error("待提醒待人工派单调度任务执行失败：", e);
		}
		
	}
	
	
	


}
