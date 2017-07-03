package com.szyciov.message;

public abstract class BaseMessage {
	
	/**
	 * 执行时间
	 */
	private Long runTime;
	
	/**
	 * 发送消息
	 * 需要子类实现
	 */
	public abstract void send();
	
	public Long getRunTime() {
		return runTime;
	}
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
}
