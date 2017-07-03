package com.szyciov.touch.dto;

/**
 * 行程费用预估实体类
 * @author chen
 *
 */
public class CostEstimateDTO {
	
	/**
	 * 预估费用，单位元
	 */
	private Double amount;
	
	/**
	 * 预估里程，单位米
	 */
	private Double distance;
	
	/**
	 * 预估时长，单位秒
	 */
	private Integer duration;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		return "预估费用->" + amount + " ； 预估里程->" + distance + " ；预估时长->" + duration;
	}
	
}
