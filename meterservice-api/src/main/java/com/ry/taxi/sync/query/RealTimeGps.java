/**
 * 
 */
package com.ry.taxi.sync.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @Title:RealTimeGps.java
 * @Package com.ry.taxi.sync.response
 * @Description 交投实时GPS信息
 * @author zhangdd
 * @date 2017年7月10日 下午4:06:20
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
public class RealTimeGps {
	/*
	 * 车牌号
	 */
	@JsonProperty("PlateNo")
	private String plateNo;
	
	/*
	 * 分公司名
	 */
	@JsonProperty("company")
	private String company;
	
	/*
	 * 总公司名
	 */
	@JsonProperty("MainCompany")
	private String mainCompany;
	
	/*
	 * Gps状态 0熄火 1空车 2重车
	 */
	@JsonProperty("GpsStatus")
	private Integer gpsStatus;
	
	/*
	 * Gps时间，格式yyyy-MM-dd HH:mm:ss 
	 */
	@JsonProperty("GpsTime")
	private String gpsTime;
	
	/*
	 * 经度
	 */
	@JsonProperty("Longitude")
	private Double longitude;
	
	/*
	 * 纬度
	 */
	@JsonProperty("Latitude")
	private Double latitude;
	
	/*
	 * 速度 公里/小时
	 */
	@JsonProperty("Speed")
	private Integer speed;
	
	/*
	 * 方向 0~360度
	 */
	@JsonProperty("Direction")
	private Integer direction;
	
	/*
	 * 高度 米
	 */
	@JsonProperty("height")
	private Integer Height;
	
	/*
	 * 有效性 0无效 1有效
	 */
	@JsonProperty("effective")
	private Integer Effective;
	
	/*
	 * 当班司机姓名
	 */
	@JsonProperty("drivername")
	private String drivername;
	
	/*
	 * 当班司机资格证号
	 */
	@JsonProperty("driverno")
	private String driverno;

}
