package com.szyciov.enums;

import com.szyciov.util.OBDConstans;

import java.util.Date;

/**
 * 订单轨迹所属表
 * Created by shikang on 2017/5/24.
 */
public enum  OrderTraceTableEnum {

    /**
     * 订单GPS表
     */
    ORDER_GPS("1", "订单GPS表"),

    /**
     * 订单GPS历史表
     */
    HISTORY_GPS("2", "订单GPS历史表"),

    /**
     * 订单GPS和历史表结合
     */
    ORDER_HISTORY_GPS("3", "订单GPS和历史表结合");

    /**
     * 根据时间查询轨迹所属表
     * @param datetime
     * @return
     */
    public static OrderTraceTableEnum getOrderTraceTable(Date datetime) {
        long time = 0;
        if(null != datetime) {
            time = System.currentTimeMillis() - datetime.getTime();
        }
        if(time < OBDConstans.ORDER_GPS_START * 1000) {
            return ORDER_GPS;
        }
        if(time > OBDConstans.ORDER_GPS_END * 1000) {
            return HISTORY_GPS;
        }
        return ORDER_HISTORY_GPS;
    }

    public String code;

    public String msg;

    OrderTraceTableEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
