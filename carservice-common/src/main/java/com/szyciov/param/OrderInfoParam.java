package com.szyciov.param;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.entity.AbstractOrder;

/**
 * @ClassName OrderInfoParam 
 * @author Efy Shu
 * @Description 派单参数
 * @date 2016年9月27日 下午7:01:16
 */
public class OrderInfoParam{
	/**
	 * 订单抽象类
	 */
	private AbstractOrder order;
	
	/**
	 * 订单信息
	 */
	private OrderInfoDetail orderinfo;
	
	/**  
	 * 获取订单抽象类  
	 * @return order 订单抽象类  
	 */
	public AbstractOrder getOrder() {
		return order;
	}
	
	/**  
	 * 设置订单抽象类  
	 * @param order 订单抽象类  
	 */
	public void setOrder(AbstractOrder order) {
		this.order = order;
	}

	/**  
	 * 获取订单信息  
	 * @return orderinfo 订单信息  
	 */
	public OrderInfoDetail getOrderinfo() {
		return orderinfo;
	}
	
	/**  
	 * 设置订单信息  
	 * @param orderinfo 订单信息  
	 */
	public void setOrderinfo(OrderInfoDetail orderinfo) {
		this.orderinfo = orderinfo;
	}
}
