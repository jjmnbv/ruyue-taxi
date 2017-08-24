package com.szyciov.supervision.api.service;

import java.io.IOException;

import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.enums.RequestType;
import com.szyciov.supervision.api.request.TokenRequest;
import com.szyciov.supervision.api.request.BasicRequest;
import com.szyciov.supervision.util.GzwycApiUtil;
import com.szyciov.supervision.api.responce.HttpContent;
import com.szyciov.supervision.api.responce.Token;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * Created by admin on 2017/7/6.
 */
@Component
public class TokenService {
    private static Logger logger = LoggerFactory.getLogger(TokenService.class);
    //保存token
    private static String ACCESS_TOKEN;

    @Autowired
    private GzwycApiUtil gzwycApiUtil;

    public   String getToken() throws IOException {

        if(ACCESS_TOKEN!=null){
            return  ACCESS_TOKEN;
        }
        TokenRequest tokenRequest = new TokenRequest(CacheHelper.getCompanyId(), CacheHelper.getKey());
        BasicRequest request = new BasicRequest(JSONUtil.toJackson(tokenRequest), InterfaceType.TOKEN, CommandEnum.ACCESS, RequestType.REQ,null);
        HttpContent httpContent = gzwycApiUtil.token(request);

        if(httpContent.getStatus() != 200){
            logger.error("API_INFO:获取token失败|状态码:{}|返回内容:{}",httpContent.getStatus(),httpContent.getContent());
            return  null;
        }
        Token token = JSONUtil.objectMapper.readValue(httpContent.getContent(), Token.class);
        ACCESS_TOKEN=token.getToken();
        logger.debug("API_INFO:获取token成功|token:{}",ACCESS_TOKEN);
        return ACCESS_TOKEN;
    }

    public static void clear(){
        ACCESS_TOKEN=null;
    }
}