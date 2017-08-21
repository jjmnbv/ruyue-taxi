package com.szyciov.supervision.serivice;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import com.szyciov.supervision.serivice.impl.PubDriverServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzw on 2017/8/18.
 */
@Transactional
public class PubDriverServiceTest extends SupervisionApplicationTests {
    private @Autowired
    PubDriverServiceImpl pubDriverService;

    @Test
    public void onWork(){
        Map<String,String> map=new HashMap<>();
        String driverid="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverid);
        map.put("onWorkTime",getNowTime());
        DriverOnWork driverOnWork=pubDriverService.onWork(map);
        System.out.println(driverOnWork);
        Assert.assertNotNull(driverOnWork);
    }

    @Test
    public void offWork(){
        Map<String,String> map=new HashMap<>();
        String driverid="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverid);
        map.put("onWorkTime",getNowTime());
        map.put("offWorkTime",getNowTime());
        DriverOffWork driverOffWork=pubDriverService.offWork(map);
        System.out.println(driverOffWork);
        Assert.assertNotNull(driverOffWork);
    }

}