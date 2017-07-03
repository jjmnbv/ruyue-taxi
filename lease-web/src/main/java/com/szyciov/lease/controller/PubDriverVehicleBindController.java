package com.szyciov.lease.controller;

import com.szyciov.entity.Retcode;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.drivervehiclebind.CarBindRecordQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 人车绑定controller
 */
@Controller
public class PubDriverVehicleBindController extends BaseController {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleBindController.class);


    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 查询车辆绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("/driverVehicleRecord/carBindRecord")
    @ResponseBody
    public JSONObject listCarBindRecord(@RequestBody CarBindRecordQueryParam param, HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        param.setPlatformType(PlatformTypeByDb.LEASE.code);
        param.setLeaseId(user.getLeasescompanyid());

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleRecord/carBindRecord",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }

        return jsonObject;
    }


    /**
     * 查询出租车绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("/driverVehicleRecord/taxiBindRecord")
    @ResponseBody
    public JSONObject listTaxiBindRecord(@RequestBody CarBindRecordQueryParam param, HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        param.setPlatformType(PlatformTypeByDb.LEASE.code);
        param.setLeaseId(user.getLeasescompanyid());

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleRecord/taxiBindRecord",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }

        return jsonObject;
    }


    /**
     * 网约车绑定 司机操作记录
     * @return
     */
    @RequestMapping(value = "/driverVehicleRecord/car/driverRecord")
    public String carBindDriverRecord() {
        return "resource/drivervehiclebind/carbind/driverRecord";
    }

    /**
     * 网约车绑定 车辆操作记录
     * @return
     */
    @RequestMapping(value = "/driverVehicleRecord/car/vehicleRecord")
    public String carBindVehicleRecord() {
        return "resource/drivervehiclebind/carbind/vehicleRecord";
    }

    /**
     * 出租车绑定 车辆操作记录
     * @return
     */
    @RequestMapping(value = "/driverVehicleRecord/taxi/vehicleRecord/{vehicleid}/{platenoStr}")
    public ModelAndView taxiBindVehicleRecord(@PathVariable String vehicleid,@PathVariable String platenoStr) {

        ModelAndView md = new ModelAndView();
        md.addObject("vehicleid",vehicleid);
        md.addObject("platenoStr",platenoStr);

        md.setViewName("resource/drivervehiclebind/taxibind/vehicleRecord");

        return md;
    }



}