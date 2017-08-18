package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.util.JUnit4ClassRunner;
import com.szyciov.util.PageBean;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 先增加，后查询，更新，....最后执行删除，保证不会有多余数据产生
 * Created by lzw on 2017/8/14.
 */
@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations="classpath:applicationContext.xml"),
        @ContextConfiguration(name="child", locations="classpath:yc-servlet.xml"),
})
@Transactional
public class OpPlatformStagnationServiceTest {

    @Autowired
    private OpPlatformStagnationService opPlatformStagnationService;

    /**
     * 增加
     * @return
     */
    public OpPlatformStagnation add(){
        OpPlatformStagnation opPlatformStagnation=new OpPlatformStagnation();
        opPlatformStagnation.setCity("广州市11");
        opPlatformStagnation.setContactaddress("广东广州");
        opPlatformStagnation.setParentad("广州交通集团");
        Map<String,String> map=opPlatformStagnationService.create(opPlatformStagnation);
        if(!map.get("status").equals("success")){
            return  null;
        }else{
            return opPlatformStagnation;
        }

    }

    @Test
    public void create() throws Exception {
        OpPlatformStagnation opPlatformStagnation=add();
        Assert.assertNotNull(opPlatformStagnation);
    }

    @Test
    public void page() throws Exception {
        OpPlatformStagnationQueryParam opPlatformStagnationQueryParam=new OpPlatformStagnationQueryParam();
        opPlatformStagnationQueryParam.setiDisplayLength(10);
        opPlatformStagnationQueryParam.setiDisplayStart(0);
        PageBean pageBean=opPlatformStagnationService.page(opPlatformStagnationQueryParam);
        System.out.println(pageBean);
        Assert.assertNotNull(pageBean);
    }



    @Test
    public void update() throws Exception {
        OpPlatformStagnation opPlatformStagnation=add();
        if(opPlatformStagnation==null){
            return;
        }
        opPlatformStagnation.setCity("武汉市");
        Map<String,String> map=opPlatformStagnationService.update(opPlatformStagnation);
        Assert.assertEquals(map.get("status"),"success");
    }



    @Test
    public void getById() throws Exception {
        OpPlatformStagnation opPlatformStagnation=add();
        if(opPlatformStagnation==null){
            return;
        }
        opPlatformStagnation=opPlatformStagnationService.getById(opPlatformStagnation.getId());
        System.out.println("获取数据byID："+opPlatformStagnation);
        Assert.assertNotNull(opPlatformStagnation);
    }

    @Test
    public void exportExcel() throws Exception {
        OpPlatformStagnationQueryParam opPlatformStagnationQueryParam=new OpPlatformStagnationQueryParam();
        List<OpPlatformStagnation> list= opPlatformStagnationService.exportExcel(opPlatformStagnationQueryParam);
        Assert.assertNotNull(list);
    }

    @Test
    public void delete() throws Exception {
        OpPlatformStagnation opPlatformStagnation=add();
        Map<String,String> map=opPlatformStagnationService.delete(opPlatformStagnation.getId());
        Assert.assertEquals(map.get("status"),"success");
    }

}