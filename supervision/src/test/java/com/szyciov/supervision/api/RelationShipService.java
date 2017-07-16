package com.szyciov.supervision.api;

import com.supervision.api.relationship.HumanVehicleInfo;
import com.szyciov.supervision.util.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 3.7	人车对应关系信息数据
 * Created by 林志伟 on 2017/7/16.
 */
public class RelationShipService extends ApiServiceTest {

    /**
     * 3.7	人车对应关系信息数据
     */
    @Test
    public void  testHumanVehicleInfo(){
        List<HumanVehicleInfo> list=new ArrayList<>();
        HumanVehicleInfo humanVehicleInfo=new HumanVehicleInfo();
        humanVehicleInfo.setAddress("440110");
        humanVehicleInfo.setCurDriverName("林志伟");
        humanVehicleInfo.setCurDriverCertNo("dashdgashgdad");
        humanVehicleInfo.setCurVehicleNo("粤A12345");
        humanVehicleInfo.setCurVehicleCertNo("bjahdjhajdhajd");
        humanVehicleInfo.setCurTimeOn("20170630000000");
        humanVehicleInfo.setCurTimeOff("20170930000000");
        humanVehicleInfo.setReportDrivers("基督教阿娇");
        humanVehicleInfo.setReportDriverCertNo("hbdaskdadad");
        humanVehicleInfo.setReportVehicleNo("粤A12345");
        humanVehicleInfo.setReportVehicleCertNo("gdgsjagdjasgj");
        humanVehicleInfo.setTimeFrom("20170630000000");
        humanVehicleInfo.setTimeTo("20170930000000");

        list.add(humanVehicleInfo);
        messageSender.send(list);
    }
}
