package com.szyciov.touch.enums;

import com.szyciov.driver.enums.PayState;
import org.apache.commons.lang.StringUtils;

/**
 * 标准化接口的订单支付状态和真实订单支付状态的转化枚举
 * @author zhu
 *
 */
public enum OrderPayStatusEnums {
	
	/**
	 * 未结算
	 */
	MENTED("20",PayState.MENTED.state,"未结算"),

	/**
	 * 结算中
	 */
	STATEMENTING("21",PayState.STATEMENTING.state,"结算中"),
	
	/**
	 * 已结算
	 */
	STATEMENTED("22",PayState.STATEMENTED.state,"已结算"),

	/**
	 * 未支付
	 */
	NOTPAY("23",PayState.NOTPAY.state,"未支付"),

	/**
	 * 已支付
	 */
	PAYED("24",PayState.PAYED.state,"已支付");
	
	/*********************************************************************************/

	/**
	 * 根据标准化接口订单支付状态获取枚举类
	 * @param state
	 * @return
	 */
	public  static OrderPayStatusEnums getOrderPayStatus(String state) {
		if(StringUtils.isBlank(state)) {
			return null;
		}
		if(state.equals(OrderPayStatusEnums.MENTED.state)) {
			return MENTED;
		} else if(state.equals(OrderPayStatusEnums.STATEMENTING.state)) {
			return STATEMENTING;
		} else if(state.equals(OrderPayStatusEnums.STATEMENTED.state)) {
			return STATEMENTED;
		} else if(state.equals(OrderPayStatusEnums.NOTPAY.state)) {
			return NOTPAY;
		} else if(state.equals(OrderPayStatusEnums.PAYED.state)) {
			return PAYED;
		} else {
			return null;
		}
	}

	public static OrderPayStatusEnums getRealOrderPayStatus(String realstate){
		if(StringUtils.isBlank(realstate)) {
			return null;
		}
		if(realstate.equals(OrderPayStatusEnums.MENTED.realstate)) {
			return MENTED;
		} else if(realstate.equals(OrderPayStatusEnums.STATEMENTING.realstate)) {
			return STATEMENTING;
		} else if(realstate.equals(OrderPayStatusEnums.STATEMENTED.realstate)) {
			return STATEMENTED;
		} else if(realstate.equals(OrderPayStatusEnums.NOTPAY.realstate)) {
			return NOTPAY;
		} else if(realstate.equals(OrderPayStatusEnums.PAYED.realstate)) {
			return PAYED;
		} else {
			return null;
		}
	}

	/**
	 * 标准化接口的订单支付状态
	 */
	public String state;
	
	/**
	 * 实际接口的订单支付状态
	 */
	public String realstate;
	
	/**
	 * 接口支付状态的描述
	 */
	public String message;

	OrderPayStatusEnums(String state,String realstate,String message){
		this.state = state;
		this.realstate = realstate;
		this.message = message;
	}
}
