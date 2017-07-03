package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.RabbitService;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.util.ApiExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 里程计算
 * Created by shikang on 2017/5/22.
 */
@Controller
public class MileageApiController extends ApiExceptionHandle {

    @Resource(name = "RabbitService")
    private RabbitService rabbitService;

    /**
     * 筛选GPS
     * @param object
     * @return
     */
    @RequestMapping(value = "api/MileageApi/UploadGps")
    @ResponseBody
    public JSONObject uploadGps(@RequestBody PubOrdergpsdata object) {
        rabbitService.sendAppgpsRabbit(object);
        return checkResult(new JSONObject());
    }

}
