package com.szyciov.driver.param;

import java.util.Date;

import com.szyciov.param.OrderApiParam;

public class OrderLineParam extends OrderApiParam {
	/**
	 * 起始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 获取起始时间
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 设置起始时间
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 获取结束时间
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 设置结束时间
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
