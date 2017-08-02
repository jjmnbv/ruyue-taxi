package com.xxkj.passenger.wechat.controller;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xxkj.passenger.wechat.dao.PubSupervisionLogDao;

/**
 * 
 */
@RestController
public class UserAccessController {

    private static Logger logger = LoggerFactory.getLogger(UserAccessController.class);


    @GetMapping("/test")
    public String test(){
        return  "test";
    }

    /**
     * 获取请求头信息
     * @param httpServletRequest
     * @return
     */
    private HashMap<String,String> getHeader(HttpServletRequest httpServletRequest){
        HashMap<String,String> map=new HashMap<>();
        return  map;
    }
}
