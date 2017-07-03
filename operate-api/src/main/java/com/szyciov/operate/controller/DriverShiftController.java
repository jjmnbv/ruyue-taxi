package com.szyciov.operate.controller;

import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.dto.driverShiftManagent.PendingSaveDto;
import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.PendInfoEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.op.dto.drivershift.PendInfoDto;
import com.szyciov.operate.service.PeDrivershiftPendingService;
import com.szyciov.operate.service.PeDrivershiftProcessedService;
import com.szyciov.operate.service.PubDriverVehicleRefService;
import com.szyciov.param.PeDrivershiftPendingParam;
import com.szyciov.param.PeDrivershiftProcessedParam;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 交接班管理Controller
 * Created by LC on 2017/3/2.
 */
@RestController
public class DriverShiftController {

    Logger logger = LoggerFactory.getLogger(DriverShiftController.class);

    @Autowired
    private PeDrivershiftPendingService pendingService;

    @Autowired
    private PeDrivershiftProcessedService processedService;

    @Autowired
    private PubDriverVehicleRefService refService;

    /**
     * 申请交接班
     * @param pending   待处理交班对象
     * @return
     */
    @RequestMapping("api/driverShift/apply")
    @ResponseBody
    public JSONObject driverShfitApply(@RequestBody PendingSaveDto pending){

        JSONObject jsonObject = new JSONObject();

        try {
            PeDrivershiftPending pending1 = pendingService.getPendingByDriverId(pending.getDriverid());
            if(pending1==null) {
                PendInfoDto infoDto = pendingService.save(pending);
                jsonObject.put("status", Retcode.OK.code);
                jsonObject.put("data", infoDto);
            }else{
                jsonObject.put("status", Retcode.FAILED.code);
                jsonObject.put("message", "当前已有交班申请，请等待处理完成！");
            }
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("新增交班记录异常："+ GsonUtil.toJson(pending),e);
        }

        return jsonObject;
    }

    /**
     * 取消交接班申请
     * @param pendingId   待处理交接班ID
     * @return
     */
    @RequestMapping("api/driverShift/removeApply")
    @ResponseBody
    public JSONObject removeShfitApply(@RequestParam String pendingId){

        JSONObject jsonObject = new JSONObject();

        try {
            pendingService.removeShfitApply(pendingId);
            jsonObject.put("status", Retcode.OK.code);
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("取消交班记录异常："+ GsonUtil.toJson(pendingId),e);
        }
        return jsonObject;
    }

    /**
     * 交接班——待处理列表
     * @param pendingParam      待处理查询条件
     * @return
     */
    @RequestMapping("api/driverShift/listPending")
    @ResponseBody
    public JSONObject listPending(@RequestBody PeDrivershiftPendingParam pendingParam){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean page = pendingService.listPending(pendingParam);
            if(page!=null) {
                jsonObject.put("data", JSONObject.fromObject(page));
                jsonObject.put("status", Retcode.OK.code);
            }else{
                jsonObject.put("status", Retcode.FAILED.code);
            }
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询交班待处理列表出错："+ JSONObject.fromObject(pendingParam),e);
        }

