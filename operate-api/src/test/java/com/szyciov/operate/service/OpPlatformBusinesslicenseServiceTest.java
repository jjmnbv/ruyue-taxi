package com.szyciov.operate.service;

import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.param.OpPlatformBusinesslicenseQueryParam;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lzw on 2017/8/16.
 */
@RunWith(JUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations="classpath:applicationContext.xml"),
        @ContextConfiguration(name="child", locations="classpath:yc-servlet.xml"),
})
@Transactional
public class OpPlatformBusinesslicenseServiceTest {
    @Autowired
    private OpPlatformBusinesslicenseService opPlatformBusinesslicenseService;
    @Test
    public void page() throws Exception {
        OpPlatformBusinesslicenseQueryParam opPlatformBusinesslicenseQueryParam=new OpPlatformBusinesslicenseQueryParam();
        opPlatformBusinesslicenseQueryParam.setiDisplayLength(10);
        opPlatformBusinesslicenseQueryParam.setiDisplayStart(0);
        PageBean pageBean=opPlatformBusinesslicenseService.page(opPlatformBusinesslicenseQueryParam);
//        System.out.println(pageBean);
        Assert.assertNotNull(pageBean);
    }

    /**
     * 添加
     * @return
     */
    public OpPlatformBusinesslicenseDto add(){
        OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto=new OpPlatformBusinesslicenseDto();
        opPlatformBusinesslicenseDto.setState(1);
        opPlatformBusinesslicenseDto.setAddress("0000001111");
        opPlatformBusinesslicenseDto.setOrganization("ddasdad");
        List<String> operationareas=new ArrayList<>();
        operationareas.add("广州市");
        operationareas.add("武汉市");
        opPlatformBusinesslicenseDto.setOperationareas(operationareas);
        Map<String,String> map=opPlatformBusinesslicenseService.create(opPlatformBusinesslicenseDto);
        if(!map.get("status").equals("success")){
            return  null;
        }else{
            return opPlatformBusinesslicenseDto;
        }
    }

    @Test
    public void create() throws Exception {
        OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto=add();
        Assert.assertNotNull(opPlatformBusinesslicenseDto);
    }

    @Test
    public void update() throws Exception {
        OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto=add();
        if(opPlatformBusinesslicenseDto==null){
            return;
        }
        List<String> operationareas=new ArrayList<>();
        operationareas.add("北京市");
        operationareas.add("广州市");
        opPlatformBusinesslicenseDto.setOperationareas(operationareas);
        Map<String,String> map=opPlatformBusinesslicenseService.update(opPlatformBusinesslicenseDto);
        Assert.assertEquals(map.get("status"),"success");
    }

    /**
     * 删除
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto=add();
        if(opPlatformBusinesslicenseDto==null){
            return;
        }
        Map<String,String> map=opPlatformBusinesslicenseService.delete(opPlatformBusinesslicenseDto.getId());
        Assert.assertEquals(map.get("status"),"success");
    }

    @Test
    public void getById() throws Exception {
        OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto=add();
        if(opPlatformBusinesslicenseDto==null){
            return;
        }
        OpPlatformBusinesslicenseVo opPlatformBusinesslicenseVo= opPlatformBusinesslicenseService.getById(opPlatformBusinesslicenseDto.getId());
        Assert.assertNotNull(opPlatformBusinesslicenseVo);

    }

    @Test
    public void exportExcel() throws Exception {
        OpPlatformBusinesslicenseQueryParam opPlatformBusinesslicenseQueryParam=new OpPlatformBusinesslicenseQueryParam();
        List<OpPlatformBusinesslicenseVo> list= opPlatformBusinesslicenseService.exportExcel(opPlatformBusinesslicenseQueryParam);
//        System.out.println(list);
        Assert.assertNotNull(list);
    }

}