package com.szyciov.supervision.api;

import com.supervision.api.order.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.3	营运订单信息数据
 * Created by 林志伟 on 2017/7/14.
 */
public class OrderService extends ApiServiceTest {
    /**
     *3.3.1	订单发起(DDFQ) 实时
     */
    @Test
    public void testOrderInitiation(){
        List<OrderInitiation> list=new ArrayList<OrderInitiation>();
        OrderInitiation orderInitiation=new OrderInitiation();
        orderInitiation.setAddress("440100");
        orderInitiation.setOrderId("A201700122344");
        orderInitiation.setDepartTime("20170526120303");
        orderInitiation.setOrderTime("20170602011205");
        orderInitiation.setPassengerPhone("18826252137");
        orderInitiation.setPassengerNote("备注");
        orderInitiation.setDeparture("出发地");
        orderInitiation.setDepLongitude("20");
        orderInitiation.setDepLatitude("23");
        orderInitiation.setDestination("dsdsdasjdh详细地址");
        orderInitiation.setDestLongitude("36");
        orderInitiation.setDestLatitude("14");
        orderInitiation.setEncrypt("1");
        orderInitiation.setFareType("sdasfdas");
        orderInitiation.setOrderSource("0");
        orderInitiation.setPsgName("林");
        orderInitiation.setPsgGender("1");
        orderInitiation.setPsgTotal("3");
        orderInitiation.setVehType("KC");
        orderInitiation.setIsReserve(0);
        orderInitiation.setIsVoice(1);
        orderInitiation.setVoiceUrl("http://www.baisdds/dsdsd");
        orderInitiation.setPreMile("45");
        orderInitiation.setPreTime("100");
        orderInitiation.setPreFare("24");
        orderInitiation.setUseTime("20170526020315");
        orderInitiation.setUseLocale("广州寻衅");
        orderInitiation.setUseLon("12");
        orderInitiation.setUseLat("1212");
        orderInitiation.setTaxiTypeCode("0");
        orderInitiation.setServiceTypeCode("服务类型");
        orderInitiation.setDepartCity("440110");
        orderInitiation.setDestCity("440400");
        list.add(orderInitiation);
        messageSender.send(list);
    }

    /***
     * 3.3.2	订单成功(DDCG) 实时
     */
    @Test
    public void testOrderSuccess(){
        List<OrderSuccess> list=new ArrayList<OrderSuccess>();
        OrderSuccess orderSuccess=new OrderSuccess();
        orderSuccess.setAddress("440100");
        orderSuccess.setOrderId("sdsad12s1d2s1");
        orderSuccess.setLongitude("10");
        orderSuccess.setLatitude("25");
        orderSuccess.setEncrypt("1");
        orderSuccess.setLicenseId("dsjdshdasdasd455");
        orderSuccess.setDriverPhone("88115514");
        orderSuccess.setVehicleNo("粤A12345");
        orderSuccess.setPlateColor("黑色");
        orderSuccess.setDistributeTime("20170602120342");
        orderSuccess.setOrderTime("20170602120342");
        orderSuccess.setCarCertNo("dgdguahdandad");
        orderSuccess.setDriverName("林志伟");
        orderSuccess.setIdNo("445202199102032563");
        orderSuccess.setDriCertNo("djhwdhiadgajdhasd");
        orderSuccess.setDepartCity("440010");
        orderSuccess.setDestCity("440200");
        orderSuccess.setDepartLocale("广州白云");
        orderSuccess.setDepartLocaleDetail("shhishdid");
        orderSuccess.setDepartLon("25");
        orderSuccess.setDepartLat("15");
        orderSuccess.setDesLocale("发发生发送");
        orderSuccess.setDesLocaleDetail("bsgjshdhsdh");
        orderSuccess.setDesLon("12");
        orderSuccess.setDesLat("15");
        orderSuccess.setResStatus("响应");
        orderSuccess.setResTime("154545454");
        list.add(orderSuccess);
        messageSender.send(list);
    }

    /**
     * 3.3.3	订单撤销(DDCX) 实时
     */
    @Test
    public void testOrderCancel(){
        List<OrderCancel> list=new ArrayList<OrderCancel>();
        OrderCancel orderCancel=new  OrderCancel();
        orderCancel.setAddress("440100");
        orderCancel.setOrderId("2017666424545");
        orderCancel.setOrderTime("20170506123654");
        orderCancel.setCancelTime("20170714122536");
        orderCancel.setInitiator("1");
        orderCancel.setCancelTypeCode("1");
        orderCancel.setCancelReason("撤销原因");
        list.add(orderCancel);
        messageSender.send(list);
    }

    /**
     * 注：该服务并不是调用监管平台，而是由监管平台调用
     * 3.3.4	订单补传请求*(DDBCQQ)
     *
     */
//    @Test
//    public void testOrderSupplementsRequest(){
//        List<OrderSupplementsRequest> list=new ArrayList<OrderSupplementsRequest>();
//        OrderSupplementsRequest orderSupplementsRequest=new OrderSupplementsRequest();
//        orderSupplementsRequest.setAddress("440100");
//        orderSupplementsRequest.setOrderId("2017456s4dsd");
//        orderSupplementsRequest.setOrderTime("20170707121545");
//
//        list.add(orderSupplementsRequest);
//        messageSender.send(list);
//    }

