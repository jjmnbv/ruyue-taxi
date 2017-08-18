package com.szyciov.carservice.service;

import com.szyciov.util.JUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/8/11 0011.
 */
@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations="classpath:applicationContext.xml"),
        @ContextConfiguration(name="child", locations="classpath:yc-servlet.xml"),
})
public class PubInfoServiceTest {
    @Autowired
    private PubInfoService pubInfoService;
    @Test
    public void getCityIdByName() throws Exception {
        Map<String,String> map=pubInfoService.getCityIdByName("广州市");
        System.out.println(map);
        Assert.assertEquals(map.get("status"),"success");
    }
    @Test
    public void getSearchCitySelect() throws Exception{
        List<Map<String,String>> list=pubInfoService.getSearchCitySelect(null);
        System.out.println(list);
        Assert.assertNotNull(list);
    }

}