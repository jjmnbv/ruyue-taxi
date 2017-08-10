package com.szyciov.carservice.util.scheduler.job;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.szyciov.carservice.service.MileageApiService;
import com.szyciov.enums.CalcTypeEnum;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 订单里程计算方式结果短信下发
 * Created by shikang on 2017/7/31.
 */
@DisallowConcurrentExecution
public class OrderMileageCalctypeJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger(OrderMileageCalctypeJob.class);

    private MileageApiService mileageApiService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schedulerCtx = context.getScheduler().getContext();
            mileageApiService = (MileageApiService) schedulerCtx.get("mileageApiService");

            //计算开始时间和结束时间
            Date nowtime = new Date();
            Date starttime = resetTime(nowtime, 0, 0, 0);
            Date endtime = resetTime(nowtime, null, 0, 0);

            //查询时间段内里程统计结果
            long obd = 0, obdgps = 0, app = 0, lbs = 0, estimate = 0;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("starttime", starttime);
            param.put("endtime", endtime);
            List<Map<String, Object>> ret = mileageApiService.getOrderMileageCalctypeCount(param);
            if(null != ret && !ret.isEmpty()) {
                for (Map<String, Object> map : ret) {
                    int key = Integer.valueOf(map.get("calctype").toString());
                    long value = Long.valueOf(map.get("typecount").toString());
                    if(CalcTypeEnum.OBD.code == key) {
                        obd = value;
                    } else if(CalcTypeEnum.OBD_GPS.code == key) {
                        obdgps = value;
                    } else if(CalcTypeEnum.APP_GPS.code == key) {
                        app = value;
                    } else if(CalcTypeEnum.LBSYUN.code == key) {
                        lbs = value;
                    } else if(CalcTypeEnum.ESTIMATE.code == key) {
                        estimate = value;
                    }
                }
            }
            long count = obd + obdgps + app + lbs + estimate;
            double obdCount = obd + obdgps;
            double obdPercent = 0;
            if(count > 0) {
                obdPercent = StringUtil.formatNum(obdCount / count * 100, 1);
            }

            //发送短信
            String phones = mileageApiService.getOrderMileageCalctypePhone();
            if(StringUtils.isBlank(phones)) {
                LOGGER.warn("订单里程统计结果短信发送号码没有配置...");
                return;
            }
            LOGGER.info("订单里程统计结果短信下发开始...");
            List<String> phoneList = Arrays.asList(phones.split(","));
            String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.carservice.service.ordermileage.calctype",
                StringUtil.formatDate(starttime, StringUtil.TIME_WITH_MINUTE),
                StringUtil.formatDate(endtime, StringUtil.TIME_ONLY_HOUR_MINUTE),
                count, obd, obdgps, app, lbs, estimate, obdPercent);
            SMMessageUtil.send(phoneList, content);
            LOGGER.info("订单里程统计结果短信下发结束(" + content + ")");
        } catch (Exception e) {
            LOGGER.error("订单里程计算方式结果短信下发调度任务异常：", e);
        }
    }

    /**
     * 重置时间
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private Date resetTime(Date date, Integer hour, Integer minute, Integer second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(null != hour) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if(null != minute) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if(null != second) {
            calendar.set(Calendar.SECOND, second);
        }
        return calendar.getTime();
    }
}
