package com.xxkj.passenger.wechat.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 讯心支付平台微信公众号支付请求，见《讯心第三方支付平台接口文档v1.0.docx》.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPlatformWechatJsapiRequest {
	
	/**
	 * 订单号.
	 */
	private String OrderId;
	
	/**
	 * 业务系统编号.
	 */
	private String BusinessNo;
	
	/**
	 * 应付金额(单位：分).
	 */
	private int OrderAmount;
	
	/**
	 * 实付金额(单位：分).
	 */
	private int OrderAmountRealPay;
	
	/**
	 * 商品信息，前端显示.
	 */
	private String ProductName;
	
	/**
	 * 返回URL.
	 */
	private String RespURL;
	
	/**
	 * 回调URL，支付平台与业务系统的回调.
	 */
	private String CallBackURL;
	
	/**
	 * 签名，页面请求无签名验证.
	 */
	private String Signature;

}
