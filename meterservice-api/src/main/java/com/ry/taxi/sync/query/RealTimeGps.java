
package com.ry.taxi.sync.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * @Title:RealTaxiMonitor.java
 * @Package com.ry.taxi.sync.monitor
 * @Description
 * @author zhangdd
 * @date 2017年7月10日 下午4:17:37
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
	@JsonProperty("Height")
	private Integer height;
	
	/*
	 * 有效性 0无效 1有效
	 */
	@JsonProperty("Effective")
	private Integer effective;
	
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
	
	/*
	 * 同步服务时间
	 */
	@JsonProperty("ServiceTime")
	private String serviceTime;

}
