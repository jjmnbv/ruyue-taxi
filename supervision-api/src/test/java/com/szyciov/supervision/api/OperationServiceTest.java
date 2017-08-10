package com.szyciov.supervision.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.szyciov.supervision.api.operation.OperationArrival;
import com.szyciov.supervision.api.operation.OperationDeparture;
import com.szyciov.supervision.api.operation.OperationPay;
import com.szyciov.supervision.api.operation.VehicleOperationOffline;
import com.szyciov.supervision.api.operation.VehicleOperationOnline;

/**
 * 3.4	经营信息数据
 * Created by 林志伟 on 2017/7/14.
 */
public class OperationServiceTest extends ApiServiceTest {

    /**
     *3.4.1	车辆营运上线(CLYYSX) 实时
     */
    @Test
    public void testVehicleOperationOnline(){
        List<VehicleOperationOnline> list =new ArrayList<VehicleOperationOnline>();
        VehicleOperationOnline  vehicleOperationOnline=new VehicleOperationOnline();
        vehicleOperationOnline.setLicenseId("shjshfsdsds");
        vehicleOperationOnline.setVehicleNo("粤A12345");
        vehicleOperationOnline.setPlateColor("黑色");
        vehicleOperationOnline.setLoginTime("20170112124523");
        vehicleOperationOnline.setLongitude("12");
        vehicleOperationOnline.setLatitude("15");
        vehicleOperationOnline.setEncrypt("1");
        vehicleOperationOnline.setAddress("440100");
        list.add(vehicleOperationOnline);
        messageSender.send(list);

    }

    /**
     * 3.4.2	车辆营运下线(CLYYXX) 实时
     */
    @Test
    public void testVehicleOperationOffline(){
        List<VehicleOperationOffline> list =new ArrayList<VehicleOperationOffline>();
        VehicleOperationOffline  vehicleOperationOffline=new VehicleOperationOffline();
        vehicleOperationOffline.setLicenseId("shjshfsdsds");
        vehicleOperationOffline.setVehicleNo("粤A12345");
        vehicleOperationOffline.setPlateColor("黑色");
        vehicleOperationOffline.setLogoutTime("20170112224523");
        vehicleOperationOffline.setLongitude("12");
        vehicleOperationOffline.setLatitude("15");
        vehicleOperationOffline.setEncrypt("1");
        vehicleOperationOffline.setAddress("440100");
        list.add(vehicleOperationOffline);
        messageSender.send(list);

    }

    /**
     *
     * 3.4.3	营运出发(YYCF) 实时
     */
    @Test
    public void  testOperationDeparture(){
        List<OperationDeparture> list =new ArrayList<OperationDeparture>();
        OperationDeparture operationDeparture=new OperationDeparture();
        operationDeparture.setOrderId("dhsdasdhshdshd");
        operationDeparture.setLicenseId("djgdsdjshdhslddsdad");
        operationDeparture.setPassengerPhone("18826252137");
        operationDeparture.setFareType("11");
        operationDeparture.setVehicleNo("粤A12345");
        operationDeparture.setPlateColor("黑色");
        operationDeparture.setDepLongitude("10");
        operationDeparture.setDepLatitude("13");
        operationDeparture.setEncrypt("1");
        operationDeparture.setDepTime("20170112224523");
        operationDeparture.setWaitMile("20");
        operationDeparture.setPassengerWaitTime("12121");
        operationDeparture.setDriveWaitTime("0");
        operationDeparture.setAddress("440100");
        operationDeparture.setDriverName("林志伟");
        operationDeparture.setDriverIDCard("445202199601022541");
        operationDeparture.setDriverCertCard("dsjdsjdhjshdsd");
        operationDeparture.setPassengerName("林大叔");

        list.add(operationDeparture);
        messageSender.send(list);
    }

    /**
     * 3.4.4	营运到达(YYDD) 实时
     */
    @Test
    public void  testOperationArrival(){
        List<OperationArrival> list =new ArrayList<OperationArrival>();
        OperationArrival operationArrival=new OperationArrival();
        operationArrival.setOrderId("dskdisdsds");
        operationArrival.setDestLongitude("12");
        operationArrival.setDestLatitude("45");
        operationArrival.setEncrypt("1");
        operationArrival.setDestTime("20170613120336");
        operationArrival.setDriveMile("21");
        operationArrival.setDriveTime("123");
        operationArrival.setAddress("440100");
        operationArrival.setDriverName("林dhsd");
        operationArrival.setDriverPhone("18826253366");
        operationArrival.setDriverIDCard("445202199407041235");
        operationArrival.setDriverCertCard("dashdjshjdhsd455");
        operationArrival.setVehicleNo("粤A12345");
        operationArrival.setDepTime("20170203141215");
        operationArrival.setDepLongitude("12");
        operationArrival.setDepLatitude("16");
        operationArrival.setWaitTime("1211");
        operationArrival.setPassengerName("里搜索");
        operationArrival.setPassengerSex("2");
        operationArrival.setPassengerPhone("18825625124");
        operationArrival.setPrice("155");

        list.add(operationArrival);
        messageSender.send(list);
    }

    /**
     * 3.4.5	营运支付(YYZF) 实时
     */
    @Test
    public void testOperationPay(){
        List<OperationPay> list =new ArrayList<OperationPay>();
        OperationPay operationPay=new OperationPay();
        operationPay.setOrderId("dhshdshd");
        operationPay.setOnArea("440100");
        operationPay.setDriverName("林志偉");
        operationPay.setLicenseId("djashjdfhahfs7");
        operationPay.setFareType("sdsdsdsad");
        operationPay.setVehicleNo("粤A12345");
        operationPay.setPlateColor("黑色");
        operationPay.setBookDepTime("20170716082700");
        operationPay.setWaitTime("10000");
        operationPay.setDepLongitude("12");
        operationPay.setDepLatitude("10");
        operationPay.setDepArea("广州xxx");
        operationPay.setDepTime("20170716082700");
        operationPay.setDestLongitude("12");
        operationPay.setDestLatitude("23");
        operationPay.setDestArea("广州");
        operationPay.setDestTime("20170716082700");
        operationPay.setBookModel("你好");
        operationPay.setModel("小车");
        operationPay.setDriveMile("12");
        operationPay.setDriveTime("1203");
        operationPay.setWaitMile("12");
        operationPay.setFactPrice("120");
        operationPay.setPrice("120");
        operationPay.setCashPrice("0");
        operationPay.setLineName("微信支付");
        operationPay.setLinePrice("120");
        operationPay.setPosName("");
        operationPay.setPosPrice("0");
        operationPay.setBenfitPrice("0");
        operationPay.setBookTip("0");
        operationPay.setPassengerTip("0");
        operationPay.setPeakUpPrice("0");
        operationPay.setNightUpPrice("0");
        operationPay.setFarUpPrice("12");
        operationPay.setOtherUpPrice("1");
        operationPay.setPayState("1");
        operationPay.setPayTime("20170716082703");
        operationPay.setOrderMatchTime("20170716082710");
        operationPay.setInvoiceStatus("一开");
        operationPay.setDriverIDCard("445201199407042416");
        operationPay.setDriverCertCard("bsajdjashdjasjd");
        operationPay.setCallPrice("102");
        operationPay.setFareRuleUrl("http://qqq.dsd.dasd/fsmfs");
        list.add(operationPay);
        messageSender.send(list);

    }


}
