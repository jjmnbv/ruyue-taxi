package com.szyciov.coupon.controller;

import java.util.List;
import java.util.Map;

import com.szyciov.coupon.dto.CouponInfoDTO;
import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.util.ResultData;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.ServiceState;
import com.szyciov.param.coupon.CouponExpenseParam;
import com.szyciov.param.coupon.CouponRequestParam;
import com.szyciov.param.coupon.CouponReserveParam;
import com.szyciov.param.coupon.CouponUseParam;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/** 
* EncounterBlackListController Tester. 
* 
* @author <Authors name> 
* @since <pre>Jun 12, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class PubCouponControllerTest {


    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationConnect;


    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
    }

    @After
    public void after() throws Exception {

    }

    /**
    *
    * 注册类型返券
     * 运管端需创建5个有效的注册活动
    *
    */
    @Test
    public void testRegister() throws Exception {

        //注册类型返券
        GenerateCouponParam param = new GenerateCouponParam();
        param.setType(CouponRuleTypeEnum.REGISTER.value);
        param.setUserType(CouponRuleTypeEnum.PERSONAL_USER.value);
        param.setCompanyId("873CF4F7-39C1-4E7F-A7FA-9F1303274218");
        param.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");
        param.setCityCode("0000002");

        String jsonParam = GsonUtil.toJson(param);

        String uri = "/coupon/generate";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        System.out.println(content);

        Assert.assertEquals(200, status);


    }


    /**
     *
     * 充值类型返券
     * 运管端需创建3个有效的充值活动
     *
     */
    @Test
    public void testRecharge() throws Exception {

        //充值类型返券
        GenerateCouponParam param = new GenerateCouponParam();
        param.setType(CouponRuleTypeEnum.RECHARGE.value);
        param.setUserType(CouponRuleTypeEnum.PERSONAL_USER.value);
        param.setCompanyId("873CF4F7-39C1-4E7F-A7FA-9F1303274218");
        param.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");
        param.setCityCode("0000002");
        param.setMoney(50.0);

        String jsonParam = GsonUtil.toJson(param);

        String uri = "/coupon/generate";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        List<GenerateCouponDTO>  dto = GsonUtil.fromJson(content,List.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(3,dto.size());

    }



    /**
     *
     * 充值类型返券
     * 运管端需创建3个有效的充值活动
     *
     */
    @Test
    public void testExpenseGenerate() throws Exception {

        //充值类型返券
        GenerateCouponParam param = new GenerateCouponParam();
        param.setType(CouponRuleTypeEnum.EXPENSE.value);
        param.setUserType(CouponRuleTypeEnum.PERSONAL_USER.value);
        param.setCompanyId("873CF4F7-39C1-4E7F-A7FA-9F1303274218");
        param.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");
        param.setCityCode("0000002");
        param.setMoney(50.0);

        String jsonParam = GsonUtil.toJson(param);

        String uri = "/coupon/generate";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        List<GenerateCouponDTO>  dto = GsonUtil.fromJson(content,List.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(0,dto.size());

    }


    /**
     *
     * 获取抵用券列表
     *
     */
    @Test
    public void testListCoupon() throws Exception {

        CouponRequestParam param = new CouponRequestParam();
        //param.setCitycode("0000002");
        param.setCompanyId("873CF4F7-39C1-4E7F-A7FA-9F1303274218");
        param.setiDisplayLength(10);
        param.setiDisplayStart(0);
        param.setServiceType(1);
        param.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");


        String jsonParam = GsonUtil.toJson(param);

        String uri = "/coupon/list";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        List<CouponInfoDTO> dtoList = GsonUtil.fromJson(dto.getData(),List.class);
        Assert.assertEquals(7,dtoList.size());

    }


    /**
     *
     * 返回最大抵用券
     *
     */
    @Test
    public void testgetMaxCoupon() throws Exception {

        CouponRequestParam param = new CouponRequestParam();
        param.setCityCode("0000002");
        param.setCompanyId("873CF4F7-39C1-4E7F-A7FA-9F1303274218");
        param.setiDisplayLength(10);
        param.setiDisplayStart(0);
        param.setServiceType(2);
        param.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");
        param.setUserType(3);


        String jsonParam = GsonUtil.toJson(param);

        String uri = "/coupon/get/max";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        CouponInfoDTO couponInfoDTO = GsonUtil.fromJson(dto.getData(),CouponInfoDTO.class);
        Assert.assertNotNull(couponInfoDTO);
        Assert.assertEquals(new Double(20),couponInfoDTO.getMoney());
    }


    /**
     *
     * 抵用券预约
     *
     */
    @Test
    public void testReserve() throws Exception {
        this.successReserve();
        //this.failureReserve();
    }

    /**
     * 成功预约
     * @throws Exception
     */
    private void successReserve()  throws  Exception{
        CouponReserveParam reserveParam = new CouponReserveParam();
        reserveParam.setCouponId("EB90245C-0EDA-4B32-92A7-42CF1CC19ED1");
        reserveParam.setMoney(10d);
        reserveParam.setOrderId("test");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/reserve";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        System.out.println(dto.getData());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("true",resultMap.get("isReserve").toString());

    }

    /**
     * 预约失败
     * @throws Exception
     */
    private void failureReserve() throws  Exception{
        CouponReserveParam reserveParam = new CouponReserveParam();
        reserveParam.setCouponId("73C31E2F-F87E-43B5-8328-C72DF9F9FF57");
        reserveParam.setMoney(10d);
        reserveParam.setOrderId("test");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/reserve";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        Assert.assertEquals(200, status);

        System.out.println(dto.getData());

        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("false",resultMap.get("isReserve").toString());

    }

    /**
     * 新建消费
     * @throws Exception
     */
    @Test
    public void createExpense()  throws  Exception{

        CouponExpenseParam reserveParam = new CouponExpenseParam();
        reserveParam.setCouponId("F6FF0A52-18C5-4C58-B2EE-0204BFFDCC41");
        reserveParam.setMoney(10d);
        reserveParam.setDiscountamount(5d);
        reserveParam.setOrderId("test");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/expense";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();


        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        System.out.println(dto.getData());
        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("true",resultMap.get("isExpense").toString());
    }

    /**
     * 已有预约，消费成功
     * @throws Exception
     */
    @Test
    public void successExpense()  throws  Exception{

        CouponExpenseParam reserveParam = new CouponExpenseParam();
        reserveParam.setCouponId("73C31E2F-F87E-43B5-8328-C72DF9F9FF57");
        reserveParam.setOldCouponId("73C31E2F-F87E-43B5-8328-C72DF9F9FF57");
        reserveParam.setUseId("EBE4BAE4-7F1E-4266-A976-53320065B700");
        reserveParam.setMoney(10d);
        reserveParam.setDiscountamount(5d);
        reserveParam.setOrderId("test");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/expense";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("true",resultMap.get("isExpense").toString());
    }


    /**
     * 消费失败，消费订单、抵用与预约不符
     * @throws Exception
     */
    @Test
    public void failureExpense() throws  Exception{

        CouponExpenseParam reserveParam = new CouponExpenseParam();
        reserveParam.setCouponId("EB90245C-0EDA-4B32-92A7-42CF1CC19ED1");
        reserveParam.setOldCouponId("EB90245C-0EDA-4B32-92A7-42CF1CC19ED1");
        reserveParam.setMoney(10d);
        reserveParam.setDiscountamount(5d);
        reserveParam.setOrderId("test1212");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/expense";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();


        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        System.out.println(dto.getData());
        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("false",resultMap.get("isExpense").toString());
    }

    /**
     * 创建消费，删除之前预约
     * @throws Exception
     */
    @Test
    public void createAndRemoveExpense() throws  Exception{

        CouponExpenseParam reserveParam = new CouponExpenseParam();
        reserveParam.setCouponId("745030F7-B452-4AB0-8294-AD11674F1079");
        reserveParam.setOldCouponId("EB90245C-0EDA-4B32-92A7-42CF1CC19ED1");
        reserveParam.setMoney(10d);
        reserveParam.setDiscountamount(5d);
        reserveParam.setOrderId("test");
        reserveParam.setUserId("test");
        reserveParam.setUseType(1);
        reserveParam.setCity("0000002");

        String jsonParam = GsonUtil.toJson(reserveParam);

        String uri = "/coupon/expense";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();


        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        System.out.println(dto.getData());
        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

        Map<String,Object> resultMap = GsonUtil.fromJson(dto.getData(), Map.class);

        Assert.assertEquals("true",resultMap.get("isExpense").toString());
    }

    /**
     * 更新实际抵扣金额
     * @throws Exception
     */
    @Test
    public void updateActualamount() throws  Exception{

        CouponUseParam useParam = new CouponUseParam();
        useParam.setOrderId("test5");
        useParam.setMoney(5d);
        useParam.setUserId("啊哈");

        String jsonParam = GsonUtil.toJson(useParam);

        String uri = "/coupon/update/actualamount";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();


        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        System.out.println(dto.getData());
        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

    }

    /**
     * 更新实际抵扣金额
     * @throws Exception
     */
    @Test
    public void abandon() throws  Exception{

        CouponRequestParam useParam = new CouponRequestParam();
        useParam.setUserId("07423FBA-CE1B-4ED0-8A0C-333A920BEB0C");

        String jsonParam = GsonUtil.toJson(useParam);

        String uri = "/coupon/abandon";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonParam)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();


        ResultData  dto = GsonUtil.fromJson(content,ResultData.class);

        System.out.println(dto.getData());
        Assert.assertEquals(200, status);
        Assert.assertEquals(ServiceState.SUCCESS.code,dto.getStatus().intValue());

    }


} 
