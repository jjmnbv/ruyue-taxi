package com.szyciov.supervision.service;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.operation.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
public class OperationServiceTest extends SupervisionApplicationTests {
    @Autowired
    private OperationService operationService;

    /**
     * y营运上线
     * @throws Exception
     */
    @Test
    public void vehicleOperationOnline() throws Exception {
        Map<String,String> map=new HashMap<>();
        String driverId="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverId);
        map.put("loginTime",getNowTime());
        map.put("longitude","12");
        map.put("latitude","20");
        VehicleOperationOnline vehicleOperationOnline=operationService.vehicleOperationOnline(map);
        System.out.println(vehicleOperationOnline);
        Assert.assertNotNull(vehicleOperationOnline);
    }

    /**
     * 营运下线
     * @throws Exception
     */
    @Test
    public void vehicleOperationOffline() throws Exception {
        Map<String,String> map=new HashMap<>();
        String driverId="098A6A32-FE04-4BC3-872D-11183B519ADA";
        map.put("driverId",driverId);
        map.put("logoutTime",getNowTime());
        map.put("longitude","12");
        map.put("latitude","20");
        VehicleOperationOffline vehicleOperationOffline=operationService.vehicleOperationOffline(map);
        System.out.println(vehicleOperationOffline);
        Assert.assertNotNull(vehicleOperationOffline);
    }



    /**
     * 营运出发--测试运营订单
     * @throws Exception
     */
    @Test
    public void operationDeparture() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        OperationDeparture operationDeparture=operationService.operationDeparture(map);
        System.out.println(operationDeparture);
        Assert.assertNotNull(operationDeparture);
    }
    /**
     * 营运出发--测试机构订单
     * @throws Exception
     */
    @Test
    public void operationDeparture2() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        OperationDeparture operationDeparture=operationService.operationDeparture(map);
        System.out.println(operationDeparture);
        Assert.assertNotNull(operationDeparture);
    }


    /**
     *  营运到达--测试运营订单
     * @throws Exception
     */

    @Test
    public void operationArrival() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        OperationArrival operationArrival=operationService.operationArrival(map);
        System.out.println(operationArrival);
        Assert.assertNotNull(operationArrival);

    }

    /**
     *  营运到达--测试机构订单
     * @throws Exception
     */

    @Test
    public void operationArrival2() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        OperationArrival operationArrival=operationService.operationArrival(map);
        System.out.println(operationArrival);
        Assert.assertNotNull(operationArrival);

    }

    /**
     *  营运到达--测试运营订单
     * @throws Exception
     */

    @Test
    public void operationPay() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        OperationPay operationPay=operationService.operationPay(map);
        System.out.println(operationPay);
        Assert.assertNotNull(operationPay);

    }

    /**
     *  营运到达--测试机构订单
     * @throws Exception
     */

    @Test
    public void operationPay2() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        OperationPay operationPay=operationService.operationPay(map);
        System.out.println(operationPay);
        Assert.assertNotNull(operationPay);


    }

}