package com.szyciov.touch.enums;

import com.szyciov.enums.OrderEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 业务类型枚举类
 * Created by shikang on 2017/5/10.
 */
public enum  OrderTypeEnum {

    /**
     * 出租车
     */
    ORDERTYPE_TAXI("1", OrderEnum.ORDERTYPE_TAXI.code, "出租车"),

    /**
     * 约车
     */
    ORDERTYPE_RESERVE("2", OrderEnum.ORDERTYPE_RESERVE.code, "约车"),

    /**
     * 接机
     */
    ORDERTYPE_PICKUP("3", OrderEnum.ORDERTYPE_PICKUP.code, "接机"),

    /**
     * 送机
     */
    ORDERTYPE_DROPOFF("4", OrderEnum.ORDERTYPE_DROPOFF.code, "送机");

    /**
     * 根据标准化接口订单业务类型获取枚举类
     * @param ordertype
     * @return
     */
    public static OrderTypeEnum getOrdertype(String ordertype) {
        if(StringUtils.isBlank(ordertype)) {
            return null;
        }
        if(ordertype.equals(OrderTypeEnum.ORDERTYPE_TAXI.ordertype)) {
            return ORDERTYPE_TAXI;
        } else if(ordertype.equals(OrderTypeEnum.ORDERTYPE_RESERVE.ordertype)) {
            return ORDERTYPE_RESERVE;
        } else if(ordertype.equals(OrderTypeEnum.ORDERTYPE_PICKUP.ordertype)) {
            return ORDERTYPE_PICKUP;
        } else if(ordertype.equals(OrderTypeEnum.ORDERTYPE_DROPOFF.ordertype)) {
            return ORDERTYPE_DROPOFF;
        } else {
            return null;
        }
    }

    /**
     * 标准化接口的订单业务类型
     */
    public String ordertype;

    /**
     * 订单的真实业务类型
     */
    public String realOrdertype;

    /**
     * 订单的业务类型的描述
     */
    public String message;

    OrderTypeEnum(String ordertype, String realOrdertype, String message) {
        this.ordertype = ordertype;
        this.realOrdertype = realOrdertype;
        this.message = message;
    }


}
