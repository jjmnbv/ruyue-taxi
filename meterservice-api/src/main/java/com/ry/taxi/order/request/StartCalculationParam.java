/**
 * 
 */
package com.ry.taxi.order.request;

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
	private String certNum;
	/**
	 * 电话号码
	 */
	private String mobile;
	/**
	 * 车牌号码
	 */
	private String plateNum;
	/**
	 * 订单号
	 */
	private String orderNum;
	/**
	 * 交易ID
	 */
	private String transId;
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 纬度
	 */
	private double latitude;
	/**
	 * 传入地图(0-高德,1-百度,2-谷歌)
	 */
    private int mapType;
    /**
     * 累计里程
     */
	private double accumulatedDistance;
	/**
	 * 乘客上车时间
	 */
	private String passengerGetOnTime;
}
