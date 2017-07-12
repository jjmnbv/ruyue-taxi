/**
 * 
 */
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
	 * ���ƺ�
	 */
	@JsonProperty("PlateNo")
	private String plateNo;
	
	/*
	 * �ֹ�˾��
	 */
	@JsonProperty("company")
	private String company;
	
	/*
	 * �ܹ�˾��
	 */
	@JsonProperty("MainCompany")
	private String mainCompany;
	
	/*
	 * Gps״̬ 0Ϩ�� 1�ճ� 2�س�
	 */
	@JsonProperty("GpsStatus")
	private Integer gpsStatus;
	
	/*
	 * Gpsʱ�䣬��ʽyyyy-MM-dd HH:mm:ss 
	 */
	@JsonProperty("GpsTime")
	private String gpsTime;
	
	/*
	 * ����
	 */
	@JsonProperty("Longitude")
	private Double longitude;
	
	/*
	 * γ��
	 */
	@JsonProperty("Latitude")
	private Double latitude;
	
	/*
	 * �ٶ� ����/Сʱ
	 */
	@JsonProperty("Speed")
	private Integer speed;
	
	/*
	 * ���� 0~360��
	 */
	@JsonProperty("Direction")
	private Integer direction;
	
	/*
	 * �߶� ��
	 */
	@JsonProperty("height")
	private Integer Height;
	
	/*
	 * ��Ч�� 0��Ч 1��Ч
	 */
	@JsonProperty("effective")
	private Integer Effective;
	
	/*
	 * ����˾������
	 */
	@JsonProperty("drivername")
	private String drivername;
	
	/*
	 * ����˾���ʸ�֤��
	 */
	@JsonProperty("driverno")
	private String driverno;

}
