package com.szyciov.carservice.util.scheduler;

public class CronTriggerBean extends TriggerBean {
	
	/**
	 * 任务执行的时间表达式
	 */
	private String cronExpression;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
}
