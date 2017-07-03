/**
 * 
 */
package com.szyciov.entity;

import com.szyciov.enums.OrderEnum;

/**
 * @ClassName OrderSource4OrderNO 
 * @author Efy Shu
 * @Description 订单号里的订单来源枚举
 * @date 2016年11月26日 下午7:25:33 
 */
public enum OrderSource4OrderNO {
	/**
	 * 乘客端 | 因公
	 */
	PASSENGER_TOB_PUB("BC","乘客端 | 因公"),
	/**
	 * 乘客端 | 因私
	 */
	PASSENGER_TOB_PRI("CJ","乘客端 | 因私"),
	/**
	 * 乘客端 | 个人
	 */
	PASSENGER_TOC("CG","乘客端 | 个人"),
	/**
	 * 机构端
	 */
	ORGAN("BJ","机构端"),
	/**
	 * 租赁端 | 因公
	 */
	LEASE_PUB("BZ","租赁端 | 因公"),
	/**
	 * 租赁端 | 因私
	 */
	LEASE_PRI("CZ","租赁端 | 因私"),
	/**
	 * 运管端
	 */
	OPERATOR("CY","运管端"),
	/**
	 * 合作方
	 */
	PARTNER("CP","合作方");

	/**
	 * 获取订单来源
	 * @param usetype 用车类型(因公、因私、个人)
	 * @param ordersource 订单来源(安卓乘客端、IOS乘客端、微信乘客端、租赁端、机构端、运管端)
	 * @return
	 */
	public static OrderSource4OrderNO getOrderSource4OrderNO(String usetype, String ordersource){
		if(OrderEnum.ORDERSOURCE_PARTNER.code.equals(ordersource)) { //合作方
			return PARTNER;
		}
		if (OrderEnum.USETYPE_PUBLIC.code.equals(usetype)) { // 因公
			if (OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_IOS.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code.equals(ordersource)) { // 乘客端
				return PASSENGER_TOB_PUB;
			} else if (OrderEnum.ORDERSOURCE_LEASE.code.equals(ordersource)) { // 租赁端
				return LEASE_PUB;
			} else if (OrderEnum.ORDERSOURCE_ORGAN.code.equals(ordersource)) { // 机构端
				return ORGAN;
			}
		} else if (OrderEnum.USETYPE_PRIVATE.code.equals(usetype)) { // 因私
			if (OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_IOS.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code.equals(ordersource)) { // 乘客端
				return PASSENGER_TOB_PRI;
			} else if (OrderEnum.ORDERSOURCE_LEASE.code.equals(ordersource)) { // 租赁端
				return LEASE_PRI;
			}
		} else if (OrderEnum.USETYPE_PERSONAL.code.equals(usetype)) { // 个人
			if (OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_IOS.code.equals(ordersource)
					|| OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code.equals(ordersource)) { // 乘客端
				return PASSENGER_TOC;
			} else if (OrderEnum.ORDERSOURCE_OPERATE.code.equals(ordersource)) { // 运管端
				return OPERATOR;
			}
		}
		return LEASE_PUB;
	}
	
	public String code;
	public String msg;
	OrderSource4OrderNO(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
