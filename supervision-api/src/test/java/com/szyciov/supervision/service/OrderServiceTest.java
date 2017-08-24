package com.szyciov.supervision.service;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import com.szyciov.supervision.api.dto.order.OrderBreach;
import com.szyciov.supervision.api.dto.order.OrderCancel;
import com.szyciov.supervision.api.dto.order.OrderInitiation;
import com.szyciov.supervision.api.dto.order.OrderSuccess;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
public class OrderServiceTest extends SupervisionApplicationTests {
    @Autowired
    private OrderService orderService;
    @Test
    public void driverOnWork() throws Exception {
        Map<String,String> map=new HashMap<>();
        String driverid="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverid);
        map.put("onWorkTime",getNowTime());
        DriverOnWork driverOnWork=orderService.driverOnWork(map);
        System.out.println(driverOnWork);
        Assert.assertNotNull(driverOnWork);

    }

    @Test
    public void driverOffWork() throws Exception {
        Map<String,String> map=new HashMap<>();
        String driverid="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverid);
        map.put("onWorkTime",getNowTime());
        map.put("offWorkTime",getNowTime());
        DriverOffWork driverOffWork=orderService.driverOffWork(map);
        System.out.println(driverOffWork);
        Assert.assertNotNull(driverOffWork);
    }

    @Test
    public void orderInitiation() throws Exception {
    	Map<String,String> map=new HashMap<>();
    	map.put("orderno", "BCI1707201900001");
    	map.put("ordertype", "1");
    	map.put("usetype", "0");
    	OrderInitiation orderInitiation = orderService.orderInitiation(map);
    	System.out.println(orderInitiation);
    	Assert.assertNotNull(orderInitiation);
    }

    @Test
    public void orderSuccess() throws Exception {
    	Map<String,String> map=new HashMap<>();
    	map.put("orderno", "BZI1707200900002");
    	map.put("ordertype", "1");
    	map.put("usetype", "0");
    	OrderSuccess orderSuccess = orderService.orderSuccess(map);
    	System.out.println(orderSuccess);
    	Assert.assertNotNull(orderSuccess);
    	
    }

    @Test
    public void orderCancel() throws Exception {
    	Map<String,String> map=new HashMap<>();
    	map.put("orderno", "CYT1708212100003");
    	map.put("ordertype", "4");
    	map.put("usetype", "2");
    	OrderCancel orderCancel = orderService.orderCancel(map);
    	System.out.println(orderCancel);
    	Assert.assertNotNull(orderCancel);
    }

    @Test
    public void orderSupplements() throws Exception {
    }

    @Test
    public void orderBreak() throws Exception {
    	Map<String,String> map=new HashMap<>();
    	map.put("orderno", "BZI1707211700004");
    	map.put("ordertype", "1");
    	map.put("usetype", "0");
    	OrderBreach  orderBreach = orderService.orderBreak(map);
    	System.out.println(orderBreach);
    	Assert.assertNotNull(orderBreach); 
    }

}