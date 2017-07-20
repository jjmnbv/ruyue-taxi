/**
 * 
 */
package com.ry.taxi.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:DistanceUploadParam.java
 * @Package com.ry.taxi.order.request
 * @Description 里程回传接口请求参数
 * @author zhangdd
 * @date 2017年7月20日 下午1:50:39
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceUploadParam {
	/**
	 * 订单号
	 */
	@JsonProperty("OrderNum")
	private String orderNum;
    /**
     * 交易流水
     */
	@JsonProperty("TransId")
	private String transId;
	/**
	 * 交易时间
	 */
	@JsonProperty("TransTime")
	private String transTime;
	/**
	 * 累计里程
	 */
	@JsonProperty("AccumulatedDistance")
	private double accumulatedDistance;
	

}