        return jsonObject;
    }


    /**
     * 交接班——已处理列表
     * @param processedParam    已处理查询条件
     * @return
     */
    @RequestMapping("api/driverShift/listProcessed")
    @ResponseBody
    public JSONObject listProcessed(@RequestBody PeDrivershiftProcessedParam processedParam){

        JSONObject jsonObject = new JSONObject();

        try {
            PageBean page = processedService.listProcessed(processedParam);
            if(page!=null) {
                jsonObject.put("data", JSONObject.fromObject(page));
                jsonObject.put("status", Retcode.OK.code);
            }else{
                jsonObject.put("status", Retcode.FAILED.code);
            }
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("查询交班已处理列表出错："+ JSONObject.fromObject(processedParam),e);
        }

        return jsonObject;
    }


    /**
     * 交接班管理——处理交接班
     * @param processedSaveDto  交接班处理dto
     * @return
     */
    @RequestMapping("api/driverShift/processd")
    @ResponseBody
    public JSONObject driverShfitProcessd(@RequestBody ProcessedSaveDto processedSaveDto){

        JSONObject jsonObject = new JSONObject();

        try {

            //获取交接班验证结果
            JSONObject resultJson = this.verifyProcessed(processedSaveDto);
            if(resultJson!=null){
                return resultJson;
            }else{
                boolean saveFlag = processedService.save(processedSaveDto);
                if(saveFlag) {
                    jsonObject.put("status", Retcode.OK.code);
                    jsonObject.put("message","人工处理成功！");
                }else {
                    jsonObject.put("status", Retcode.FAILED.code);
                    jsonObject.put("message", "请确认该车辆与当前司机绑定状态！");
                }
            }
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("处理交接班出错："+ JSONObject.fromObject(processedSaveDto),e);
        }

        return jsonObject;
    }


    /**
     * 验证接班操作的有效性
     * @param processedSaveDto
     * @return
     */
    private JSONObject verifyProcessed(ProcessedSaveDto processedSaveDto){
        //验证交班申请的有效性
        JSONObject jsonObject = this.verifyPending(processedSaveDto.getPendingId());
        if(jsonObject!=null){
            return  jsonObject;
        }
        //如果是人工交接班 处理
        if(PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code.equals(processedSaveDto.getShifttype())) {
            //判断接班司机是否为空
            if (StringUtils.isEmpty(processedSaveDto.getRelieveddriverid())) {
                jsonObject = new JSONObject();
                jsonObject.put("status", Retcode.FAILED.code);
                jsonObject.put("message", "接班司机不允许为空，请确认！");
                return jsonObject;
            }
        }

        //如果是车辆回收，需判断是否有未完成订单
        if(PeDrivershiftEnum.SHIFT_TYPE_RECYCLE.code.equals(processedSaveDto.getShifttype())){
            if(pendingService.isUnfinishedOrder(processedSaveDto.getPendingId())){
                jsonObject = new JSONObject();
                jsonObject.put("status", Retcode.FAILED.code);
                jsonObject.put("message","该车辆有未完成订单，请更换其他车辆后，再进行回收！");
                return jsonObject;
            }
        }
        return null;
    }


    /**
     * 车辆待接班司机信息
     * @param  map  待交接班对象
     * @return
     */
    @RequestMapping("api/driverShift/listBindDrivers")
    @ResponseBody
    public JSONObject listBindDrivers(@RequestBody Map<String,String> map){

        JSONObject jsonObject = new JSONObject();

        try {
            //验证交班申请有效性
            jsonObject = this.verifyPending(map.get("id"));
            if(jsonObject!=null){
                return jsonObject;
            }
            jsonObject = new JSONObject();
            PeDrivershiftPending paramPend = new PeDrivershiftPending();
            paramPend.setId(map.get("id"));
            PeDrivershiftPending pending = pendingService.getAllPending(paramPend);
            if(pending!=null) {
                List<VehicleBindInfoDto> list = refService.listVehicleBindInfo(pending.getVehicleid(), pending.getDriverid());
                if (list != null) {
                    jsonObject.put("status", Retcode.OK.code);
                    jsonObject.put("data", list);
                } else {
                    jsonObject.put("status", Retcode.FAILED.code);
                    jsonObject.put("message","没有对班司机，请确认绑定关系后再试！");

                }
            }
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            logger.error("返回待接班司机信息错误："+ map.get("id"),e);
        }

        return jsonObject;
    }
    /**
     * 验证交班申请的有效性
     * @param pendingId
     * @return
     */
    private JSONObject verifyPending(String pendingId){
        JSONObject jsonObject = new JSONObject();
        //判断是否已被处理
        PeDrivershiftProcessed processed = processedService.getProcessed(pendingId);
        if(processed!=null){
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("message","该交接班的人工指派已被其他客服处理！");
            return jsonObject;
        }


        PeDrivershiftPending paramPend = new PeDrivershiftPending();
        paramPend.setId(pendingId);
        //获取交接班申请数据
        PeDrivershiftPending pending = pendingService.getAllPending(paramPend);
        //是否存在交班申请
        if(pending==null){
            jsonObject.put("status", Retcode.FAILED.code);
            jsonObject.put("message","当前交班申请已被取消！");
            return jsonObject;
        }

        //如果交班申请超时
        if(PendInfoEnum.OUT_TIME.code.equals(pendingService.getPendInfo(pending.getDriverid()).getState())){
            jsonObject.put("status", Retcode.FAILED.code);
            jsonObject.put("message","该交接班的人工指派已超时！");
            return jsonObject;
        }

        return null;
    }

    /**
     * 检查是否正在处理中
     * @param map  待接班对象Id
     * @return
     */
    @RequestMapping("api/driverShift/processing")
    @ResponseBody
    public Boolean checkProcessing(@RequestBody Map<String,String> map){

        try {
            String id = map.get("id");
            //获取缓存状态值
            String val = JedisUtil.getString(RedisKeyEnum.MESSAGE_PENDING.code+id);
            //如果值不为空则代表正在处理
            if(StringUtils.isNotEmpty(val)){
                return new Boolean(false);
            }else{
                //设置设置状态值，自动过期200秒
                JedisUtil.setString(RedisKeyEnum.MESSAGE_PENDING.code+id,200,"0");
            }
        }catch (Exception e){
            logger.error("检查是否正在处理中异常："+ map.get("id"),e);
        }
        //默认可处理
        return new Boolean(true);
    }

    /**
     * 获取交接班司机记录
     * @param   map  车辆ID及类型
     * @return
     */
    @RequestMapping("api/driverShift/listShiftRecordDriver")
    @ResponseBody
    public JSONObject listShiftRecordDriver(@RequestBody Map<String,String> map){

        JSONObject jsonObject = new JSONObject();
        String vehicleid = map.get("vehicleid");
        String shiftType = map.get("shiftType");
        String queryText = map.get("queryText");
        try {
            List<PubDriverSelectDto> dto = processedService.listShiftRecordDriver(PlatformTypeByDb.OPERATING.code,vehicleid,shiftType,queryText);
            jsonObject.put("status", Retcode.OK.code);
            jsonObject.put("data", JSONArray.fromObject(dto));
        }catch (Exception e){
            jsonObject.put("status", Retcode.EXCEPTION.code);
            jsonObject.put("message", "系统繁忙请稍后再试！");
            logger.error("获取交接班司机记录异常："+ GsonUtil.toJson(map),e);
        }

        return jsonObject;
    }



}
 