package com.szyciov.touch.dto;

/**
 * 出租车计费规则实体
 * @author chen
 *
 */
public class TaxiChargeRuleDTO {
	
	/**
	 * 起租价
	 */
	private Double onhirePrice;
	
	/**
	 * 起租里程
	 */
	private Double onhireMileage;
	
	/**
	 * 续租价
	 */
	private Double reletePrice;
	
	/**
	 * 空驶费（标准里程）
	 */
	private Double emptyFee;
	
	/**
	 * 空驶费率
	 */
	private Double emptyRate;
	
	/**
	 * 附加费
	 */
	private Double attachedFee;

	public Double getOnhirePrice() {
		return onhirePrice;
	}

	public void setOnhirePrice(Double onhirePrice) {
		this.onhirePrice = onhirePrice;
	}

	public Double getOnhireMileage() {
		return onhireMileage;
	}

	public void setOnhireMileage(Double onhireMileage) {
		this.onhireMileage = onhireMileage;
	}

	public Double getReletePrice() {
		return reletePrice;
	}

	public void setReletePrice(Double reletePrice) {
		this.reletePrice = reletePrice;
	}

	public Double getEmptyFee() {
		return emptyFee;
	}

	public void setEmptyFee(Double emptyFee) {
		this.emptyFee = emptyFee;
	}

	public Double getEmptyRate() {
		return emptyRate;
	}

	public void setEmptyRate(Double emptyRate) {
		this.emptyRate = emptyRate;
	}

	public Double getAttachedFee() {
		return attachedFee;
	}

	public void setAttachedFee(Double attachedFee) {
		this.attachedFee = attachedFee;
	}
	
	@Override
	public String toString() {
		return "起租价->" + onhirePrice + " ； 起租里程->" + onhireMileage + " ；续租价->" + reletePrice + "空驶费（标准里程）->" + emptyFee
				+ " ； 空驶费率->" + emptyRate + "附加费->" + attachedFee;
	}
}