    /**
     * 3.3.5	订单补传*(DDBC) 实时
     */
    @Test
    public void testOrderSupplements(){
        List<OrderSupplements> list=new ArrayList<OrderSupplements>();
        OrderSupplements orderSupplements=new OrderSupplements();
        orderSupplements.setAddress("440100");
        orderSupplements.setOrderId("2017456s4dsd");
        orderSupplements.setOrderStatus("DDLR");
        orderSupplements.setOrderTime("20170707121545");
        orderSupplements.setVehType("KC");//用车类型
        orderSupplements.setIsReserve(0);
        orderSupplements.setIsVoice(1);
        orderSupplements.setVoiceUrl("http://sdnsd.dsjdhjs.sd/sdjs/dadd");
        orderSupplements.setUseTime("20170702010202");
        orderSupplements.setUseLocale("广州");
        orderSupplements.setEncrypt("1");
        orderSupplements.setUseLon("21");
        orderSupplements.setUseLat("45");
        orderSupplements.setDestLocale("dgsgdsgd");
        orderSupplements.setDestLon("45");
        orderSupplements.setDestLat("12");
        orderSupplements.setDriName("林志伟");
        orderSupplements.setDriTel("88878875");
        orderSupplements.setQulificationNo("12345678");
        orderSupplements.setVehicleNo("粤A12345");
        orderSupplements.setCarCertNo("sdshdshdsdsd");
        orderSupplements.setOnTime("20170526122412");
        orderSupplements.setOffTime("20170715125612");
        orderSupplements.setPsgMile("15");
        orderSupplements.setDuration("5654564");
        orderSupplements.setReceivable("200");
        orderSupplements.setPaid("100");
        orderSupplements.setDiscount("100");
        orderSupplements.setCash("100");
        orderSupplements.setElePay("");
        orderSupplements.setEleOrg("");
        orderSupplements.setPosPay("");
        orderSupplements.setPosOrg("");
        orderSupplements.setCallPay("154");
        orderSupplements.setExtraPay("");
        orderSupplements.setPeakPay("");
        orderSupplements.setNightPay("");
        orderSupplements.setBillStatus("一开");
        orderSupplements.setPsgName("你好");
        orderSupplements.setPsgGender("1");
        orderSupplements.setPsgTel("88888888");


        list.add(orderSupplements);
        messageSender.send(list);
    }

    /**
     * 3.3.6	订单违约*(DDWY) 实时
     */
    @Test
    public void  testOrderBreach(){
        List<OrderBreach> list=new ArrayList<OrderBreach>();
        OrderBreach orderBreach=new OrderBreach();
        orderBreach.setAddress("440100");
        orderBreach.setOrderId("1122122545");
        orderBreach.setOrderTime("20170705121415");
        orderBreach.setDriCertNo("dshdhsidhsidia");
        orderBreach.setVehicleNo("粤A12345");
        orderBreach.setBreakPart("0");
        orderBreach.setBreakReason("shdsdisd");
        orderBreach.setPsgTel("88888888");
        orderBreach.setBreakTime("20170705121415");
        list.add(orderBreach);
        messageSender.send(list);
    }

    /**
     * 3.3.7	驾驶员上班*(JSYSB) 实时
     */
    @Test
    public void testDriverOnWork(){
        List<DriverOnWork> list=new ArrayList<DriverOnWork>();
        DriverOnWork driverOnWork=new  DriverOnWork();
        driverOnWork.setAddress("440100");
        driverOnWork.setIdentifier("91440101MA59EE6A9M");
        driverOnWork.setDriverName("林志伟");
        driverOnWork.setDriverIDCard("445202199302121452");
        driverOnWork.setDriverCertCard("dhaskhdiashdsdsd");
        driverOnWork.setDriverPhone("18815262134");
        driverOnWork.setVehicleNo("粤A12345");
        driverOnWork.setPlateColor("黑色");
        driverOnWork.setBrand("车辆厂牌");
        driverOnWork.setOnWorkTime("20170714112526");

        list.add(driverOnWork);
        messageSender.send(list);

    }

    /**
     * 3.3.8	驾驶员下班*(JSYXB) 实时
     */
    @Test
    public void testDriverOffWork(){
        List<DriverOffWork> list=new ArrayList<DriverOffWork>();
        DriverOffWork driverOffWork=new  DriverOffWork();
        driverOffWork.setAddress("440100");
        driverOffWork.setIdentifier("91440101MA59EE6A9M");
        driverOffWork.setDriverName("林志伟");
        driverOffWork.setDriverIDCard("445202199302121452");
        driverOffWork.setDriverCertCard("dhaskhdiashdsdsd");
        driverOffWork.setDriverPhone("18815262134");
        driverOffWork.setVehicleNo("粤A12345");
        driverOffWork.setPlateColor("黑色");
        driverOffWork.setOnWorkTime("20170714112526");
        driverOffWork.setOffWorkTime("20170714212526");
        driverOffWork.setDriveCount("6");
        driverOffWork.setDriveMile("123");
        driverOffWork.setDriveTime("10");
        driverOffWork.setPrice("1240");
        driverOffWork.setFactPrice("1230");

        list.add(driverOffWork);
        messageSender.send(list);
    }


}
