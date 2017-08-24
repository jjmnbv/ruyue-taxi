package com.xxkj.passenger.wechat.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xxkj.passenger.wechat.Const;
import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.resp.WechatWebAccessTokenResp;
import com.xxkj.passenger.wechat.service.IUserAccessService;
import com.xxkj.passenger.wechat.util.HttpsUtil;


/**
 * 
 */
@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserAccessController {

    private static Logger logger = LoggerFactory.getLogger(UserAccessController.class);
    
    private static final String WECHAT_WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    
    @Autowired
    private IUserAccessService userAccessService;
    
    @Value("${wxpub.appid}")
    private String wechatAppId;
    
    @Value("${wxpub.appsecret}")
    private String wechatAppSecrect;

    /**
     *  获取code 去换openid， 然后校验是否已注册和绑定了openId
     * */
    @RequestMapping(value = "/Verify", method = RequestMethod.GET)
    @ResponseBody
    public String getWechatCode(@RequestParam(name="code",required=false)String code,
                                @RequestParam(name="state")String state) throws NoSuchAlgorithmException{

        logger.info("-----收到公众号发送的请求:code=" + code + " state=" + state);
        
        String openId = "";

        //通过code换取网页授权openId
        if(code != null && !"".equals(code)){
            //替换字符串，获得请求URL
        	String accessTokenUrl = String.format(WECHAT_WEB_ACCESS_TOKEN_URL, wechatAppId, wechatAppSecrect, code);
        	
            //通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(accessTokenUrl, "GET", null);
            logger.info("-----get web access token result:" + response);
            
            WechatWebAccessTokenResp accessTokenResp = JSON.parseObject(response, WechatWebAccessTokenResp.class);
            
            if(accessTokenResp.getErrcode() != null && accessTokenResp.getErrcode() != 0) {
            	return "获取openid失败";
            }
            else {
            	openId = accessTokenResp.getOpenid();
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
            return "未绑定openId或用户不存在，跳转登陆界面" + openId;
        }
    }

    @RequestMapping(value="/Login")
    @ResponseBody
    public String bindUserWithOpenId(HttpServletResponse response, String phone, String smsCode, String openId) {
        userAccessService.registerAndLogin(phone, smsCode, openId);

        return "redirect to home";
    }
}
