package com.szyciov.touch.dto;

import java.util.List;

/**
 * 账单明细实体
 * @author zhu
 *
 */
public class OrderBillDetailDTO {
	
	/**
	 * 客户名称
	 */
	private String channelName;
	
	/**
	 * 账单状态，1-生成账单、2-待核对、3-待机构核对、4-待机构付款、5-机构退回账单、6-机构已付款、7-已结算、8-已作废
	 */
	private String billState;
	
	/**
	 * 账单名称
	 */
	private String billName;
	
	/**
	 * 账单金额
	 */
	private Double billAmount;
	
	/**
	 * 账单生成时间
	 */
	private String billCreateTime;
	
	/**
	 * 账单明细
	 */
	private List<OrderListDTO> orderList;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public String getBillCreateTime() {
		return billCreateTime;
	}

	public void setBillCreateTime(String billCreateTime) {
		this.billCreateTime = billCreateTime;
	}

	public List<OrderListDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderListDTO> orderList) {
		this.orderList = orderList;
	}
}
