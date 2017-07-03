package com.szyciov.lease.controller;

import com.szyciov.dto.driverShiftManagent.PendingSaveDto;
import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.User;
import com.szyciov.param.PeDrivershiftPendingParam;
import com.szyciov.param.PeDrivershiftProcessedParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 交接班管理Controller
 * Created by LC on 2017/3/2.
 */
@Controller
public class DriverShiftController extends BaseController {

    Logger logger = LoggerFactory.getLogger(DriverShiftController.class);


    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 申请交接班
     * @param pending   待处理交班对象
     * @return
     */
    @RequestMapping("/driverShift/apply")
    @ResponseBody
    public JSONObject driverShfitApply(@RequestBody PendingSaveDto pending, HttpServletRequest request,
                                       HttpServletResponse response){


        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        pending.setPlatformtype(PlatformTypeByDb.LEASE.code);
        pending.setLeasescompanyid(user.getLeasescompanyid());

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverShift/apply",
                HttpMethod.POST, usertoken, pending, JSONObject.class);


        return jsonObject;
    }

    /**
     * 自主交接班转人工交接班
     * @param pendingId    待处理交接班ID
     * @return

    @RequestMapping("/driverShift/toManpower")
    @ResponseBody
    public JSONObject toManpower(@RequestBody String pendingId, HttpServletRequest request,
                                 HttpServletResponse response){

        JSONObject jsonObject ;

        jsonObject = templateHelper.dealRequestWithToken("/driverShift/toManpower",
                HttpMethod.POST, null, pendingId, JSONObject.class);




        return jsonObject;
    }
    */

    /**
     * 交接班——待处理列表
     * @param pendingParam      待处理查询条件
     * @return
     */
    @RequestMapping("/driverShift/listPending")
    @ResponseBody
    public JSONObject listPending(@RequestBody PeDrivershiftPendingParam pendingParam, HttpServletRequest request,
                                HttpServletResponse response){


        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        pendingParam.setPlatformtype(PlatformTypeByDb.LEASE.code);
        pendingParam.setLeasescompanyid(user.getLeasescompanyid());

        JSONObject jsonObject = templateHelper.dealRequestWithToken("/driverShift/listPending",
                HttpMethod.POST, usertoken, pendingParam, JSONObject.class);
        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }
        return null;
    }


    /**
     * 交接班——已处理列表
     * @param processedParam    已处理查询条件
     * @return
     */
    @RequestMapping("/driverShift/listProcessed")
    @ResponseBody
    public JSONObject listProcessed(@RequestBody PeDrivershiftProcessedParam processedParam, HttpServletRequest request,
                                    HttpServletResponse response){


        String usertoken = getUserToken(request);
        User user = getLoginLeUser(request);

        processedParam.setPlatformtype(PlatformTypeByDb.LEASE.code);
        processedParam.setLeasescompanyid(user.getLeasescompanyid());


        JSONObject jsonObject =  templateHelper.dealRequestWithToken("/driverShift/listProcessed",
                HttpMethod.POST, usertoken, processedParam, JSONObject.class);

        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONObject json = jsonObject.getJSONObject("data");
            return json;
        }

        return null;
    }


    /**
     * 交接班管理
     * @param processedSaveDto  交接班处理dto
     * @return
     */
    @RequestMapping("/driverShift/processd")
    @ResponseBody
    public JSONObject manpowerHandle(@RequestBody ProcessedSaveDto processedSaveDto, HttpServletRequest request,
                                     HttpServletResponse response){

        User user = getLoginLeUser(request);
        processedSaveDto.setProcessperson(user.getId());
        processedSaveDto.setProcesspersonname(user.getNickname());
        processedSaveDto.setRelievedtype(PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.code);
        JSONObject jsonObject =  templateHelper.dealRequestWithToken("/driverShift/processd",
                HttpMethod.POST, null, processedSaveDto, JSONObject.class);

        return jsonObject;
    }

    /**
     * 交接班管理
     * @param processedSaveDto  交接班处理dto
     * @return
     */
    @RequestMapping("/driverShift/saveAssign")
    @ResponseBody
    public JSONObject saveAssign(@RequestBody ProcessedSaveDto processedSaveDto, HttpServletRequest request,
                                     HttpServletResponse response){

        User user = getLoginLeUser(request);

        processedSaveDto.setProcessperson(user.getId());
        processedSaveDto.setProcesspersonname(user.getNickname());
        processedSaveDto.setLeaseId(user.getLeasescompanyid());

        JSONObject jsonObject =  templateHelper.dealRequestWithToken("/driverShift/saveAssign",
                HttpMethod.POST, null, processedSaveDto, JSONObject.class);

        return jsonObject;
    }


    /**
     * 返回车辆绑定的司机信息
     * @param id  待接班对象Id
     * @return
     */
    @RequestMapping("/driverShift/listBindDrivers")
    @ResponseBody
    public JSONObject listBindDrivers(@RequestParam String id, HttpServletRequest request,
                                      HttpServletResponse response){
        String usertoken = getUserToken(request);

        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        JSONObject jsonObject = templateHelper.dealRequestWithToken  ("/driverShift/listBindDrivers",
                HttpMethod.POST, usertoken,map, JSONObject.class);

        return jsonObject;
    }





    /**
     * 检查是否正在处理中
     * @param id  待接班对象Id
     * @return
     */
    @RequestMapping("/driverShift/processing")
    @ResponseBody
    public Boolean checkProcessing(@RequestParam String id, HttpServletRequest request,
                                   HttpServletResponse response){

        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        return  templateHelper.dealRequestWithToken  ("/driverShift/processing",
                HttpMethod.POST,null,map, Boolean.class);

    }

    /**
     * 是否需要指定当班
     * @param vehicleId   待处理交班对象
     * @return
     */
    @RequestMapping("/driverShift/isAssign/{vehicleId}")
    @ResponseBody
    public JSONObject isAssign(@PathVariable String vehicleId,HttpServletRequest request){

        System.out.println("ssssssssssssss");

        return  templateHelper.dealRequestWithToken("/driverShift/isAssign/"+vehicleId,
                HttpMethod.POST,null,null, JSONObject.class);

    }

    /**
     * 获取交接班司机记录
     * @param   map  车辆ID及类型
     * @return
     */
    @RequestMapping("/driverShift/listShiftRecordDriver")
    @ResponseBody
    public JSONArray listShiftRecordDriver(@RequestParam Map<String,String> map,HttpServletRequest request){

        JSONObject jsonObject =  templateHelper.dealRequestWithToken  ("/driverShift/listShiftRecordDriver",
                HttpMethod.POST, null, map, JSONObject.class);

        if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
            JSONArray json = jsonObject.getJSONArray("data");
            return json;
        }
        return new JSONArray();
    }

    /**
     * 返回交接班信息列表
     * @return
     */
    @RequestMapping(value = "/driverShift/record/list")
    public String listDriverShiftRecord() {
        return "resource/driverShiftManage/driverShiftRecord/index";
    }

    /**
     * 跳转至车辆交接班详情列表
     * @return
     */
    @RequestMapping(value = "/driverShift/record/vehicledetail")
     public String listDriverShiftRecordDetail(@RequestParam String vehicleId) {
        return "resource/driverShiftManage/driverShiftRecord/detail";
    }

    @RequestMapping(value = "/driverShift/Index")
    public String driverShiftIndex() {
        return "resource/driverShiftManage/driverShift/index";
    }

    @RequestMapping(value = "/driverShift/record/Index")
    public String driverShiftRecordIndex() {
        return "resource/driverShiftManage/driverShiftRecord/index";
    }

    @RequestMapping(value = "/driverShift/AlIndex")
    public String driverShiftAlIndex() {
        return "resource/driverShiftManage/driverShift/alindex";
    }

}
 