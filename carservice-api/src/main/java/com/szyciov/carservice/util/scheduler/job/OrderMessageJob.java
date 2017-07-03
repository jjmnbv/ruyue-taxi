package com.szyciov.carservice.util.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class OrderMessageJob extends QuartzJobBean{
	
	private static final Logger ORDERMESSAGEJOB_LOG = Logger.getLogger(OrderMessageJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {/*
		Date curDate = new Date();
		Calendar calendar = Calendar.getInstance(); 

		for(String key : OrderMessageUtil.getOrgInstance().keySet()) {
			OrgOrder order =  OrderMessageUtil.getOrgInstance().get(key);
			
			Date orderUseTime = order.getUsetime();
			Date lastSendTime = order.getLastsendtime();
			
			ORDERMESSAGEJOB_LOG.info("lastSendTime：" + lastSendTime);
			long diffMin = (orderUseTime.getTime() - curDate.getTime()) / 1000 / 60;
			long min = diffMin % 5;
			if(order.getSendstatus() ==0 && diffMin > 0 && diffMin <= 30 && min <= 1 && min >= 0) {
				
				if(lastSendTime == null || lastSendTime.getTime() < curDate.getTime()) {
					BaseMessage message = new OrderMessage(order, OrderMessage.REMINDORDER);
					MessageUtil.sendMessage(message);
					
					calendar.setTime(curDate);
					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 4);
					order.setLastsendtime(calendar.getTime());
					OrderMessageUtil.getOrgInstance().put(key, order);
					
					ORDERMESSAGEJOB_LOG.info("---------------------------------------------");
					ORDERMESSAGEJOB_LOG.info("当前待提醒机构订单量:" + OrderMessageUtil.getOrgInstance().size());
					ORDERMESSAGEJOB_LOG.info("订单号:" + order.getOrderno() + "消息发送成功!");
					ORDERMESSAGEJOB_LOG.info("---------------------------------------------");
				}
			}
		}
		
		for(String key : OrderMessageUtil.getOpInstance().keySet()) {
			OpOrder order =  OrderMessageUtil.getOpInstance().get(key);
			
			Date orderUseTime = order.getUsetime();
			Date lastSendTime = order.getLastsendtime();
			
			int min = (int) ((orderUseTime.getTime() - curDate.getTime()) / 1000 / 60) % 5;
			if(orderUseTime.getTime() > curDate.getTime() && min <= 1) {
				
				if(lastSendTime == null || lastSendTime.getTime() > curDate.getTime()) {
					BaseMessage message = new OrderMessage(order, OrderMessage.REMINDORDER);
					MessageUtil.sendMessage(message);
					
					order.setLastsendtime(curDate);
					OrderMessageUtil.getOpInstance().put(key, order);
					
					ORDERMESSAGEJOB_LOG.info("---------------------------------------------");
					ORDERMESSAGEJOB_LOG.info("当前待提醒个人订单量:" + OrderMessageUtil.getOpInstance().size());
					ORDERMESSAGEJOB_LOG.info("订单号:" + order.getOrderno() + "消息发送成功!");
					ORDERMESSAGEJOB_LOG.info("---------------------------------------------");
				}
			}
		}
	*/}

}
