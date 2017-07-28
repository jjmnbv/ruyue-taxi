package com.szyciov.operate.controller;


import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryFenceViolation;
import com.szyciov.op.param.QueryFenceViolationParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电子栅栏控制层
 * Created by liubangwei_lc on 2017/7/5
 */
@Controller
public class AlarmElectronicLimitController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");

    /**
     * 跳转首页
     * @param request
     * @param response
     * @return
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
    @RequestMapping(value = "/AlarmElectronicLimit/Index")
    public ModelAndView getAlarmElectronicLimitIndex(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, ParseException {
        Map<String, Object> model = new HashMap<>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<com.szyciov.entity.Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);

        model.put("durationList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET,
                userToken, null, List.class));
        model.put("mileageList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=里程范围", HttpMethod.GET,
                userToken, null, List.class));

        ModelAndView mv = new ModelAndView("resource/alarmElectronicLimit/index", model);
        return mv;
    }

    /**
     * 分页显示数据
     * @param queryFenceViolationParam
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AlarmElectronicLimit/queryAlarmElectronicLimitList")
    @ResponseBody
    public PageBean getqueryAlarmElectronicLimit(QueryFenceViolationParam queryFenceViolationParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        queryFenceViolationParam.setApikey(vmsApikey);
        PageBean pageBean = new PageBean();
        String userToken = getUserToken(); //调通父类方法获取userToken
        List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);//获取用户的所属单位

        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryFenceViolationParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        //调用接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryFenceViolation?" + ReflectClassField.getMoreFieldsValue(queryFenceViolationParam),
                HttpMethod.GET, userToken, null, Map.class);

        List<QueryFenceViolation> list = (List<QueryFenceViolation>) map.get("fenceViolation");
        int i = (int) map.get("iTotalRecords");
        pageBean.setsEcho(queryFenceViolationParam.getsEcho());
        pageBean.setiTotalRecords(i);
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setAaData(list);
        return pageBean;

    }

    /**
     * 电子围栏轨迹
     * @param id
     * @param trackId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/AlarmElectronicLimit/toTravelTrack/{id}/{trackId}")
    @SuppressWarnings("unchecked")
    public String toTravelTrack(@PathVariable("id") String id , @PathVariable("trackId") String trackId,
                                HttpServletRequest request, Model model){
        QueryFenceViolationParam param = new QueryFenceViolationParam();
        param.setId(id);

        //调用接口
        Map<String,Object>	map=templateHelper.dealRequestWithFullUrlToken(vmsApiUrl+"/Monitor/QueryFenceViolation?apikey="+vmsApikey+"&"
                +ReflectClassField.getMoreFieldsValue(param),HttpMethod.GET, null, param, Map.class);

        //转换接口数据
        JSONArray jsonArray = JSONArray.fromObject(map.get("fenceViolation"));
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(jsonArray);
        QueryFenceViolation fenceViolation = new QueryFenceViolation();
        for (Map<String, Object> m : list) {
            fenceViolation.setPlate(m.get("plate").toString());
            fenceViolation.setImei(m.get("imei").toString());
            fenceViolation.setDepartment(m.get("department").toString());
            fenceViolation.setFenceName(m.get("fenceName").toString());
            fenceViolation.setStartTime(m.get("startTime").toString());
            fenceViolation.setEndTime(m.get("endTime").toString());
            fenceViolation.setLengthOfViolation(m.get("lengthOfViolation").toString());
            fenceViolation.setIllegalMileage(new BigDecimal(m.get("illegalMileage").toString()));
            fenceViolation.setStartOfViolation(m.get("startOfViolation").toString());
            fenceViolation.setEndOfViolation(m.get("endOfViolation").toString());
            fenceViolation.setTrackStatus(m.get("trackStatus").toString());
            fenceViolation.setEqpId(m.get("eqpId").toString());
        }

        model.addAttribute("fenceViolation",fenceViolation);
        model.addAttribute("trackId",trackId);

        return "resource/alarmElectronicLimit/electronicTravelTrack";
    }

}
