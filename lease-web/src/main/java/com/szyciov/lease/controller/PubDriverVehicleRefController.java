package com.szyciov.lease.controller;

import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.dto.driverVehicleBindManage.UnBindDto;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubDriverVehicleRefQueryParam;
import com.szyciov.lease.param.drivervehiclebind.CarBindInfoQueryParam;
import com.szyciov.lease.param.drivervehiclebind.TaxiBindInfoQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 人车绑定controller
 */
@Controller
public class PubDriverVehicleRefController extends BaseController {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleRefController.class);


    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 查询网约车绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("/driverVehicleBind/listCarBindinfo")
    @ResponseBody
    public JSONObject listCarBindinfo(@RequestBody CarBindInfoQueryParam param,HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        param.setPlatformtype(PlatformTypeByDb.LEASE.code);
        param.setLeaseId(user.getLeasescompanyid());
        param.setVehicletype(VehicleEnum.VEHICLE_TYPE_CAR.code);

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleBind/listCarBindinfo",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }
        return jsonObject;
    }


    /**
     * 查询绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("/driverVehicleBind/listUnBindVehicle")
    @ResponseBody
    public JSONObject listUnBindVehicle(@RequestBody CarBindInfoQueryParam param,HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);
        //租赁端
        param.setPlatformtype(PlatformTypeByDb.LEASE.code);
        //租赁公司ID
        param.setLeaseId(user.getLeasescompanyid());
        //城市
        if (StringUtils.isNotBlank(param.getqCity())) {
        	param.setCity(param.getqCity());
        } else {
        	param.setCity(param.getCity());
        }
        //未绑定标记
        param.setBoundState(BindingStateEnum.UN_BINDING.code);
        //网约车
        param.setVehicletype(VehicleEnum.VEHICLE_TYPE_CAR.code);
        //营运中
        param.setVehiclestatus(VehicleEnum.VEHICLE_STATUS_ONLINE.code);
        //归属车企
        if (StringUtils.isNotBlank(param.getqBelongLeasecompany())) {
        	param.setBelongLeasecompany(param.getqBelongLeasecompany());
        }

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleBind/listUnBindVehicle",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }
        return jsonObject;
    }

    /**
     * 返回网约车绑定的服务车型及城市
     * @return
     */
    @RequestMapping("/driverVehicleBind/listCarBindModelAndCity")
    @ResponseBody
    public JSONObject listCarBindModelAndCity(HttpServletRequest request){

        String usertoken = getUserToken(request);
        CarBindInfoQueryParam param = new CarBindInfoQueryParam();
        User user = getLoginLeUser(request);
        //租赁端
        param.setPlatformtype(PlatformTypeByDb.LEASE.code);
        //租赁公司ID
        param.setLeaseId(user.getLeasescompanyid());

        return templateHelper.dealRequestWithToken("/driverVehicleBind/listCarBindModelAndCity",
                HttpMethod.POST, usertoken, param, JSONObject.class);
    }

    @RequestMapping("/PubDriverVehicleRef/GetPubDriverVehicleRefByQuery")
    @ResponseBody
    public PageBean getPubDriverVehicleRefByQuery(@RequestBody PubDriverVehicleRefQueryParam pubDriverVehicleRefQueryParam, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        if(pubDriverVehicleRefQueryParam.getKey()!=null && !pubDriverVehicleRefQueryParam.getKey().equals("")){

        }else{
            pubDriverVehicleRefQueryParam.setKey(request.getParameter("cityId"));
        }
        pubDriverVehicleRefQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
        return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetPubDriverVehicleRefByQuery", HttpMethod.POST, userToken,
                pubDriverVehicleRefQueryParam,PageBean.class);
    }

    /**
     * 网约车绑定
     * @param bindDto
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/PubDriverVehicleRef/carBin")
    @ResponseBody
    public JSONObject carBin(@RequestBody BindDto bindDto, HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        bindDto.setCreater(user.getId());
        bindDto.setDrivertype(VehicleEnum.VEHICLE_TYPE_CAR.code);
        return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/saveBind", HttpMethod.POST, usertoken,
                bindDto,JSONObject.class);
    }


    /**
     * 网约车解绑
     * @param unBindDto     网约车解绑
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/PubDriverVehicleRef/carUnBin")
    @ResponseBody
    public JSONObject carUnBin(@RequestBody UnBindDto unBindDto, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        unBindDto.setCreater(user.getId());
        unBindDto.setDrivertype(VehicleEnum.VEHICLE_TYPE_CAR.code);
        return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/unBind", HttpMethod.POST, usertoken,
                unBindDto,JSONObject.class);
    }

    /**
     * 是否存在网约车有效订单
     * @param driverId     司机ID
     * @return
     * @throws IOException
     */
    @RequestMapping("/PubDriverVehicleRef/isExistsValidCarOrder")
    @ResponseBody
    public JSONObject isExistsValidCarOrder(@RequestParam String driverId, HttpServletRequest request) throws IOException {
        String usertoken = getUserToken(request);

        return templateHelper.dealRequestWithToken  ("/PubDriverVehicleRef/isExistsValidCarOrder/"+driverId,
                HttpMethod.POST, usertoken,null, JSONObject.class);
    }


    /**
     * 出租车解绑
     * @param unBindDto   解绑绑定dto
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/PubDriverVehicleRef/taxiUnBin")
    @ResponseBody
    public JSONObject taxiUnBin(@RequestBody UnBindDto unBindDto, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        unBindDto.setCreater(user.getId());
        unBindDto.setDrivertype(VehicleEnum.VEHICLE_TYPE_TAXI.code);
        return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/unBind", HttpMethod.POST, usertoken,
                unBindDto,JSONObject.class);
    }

    /**
     * 出租车绑定
     * @param bindDto   绑定dto
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/PubDriverVehicleRef/taxiBin")
    @ResponseBody
    public JSONObject taxiBin(@RequestBody BindDto bindDto, HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        bindDto.setCreater(user.getId());
        bindDto.setDrivertype(VehicleEnum.VEHICLE_TYPE_TAXI.code);
        return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/saveBind", HttpMethod.POST, usertoken,
                bindDto,JSONObject.class);
    }



    /**
     * 返回出租车城市
     * @return
     */
    @RequestMapping("/driverVehicleBind/listTaxiVehicleRefCity")
    @ResponseBody
    public JSONObject listTaxiVehicleRefCity(HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        TaxiBindInfoQueryParam param = new TaxiBindInfoQueryParam();
        param.setLeaseId(user.getLeasescompanyid());
        param.setPlatformtype(PlatformTypeByDb.LEASE.code);

        return templateHelper.dealRequestWithToken("/driverVehicleBind/listTaxiVehicleRefCity",
                HttpMethod.POST, usertoken, param, JSONObject.class);
    }


    /**
     * 查询出租车绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("/driverVehicleBind/listTaxiVehicleRef")
    @ResponseBody
    public JSONObject listTaxiVehicleRef(@RequestBody TaxiBindInfoQueryParam param, HttpServletRequest request){

        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);
        //租赁端
        param.setPlatformtype(PlatformTypeByDb.LEASE.code);
        //租赁公司ID
        param.setLeaseId(user.getLeasescompanyid());

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleBind/listTaxiVehicleRef",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }
        return jsonObject;
    }



    /**
     * 查询车辆对应绑定司机列表
     * @param param   车辆ID
     * @return
     */
    @RequestMapping(value = "/driverVehicleBind/listAllVehicleBindInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listAllVehicleBindInfo(@RequestBody TaxiBindInfoQueryParam param, HttpServletRequest request){

        String usertoken = getUserToken(request);


        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverVehicleBind/listAllVehicleBindInfo",
                HttpMethod.POST, usertoken, param, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }
        return null;
    }


    /**
     * 查询出租车绑定司机列表
     * @param vehicleid   车辆id
     * @return
     */
    @RequestMapping(value = "/driverVehicleBind/listTaxiBindDriver/{vehicleid}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listTaxiBindDriver(@PathVariable String vehicleid, HttpServletRequest request){

        String usertoken = getUserToken(request);

        return templateHelper.dealRequestWithToken  ("/driverVehicleBind/listTaxiBindDriver/"+vehicleid,
                HttpMethod.POST, usertoken,null, JSONObject.class);
    }

    /**
     * 查询出租车指定交班司机
     * @param vehicleid   车辆id
     * @return
     */
    @RequestMapping(value = "/driverVehicleBind/listAssignDriver/{vehicleid}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listAssignDriver(@PathVariable String vehicleid, HttpServletRequest request){

        String usertoken = getUserToken(request);

        return templateHelper.dealRequestWithToken  ("/driverVehicleBind/listAssignDriver/"+vehicleid,
                HttpMethod.POST, usertoken,null, JSONObject.class);
    }



    @RequestMapping(value = "/driverVehicleBind/carbind/Index")
    public String carbindIndex() {
        return "resource/drivervehiclebind/carbind/index";
    }

    @RequestMapping(value = "/driverVehicleBind/taxibind/Index")
    public String taxibind() {
        return "resource/drivervehiclebind/taxibind/index";
    }
    
    /**
     * 返回归属车企
     * @return
     */
    @RequestMapping("/driverVehicleBind/getBelongLeasecompany")
    @ResponseBody
    public JSONObject getBelongLeasecompany(HttpServletRequest request){

        String usertoken = getUserToken(request);
        return templateHelper.dealRequestWithToken("/driverVehicleBind/getBelongLeasecompany",
                HttpMethod.POST, usertoken, null, JSONObject.class);
    }

}