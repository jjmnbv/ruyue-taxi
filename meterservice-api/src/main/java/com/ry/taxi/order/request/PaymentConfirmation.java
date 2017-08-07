package com.ry.taxi.order.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmation {
	
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String ordernum;
	
	/**
	 * 交易流水号
	 */
	@JsonProperty("transid")
	private String TransId;
	
	/**
	 * 订单应付金额,TotalPayable= RewardFeePayable + KmFeePayable
	 */
	@JsonProperty("TotalPayable")
	private Double totalpayable;
	
	/**
	 * 应付里程费,单位：元
	 */
	@JsonProperty("KmFeePayable")
	private Double kmfeepayable;
	
	/**
	 * 订单实付金额,TotalFee= RewardFee + KmFee
	 */
	@JsonProperty("totalfee")
	private Double TotalFee;
	
	/**
	 * 实付里程费,单位：元
	 */
	@JsonProperty("kmfee")
	private Double KmFee;
	
	/**
	 * 交易时间
	 */
	@JsonProperty("TransTime")
	private String transtime;
	
	/**
	 * 交易方式,4现金 5羊城通
	 */
	@JsonProperty("TransType")
	private int transtype;
	
	/**
	 * 交易模式,3 里程费(cash)
	 */
	@JsonProperty("TransMode")
	private int transmode;

}
