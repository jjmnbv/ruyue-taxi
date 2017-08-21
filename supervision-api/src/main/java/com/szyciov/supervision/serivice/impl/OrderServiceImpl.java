package com.szyciov.supervision.serivice.impl;


import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.order.*;
import com.szyciov.supervision.mapper.OrderMapper;
import com.szyciov.supervision.serivice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 3.3	营运订单信息数据
 * Created by lzw on 2017/8/18.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;


    /**
     * 司机上班
     * @param map
     * @return
     */
    @Override
    public DriverOnWork driverOnWork(Map<String, String> map) {
        DriverOnWork driverOnWork =orderMapper.driverOnWork(map.get("driverId"));
        if(driverOnWork!=null) {
            driverOnWork.setOnWorkTime(map.get("onWorkTime"));
        }
        return driverOnWork;
    }

    /**
     * 司机下班
     * @param map
     * @return
     */
    @Override
    public DriverOffWork driverOffWork(Map<String, String> map) {
        DriverOffWork driverOffWork =orderMapper.driverOffWork(map.get("driverId"));
        if(driverOffWork!=null) {
            driverOffWork.setOnWorkTime(map.get("onWorkTime"));
            driverOffWork.setOffWorkTime(map.get("offWorkTime"));
        }
        return driverOffWork;
    }

    /**
     * 订单发起
     * @param map
     * @return
     */
    @Override
    public OrderInitiation orderInitiation(Map<String, String> map) {
        return null;
    }

    /**
     * 订单成功
     * @param map
     * @return
     */
    @Override
    public OrderSuccess orderSuccess(Map<String, String> map) {
        return null;
    }

    /**
     * 订单取消
     * @param map
     * @return
     */
    @Override
    public OrderCancel orderCancel(Map<String, String> map) {
        return null;
    }

    /**
     * 订单补传
     * @param map
     * @return
     */
    @Override
    public OrderSupplements orderSupplements(Map<String, String> map) {
        return null;
    }

    /**
     * 订单违约
     * @param map
     * @return
     */
    @Override
    public OrderBreach orderBreak(Map<String, String> map) {
        return null;
    }

    /**
     * 执行命令
     * @param commandEnum
     * @param map
     * @return
     */
    @Override
    public BaseApi execute(CommandEnum commandEnum,Map<String,String> map) {
        switch (commandEnum){
            case DriverOnWork:
                return this.driverOnWork(map);
            case DriverOffWork:
                return this.driverOffWork(map);
            case OrderInitiation:
                return  this.orderInitiation(map);
            case OrderSuccess:
                return  this.orderSuccess(map);
            case OrderCancel:
                return  this.orderCancel(map);
            case OrderSupplements:
                return  this.orderSupplements(map);
            case OrderBreach:
                return  this.orderBreak(map);
        }
        return null;
    }
}
