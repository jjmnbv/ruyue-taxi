package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.SetTimeFence;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.*;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

/**
 * 系统设置 时间栅栏设置
 *
 * @author liubangwei_lc
 */
@RequestMapping("/SetTimeFence")
@Controller
public class SetTimeFenceController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


    @RequestMapping("/Index")
    public ModelAndView getSetTimeFenceIndex(HttpServletRequest request)
            throws NoSuchAlgorithmException, ParseException {

        Map<String, Object> model = new HashMap<>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);

        return new ModelAndView("resource/setTimeFence/index", model);
    }

    /**
     * 分页显示数据
     * @param queryFenceParam
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getSetTimeFenceByPage")
    @ResponseBody
    public PageBean getSetTimeFenceByPage(@RequestBody  QueryFenceParam queryFenceParam, HttpServletRequest request,
                                          HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String userToken = getUserToken(); //调通父类方法获取userToken
        queryFenceParam.setApikey(vmsApikey);
        PageBean pageBean = new PageBean();
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/SystemSet/QueryTimeFence?" + ReflectClassField.getMoreFieldsValue(queryFenceParam),
                HttpMethod.GET, userToken, null, Map.class);

        List<QueryTimeFence> list = (List<QueryTimeFence>) map.get("timeFence");

        pageBean.setsEcho(queryFenceParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);

        return pageBean;
    }

    /**
     * 跳转到编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toAddOrUpdate/{id}")
    public String toAddOrUpdate(@PathVariable("id") String id, Model model) {
        if (id != null) {
            model.addAttribute("id", id);
        }
        return "resource/setTimeFence/edit";
    }

    /**
     * 更新异步请求数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    @ResponseBody
    public SetTimeFence toUpdate(String id) {
        CommonParam commonParam = new CommonParam();
        commonParam.setId(id);
        commonParam.setDataStatusNormal(1);
        commonParam.setApikey(vmsApikey);

        //调用接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/SystemSet/getSetTimeFenceById?" + ReflectClassField.getMoreFieldsValue(commonParam),
                HttpMethod.GET, null, null, Map.class);

        //转换接口值
        Object setTimeFence = map.get("timeFenceById");
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap<String,Object>)setTimeFence;
        SetTimeFence timeFenceById = new SetTimeFence();
        timeFenceById.setEntityName(String.valueOf(linkedHashMap.get("entityName")));
        timeFenceById.setStartTime(String.valueOf(linkedHashMap.get("startTime")));
        timeFenceById.setEndTime(String.valueOf(linkedHashMap.get("endTime")));
        timeFenceById.setMonitorperiod(String.valueOf(linkedHashMap.get("monitorperiod")));
        timeFenceById.setSwitchState(Integer.parseInt(String.valueOf(linkedHashMap.get("switchState"))));
        return timeFenceById;
    }


    /**
     * 获取重复周期
     *
     * @param request
     * @param queryFenceParam
     * @return
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
    @RequestMapping("/getMonitorperiod")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Map<String, Object> getMonitorperiod(String id, HttpServletRequest request, QueryFenceParam queryFenceParam)
            throws NoSuchAlgorithmException, ParseException {

        Map<String, Object> data = new HashMap<>();

        // 获取字典列表
        List<Dictionary> listDictionary = templateHelper.dealRequestWithFullUrlToken(
                baseApiUrl + "/Dictionary/GetDictionaryByType?type=周期", HttpMethod.GET, null,null, List.class);

        List<TextAndValue> listMonitorPeriod = new ArrayList<>();

        // 将字典赋值给listMonitorPeriod
        for (Object dictionary : listDictionary) {

            // listDictionary中的实体为LinkedHashMap 需要转换
            LinkedHashMap<String, Object> dictionaryList = (LinkedHashMap<String, Object>) dictionary;
            TextAndValue monitorPeriod = new TextAndValue();
            monitorPeriod.setText(String.valueOf(dictionaryList.get("Text")));
            monitorPeriod.setValue(String.valueOf(dictionaryList.get("Value")));

            // id == -1新增操作 否则编辑
            if (id.equals("-1")) {
                listMonitorPeriod.add(monitorPeriod);
            } else {
                SetTimeFence timeFence = toUpdate(id); //根据ID查询实体数据
                String monitorperiod = timeFence.getMonitorperiod();
                String[] periods = monitorperiod.split("\\|");
                for (String string : periods) {
                    String value = String.valueOf(dictionaryList.get("Value"));
                    if (string.equals(value)) {
                        monitorPeriod.setStatus(1);
                    }
                }
                listMonitorPeriod.add(monitorPeriod);
            }

        }

        data.put("success", listMonitorPeriod);

        return data;
    }

    /**
     * 新增、修改 、启用、停用 时间栅栏<一句话功能简述> <功能详细描述>
     *
     * @param queryFenceParam
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/setTimeFenceAll")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Map<String, Object> setTimeFenceAll(@RequestBody SetTimeFenceParam setTimeFenceParam,
                                               HttpServletRequest request, HttpServletResponse response) {

        setTimeFenceParam.setApikey(vmsApikey);
        OpUser loginOpUser = getLoginOpUser(request);
        setTimeFenceParam.setOperateId(loginOpUser.getId());
        setTimeFenceParam.setOperateStaff(loginOpUser.getNickname());

        // id为0 执行新增操作
        if (setTimeFenceParam.getId().equals("0")) {
            setTimeFenceParam.setOperateType(1);
        } else {
            if (setTimeFenceParam.getName() == null) {
                setTimeFenceParam.setOperateType(3); // 启用/停用
            } else {
                setTimeFenceParam.setOperateType(2); // 修改
            }
        }
        //调用接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/SetTimeFence", HttpMethod.POST,
                null, setTimeFenceParam, Map.class);
        return map;
    }

    /**
     * 刪除时间栅栏
     *
     * @param queryParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTimeFence")
    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteTimeFence(@RequestBody QueryFenceParam queryParam, HttpServletRequest request) {
        queryParam.setApikey(vmsApikey);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DelTimeFence", HttpMethod.POST,
                null, queryParam, Map.class);
        return map;
    }

    /**
     * 详情列表
     *
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/timeFenceInfo")
    @SuppressWarnings("unchecked")
    public PageBean timeFenceInfo(@RequestBody QueryControlledEqpParam queryParam, HttpServletRequest request) {
        PageBean pageBean = new PageBean();
        queryParam.setApikey(vmsApikey);
        queryParam.setFenceType(1);

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/QueryControlledEqp?"
                        + ReflectClassField.getMoreFieldsValue(queryParam),
                HttpMethod.GET, null, queryParam, Map.class);

        List<QueryControlledEqp> list = (List<QueryControlledEqp>) map.get("controlledEqp");
        pageBean.setsEcho(queryParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);

        return pageBean;
    }

    /**
     * 详情
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/timeFenceSetInfo/{id}")
    public String timeFenceSetInfo(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "resource/setTimeFence/timeFenceInfo";
    }


    /**
     * 添加车辆列表
     *
     * @param queryParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVehcList")
    @SuppressWarnings("unchecked")
    public PageBean getVehcList(@RequestBody QueryVehcAndEqpParam queryParam, HttpServletRequest request) {

        QueryControlledEqpParam param = new QueryControlledEqpParam();
        param.setFenceType(1);
        param.setApikey(vmsApikey);
        param.setFenceId(queryParam.getFenceId());
        Map<String, Object> mapControlledEqp = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/QueryControlledEqp?"
                        + ReflectClassField.getMoreFieldsValue(param),
                HttpMethod.GET, null, param, Map.class);

        JSONArray jsonArray = JSONArray.fromObject(mapControlledEqp.get("controlledEqp"));
        List<Map<String, Object>> listControlledEqp = JSONUtil.parseJSON2Map(jsonArray);
        List<String> eqpIdList = new ArrayList<>();
        for (Map<String, Object> map : listControlledEqp) {
            eqpIdList.add(map.get("eid").toString());
        }

        PageBean pageBean = new PageBean();
        queryParam.setApikey(vmsApikey);
        queryParam.setEqpIdList(eqpIdList);

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Common/QueryVehcAndEqpJson",
                HttpMethod.POST, null, queryParam, Map.class);

        List<QueryVehcAndEqp> list = (List<QueryVehcAndEqp>) map.get("vhecEqpList");
        pageBean.setsEcho(queryParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);

        return pageBean;
    }


    /**
     * 分配车辆列表
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/timeFenceToVehc/{id}")
    public String timeFenceToVehc(@PathVariable String id, Model model, HttpServletRequest request) {

        model.addAttribute("workStatusList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=设备状态",
                HttpMethod.GET, null, null, List.class));

        model.addAttribute("id", id);
        return "resource/setTimeFence/timeFenceToVehc";
    }

    /**
     * 添加车辆
     *
     * @param queryParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/distributionEqp")
    @SuppressWarnings("unchecked")
    public Map<String, Object> distributionEqp(@RequestBody QueryControlledEqpParam queryParam, HttpServletRequest request) {

        OpUser loginOpUser = getLoginOpUser(request);
        queryParam.setOperateId(loginOpUser.getId());
        queryParam.setOperateStaff(loginOpUser.getNickname());
        queryParam.setApikey(vmsApikey);

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DistributionEqp",
                HttpMethod.POST, null, queryParam, Map.class);


        return map;
    }

    /**
     * 删除设备
     *
     * @param queryParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeEqp")
    @SuppressWarnings("unchecked")
    public Map<String, Object> removeEqp(@RequestBody QueryControlledEqpParam queryParam, HttpServletRequest request) {

        queryParam.setApikey(vmsApikey);
        OpUser loginOpUser = getLoginOpUser(request);
        queryParam.setOperateId(loginOpUser.getId());
        queryParam.setOperateStaff(loginOpUser.getNickname());

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DelDistributionEqp", HttpMethod.POST,
                null, queryParam, Map.class);

        return map;
    }
}
