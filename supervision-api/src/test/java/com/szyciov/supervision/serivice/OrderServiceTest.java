package com.szyciov.supervision.serivice;

import com.sun.tracing.dtrace.ArgsAttributes;
import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
    }

    @Test
    public void orderSuccess() throws Exception {
    }

    @Test
    public void orderCancel() throws Exception {
    }

    @Test
    public void orderSupplements() throws Exception {
    }

    @Test
    public void orderBreak() throws Exception {
    }

}