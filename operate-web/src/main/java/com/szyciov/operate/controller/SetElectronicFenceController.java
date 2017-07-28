package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.*;
import com.szyciov.util.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统设置   电子围栏设置
 *
 * @author liubangwei_lc
 */
@RequestMapping("/SetElectronicFence")
@Controller
public class SetElectronicFenceController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


    @RequestMapping("/Index")
    public ModelAndView getSetElectronicFenceIndex(HttpServletRequest request)
            throws NoSuchAlgorithmException, ParseException {

        Map<String, Object> model = new HashMap<>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);

        return new ModelAndView("resource/setElectronicFence/index", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/getSetElectronicFenceByPage")
    @ResponseBody
    public PageBean getSetElectronicFenceByPage(@RequestBody QueryFenceParam queryFenceParam, HttpServletRequest request,
                                                HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        queryFenceParam.setApikey(vmsApikey);

        PageBean pageBean = new PageBean();
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/QueryElectronicFence?" + ReflectClassField.getMoreFieldsValue(queryFenceParam),
                HttpMethod.GET, null, queryFenceParam, Map.class);

        List<QueryElectronicFence> list = (List<QueryElectronicFence>) map.get("electronicFence");
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
    public String toAddOrUpdate(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        if (id != null) {
            model.addAttribute("id", id);
        }
        model.addAttribute("alartTypeList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=电子围栏报警", HttpMethod.GET,
                null, null, List.class));
        return "resource/setElectronicFence/edit";
    }

    /**
     * 更新异步请求数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    @ResponseBody
    public Map<String, Object> toUpdate(String id, HttpServletRequest request) {
        QuerySetElectronicFenceParam queryParam = new QuerySetElectronicFenceParam();
        queryParam.setElectronicFenceId(id);
        queryParam.setApikey(vmsApikey);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/QuerySetElectronicFence?" + ReflectClassField.getMoreFieldsValue(queryParam), HttpMethod.GET,
                null, queryParam, Map.class);
        return map;
    }


    /**
     * 新增、修改 、启用、停用 时间栅栏<一句话功能简述> <功能详细描述>
     *
     * @param setElectronicFenceParam
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/setSetElectronicFenceAll")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Map<String, Object> setSetElectronicFenceAll(@RequestBody SetElectronicFenceParam setElectronicFenceParam,
                                                        HttpServletRequest request, HttpServletResponse response) {

        setElectronicFenceParam.setApikey(vmsApikey);
        OpUser loginOpUser = getLoginOpUser(request);
        setElectronicFenceParam.setOperateId(loginOpUser.getId());
        setElectronicFenceParam.setOperateStaff(loginOpUser.getNickname());


        // id为0 执行新增操作
        if (setElectronicFenceParam.getElectronicFenceId().equals("0")) {
            setElectronicFenceParam.setOperateType(1);
        } else {
            if (setElectronicFenceParam.getName() == null) {
                setElectronicFenceParam.setOperateType(3); // 启用/停用
            } else {
                setElectronicFenceParam.setOperateType(2); //修改
            }
        }

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/SetElectronicFence", HttpMethod.POST,
                null, setElectronicFenceParam, Map.class);
        return map;
    }

    /**
     * 刪除时间栅栏
     *
     * @param queryFenceParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteElectronicFence")
    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteElectronicFence(@RequestBody QueryFenceParam queryFenceParam, HttpServletRequest request) {

        queryFenceParam.setApikey(vmsApikey);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DelElectronicFence", HttpMethod.POST,
                null, queryFenceParam, Map.class);
        return map;
    }


    /**
     * 详情列表
     *
     * @param queryParam
     * @return
     */
    @ResponseBody
    @RequestMapping("/electronicFenceInfo")
    @SuppressWarnings("unchecked")
    public PageBean electronicFenceInfo(@RequestBody QueryControlledEqpParam queryParam) {
        PageBean pageBean = new PageBean();
        queryParam.setFenceType(3);
        queryParam.setApikey(vmsApikey);
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
    @RequestMapping("/electronicFenceInfo/{id}")
    public String timeFenceSetInfo(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "resource/setElectronicFence/electronicFenceInfo";
    }

    /**
     * 分配车辆跳转
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/electronicFenceToVehc/{id}")
    public String timeFenceToVehc(@PathVariable String id, Model model) {

        model.addAttribute("workStatusList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=设备状态",
                HttpMethod.GET, null, null, List.class));

        model.addAttribute("id", id);
        return "resource/setElectronicFence/electronicFenceToVehc";
    }

    /**
     * 添加车辆列表
     *
     * @param queryParam
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVehcList")
    @SuppressWarnings("unchecked")
    public PageBean getVehcList(@RequestBody QueryVehcAndEqpParam queryParam) {
        PageBean pageBean = new PageBean();
        queryParam.setApikey(vmsApikey);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Common/QueryVehcAndEqp?"
                        + ReflectClassField.getMoreFieldsValue(queryParam),
                HttpMethod.GET, null, queryParam, Map.class);
        List<QueryVehcAndEqp> list = (List<QueryVehcAndEqp>) map.get("vhecEqpList");

        pageBean.setsEcho(queryParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);

        return pageBean;
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
