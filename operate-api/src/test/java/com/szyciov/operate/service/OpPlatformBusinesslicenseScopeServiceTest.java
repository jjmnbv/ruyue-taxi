package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;
import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.operate.dao.OpPlatformBusinesslicenseScopeDao;
import com.szyciov.util.JUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lzw on 2017/8/15.
 */
@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations="classpath:applicationContext.xml"),
        @ContextConfiguration(name="child", locations="classpath:yc-servlet.xml"),
})
@Transactional
public class OpPlatformBusinesslicenseScopeServiceTest {
    @Autowired
    private OpPlatformBusinesslicenseScopeService opPlatformBusinesslicenseScopeService;
    @Test
    public void deleteOther() throws Exception {
        OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=add();
        if(opPlatformBusinesslicenseScope==null){
            return;
        }
        Map<String,String> map=opPlatformBusinesslicenseScopeService.deleteOther(opPlatformBusinesslicenseScope.getBusinesslicenseid(),null);
        Assert.assertEquals(map.get("status"),"success");

    }

    @Test
    public void create() throws Exception {
        OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=add();
        Assert.assertNotNull(opPlatformBusinesslicenseScope);
    }


    public OpPlatformBusinesslicenseScope add(){
        OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=new OpPlatformBusinesslicenseScope();
        opPlatformBusinesslicenseScope.setOperationarea("广州市");
        opPlatformBusinesslicenseScope.setBusinesslicenseid("jdfkasdadgjgutuwegrui");
        Map<String,String> map=opPlatformBusinesslicenseScopeService.create(opPlatformBusinesslicenseScope);
        if(map.get("status").equals("success")){
            return  opPlatformBusinesslicenseScope;
        }else {
            return  null;
        }
    }
    @Test
    public void find(){
        OpPlatformBusinesslicenseScope opPlatformBusinesslicenseScope=add();
        if(opPlatformBusinesslicenseScope==null){
            return;
        }
        opPlatformBusinesslicenseScope=opPlatformBusinesslicenseScopeService.find(opPlatformBusinesslicenseScope.getBusinesslicenseid(),opPlatformBusinesslicenseScope.getOperationarea());
        Assert.assertNotNull(opPlatformBusinesslicenseScope);

    }
}