package com.szyciov.touch.dto;

/**
 * 计价规则实体类
 * @author chen
 *
 */
public class ChargeRuleDTO {
	
	/**
	 * 规则类型:1-	出租车模式；2-网约车模式
	 */
	private String ruleType;
	
	/**
	 * 出租车计费规则实体
	 */
	private TaxiChargeRuleDTO taxiRule;
	
	/**
	 * 网约车计费规则实体
	 */
	private NetworkCarChargeRuleDTO netWorkRule;
	
	/**
	 * 车型名称
	 */
	private String modelsName;
	
	/**
	 * 车型图片
	 */
	private String modelsLogo;

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public TaxiChargeRuleDTO getTaxiRule() {
		return taxiRule;
	}

	public void setTaxiRule(TaxiChargeRuleDTO taxiRule) {
		this.taxiRule = taxiRule;
	}

	public NetworkCarChargeRuleDTO getNetWorkRule() {
		return netWorkRule;
	}

	public void setNetWorkRule(NetworkCarChargeRuleDTO netWorkRule) {
		this.netWorkRule = netWorkRule;
	}

	public String getModelsName() {
		return modelsName;
	}

	public void setModelsName(String modelsName) {
		this.modelsName = modelsName;
	}

	public String getModelsLogo() {
		return modelsLogo;
	}

	public void setModelsLogo(String modelsLogo) {
		this.modelsLogo = modelsLogo;
	}
	
	@Override
	public String toString() {
		return "规则类型->" + ruleType + " ； 出租车计费规则实体->" + taxiRule + " ；网约车计费规则实体->" + netWorkRule + "车型名称->" + modelsName
				+ " ； 车型图片->" + modelsLogo;
	}
}
