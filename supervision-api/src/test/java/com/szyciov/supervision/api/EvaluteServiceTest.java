package com.szyciov.supervision.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.szyciov.supervision.api.responce.EntityInfoList;
import com.szyciov.supervision.api.responce.HttpContent;
import org.junit.Assert;
import org.junit.Test;

import com.szyciov.supervision.api.dto.evaluate.DriverCreditInfo;
import com.szyciov.supervision.api.dto.evaluate.DriverPenaltyInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerComplaintInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerEvaluationInformation;

/**
 * Created by 林志伟 on 2017/7/16.
 */
public class EvaluteServiceTest extends ApiServiceTest {

    /**
     * 3.6.1	乘客评价信息(CKPJ) 实时
     */
    @Test
    public void testPassengerEvaluationInformation() throws IOException {
        List<PassengerEvaluationInformation> list=new ArrayList<>();
        PassengerEvaluationInformation passengerEvaluationInformation=new PassengerEvaluationInformation();
        passengerEvaluationInformation.setOrderId("20171522145");
        passengerEvaluationInformation.setEvaluateTime("20170716092136");
        passengerEvaluationInformation.setServiceScore("5");
        passengerEvaluationInformation.setDriverScore("5");
        passengerEvaluationInformation.setVehicleScore("5");
        passengerEvaluationInformation.setDetail("评价内容");
        passengerEvaluationInformation.setPhotoDetail("");
        passengerEvaluationInformation.setSupDetail("");
        passengerEvaluationInformation.setSupTime("");
        passengerEvaluationInformation.setAddress("440110");
        passengerEvaluationInformation.setTotalScore("5");

        list.add(passengerEvaluationInformation);
        HttpContent httpContent=baseApiService.sendApi(list);
        System.out.println(httpContent);
//		如果状态码不是200，测试失败
        Assert.assertEquals(httpContent.getStatus(),200);
    }


    /**
     * 3.6.2	乘客投诉处理信息*（CKTSCL）
     */
    @Test
    public void testPassengerComplaintInfo() throws IOException {
        List<PassengerComplaintInfo> list=new ArrayList<>();
        PassengerComplaintInfo passengerComplaintInfo=new PassengerComplaintInfo();
        passengerComplaintInfo.setOrderId("dsjdjasdj");
        passengerComplaintInfo.setIsComplaint("1");
        passengerComplaintInfo.setComplaintTime("20170925092612");
        passengerComplaintInfo.setPhoto("");
        passengerComplaintInfo.setDealState("1");
        passengerComplaintInfo.setDetail("详细");
        passengerComplaintInfo.setResult("投诉处理结果");
        passengerComplaintInfo.setVisitTime("20170925092612");
        passengerComplaintInfo.setVisitResult("投诉结果很多哈啊啊  啊啊");


        list.add(passengerComplaintInfo);
        HttpContent httpContent=baseApiService.sendApi(list);
        System.out.println(httpContent);
//		如果状态码不是200，测试失败
        Assert.assertEquals(httpContent.getStatus(),200);
    }

    /**
     * 3.6.3	驾驶员处罚信息（JSYCF）
     */

    @Test
    public void testDriverPenaltyInfo() throws IOException {
        List<DriverPenaltyInfo> list=new ArrayList<>();
        DriverPenaltyInfo driverPenaltyInfo=new DriverPenaltyInfo();
        driverPenaltyInfo.setLicenseId("dsbdsgdsgdsdd");
        driverPenaltyInfo.setPunishTime("20170626092612");
        driverPenaltyInfo.setPunishReason("太拽");
        driverPenaltyInfo.setPunishResult("罚款50");

        list.add(driverPenaltyInfo);
        HttpContent httpContent=baseApiService.sendApi(list);
        System.out.println(httpContent);
//		如果状态码不是200，测试失败
        Assert.assertEquals(httpContent.getStatus(),200);
    }

    /**
     * 3.6.4	驾驶员信誉信息(JSYXY)
     */
    @Test
    public void testDriverCreditInfo() throws IOException {
        List<DriverCreditInfo> list=new ArrayList<>();
        DriverCreditInfo driverCreditInfo=new DriverCreditInfo();
        driverCreditInfo.setAddress("440100");
        driverCreditInfo.setLicenseId("dhsjhdjshdjdasd");
        driverCreditInfo.setOrderCount("10");
        driverCreditInfo.setPunishCount("12");
        driverCreditInfo.setComplaintCount("12");
        driverCreditInfo.setLevel("1");
        driverCreditInfo.setTestDate("20170630");
        driverCreditInfo.setTestDepartment("如约第十");
        driverCreditInfo.setDriverName("司机名称");
        driverCreditInfo.setDriverCertCard("hgjdgsdgjewjdhi");
        driverCreditInfo.setVehicleNo("粤A12345");
        driverCreditInfo.setVehicleCertNo("badsgdwuwej");
        driverCreditInfo.setTestResult("很好");
        list.add(driverCreditInfo);
        HttpContent httpContent=baseApiService.sendApi(list);
        System.out.println(httpContent);
//		如果状态码不是200，测试失败
        Assert.assertEquals(httpContent.getStatus(),200);
    }

}
