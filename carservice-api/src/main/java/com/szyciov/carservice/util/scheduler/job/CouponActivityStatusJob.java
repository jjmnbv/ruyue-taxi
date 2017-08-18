package com.szyciov.carservice.util.scheduler.job;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.szyciov.carservice.service.CouponActivityStatusService;
import com.szyciov.carservice.service.RabbitService;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.util.DateUtil;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CouponActivityStatusJob extends QuartzJobBean{

	private static final Logger Log = Logger.getLogger(CouponActivityStatusJob.class);
	
	private CouponActivityStatusService service;
	private RabbitService rabbitService;

	public RabbitService getRabbitService() {
		return rabbitService;
	}

	public void setRabbitService(RabbitService rabbitService) {
		this.rabbitService = rabbitService;
	}
	
	public CouponActivityStatusService getService() {
		return service;
	}

	public void setService(CouponActivityStatusService service) {
		this.service = service;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			 Log.info("=================抵扣券活动状态更新任务开始====================");
			 Date now=new Date();
			 SchedulerContext schedulerCtx = context.getScheduler().getContext();
			 service = (CouponActivityStatusService) schedulerCtx.get("CouponActivityStatusService");
			 String dateStr=DateUtil.format(now, "yyyy-MM-dd");
			 //获取所有发放开始时间在今天的抵扣券活动，进行状态更新  待发放==》发放中
			 List<PubCouponActivityDto> notStartActivitys=service.getAllNotStartCouponActivity(dateStr);
			 //获取所有发放结束时间小于今天的抵扣券活动，进行状态更新  发放中==》已过期
			 List<PubCouponActivityDto> expiredActivitys=service.getAllExpiredCouponActivity(dateStr);
			 Log.info("抵扣券活动开始数量:"+notStartActivitys.size());
			 Log.info("抵扣券活动过期数量:"+expiredActivitys.size());
			 if(notStartActivitys.size()>0){
				 service.updateAllNotStartCouponActivity(notStartActivitys);
				 for(PubCouponActivityDto activity:notStartActivitys){

					 sendAutoGenerateCoupon(activity,schedulerCtx);

					//添加抵扣券活动到redis缓存
				     JedisUtil.hSet(RedisKeyEnum.COUPON_ACTIVY.code+activity.getLecompanyid()+"_"+activity.getSendruletarget()+"_"+activity.getSendruletype(), activity.getId(), GsonUtil.toJson(activity));
				 }
		     }
		     if(expiredActivitys.size()>0){
		    	 service.updateAllExpiredCouponActivity(expiredActivitys);
				 for(PubCouponActivityDto activity:expiredActivitys){
					   //从redis缓存中删除过期活动  租赁公司ID_规则对象(数字)_派发类别(数字)
					 JedisUtil.hDel(RedisKeyEnum.COUPON_ACTIVY.code+activity.getLecompanyid()+"_"+activity.getSendruletarget()+"_"+activity.getSendruletype(), activity.getId());
				 }
		     }
		     Log.info("=================抵扣券活动状态更新任务结束====================");
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof SchedulerException)
			 Log.info("抵扣券状态更新任务调度异常",e);
			if(e instanceof SQLException)
			 Log.info("抵扣券状态更新执行异常",e);
			Log.info("抵扣券状态更新异常",e);
		} 
		 
		 
	}


	private void sendAutoGenerateCoupon(PubCouponActivityDto activity,SchedulerContext schedulerCtx){
		Log.info("================判断是否需自动发券====================");
		rabbitService = (RabbitService)schedulerCtx.get("rabbitService");
		//是否发送机构客户 活动
		boolean isSendOrgan = CouponRuleTypeEnum.ACTIVITY.value.equals(activity.getSendruletype())&&
			CouponRuleTypeEnum.ORGAN_CONSUMER.value.equals(activity.getSendruletarget());

		//是否人工发券
		boolean isSendManual = CouponRuleTypeEnum.MANUAL.value.equals(activity.getSendruletype());

		Log.info("=================是否机构客户活动："+isSendOrgan);
		Log.info("=================是否人工发券："+isSendManual);
		if( isSendOrgan || isSendManual){
			rabbitService.sendCouponRabbit(activity);
			Log.info("=================消息队列放入完毕===================");
		}
	}
}
