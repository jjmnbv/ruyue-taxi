package com.szyciov.supervision.api;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.service.BaseApiService;
import com.szyciov.supervision.mq.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 林志伟 on 2017/7/13.
 */
public class ApiServiceTest extends SupervisionApplicationTests {


    protected  @Autowired BaseApiService baseApiService;





    /**
     * 唯一的标识，相当于主键
     * @return
     */
    public String getSymbol(){
        return  "RY_"+System.currentTimeMillis()+"_"+Math.random();
    }



}
