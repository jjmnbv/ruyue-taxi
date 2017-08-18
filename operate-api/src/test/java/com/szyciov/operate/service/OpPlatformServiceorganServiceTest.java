package com.szyciov.operate.service;

import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.op.param.OpPlatformServiceorganQueryParam;
import com.szyciov.util.JUnit4ClassRunner;
import com.szyciov.util.PageBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/9 0009.
 */
@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations="classpath:applicationContext.xml"),
        @ContextConfiguration(name="child", locations="classpath:yc-servlet.xml"),
})
@Transactional
public class OpPlatformServiceorganServiceTest {
    @Autowired
    private OpPlatformServiceorganService opPlatformServiceorganService;
    @Test
    public void getServiceorganByQuery() throws Exception {
        OpPlatformServiceorganQueryParam opPlatformServiceorganQueryParam=new OpPlatformServiceorganQueryParam();
        opPlatformServiceorganQueryParam.setiDisplayLength(10);
        opPlatformServiceorganQueryParam.setiDisplayStart(0);
        PageBean pageBean=opPlatformServiceorganService.page(opPlatformServiceorganQueryParam);
        System.out.println(pageBean);
        Assert.assertNotNull(pageBean);
    }

    public OpPlatformServiceorgan add(){
        OpPlatformServiceorgan opPlatformServiceorgan=new OpPlatformServiceorgan();
        opPlatformServiceorgan.setAddress("440001");
        opPlatformServiceorgan.setContactphone("18826252137");
        Map<String,String> map=opPlatformServiceorganService.create(opPlatformServiceorgan);
        if(!map.get("status").equals("success")){
            return  null;
        }else {
            return  opPlatformServiceorgan;
        }
    }

    @Test
    public void create(){
        OpPlatformServiceorgan opPlatformServiceorgan=add();
        Assert.assertNotNull(opPlatformServiceorgan);
    }

    @Test
    public void update(){
        OpPlatformServiceorgan opPlatformServiceorgan=add();
        if(opPlatformServiceorgan==null){
            return;
        }
        opPlatformServiceorgan.setAddress("440002");
        Map<String,String> map=opPlatformServiceorganService.update(opPlatformServiceorgan);
        Assert.assertEquals(map.get("status"),"success");
    }

    @Test
    public void getById(){
        OpPlatformServiceorgan opPlatformServiceorgan=add();
        if(opPlatformServiceorgan==null){
            return;
        }
        OpPlatformServiceorganVo  opPlatformServiceorganVo=opPlatformServiceorganService.getById(opPlatformServiceorgan.getId());
        System.out.println("获取机构："+opPlatformServiceorganVo);
        Assert.assertNotNull(opPlatformServiceorganVo);
    }


    @Test
    public void delete(){
        OpPlatformServiceorgan opPlatformServiceorgan=add();
        if(opPlatformServiceorgan==null){
            return;
        }
        Map<String,String> map=opPlatformServiceorganService.delete(opPlatformServiceorgan.getId());
        Assert.assertEquals(map.get("status"),"success");
    }



    @Test
    public void exportExcel(){
        OpPlatformServiceorganQueryParam opPlatformServiceorganQueryParam=new OpPlatformServiceorganQueryParam();
        List<OpPlatformServiceorganVo> list= opPlatformServiceorganService.exportExcel(opPlatformServiceorganQueryParam);
        Assert.assertNotNull(list);
    }

}