package com.szyciov.lease.controller;

import com.szyciov.entity.Retcode;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.drivervehiclebind.CarBindRecordQueryParam;
import com.szyciov.lease.service.PubDriverVehicleBindService;
import com.szyciov.util.BaseController;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 人车绑定controller
 */
@Controller
public class PubDriverVehicleBindController extends BaseController {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleBindController.class);

    @Autowired
    private PubDriverVehicleBindService bindService;


    /**
     * 查询网约车绑定记录
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("api/driverVehicleRecord/carBindRecord")
    @ResponseBody
    public JSONObject listCarBindRecord(@RequestBody CarBindRecordQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean infoDto = bindService.listCarBindRecord(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询网约车绑定记录失败："+ GsonUtil.toJson(param),e);
        }
        return jsonObject;
    }


    /**
     * 查询出租车绑定记录
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("api/driverVehicleRecord/taxiBindRecord")
    @ResponseBody
    public JSONObject listTaxiBindRecord(@RequestBody CarBindRecordQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean infoDto = bindService.listTaxiBindRecord(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询出租车绑定记录失败："+ GsonUtil.toJson(param),e);
        }
        return jsonObject;
    }


}