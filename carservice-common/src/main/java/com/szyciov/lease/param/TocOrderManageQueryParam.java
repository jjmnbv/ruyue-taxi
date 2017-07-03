package com.szyciov.lease.param;

import com.szyciov.param.QueryParam;

public class TocOrderManageQueryParam extends QueryParam {
	public TocOrderManageQueryParam() {
	}
	
	/**
	 * 订单号
	 */
	public String orderNo;
	
	/**
	 * 订单类型
	 */
	public String orderType;
	
	/**
	 * 订单状态
	 */
	public String orderStatus;
	
	/**
	 * 下单人
	 */
	public String orderPerson;
	
	/**
	 * 司机
	 */
	public String driver;
	
	/**
	 * 支付渠道
	 */
	public String payType;
	
	/**
	 * 取消方
	 */
	public String cancelParty;
	
	/**
	 * 订单来源
	 */
	public String orderSource;
	
	/**
	 * 交易流水号
	 */
	public String tradeNo;
	
	/**
	 * 用车开始时间
	 */
	public String minUseTime;
	
	/**
	 * 用车结束时间
	 */
	public String maxUseTime;
	
	/**
	 * 所属租赁公司
	 */
	public String companyId;

    /**
     * 服务车企
     */
    private String belongleasecompany;

    public String getBelongleasecompany() {
        return belongleasecompany;
    }

    public void setBelongleasecompany(String belongleasecompany) {
        this.belongleasecompany = belongleasecompany;
    }

    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderPerson() {
		return orderPerson;
	}

	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCancelParty() {
		return cancelParty;
	}

	public void setCancelParty(String cancelParty) {
		this.cancelParty = cancelParty;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getMinUseTime() {
		return minUseTime;
	}

	public void setMinUseTime(String minUseTime) {
		this.minUseTime = minUseTime;
	}

	public String getMaxUseTime() {
		return maxUseTime;
	}

	public void setMaxUseTime(String maxUseTime) {
		this.maxUseTime = maxUseTime;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
