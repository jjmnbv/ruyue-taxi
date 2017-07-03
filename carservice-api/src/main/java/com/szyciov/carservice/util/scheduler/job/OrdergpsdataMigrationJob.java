package com.szyciov.carservice.util.scheduler.job;

import com.szyciov.carservice.service.MileageApiService;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 订单GPS数据迁移
 * Created by shikang on 2017/5/25.
 */
@DisallowConcurrentExecution
public class OrdergpsdataMigrationJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger(OrdergpsdataMigrationJob.class);

    private MileageApiService mileageApiService;
    public void setMileageApiService(MileageApiService mileageApiService) {
        this.mileageApiService = mileageApiService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            mileageApiService.migrationPubOrdergpsdata();
        } catch (Exception e) {
            LOGGER.error("订单GPS数据迁移调度任务异常：", e);
        }
    }
}
