package com.szyciov.operate.service;

import com.szyciov.param.VehcParam;
import com.szyciov.util.TemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Created by liubangwei_lc on 2017/6/27.
 */
@Service("vehcSevice")
public class VehcSevice {

    private Logger logger = LoggerFactory.getLogger(VehcSevice.class);
    private TemplateHelper templateHelper = new TemplateHelper();

    public VehcParam getVehcById(String vehcId, String userToken) {
        return templateHelper.dealRequestWithToken("/Vehc/GetVehcById/{vehcId}", HttpMethod.GET, userToken, null, VehcParam.class, vehcId);
    }
}
