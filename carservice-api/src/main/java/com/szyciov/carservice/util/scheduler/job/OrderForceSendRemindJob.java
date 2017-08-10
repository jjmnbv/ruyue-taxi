/**
 * 
 */
package com.szyciov.carservice.util.scheduler.job;

import java.util.Date;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import com.szyciov.entity.AbstractOrder;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.BaseMessage;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName OrderForceSendRemindJob 
 * @author Efy Shu
 * @Description 强派订单行程提醒任务类
 * @date 2017年8月2日 上午11:34:25 
 */
@DisallowConcurrentExecution
public class OrderForceSendRemindJob extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(OrderForceSendRemindJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Set<String> keys = JedisUtil.getKeys(RedisKeyEnum.ORDER_FORCESEND_REMINDER.code+"*");
			for(String key : keys){
				try {
					String usetype = key.split("_")[4];
					String orderStr = JedisUtil.getString(key);
					JSONObject json = JSONObject.parseObject(orderStr);
					long usetime = json.getJSONObject("usetime").getLong("time");
					AbstractOrder order = null;
					if(OrderEnum.USETYPE_PERSONAL.code.equals(usetype)){
						order = StringUtil.parseJSONToBean(orderStr, OpOrder.class);
					}else{
						order = StringUtil.parseJSONToBean(orderStr, OrgOrder.class);
					}
					order.setUsetime(new Date(usetime));
					Date now = new Date();
					Date lastSendTime = order.getLastsendtime();
					if(lastSendTime.before(now) || lastSendTime.getTime() == now.getTime()){
						// 推送行程提醒
						BaseMessage message = new OrderMessage(order, OrderMessage.TASKORDER);
						MessageUtil.sendMessage(message);
						logger.info("强派订单提醒发送成功：" + order.getOrderno());
						// 创建行程提醒消息
						// createDriverNews(order, currentDate);
						order.setLastsendtime(StringUtil.addDate(lastSendTime, 20));
						JedisUtil.delKey(key);
						JedisUtil.setString(key, 100, StringUtil.parseBeanToJSON(order));
					}
				} catch (Exception e) {
					logger.error("推送强派订单提醒异常：" + e);
				}
			}
		} catch (Exception e) {
			logger.error("强派订单待确认任务异常：" + e);
		}
	}
}
