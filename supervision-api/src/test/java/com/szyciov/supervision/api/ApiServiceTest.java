package com.szyciov.supervision.api;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.service.BaseService;
import com.szyciov.supervision.mq.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 林志伟 on 2017/7/13.
 */
public class ApiServiceTest extends SupervisionApplicationTests {


    @Autowired
    protected  MessageSender messageSender;


    /**
     * 获取现在时间
     *
     * @return 返回时间类型
     */
    public  String getNowTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 唯一的标识，相当于主键
     * @return
     */
    public String getSymbol(){
        return  "RY_"+System.currentTimeMillis()+"_"+Math.random();
    }



}
