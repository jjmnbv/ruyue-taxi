package com.xxkj.passenger.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xxkj.passenger.wechat.Const;
import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.service.IUserAccessService;
import com.xxkj.passenger.wechat.util.HttpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "home";
    }

    /**
     *  获取code 去换openid， 然后校验是否已注册和绑定了openId
     * */
    @RequestMapping(value = "/Verify", method = RequestMethod.GET)
    @ResponseBody
    public String getWechatCode(@RequestParam(name="code",required=false)String code,
                                @RequestParam(name="state")String state) throws NoSuchAlgorithmException{

        logger.info("-----收到公众号发送的请求:code=" + code + " state=" + state);

        String APPID = "wx7930eb47d68e7019";
        String SECRET = "";
        String CODE = code;
        String openId = "";


//        return "-----收到公众号发送的请求:code=" + code + " state=" + state;

        //通过code换取网页授权openId
        if(code != null || !(code.equals(""))){


            //替换字符串，获得请求URL
            String token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret="+ SECRET +"&code="+CODE+"&grant_type=authorization_code ";
            System.out.println("----------------------------token为："+token);
            //通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(token, "GET", null);
            JSONObject jsonObject = JSON.parseObject(response);
            System.out.println("jsonObject------"+jsonObject);
            if (null != jsonObject) {
                try {

//                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    System.out.println("openId-------------------------"+openId);

                } catch (JSONException e) {

                }
            }
        }


        User user = userAccessService.getUserByOpenId(openId);

        if (user != null) {
            // 用户存在，则静默登陆，创建或更新usertoken，然后跳转到首页
            Map<String, Object> res = userAccessService.login(user, Const.DELLOGIN);

            logger.info("用已注册与绑定openid，跳转首页");
//            return "redirect:/home";
            return "静默登陆成功，跳转到首页!";
        }else{
            // 没绑定openId或是没注册, 则跳转到登陆绑定界面
            logger.info("没绑定openId或是没注册, 跳转到登陆绑定");
//            return "redirect:/Login";
            return "未绑定openId或用户不存在，跳转登陆界面";
        }
    }

    @RequestMapping(value="/Login")
    @ResponseBody
    public String bindUserWithOpenId(HttpServletResponse response, String phone, String smsCode, String openId) {
        userAccessService.registerAndLogin(phone, smsCode, openId);

        return "redirect to home";
    }
}
