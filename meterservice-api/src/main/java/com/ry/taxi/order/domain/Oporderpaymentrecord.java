package com.ry.taxi.order.domain;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oporderpaymentrecord {
	
	/**
	 * 支付订单号（主键）
	 */
	private String outtradeno;
	
	/**
	 * 所属订单
	 */
	private String orderno;
	
	/**
	 * 支付方式
	 */
	private String paymenttype;
	
	/**
	 * 流水号
	 */
	private String tradeno;
	
	/**
	 * 支付密钥
	 */
	private String privatekey;
	
	/**
	 * 操作结果
	 */
	private int operateresult;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;
	
	/**
	 * 数据状态
	 */
	private int status;
	

}
