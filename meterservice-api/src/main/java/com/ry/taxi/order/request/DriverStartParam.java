package com.ry.taxi.order.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
* @ClassName: DriverStartParam 
* @Package com.ry.taxi.order.request
* @Description: 司机执行订单通知（预约单）body参数
* @author LPF linpf@xunxintech.com
* @date 2017年7月18日 上午10:11:28 
* 
* @version 版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverStartParam {
	
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
	 * 车牌号
	 */
	@JsonProperty("PlateNum")
	private String platenum;
	
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String ordernum;
	
	/**
	 * 发前往订单候车点时间 yyyy-MM-dd hh:mm:ss
	 */
	@JsonProperty("DepartureTime")
	private String departuretime;
	
	/**
	 * 经度
	 */
	@JsonProperty("Longitude")
	private double longitude;
	/**
	 * 纬度
	 */
	@JsonProperty("Latitude")
	private double latitude ;
	/**
	 * 地图类型（0-高德，1-百度，2-谷歌）
	 */
	@JsonProperty("MapType")
	private int mapType;

}
