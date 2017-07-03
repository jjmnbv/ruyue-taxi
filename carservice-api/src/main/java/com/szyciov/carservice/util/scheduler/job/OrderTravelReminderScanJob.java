package com.szyciov.carservice.util.scheduler.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.enums.MessageType;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.NewsState;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.BaseMessage;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.RedisUtil;

import redis.clients.jedis.Jedis;

/**
 * 待出发订单提醒任务
 * @author Fisher
 *
 */
@DisallowConcurrentExecution
public class OrderTravelReminderScanJob extends QuartzJobBean{
	

	private static final Logger logger = Logger.getLogger(OrderTravelReminderScanJob.class);
	private ObjectMapper objectMapper = new  ObjectMapper();

	private OrderApiService service;

	public void setOrderApiService(OrderApiService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Jedis jedis = RedisUtil.getJedis();
		try {
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance(); 
			
			SchedulerContext schedulerCtx = context.getScheduler().getContext();
			service = (OrderApiService) schedulerCtx.get("OrderApiService");
			
			List<OrgOrder> orgOrderList = service.getOrgBeDepartureOrder(null);
			List<OpOrder> opOrderList = service.getOpBeDepartureOrder(null);
            List<OpTaxiOrder> opTaxiOrderList = service.getOpTaxiBeDepartureOrder(null);
			
			for(OrgOrder order : orgOrderList) {
				String redisKey = RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_ORG.code + order.getOrderno();
				if(!jedis.exists(redisKey)) {
                    String orderStr = objectMapper.writeValueAsString(order);
					jedis.set(redisKey, orderStr);
					jedis.expire(redisKey, 1900);
				}
			}
			
			for(OpOrder order : opOrderList) {
				String redisKey = RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_OP.code + order.getOrderno();
				if(!jedis.exists(redisKey)) {
					String orderStr = objectMapper.writeValueAsString(order);
					jedis.set(redisKey, orderStr);
					jedis.expire(redisKey, 1900);
				}
			}

            for(OpTaxiOrder order : opTaxiOrderList) {
                String redisKey = RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_OPTAXI.code + order.getOrderno();
                if(!jedis.exists(redisKey)) {
					String orderStr = objectMapper.writeValueAsString(order);
					jedis.set(redisKey, orderStr);
					jedis.expire(redisKey, 1900);
                }
            }

			
			Set<String> travelReminderSet =
					jedis.keys(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_PREFIX.code + "*");
			
			for(String travelReminderOrderno : travelReminderSet) {
				String orderStr = jedis.get(travelReminderOrderno);
				AbstractOrder order = null;
				try {
                    if(travelReminderOrderno.indexOf(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_ORG.code) != -1) {
                        order = objectMapper.readValue(orderStr, OrgOrder.class);
                    } else if(travelReminderOrderno.indexOf(RedisKeyEnum.MESSAGE_TRAVEL_REMINDER_OP.code) != -1) {
						order = objectMapper.readValue(orderStr, OpOrder.class);
                    } else
						order = objectMapper.readValue(orderStr, OpTaxiOrder.class);
                } catch (Exception e) {
				    logger.error("订单解析异常：", e);
                }


				if(order == null) break;
				
				int orderMinute = ((int)(order.getUsetime().getTime() / 1000 / 60) - (int)(currentDate.getTime() / 1000 / 60)) % 5;
				
				if((order.getLastsendtime() == null
						|| (order.getLastsendtime() != null && order.getLastsendtime().getTime() < currentDate.getTime()))
						&& orderMinute == 0 && (order.getUsetime().getTime() + 1000 * 60) > currentDate.getTime()) {
					// 推送行程提醒
					BaseMessage message = new OrderMessage(order, OrderMessage.REMINDORDER);
					MessageUtil.sendMessage(message);
					// 创建行程提醒消息
					// createDriverNews(order, currentDate);
					
					calendar.setTime(currentDate);
					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 3);
					order.setLastsendtime(calendar.getTime());
					
					logger.warn("行程提醒发送成功：" + order.getOrderno());

					jedis.set(travelReminderOrderno, objectMapper.writeValueAsString(order));
					jedis.expire(travelReminderOrderno, 1900);
				}
			
			}
			
		} catch (Exception e) {
			logger.error("待出发订单提醒任务执行失败", e);
		} finally {
			if(jedis != null) RedisUtil.freedResource(jedis);
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