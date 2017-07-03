package com.szyciov.touch.enums;

import com.szyciov.driver.enums.OrderState;
import org.apache.commons.lang.StringUtils;

/**
 * 订单的真实状态和标准化接口的订单状态的枚举类
 * @author zhu
 *
 */
public enum OrderStatusEnums {

	/*
	 * 10 待接单 11 待人工派单 12 待出发 13 已出发 14 已抵达 15 接到乘客 16 服务中 17 行程结束 18 已取消  19 待确费
	 */
	
	/**
	 * 待接单
	 */
	WAITTAKE("10",OrderState.WAITTAKE.state, "待接单"),
	
	/**
	 * 待人工派单
	 */
	MANTICSEND("11",OrderState.MANTICSEND.state, "待人工派单"),
	
	/**
	 * 司机未出发
	 */
	WAITSTART("12",OrderState.WAITSTART.state, "待出发"),
	
	/**
	 * 司机已出发
	 */
	START("13",OrderState.START.state, "已出发"),
	
	/**
	 * 司机已抵达
	 */
	ARRIVAL("14 ",OrderState.ARRIVAL.state, "已抵达"),
	
	/**
	 * 已接到乘客
	 */
	PICKUP("15",OrderState.PICKUP.state, "已接到乘客"),
	
	/**
	 * 服务中
	 */
	INSERVICE("16",OrderState.INSERVICE.state, "服务中"),
	
	/**
	 * 行程结束
	 */
	SERVICEDONE("17",OrderState.SERVICEDONE.state, "服务结束"),
	
	/**
	 * 订单取消
	 */
	CANCEL("18",OrderState.CANCEL.state, "已取消"),
	
	/**
	 * 待确费
	 */
	WAITMONEY("19",OrderState.WAITMONEY.state, "待确费");
	
	/*********************************************************************************/

	/**
	 * 根据标准化接口订单状态获取枚举类
	 * @param state
	 * @return
	 */
	public static OrderStatusEnums getOrderStatus(String state) {
		if(StringUtils.isBlank(state)) {
			return null;
		}
		if(state.equals(OrderStatusEnums.WAITTAKE.state)) {
			return WAITTAKE;
		} else if(state.equals(OrderStatusEnums.MANTICSEND.state)) {
			return MANTICSEND;
		} else if(state.equals(OrderStatusEnums.WAITSTART.state)) {
			return WAITSTART;
		} else if(state.equals(OrderStatusEnums.START.state)) {
			return START;
		} else if(state.equals(OrderStatusEnums.ARRIVAL.state)) {
			return ARRIVAL;
		} else if(state.equals(OrderStatusEnums.PICKUP.state)) {
			return PICKUP;
		} else if(state.equals(OrderStatusEnums.INSERVICE.state)) {
			return INSERVICE;
		} else if(state.equals(OrderStatusEnums.SERVICEDONE.state)) {
			return SERVICEDONE;
		} else if(state.equals(OrderStatusEnums.CANCEL.state)) {
			return CANCEL;
		} else if(state.equals(OrderStatusEnums.WAITMONEY.state)) {
			return WAITMONEY;
		} else {
			return null;
		}
	}

	/**
	 * 根据真实订单状态获取枚举类
	 * @param state
	 * @return
	 */
	public static OrderStatusEnums getRealOrderstatus(String state) {
		if(StringUtils.isBlank(state)) {
			return null;
		}
		if(state.equals(OrderStatusEnums.WAITTAKE.realstate)) {
			return WAITTAKE;
		} else if(state.equals(OrderStatusEnums.MANTICSEND.realstate)) {
			return MANTICSEND;
		} else if(state.equals(OrderStatusEnums.WAITSTART.realstate)) {
			return WAITSTART;
		} else if(state.equals(OrderStatusEnums.START.realstate)) {
			return START;
		} else if(state.equals(OrderStatusEnums.ARRIVAL.realstate)) {
			return ARRIVAL;
		} else if(state.equals(OrderStatusEnums.PICKUP.realstate)) {
			return PICKUP;
		} else if(state.equals(OrderStatusEnums.INSERVICE.realstate)) {
			return INSERVICE;
		} else if(state.equals(OrderStatusEnums.SERVICEDONE.realstate)) {
			return SERVICEDONE;
		} else if(state.equals(OrderStatusEnums.CANCEL.realstate)) {
			return CANCEL;
		} else if(state.equals(OrderStatusEnums.WAITMONEY.realstate)) {
			return WAITMONEY;
		} else {
			return null;
		}
	}

	/**
	 * 标准化接口的订单状态
	 */
	public String state;
	
	/**
	 * 订单的真实状态
	 */
	public String realstate;
	
	/**
	 * 订单的状态的描述
	 */
	public String message;

	OrderStatusEnums(String state,String realstate,String message){
		this.state = state;
		this.realstate = realstate;
		this.message = message;
	}
}
