/**
 * 
 */
package com.ry.taxi.order.domain;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:OpTaxiOrder.java
 * @Package com.ry.taxi.order.domain
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 下午2:22:47
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpTaxiOrder {	
	
    /**
     * 订单号
     */
	private String orderno;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 调度费
	 */
	private Integer schedulefee;
	
	/**
	 * 打表来接里程数
	 */
	private Integer meterrange;
	
	/**
	 * 收款方式(0-线上,1-线下)
	 */
	private String paymentmethod;
	
	/**
	 * 收款时间
	 */
	private Date paymenttime;
	
	/**
	 * 结算时间
	 */
	private Date settlementtime;
	
	/**
	 * 订单排序字段(1-待接单,2-待出发,3-已出发,4-已抵达,5-服务中,6-待确费,7-未支付,8-已支付,9-未结算,10-结算中,11-已结算,12-未付结,13-已付结,14-已取消)
	 */
	private Integer ordersortcolumn;
	
	/**
	 * 开始服务地址城市
	 */
	private String startcity;
	/**
	 * 开始服务地址
	 */
	private String startaddress;
	/**
	 * 开始服务地址经度
	 */
	private String startlng;
	/**
	 * 开始服务地址纬度
	 */
	private String startllat;
	/**
	 * 结束服务地址城市
	 */
	private String endcity;
	/**
	 * 结束服务地址
	 */
	private String endaddress;
	/**
	 * 结束服务地址经度
	 */
	private String endlng;
	/**
	 * 结束服务地址纬度
	 */
	private String endllat;
	
	/**
	 * 第三方订单号
	 */
	private String thirdorderno;


}
