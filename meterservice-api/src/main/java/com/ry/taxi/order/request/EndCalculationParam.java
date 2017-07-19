package com.ry.taxi.order.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndCalculationParam {
	
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String ordernum;
	
	/**
	 * 资格证号
	 */
	@JsonProperty("CertNum")
	private String certnum;
	
	/**
	 * 电话号码
	 */
	@JsonProperty("Mobile")
	private String mobile;
	
	/**
	 * 车牌号码
	 */
	@JsonProperty("PlateNum")
	private String platenum;
	
	/**
	 * 起表时位置,传入经度
	 */
	@JsonProperty("InputLon")
	private Double inputlon;
	
	/**
	 * 起表时位置,传入纬度
	 */
	@JsonProperty("InputLat")
	private Double inputlat;
	
	/**
	 * 传入地图（0-高德，1-百度，2-谷歌）
	 */
	@JsonProperty("MapType")
	private int maptype;
	
	/**
	 * 累计里程,单位：KM
	 */
	@JsonProperty("AccumulatedDistance")
	private Double accumulateddistance;
	
	/**
	 * 交易ID
	 */
	@JsonProperty("TransId")
	private String transid;
	
	/**
	 * 订单应付金额,单位：元
	 */
	@JsonProperty("AmountPayable")
	private Double amountpayable;
	
	/**
	 * 订单完成时间 yyyy-MM-dd hh:mm:ss
	 */
	@JsonProperty("OrderEndTime")
	private DateTime orderendtime;


}
