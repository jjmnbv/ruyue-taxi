package com.szyciov.supervision.util;

import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;

/**
 * carservice:模板助手
 * Created by lzw on 2017/8/22.
 */
@Component
public class CarserviceTemplateHelper {
    @Value("${carservice-api}")
    private String carserviceApi=null;
    TemplateHelper templateHelper=new TemplateHelper();


    public <T> T dealRequest(String url, HttpMethod method, String userToken, Object entity,
                             Class<T> responseType, Object... uriVariables) throws RestClientException {
        return templateHelper.dealRequestWithToken(carserviceApi+"/"+url, method, userToken, entity, responseType, uriVariables);
    }

}
