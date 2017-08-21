package com.szyciov.supervision.serivice;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.operation.VehicleOperationOffline;
import com.szyciov.supervision.api.dto.operation.VehicleOperationOnline;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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

    @Test
    public void operationPay() throws Exception {
    }

    @Test
    public void operationDeparture() throws Exception {
    }

    @Test
    public void operationArrival() throws Exception {
    }

}