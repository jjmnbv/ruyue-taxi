package com.szyciov.carservice.util.scheduler.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.util.OrderMessageUtil;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.MessageType;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.NewsState;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.message.BaseMessage;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;

/**
 * 待出发订单提醒任务
 * @author Fisher
 *
 */
@DisallowConcurrentExecution
public class OrderScanJob extends QuartzJobBean{
	
	private static final Logger logger = Logger.getLogger(OrderScanJob.class); 
	
	private OrderApiService service;

	public void setOrderApiService(OrderApiService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance(); 
			
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderApiService) schedulerCtx.get("OrderApiService");
			
			List<OrgOrder> orgOrderList = service.getOrgBeDepartureOrder(null);
			List<OpOrder> opOrderList = service.getOpBeDepartureOrder(null);
			
			for(String key : OrderMessageUtil.getOrgInstance().keySet()) {
				OrgOrder order = OrderMessageUtil.getOrgInstance().get(key);
				if(order.getLastsendtime().getTime() < currentDate.getTime()) {
					OrderMessageUtil.getOrgInstance().remove(key);
				}
			}
			
			for(String key : OrderMessageUtil.getOpInstance().keySet()) {
				OpOrder order = OrderMessageUtil.getOpInstance().get(key);
				if(order.getLastsendtime().getTime() < currentDate.getTime()) {
					OrderMessageUtil.getOpInstance().remove(key);
				}
			}
			
			for(OrgOrder order : orgOrderList) {
				String orderno = order.getOrderno();
				if(!OrderMessageUtil.getOrgInstance().containsKey(orderno) 
						&& !OrderMessageUtil.getUnRemindOrderInstance().containsKey(orderno)) {
					BaseMessage message = new OrderMessage(order, OrderMessage.REMINDORDER);
					MessageUtil.sendMessage(message);
					
					createDriverNews(order, currentDate);

					calendar.setTime(currentDate);
					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 2);
					order.setLastsendtime(calendar.getTime());
					OrderMessageUtil.getOrgInstance().put(orderno, order);
					
					logger.info("------------------------------------------------------------");
					logger.info("当前提醒机构订单号：" + orderno);
					logger.info("------------------------------------------------------------");
				}
			}
			
			for(OpOrder order : opOrderList) {
				String orderno = order.getOrderno();
				if(!OrderMessageUtil.getOpInstance().containsKey(orderno)
						 && !OrderMessageUtil.getUnRemindOrderInstance().containsKey(orderno)) {
					BaseMessage message = new OrderMessage(order, OrderMessage.REMINDORDER);
					MessageUtil.sendMessage(message);
					
					createDriverNews(order, currentDate);
					
					calendar.setTime(currentDate);
					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
					order.setLastsendtime(currentDate);
					OrderMessageUtil.getOpInstance().put(orderno, order);
					
					logger.info("------------------------------------------------------------");
					logger.info("当前提醒个人订单号：" + orderno);
					logger.info("------------------------------------------------------------");
				}
			}
			
			
		} catch (Exception e) {
			logger.info("待出发订单提醒任务执行失败", e);
		}
	}
	
	
	/**
	 * 创建司机存库消息（订单提醒 - 系统消息）
	 * @param order
	 */
	private void createDriverNews(AbstractOrder order, Date currentDate) {
		try {
			//保存消息到数据库,供司机端消息中心访问
			OrderMessageType msgType = OrderMessageType.REMINDORDER;
			OrderMessageFactory messageFactory = new OrderMessageFactory(order, msgType);
			OrderInfoMessage orderinfo = messageFactory.createMessage();
			PubDriverNews pdn = new PubDriverNews();
			pdn.setId(GUIDGenerator.newGUID());
			pdn.setUserid(order.getDriverid());
			pdn.setNewsstate(NewsState.UNREAD.code);
			pdn.setType(MessageType.SYSTEM.type);
			pdn.setContent(orderinfo.toString());
			pdn.setCreatetime(currentDate);
			pdn.setUpdatetime(currentDate);
			pdn.setStatus(1);
			service.savePubDriverNews(pdn);
			// contentJson.put("times", (int) (order.getUsetime().getTime() - currentDate.getTime()) / 1000 / 60);
		} catch (Exception e) {
			logger.error("订单提醒司机存库消息创建失败：", e);
		}
	}

}