/**
 * 
 */
package com.ry.taxi.order.domain;

import java.util.Date;

import com.szyciov.entity.AbstractOrder;


/**
 * @Title:OpTaxiOrder.java
 * @Package com.ry.taxi.order.domain
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 下午2:22:47
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */

public class OpTaxiOrder extends AbstractOrder {	
	
	/**
	 * 调度费
	 */
	private Integer schedulefee;
	
	/**
	 * 打表来接里程数
	 */
	private Integer meterrange;
	
	/**
	 * 收款方式(0-线上,1-线下)
	 */
	private String paymentmethod;
	
	/**
	 * 收款时间
	 */
	private Date paymenttime;
	
	/**
	 * 结算时间
	 */
	private Date settlementtime;
	
	/**
	 * 订单排序字段(1-待接单,2-待出发,3-已出发,4-已抵达,5-服务中,6-待确费,7-未支付,8-已支付,9-未结算,10-结算中,11-已结算,12-未付结,13-已付结,14-已取消)
	 */
	private Integer ordersortcolumn;
	/**
	 * 开始服务地址城市
	 */
	private String startcity;
	/**
	 * 开始服务地址
	 */
	private String startaddress;

	/**
	 * 结束服务地址城市
	 */
	private String endcity;
	/**
	 * 结束服务地址
	 */
	private String endaddress;


    private String manualDriver; // 手动选择司机 0: 否    1:是

    /**
     * "0"表示机构订单，"1"表示个人订单
     */
    private String type;
    
    /**
     * 第三方订单号
     */
    private String thirdorderno;

	/**
	 * @return the schedulefee
	 */
	public Integer getSchedulefee() {
		return schedulefee;
	}

	/**
	 * @param schedulefee the schedulefee to set
	 */
	public void setSchedulefee(Integer schedulefee) {
		this.schedulefee = schedulefee;
	}

	/**
	 * @return the meterrange
	 */
	public Integer getMeterrange() {
		return meterrange;
	}

	/**
	 * @param meterrange the meterrange to set
	 */
	public void setMeterrange(Integer meterrange) {
		this.meterrange = meterrange;
	}

	/**
	 * @return the paymentmethod
	 */
	public String getPaymentmethod() {
		return paymentmethod;
	}

	/**
	 * @param paymentmethod the paymentmethod to set
	 */
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	/**
	 * @return the paymenttime
	 */
	public Date getPaymenttime() {
		return paymenttime;
	}

	/**
	 * @param paymenttime the paymenttime to set
	 */
	public void setPaymenttime(Date paymenttime) {
		this.paymenttime = paymenttime;
	}

	/**
	 * @return the settlementtime
	 */
	public Date getSettlementtime() {
		return settlementtime;
	}

	/**
	 * @param settlementtime the settlementtime to set
	 */
	public void setSettlementtime(Date settlementtime) {
		this.settlementtime = settlementtime;
	}

	/**
	 * @return the ordersortcolumn
	 */
	public Integer getOrdersortcolumn() {
		return ordersortcolumn;
	}

	/**
	 * @param ordersortcolumn the ordersortcolumn to set
	 */
	public void setOrdersortcolumn(Integer ordersortcolumn) {
		this.ordersortcolumn = ordersortcolumn;
	}

	/**
	 * @return the startcity
	 */
	public String getStartcity() {
		return startcity;
	}

	/**
	 * @param startcity the startcity to set
	 */
	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}

	/**
	 * @return the startaddress
	 */
	public String getStartaddress() {
		return startaddress;
	}

	/**
	 * @param startaddress the startaddress to set
	 */
	public void setStartaddress(String startaddress) {
		this.startaddress = startaddress;
	}

	/**
	 * @return the endcity
	 */
	public String getEndcity() {
		return endcity;
	}

	/**
	 * @param endcity the endcity to set
	 */
	public void setEndcity(String endcity) {
		this.endcity = endcity;
	}

	/**
	 * @return the endaddress
	 */
	public String getEndaddress() {
		return endaddress;
	}

	/**
	 * @param endaddress the endaddress to set
	 */
	public void setEndaddress(String endaddress) {
		this.endaddress = endaddress;
	}

	/**
	 * @return the manualDriver
	 */
	public String getManualDriver() {
		return manualDriver;
	}

	/**
	 * @param manualDriver the manualDriver to set
	 */
	public void setManualDriver(String manualDriver) {
		this.manualDriver = manualDriver;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the thirdorderno
	 */
	public String getThirdorderno() {
		return thirdorderno;
	}

	/**
	 * @param thirdorderno the thirdorderno to set
	 */
	public void setThirdorderno(String thirdorderno) {
		this.thirdorderno = thirdorderno;
	}
    
    
    
	
	
}
