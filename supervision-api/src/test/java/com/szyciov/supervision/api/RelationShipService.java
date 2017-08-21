package com.szyciov.supervision.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.szyciov.supervision.api.responce.EntityInfoList;
import com.szyciov.supervision.api.responce.HttpContent;
import org.junit.Assert;
import org.junit.Test;

import com.szyciov.supervision.api.dto.relationship.HumanVehicleInfo;

/**
 * 3.7	人车对应关系信息数据
 * Created by 林志伟 on 2017/7/16.
 */
public class RelationShipService extends ApiServiceTest {

    /**
     * 3.7	人车对应关系信息数据
     */
    @Test
    public void  testHumanVehicleInfo() throws IOException {
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
        HttpContent httpContent=baseApiService.sendApi(list);
        System.out.println(httpContent);
//		如果状态码不是200，测试失败
        Assert.assertEquals(httpContent.getStatus(),200);
    }
}
