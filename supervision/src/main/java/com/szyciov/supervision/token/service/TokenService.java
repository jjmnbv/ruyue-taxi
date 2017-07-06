package com.szyciov.supervision.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szyciov.supervision.TaxiExecption;
import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;
import com.szyciov.supervision.enums.RequestType;
import com.szyciov.supervision.token.domain.TokenRequest;
import com.szyciov.supervision.util.BasicRequest;
import com.szyciov.supervision.util.GzwycApi;
import com.szyciov.supervision.util.GzwycResult;
import com.szyciov.supervision.util.Token;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by admin on 2017/7/6.
 */
@Component
public class TokenService {

    public GzwycResult getToken() throws IOException {
        TokenRequest tokenRequest = new TokenRequest(CacheHelper.getCompanyId(), CacheHelper.getKey());
        BasicRequest request = new BasicRequest(JSONUtil.toJackson(tokenRequest), InterfaceType.TOKEN, CommandEnum.ACCESS, RequestType.REQ,null);

        String responseString = GzwycApi.token(request);
        Token token = JSONUtil.objectMapper.readValue(responseString, Token.class);
        GzwycResult result = new GzwycResult();
        result.setStatus(200);
        result.setContent(token.getToken());
        return result;
    }
}
