package com.szyciov.util;

import java.util.HashMap;
import java.util.Map;

import com.szyciov.entity.AbstractOrder;

/**
 * web message 内容创建工厂
 * 消息内容信息
 * 含有的参数key/value
 * 必须有type web消息类型
 * 1-普通消息中心消息
 * 2-待人工派单消息
 * 3-客户派单成功消息
 * 需要弹出时需要制定needalertkey值为true即可
 * @author admin
 *
 */
public class ContentFactory {
	
	/**
	 * 普通消息
	 */
	public static final String NORMAL = "1";
	
	/**
	 * 人工派单消息
	 */
	public static final String MANTIC = "2";
	
	/**
	 * 订单被接消息
	 */
	public static final String ORDERTAKED = "3";
	
	/**
	 * 创建一个普通的web消息的内容信息
	 * @param content
	 * @return
	 */
	public static Map<String,Object> createNormContent(String content){
		Map<String,Object> contentinfo = new HashMap<String,Object>();
		contentinfo.put("type", NORMAL);
		contentinfo.put("content", content);
		contentinfo.put("needalertkey", false);
		return contentinfo;
	}
	
	/**
	 * 创建一个web端带人工派单的消息内容
	 * @param order
	 * @return
	 */
	public static Map<String,Object> createManticContent(AbstractOrder order){
		Map<String,Object> contentinfo = new HashMap<String,Object>();
		contentinfo.put("type", MANTIC);
		contentinfo.put("needalertkey", true);
		//todo设置订单的一些信息
		contentinfo.put("orderid", order.getOrderno());
		contentinfo.put("", "");
		return contentinfo;
	}
	
	/**
	 * 创建一个司机已接单的web消息内容
	 * @param order
	 * @return
	 */
	public static Map<String,Object> createOrderTakeredContent(AbstractOrder order){
		Map<String,Object> contentinfo = new HashMap<String,Object>();
		contentinfo.put("type", ORDERTAKED);
		contentinfo.put("needalertkey", true);
		//todo设置订单的一些信息
		contentinfo.put("orderid", order.getOrderno());
		contentinfo.put("", "");
		return contentinfo;
	}
}
