package com.szyciov.touch.dto;

/**
 * 营收统计实体
 * @author zhu
 *
 */
public class RevenuesAccordDTO {

	/**
	 * 城市ID
	 */
	private String cityId;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 用车业务，1-出租车，2-约车，3-接机，4-送机
	 */
	private String useType;
	
	/**
	 * 用车次数
	 */
	private Integer useTimes;
	
	/**
	 * 用车金额
	 */
	private Double amount;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Integer getUseTimes() {
		return useTimes;
	}

	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
