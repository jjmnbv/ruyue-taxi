/**
 * 
 */
package com.ry.taxi.sync.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @Title:RealTimeGps.java
 * @Package com.ry.taxi.sync.response
 * @Description ��ͶʵʱGPS��Ϣ
 * @author zhangdd
 * @date 2017��7��10�� ����4:06:20
 * @version 
 *
 * @Copyrigth  ��Ȩ���� (C) 2017 ����Ѷ����Ϣ�Ƽ����޹�˾.
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
