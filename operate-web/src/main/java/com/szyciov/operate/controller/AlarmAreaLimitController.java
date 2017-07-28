package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryAreaViolation;
import com.szyciov.op.param.QueryAreaViolationParam;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域栅栏控制层
 * Created by liubangwei_lc on 2017/7/5
 */
@Controller
@RequestMapping("/AlarmAreaLimit")
public class AlarmAreaLimitController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


    /**
     * 跳转首页 赋予权限..
     *
     * @param request
     * @return
     * @throws NoSuchAlgorithmException
     * @throws ParseException
     */
    @RequestMapping("/Index")
    public ModelAndView getAlarmIdleIndex(HttpServletRequest request) throws NoSuchAlgorithmException, ParseException {
        Map<String, Object> model = new HashMap<>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);

        model.put("timeList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET,
                userToken, null, List.class));
        model.put("outOfBounds", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=里程范围", HttpMethod.GET,
                userToken, null, List.class));
        return new ModelAndView("resource/alarmAreaLimit/index", model);
    }

    /**
     * 分页显示数据
     *
     * @param queryAreaViolationParam
     * @param request
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getAlarmAreaLimitByPage")
    @ResponseBody
    public PageBean getAlarmAreaLimitByPage(QueryAreaViolationParam queryAreaViolationParam, HttpServletRequest request) throws ParseException {
        queryAreaViolationParam.setApikey(vmsApikey);
        PageBean pageBean = new PageBean();
        String userToken = getUserToken(); //调通父类方法获取userToken
        List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);//获取用户的所属单位

        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryAreaViolationParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        //调用接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryAreaViolation?" + ReflectClassField.getMoreFieldsValue(queryAreaViolationParam),
                HttpMethod.GET, userToken, null, Map.class);

        //获取list数据
        List<QueryAreaViolation> list = (List<QueryAreaViolation>) map.get("areaViolation");
        //分页处理
        pageBean.setsEcho(queryAreaViolationParam.getsEcho());
        pageBean.setAaData(list);
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);

        return pageBean;
    }

    /**
     * 区域栅栏行驶轨迹
     * @param id
     * @param trackId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/AlarmAreaLimit/toTravelTrack/{id}/{trackId}")
    @SuppressWarnings("unchecked")
    public String toTravelTrack(@PathVariable("id") String id , @PathVariable("trackId") String trackId,
                                HttpServletRequest request, Model model){
        QueryAreaViolationParam param = new QueryAreaViolationParam();
        param.setId(id);

        //调用接口
        Map<String,Object>	map=templateHelper.dealRequestWithFullUrlToken(vmsApiUrl+"/Monitor/QueryAreaViolation?apikey="+vmsApikey+"&"
                +ReflectClassField.getMoreFieldsValue(param),HttpMethod.GET, null, param, Map.class);

        //转换接口数据
        JSONArray jsonArray = JSONArray.fromObject(map.get("areaViolation"));
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(jsonArray);
        QueryAreaViolation areaViolation = new QueryAreaViolation();
        for (Map<String, Object> m : list) {
            areaViolation.setPlate(m.get("plate").toString());
            areaViolation.setImei(m.get("imei").toString());
            areaViolation.setDepartment(m.get("department").toString());
            areaViolation.setStartTime(m.get("startTime").toString());
            areaViolation.setEndTime(m.get("endTime").toString());
            areaViolation.setLengthOfViolation(m.get("lengthOfViolation").toString());
            areaViolation.setOutOfBoundsMileage(new BigDecimal(m.get("outOfBoundsMileage").toString()));
            areaViolation.setEndOfViolation(m.get("endOfViolation").toString());
            areaViolation.setTrackStatus(m.get("trackStatus").toString());
            areaViolation.setEqpId(m.get("eqpId").toString());
        }

        model.addAttribute("areaViolation",areaViolation);
        model.addAttribute("trackId",trackId);

        return "resource/alarmAreaLimit/areaTravelTrack";
    }
}
