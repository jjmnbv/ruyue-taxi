package com.xxkj.passenger.wechat.controller;

import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.service.UserAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 */
@RestController
public class UserAccessController {
    @Autowired
    UserAccessService userAccessService;

    private static Logger logger = LoggerFactory.getLogger(UserAccessController.class);

    @RequestMapping("/autoLogin")
    public String verifyOpenId(String openId){

        boolean isBindOpenId = userAccessService.verifyOpenId(openId);
        return  isBindOpenId ? "是" : "否";
    }

    @RequestMapping(value="/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User bindUserWithOpenId(String userId, String openId) {
        User user = new User();
        user.setId(userId);
        user.setWechatopenid(openId);
        userAccessService.bindUserWithOpenId(user);
        return user;
    }
}
