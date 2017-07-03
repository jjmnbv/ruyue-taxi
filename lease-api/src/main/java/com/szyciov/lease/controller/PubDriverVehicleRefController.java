package com.szyciov.lease.controller;

import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.dto.driverVehicleBindManage.UnBindDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.City;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.lease.dto.drivervehiclebind.car.CarBindInfoDto;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.drivervehiclebind.CarBindInfoQueryParam;
import com.szyciov.lease.param.drivervehiclebind.TaxiBindInfoQueryParam;
import com.szyciov.lease.service.PubDriverService;
import com.szyciov.lease.service.PubDriverVehicleRefService;
import com.szyciov.lease.service.PubVehicleService;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人车绑定service
 */
@Controller
public class PubDriverVehicleRefController {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleRefController.class);


    @Autowired
    private PubDriverVehicleRefService refService;

    @Autowired
    private PubVehicleService vehicleService;

    @Autowired
    private PubDriverService driverService;

    /**
     * 查询绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping("api/driverVehicleBind/listCarBindinfo")
    @ResponseBody
    public JSONObject listCarBindinfo(@RequestBody CarBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean infoDto = refService.listCarBindinfo(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询绑定信息列表："+ GsonUtil.toJson(param),e);
        }

        return jsonObject;
    }

    /**
     * 返回网约车绑定的服务车型及城市
     * @return
     */
    @RequestMapping("api/driverVehicleBind/listCarBindModelAndCity")
    @ResponseBody
    public JSONObject listCarBindModelAndCity(@RequestBody CarBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            List<CarBindInfoDto> infoDto = refService.listCarBindModelAndCity(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONArray.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("返回网约车绑定的服务车型及城市失败！",e);
        }

        return jsonObject;
    }

    /**
     * 返回出租车绑定城市
     * @return
     */
    @RequestMapping("api/driverVehicleBind/listTaxiVehicleRefCity")
    @ResponseBody
    public JSONObject listTaxiVehicleRefCity(@RequestBody TaxiBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();
        param.setVehicletype(VehicleEnum.VEHICLE_TYPE_TAXI.code);
        try {
            List<City> infoDto = refService.listVehicleRefCity(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONArray.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("返回网约车绑定的服务车型及城市失败！",e);
        }

        return jsonObject;
    }





    /**
     * 查询未绑定网约车信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping(value = "api/driverVehicleBind/listUnBindVehicle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listUnBindVehicle(@RequestBody CarBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean infoDto = refService.listUnBindVehicleByQuery(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询未绑定网约车信息列表："+ GsonUtil.toJson(param),e);
        }

        return jsonObject;
    }


    /**
     * 查询车辆对应绑定司机列表
     * @param param   车辆ID
     * @return
     */
    @RequestMapping(value = "api/driverVehicleBind/listAllVehicleBindInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listAllVehicleBindInfo(@RequestBody TaxiBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            List<VehicleBindInfoDto> list = refService.listAllVehicleBindInfo(param.getVehicleId());
            PageBean page = new PageBean();
            page.setAaData(list);
            page.setiTotalDisplayRecords(10);
            page.setiTotalRecords(10);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(page));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询车辆对应绑定司机列表失败--vehicleid：{}", GsonUtil.toJson(param),e);
        }

        return jsonObject;
    }


    /**
     * 保存绑定关系
     * @param bindDto
     * @return
     * @throws IOException
     */
    @RequestMapping("api/PubDriverVehicleRef/saveBind")
    @ResponseBody
    public JSONObject saveBind(@RequestBody BindDto bindDto) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try{
            //如果是网约车绑定
            if(VehicleEnum.VEHICLE_TYPE_CAR.code.equals(bindDto.getDrivertype())){
                String erroMsg = null;
                if((erroMsg=this.verifyCarBind(bindDto))!=null){
                    jsonObject.put("message",erroMsg);
                    jsonObject.put("status",Retcode.FAILED.code);
                    return jsonObject;
                }
                //设置绑定状态
                bindDto.setBindStatus(BindingStateEnum.BINDING.code);
                this.refService.saveCarBind(bindDto);
            }else{
                String erroMsg = null;
                if((erroMsg=this.verifyTaxiBind(bindDto))!=null){
                    jsonObject.put("message",erroMsg);
                    jsonObject.put("status",Retcode.FAILED.code);
                    return jsonObject;
                }
                //设置绑定状态
                bindDto.setBindStatus(BindingStateEnum.BINDING.code);
                this.refService.saveTaxiBind(bindDto);
            }
            jsonObject.put("message","绑定成功！");
            jsonObject.put("status",Retcode.OK.code);

        }catch (Exception e){
            jsonObject.put("message","绑定失败，请稍后再试！");
            jsonObject.put("status",Retcode.FAILED.code);
            logger.error("人车绑定失败-----binddto:{}",GsonUtil.toJson(bindDto),e);
        }
        return jsonObject;
    }

    /**
     * 验证出租车绑定
     * @param bindDto
     * @return
     */
    private String verifyTaxiBind(BindDto bindDto){
        PubVehicle vehicle = vehicleService.getVehicleById(bindDto.getVehicleid());
        if(vehicle==null || VehicleEnum.VEHICLE_STATUS_OFFLINE.code.equals(vehicle.getVehicleStatus())){
            return "当前车辆不可用，请选择其他车辆！";
        }
        PubDriver driver = driverService.getByPubDriverId(bindDto.getDriverID());
        if(driver==null || DriverEnum.JOB_STATUS_DIMISSION.code.equals(driver.getJobStatus())){
            return "当前司机已离职，请选择其他司机！";
        }
        if(refService.listDriverBindInfo(bindDto.getDriverID()).size()>0){
            return "当前资源不可用，请选择其他司机!";
        }
        if(!vehicle.getCity().equals(driver.getCity())){
        	return "司机与车辆登记城市不一致，不可绑定！";
        }
        if(!vehicle.getBelongleasecompany().equals(driver.getBelongleasecompany())){
        	return "司机与车辆归属车企不一致，不可绑定！";
        }
        return null;
    }

    /**
     * 验证网约车绑定
     * @param bindDto
     * @return
     */
    private String verifyCarBind(BindDto bindDto){
        if(refService.isExistsBind(bindDto.getVehicleid(),bindDto.getDriverID())>0){
            return "该车辆或司机已存在绑定关系，请解除绑定关系后在试！";
        }
        PubVehicle vehicle = vehicleService.getVehicleById(bindDto.getVehicleid());
        if(vehicle==null || VehicleEnum.VEHICLE_STATUS_OFFLINE.code.equals(vehicle.getVehicleStatus())){
            return "当前车辆不可用，请选择其他车辆！";
        }
        PubDriver driver = driverService.getByPubDriverId(bindDto.getDriverID());
        if(driver==null || DriverEnum.JOB_STATUS_DIMISSION.code.equals(driver.getJobStatus())){
            return "当前司机已离职，请选择其他司机！";
        }
        if(!vehicle.getCity().equals(driver.getCity())){
        	return "车辆与司机登记城市不一致，不可绑定！";
        }
        if(!vehicle.getBelongleasecompany().equals(driver.getBelongleasecompany())){
        	return "车辆与司机归属车企不一致，不可绑定！";
        }
        return null;
    }


    /**
     * 人车解绑
     * @param unBindDto     人车解绑
     * @return
     * @throws IOException
     */
    @RequestMapping("api/PubDriverVehicleRef/unBind")
    @ResponseBody
    public JSONObject unBind(@RequestBody UnBindDto unBindDto) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try{
            if(VehicleEnum.VEHICLE_TYPE_CAR.code.equals(unBindDto.getDrivertype())){
                //设置绑定状态
                this.refService.unCarBind(unBindDto);
                jsonObject.put("status",Retcode.OK.code);
                jsonObject.put("message","解绑成功！");
            }else{
                String message = refService.verifyTaxiUnBind(unBindDto);
                if(message==null){
                    //设置绑定状态
                    this.refService.unTaxiBind(unBindDto);
                    jsonObject.put("message","解绑成功");
                    jsonObject.put("status",Retcode.OK.code);
                }else {
                    jsonObject.put("message", message);
                    jsonObject.put("status", Retcode.FAILED.code);
                }
            }

        }catch (Exception e){
            jsonObject.put("message","解绑失败，请稍后再试！");
            jsonObject.put("status",Retcode.EXCEPTION.code);
            logger.error("人车绑定失败-----binddto:{}",GsonUtil.toJson(unBindDto),e);
        }
        return jsonObject;
    }


    /**
     * 是否存在网约车有效订单
     * @param driverId     司机ID
     * @return
     * @throws IOException
     */
    @RequestMapping("api/PubDriverVehicleRef/isExistsValidCarOrder/{driverId}")
    @ResponseBody
    public JSONObject isExistsValidCarOrder(@PathVariable String driverId) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("status",Retcode.OK.code);
            jsonObject.put("data",refService.isExistsValidCarOrder(driverId));
        }catch (Exception e){
            jsonObject.put("status",Retcode.FAILED.code);
            logger.error("查询网约车有效订单失败-----driverId:{}",driverId,e);
        }
        return jsonObject;
    }



    /**
     * 查询出租车绑定信息列表
     * @param param   查询条件对象
     * @return
     */
    @RequestMapping(value = "api/driverVehicleBind/listTaxiVehicleRef", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listTaxiVehicleRef(@RequestBody TaxiBindInfoQueryParam param){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean infoDto = refService.listTaxiVehicleRef(param);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONObject.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询出租车绑定信息列表："+ GsonUtil.toJson(param),e);
        }

        return jsonObject;
    }

    /**
     * 查询出租车绑定司机列表
     * @param vehicleid   查询条件对象
     * @return
     */
    @RequestMapping(value = "api/driverVehicleBind/listTaxiBindDriver/{vehicleid}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listTaxiBindDriver(@PathVariable String vehicleid){

        JSONObject jsonObject = new JSONObject();

        try {

            //获取全部司机
            List<VehicleBindInfoDto> list = refService.listAllVehicleBindInfo(vehicleid);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data",  list);
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询出租车绑定司机列表："+ vehicleid,e);
        }

        return jsonObject;
    }



    /**
     * 查询出租车指定交班司机
     * @param vehicleid   查询条件对象
     * @return
     */
    @RequestMapping(value = "api/driverVehicleBind/listAssignDriver/{vehicleid}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listAssignDriver(@PathVariable String vehicleid){

        JSONObject jsonObject = new JSONObject();

        try {
            //获取在线司机
            List<VehicleBindInfoDto> list = refService.listVehicleBindInfoOfOnline(vehicleid);

            //如果没有
            if(list==null || list.size()<1){
                //获取全部司机
                list = refService.listAllVehicleBindInfo(vehicleid);
                //返回存在有效订单的司机
                VehicleBindInfoDto dto = refService.countValidTaxiOrder(list);
                if(dto!=null){
                    //重置选项
                    list = new ArrayList<>();
                    list.add(dto);
                }
            }

            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data",  list);
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询出租车绑定司机列表："+ vehicleid,e);
        }

        return jsonObject;
    }
    
    /**
     * 返回归属车企
     * @return
     */
    @RequestMapping("api/driverVehicleBind/getBelongLeasecompany")
    @ResponseBody
    public JSONObject getBelongLeasecompany() {
    	JSONObject jsonObject = new JSONObject();

        try {
        	List<Map<String, Object>> infoDto = refService.getBelongLeasecompany();
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONArray.fromObject(infoDto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("返回归属车企失败！",e);
        }

        return jsonObject;
    }


}