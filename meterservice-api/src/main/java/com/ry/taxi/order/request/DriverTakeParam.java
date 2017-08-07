/**
 * 
 */
package com.ry.taxi.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:DriverTakeParam.java
 * @Package com.ry.taxi.order.request
 * @Description 司机应邀通知,请求body参数
 * @author zhangdd
 * @date 2017年7月17日 上午11:02:32
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverTakeParam {
	/*
	 * 司机资格证号
	 */
	@JsonProperty("CertNum")
	private String certNum;
	/*
	 * 电话号码
	 */
	@JsonProperty("Mobile")
	private String mobile;
	/*
	 * 车牌号
	 */
	@JsonProperty("PlateNum")
	private String plateNum;
	/*
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String orderNum;
	/*
	 * 抢单类型 1-举手抢单
	 */
	@JsonProperty("ResponseType")
	private String responseType;

}
