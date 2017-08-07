/**
 * 
 */
package com.ry.taxi.sync.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:GciVehicle.java
 * @Package com.ry.taxi.sync.domain
 * @Description 对应gci_vehicle表
 * @author zhangdd
 * @date 2017年7月19日 下午4:43:41
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GciVehicleTrace {
	
	/**
	 * 车牌号
	 */
	@JsonProperty("PlateNo")
	private String plateno;
	/**
	 * gps状态
	 */
	@JsonProperty("GpsStatus")
	private int gpsstatus;
	/**
	 * gps时间
	 */
	@JsonProperty("GpsTime")
	private String gpstime;
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
	 * 速度
	 */
	@JsonProperty("Speed")
	private int speed;
	/**
	 * 方向
	 */
	@JsonProperty("Direction")
	private int direction;
	/**
	 * 有效性 0无效 1有效
	 */
	@JsonProperty("Effective")
	private int effective;
	/**
	 * 司机姓名
	 */
	@JsonProperty("drivername")
	private String drivername;
	/**
	 * 司机资格证
	 */
	@JsonProperty("driverno")
	private String driverno;
	/**
	 * 分公司名
	 */
	@JsonProperty("Company")
	private String company;
	/**
	 * 总公司名
	 */
	@JsonProperty("MainCompany")
	private String maincompany;
	

}
