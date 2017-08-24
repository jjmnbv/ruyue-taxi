package com.szyciov.supervision.service.impl;


import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.order.*;
import com.szyciov.supervision.mapper.OrderMapper;
import com.szyciov.supervision.service.OrderService;

import org.apache.commons.lang.StringUtils;
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
    	OrderInitiation orderInitiation = null;
    	String ordertype = map.get("ordertype");//订单类型包括1-约车、2-接机、3-送机，4-出租车'
    	String usetype = map.get("usetype");// 0-因公，1-因私，2-个人
    	if (StringUtils.isNotBlank(ordertype) && StringUtils.isNotBlank(usetype)){
    		//1.用车类型不是2,则认为是因公或者因私用车,即属于机构订单
    		//2.用车类型为2,且订单类型为4,属于运营出租车订单
    		//3.剩下类型默认为运营订单
	    	if (!"2".equals(usetype)){
	    		orderInitiation = orderMapper.orgOrderInitiation(map);
	    	}else if ("4".equals(ordertype)){
	    		orderInitiation = orderMapper.taxiOrderInitiation(map);
	    	}else{
	    		orderInitiation = orderMapper.opOrderInitiation(map);
	    	}
    	}
        return orderInitiation;
    }

    /**
     * 订单成功
     * @param map
     * @return
     */
    @Override
    public OrderSuccess orderSuccess(Map<String, String> map) {
    	OrderSuccess orderSuccess = null;
    	String ordertype = map.get("ordertype");//订单类型包括1-约车、2-接机、3-送机，4-出租车'
    	String usetype = map.get("usetype");// 0-因公，1-因私，2-个人
    	if (StringUtils.isNotBlank(ordertype) && StringUtils.isNotBlank(usetype)){
    		//1.用车类型是0,1,则认为是因公或者因私用车,即属于机构订单
    		//2.用车类型为2,且订单类型为4,属于运营出租车订单
    		//3.剩下类型默认为运营订单
	    	if ("0".equals(usetype) || "1".equals(usetype)){
	    		map.put("table", "org_order");	
	    	}else if ("4".equals(ordertype)){
	    		map.put("table", "op_taxiorder");	
	    	}else{
	    		map.put("table", "op_order");	
	    	}
	    	orderSuccess = orderMapper.orderSuccess(map);
    	}
        return orderSuccess;
    }

    /**
     * 订单取消
     * @param map
     * @return
     */
    @Override
    public OrderCancel orderCancel(Map<String, String> map) {
    	OrderCancel orderCancel = null;
    	String ordertype = map.get("ordertype");//订单类型包括1-约车、2-接机、3-送机，4-出租车'
    	String usetype = map.get("usetype");// 0-因公，1-因私，2-个人
    	if (StringUtils.isNotBlank(ordertype) && StringUtils.isNotBlank(usetype)){
    		//1.用车类型是0,1,则认为是因公或者因私用车,即属于机构订单
    		//2.用车类型为2,且订单类型为4,属于运营出租车订单
    		//3.剩下类型默认为运营订单
    		String tableOrder = "";
    		String tableCancel = "";
	    	if ("0".equals(usetype) || "1".equals(usetype)){
	    		tableOrder = "org_order";
	    		tableCancel = "org_ordercancel";
	    	}else if ("4".equals(ordertype)){
	    		tableOrder = "op_taxiorder";
	    		tableCancel = "op_taxiordercancel";
	    	}else{
	    		tableOrder = "op_order";
	    		tableCancel = "op_ordercancel";
	    	}
	    	map.put("table_order", tableOrder);	
	    	map.put("table_cancel", tableCancel);	
	    	orderCancel = orderMapper.orderCancel(map);
    	}
        return orderCancel;
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
        OrderBreach  orderBreach = null;
        String ordertype = map.get("ordertype");//订单类型包括1-约车、2-接机、3-送机，4-出租车'
    	String usetype = map.get("usetype");// 0-因公，1-因私，2-个人
    	if (StringUtils.isNotBlank(ordertype) && StringUtils.isNotBlank(usetype)){
    		//1.用车类型是0,1,则认为是因公或者因私用车,即属于机构订单
    		//2.用车类型为2,且订单类型为4,属于运营出租车订单
    		//3.剩下类型默认为运营订单
    		String tableOrder = "";
    		String tableCancel = "";
	    	if ("0".equals(usetype) || "1".equals(usetype)){
	    		tableOrder = "org_order";
	    		tableCancel = "org_ordercancel";
	    	}else if ("4".equals(ordertype)){
	    		tableOrder = "op_taxiorder";
	    		tableCancel = "op_taxiordercancel";
	    	}else{
	    		tableOrder = "op_order";
	    		tableCancel = "op_ordercancel";
	    	}
	    	map.put("table_order", tableOrder);	
	    	map.put("table_cancel", tableCancel);	
	    	orderBreach = orderMapper.orderBreak(map);
    	}
        return orderBreach;
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
