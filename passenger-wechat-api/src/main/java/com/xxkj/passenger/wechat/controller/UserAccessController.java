package com.xxkj.passenger.wechat.controller;

import com.xxkj.passenger.wechat.Const;
import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.service.IUserAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


/**
 * 
 */
@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserAccessController {
    @Autowired
    IUserAccessService userAccessService;

    private static Logger logger = LoggerFactory.getLogger(UserAccessController.class);

    /**
     *  校验是否已注册和绑定了openId
     * */
    @RequestMapping(value = "/Verify", method = RequestMethod.GET)
    public String verifyOpenId(HttpServletResponse response, String openId) throws NoSuchAlgorithmException {
//        boolean isBindOpenId = userAccessService.verifyOpenId(openId);
//        return  isBindOpenId ? "是" : "否";
        User user = userAccessService.getUserByOpenId(openId);

        if (user != null) {
            // 用户存在，则静默登陆，创建或更新usertoken，然后跳转到首页
            Map<String, Object> res = userAccessService.login(user, Const.DELLOGIN);

            logger.info("用已注册与绑定openid，跳转首页");
            return "redirect:/home";
        }else{
            // 没绑定openId或是没注册, 则跳转到登陆绑定界面
            logger.info("没绑定openId或是没注册, 跳转到登陆绑定");
            return "redirect:/Login";
        }
    }

    @RequestMapping(value="/Login")
    public User bindUserWithOpenId(HttpServletResponse response, String userId, String openId) {
        User user = new User();
        user.setId(userId);
        user.setWechatopenid(openId);
        userAccessService.bindUserWithOpenId(user);


        // 保存usertoken到cookie中
        Cookie cookie = new Cookie("usertoken", "usertoken");
        cookie.setMaxAge(30 * 60);// 设置为30min
        cookie.setPath("/");
        System.out.println("已添加===============");
        response.addCookie(cookie);
        return user;
    }
}
