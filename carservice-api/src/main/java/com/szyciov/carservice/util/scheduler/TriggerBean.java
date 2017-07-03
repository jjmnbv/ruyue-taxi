package com.szyciov.carservice.util.scheduler;

import org.springframework.scheduling.quartz.QuartzJobBean;

public class TriggerBean {
	
	/**
	 * 任务类
	 */
	private Class<? extends QuartzJobBean> jobClass;
	
	public Class<? extends QuartzJobBean> getJobClass() {
		return jobClass;
	}

	public void setJobClass(Class<? extends QuartzJobBean> jobClass) {
		this.jobClass = jobClass;
	}

}
