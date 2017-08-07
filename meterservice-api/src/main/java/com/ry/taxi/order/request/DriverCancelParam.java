package com.ry.taxi.order.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
* @ClassName: DriverCancelParam 
* @Package com.ry.taxi.order.request
* @Description: 司机取消通知 body参数
* @author LPF linpf@xunxintech.com
* @date 2017年7月18日 上午10:42:52 
* 
* @version 版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverCancelParam {
	
	
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String ordernum;
	
	/**
	 * 订单取消时间 yyyy-MM-dd hh:mm:ss
	 */
	@JsonProperty("CancelTime")
	private String canceltime;
	
	/**
	 * 1.等候超过30分钟乘客仍未到达
	 * 2.改接扬招单
	 */
	@JsonProperty("CancelType")
	private int canceltype;

}
