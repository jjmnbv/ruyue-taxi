package com.szyciov.supervision.token.service;

import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;
import com.szyciov.supervision.enums.RequestType;
import com.szyciov.supervision.token.request.TokenRequest;
import com.szyciov.supervision.util.BasicRequest;
import com.szyciov.supervision.util.GzwycApi;
import com.szyciov.supervision.util.Token;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by admin on 2017/7/6.
 */
@Component
public class TokenService {

    public String getToken() throws IOException {
        TokenRequest tokenRequest = new TokenRequest(CacheHelper.getCompanyId(), CacheHelper.getKey());
        BasicRequest request = new BasicRequest(JSONUtil.toJackson(tokenRequest), InterfaceType.TOKEN, CommandEnum.ACCESS, RequestType.REQ,null);

        String responseString = GzwycApi.token(request);
        Token token = JSONUtil.objectMapper.readValue(responseString, Token.class);
        return token.getToken();
    }
}
