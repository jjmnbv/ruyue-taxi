package com.szyciov.carservice.util.scheduler;

public class SimpleTriggerBean extends TriggerBean {
	
	/**
	 * 重复触发的时间间隔(毫秒)
	 */
	private long repeatInterval;
	
	/**
	 * 执行次数
	 */
	private int triggerRepeatCount = -1;

	public long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public int getTriggerRepeatCount() {
		return triggerRepeatCount;
	}

	public void setTriggerRepeatCount(int triggerRepeatCount) {
		this.triggerRepeatCount = triggerRepeatCount;
	}
	
}
