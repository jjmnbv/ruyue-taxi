package com.szyciov.supervision.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.szyciov.supervision.api.gps.DriverLocationInfo;
import com.szyciov.supervision.api.gps.PassengerLocationInfo;

/**
 * Created by 林志伟 on 2017/7/16.
 */
public class GpsServiceTest extends ApiServiceTest {


    /**
     * 3.5.1	驾驶员定位信息
     */
    @Test
    public void testDriverLocationInfo(){
        List<DriverLocationInfo> list =new ArrayList<DriverLocationInfo>();
        DriverLocationInfo driverLocationInfo=new DriverLocationInfo();
        driverLocationInfo.setAddress("440110");
        driverLocationInfo.setDriverRegionCode("440100");
        driverLocationInfo.setVehicleNo("粤A12345");
        driverLocationInfo.setPlateColor("黑色");
        driverLocationInfo.setVehicleRegionCode("440101");
        driverLocationInfo.setPositionTime("20170716085612");
        driverLocationInfo.setLongitude("12");
        driverLocationInfo.setLatitude("14");
        driverLocationInfo.setSpeed("124");
        driverLocationInfo.setDirection("12");
        driverLocationInfo.setElevation("14");
        driverLocationInfo.setMileage("145");
        driverLocationInfo.setEncrypt("1");
        driverLocationInfo.setWarnStatus("1");
        driverLocationInfo.setVehStatus("开车");
        driverLocationInfo.setBizStatus("1");
        driverLocationInfo.setOrderId("ddjshdsdsd4545");
        driverLocationInfo.setDriverName("林志伟");
        driverLocationInfo.setDriverId("dhasjhdjashdjhs");
        driverLocationInfo.setDriverCertCard("dhsjhdjdjhsjd");
        driverLocationInfo.setVehicleType("大车");
        driverLocationInfo.setPositionType("SJSB");
        driverLocationInfo.setValidity(1);
        list.add(driverLocationInfo);
        messageSender.send(list);
    }

    /**
     * 3.5.2	乘客定位信息
     */
    @Test
    public void testPassengerLocationInfo(){
        List<PassengerLocationInfo> list=new ArrayList<>();
        PassengerLocationInfo passengerLocationInfo=new PassengerLocationInfo();
        passengerLocationInfo.setAddress("440110");
        passengerLocationInfo.setOrderId("daudhjahdjdda");
        passengerLocationInfo.setDriverName("李司机");
        passengerLocationInfo.setDriverPhone("18826252137");
        passengerLocationInfo.setDriverIDCard("445201199607042635");
        passengerLocationInfo.setDriverCertCard("dadasdasdwrqwr");
        passengerLocationInfo.setPassengerPhone("18826252137");
        passengerLocationInfo.setVehicleNo("粤A12345");
        passengerLocationInfo.setPlateColor("黑色");
        passengerLocationInfo.setBrand("中国");
        passengerLocationInfo.setEncrypt("1");
        passengerLocationInfo.setLongitude("10");
        passengerLocationInfo.setLatitude("12");
        passengerLocationInfo.setSpeed("12");
        passengerLocationInfo.setWarnStatus("1");
        passengerLocationInfo.setPositionTime("20170716091612");
        passengerLocationInfo.setValidity(1);


        list.add(passengerLocationInfo);
        messageSender.send(list);

    }
}
