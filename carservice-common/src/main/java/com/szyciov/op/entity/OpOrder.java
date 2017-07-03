package com.szyciov.op.entity;

import com.szyciov.entity.AbstractOrder;

/**
  * @ClassName OpOrder
  * @author Efy
  * @Description 运营端订单表
  * @date 2016年9月20日 15:09:20
  */ 
public class OpOrder extends AbstractOrder{
	/**
	 * 计费规则(0-标准 1-个性化)
	 */
	private String rulestype;
	
	/**
	 * 实际车型
	 */
	private String factmodel;
	
	/**
	 * 计费车型
	 */
	private String pricemodel;

    private String manualDriver; // 手动选择司机 0: 否    1:是
	
	/**  
	 * 获取计费规则(0-标准1-个性化)  
	 * @return rulestype 计费规则(0-标准1-个性化)  
	 */
	public String getRulestype() {
		return rulestype;
	}
	

	/**  
	 * 设置计费规则(0-标准1-个性化)  
	 * @param rulestype 计费规则(0-标准1-个性化)  
	 */
	public void setRulestype(String rulestype) {
		this.rulestype = rulestype;
	}



	public String getFactmodel() {
		return factmodel;
	}



	public void setFactmodel(String factmodel) {
		this.factmodel = factmodel;
	}



	public String getPricemodel() {
		return pricemodel;
	}



	public void setPricemodel(String pricemodel) {
		this.pricemodel = pricemodel;
	}

    public String getManualDriver() {
        return manualDriver;
    }

    public void setManualDriver(String manualDriver) {
        this.manualDriver = manualDriver;
    }
}
