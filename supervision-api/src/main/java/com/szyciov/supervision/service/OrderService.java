package com.szyciov.supervision.service;

import com.szyciov.supervision.api.dto.order.*;

import java.util.Map;

/**
 * 营运订单数据服务
 * Created by lzw on 2017/8/18.
 */
public interface OrderService extends CommonApiService{

    /**
     * 上班
     * @param map
     * @return
     */
    DriverOnWork driverOnWork(Map<String,String> map);

    /**
     * 下班
     * @param map
     * @return
     */
    DriverOffWork driverOffWork(Map<String, String> map);

    /**
     * 订单发起
     * @param map
     * @return
     */
    OrderInitiation orderInitiation(Map<String, String> map);

    /**
     * 订单成功
     * @param map
     * @return
     */
    OrderSuccess orderSuccess(Map<String, String> map);

    /**
     * 订单取消
     * @param map
     * @return
     */
    OrderCancel orderCancel(Map<String, String> map);

    /**
     * 订单补传
     * @param map
     * @return
     */
    OrderSupplements orderSupplements(Map<String, String> map);

    /**
     * 订单违约
     * @param map
     * @return
     */
    OrderBreach orderBreak(Map<String, String> map);

}
