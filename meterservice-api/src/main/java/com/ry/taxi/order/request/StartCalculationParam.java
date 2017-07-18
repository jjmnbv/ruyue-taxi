/**
 * 
 */
package com.ry.taxi.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:StartCalculationParam.java
 * @Package com.ry.taxi.order.request
 * @Description
 * @author zhangdd
 * @date 2017年7月18日 下午4:02:24
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartCalculationParam {	
	/**
	 * 司机资格证号
	 */
	@JsonProperty("CertNum")
	private String certNum;
	/**
	 * 电话号码
	 */
	@JsonProperty("Mobile")
	private String mobile;
	/**
	 * 车牌号码
	 */
	@JsonProperty("PlateNum")
	private String plateNum;
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String orderNum;
	/**
	 * 交易ID
	 */
	@JsonProperty("TransId")
	private String transId;
	/**
	 * 经度
	 */
	@JsonProperty("Longitude")
	private double longitude;
	/**
	 * 纬度
	 */
	@JsonProperty("Latitude")
	private double latitude;
	/**
	 * 传入地图(0-高德,1-百度,2-谷歌)
	 */
	@JsonProperty("MapType")
    private int mapType;
    /**
     * 累计里程
     */
	@JsonProperty("AccumulatedDistance")
	private double accumulatedDistance;
	/**
	 * 乘客上车时间
	 */
	@JsonProperty("PassengerGetOnTime")
	private String passengerGetOnTime;
}
