package com.szyciov.operate.controller;


import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryFenceViolation;
import com.szyciov.op.param.QueryFenceViolationParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
     * 导出
     * @param queryFenceViolationParam
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AlarmElectronicLimit/Export")
    @ResponseBody
    public Map<String, String> export(QueryFenceViolationParam queryFenceViolationParam, HttpServletRequest request, HttpServletResponse response
            , HttpSession session) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String fileName = "";
        String title = "电子围栏";
        List<String> header = new ArrayList<>();
        header.add("车牌");
        header.add("所属部门");
        header.add("电子围栏名称");
        header.add("开始时间");
        header.add("结束时间");
        header.add("开始地点");
        header.add("结束地点");
        header.add("违规时长");
        header.add("违规里程");
        List<String> seq = new ArrayList<>();
        seq.add("plate");
        seq.add("department");
        seq.add("fenceName");
        seq.add("startTime");
        seq.add("endTime");
        seq.add("startOfViolation");
        seq.add("endOfViolation");
        seq.add("lengthOfViolation");
        seq.add("illegalMileage");
        queryFenceViolationParam.setiDisplayStart(0);
        queryFenceViolationParam.setiDisplayLength(9999);

//			User user = (User) request.getSession().getAttribute("user");
//			String apikey = userService.queryApikeyByUser(user.getId());
//
//			//调用查询疲劳驾驶信息接口
//			Map<String,Object>	map=templateHelper.dealRequest(vmsApiUrl+"/Monitor/QueryFenceViolation?apikey=EFLc9FVyIHUWE9xKYFETDeF&"
//					+ ReflectClassField.getMoreFieldsValue(queryFenceViolationParam),
//					HttpMethod.GET, request, null, Map.class);
//
//			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddhhmmss");
//			fileName += "电子围栏"+ sdfDate.format(new Date());
//			JSONArray jsonArray = JSONArray.fromObject(map.get("fenceViolation"));//转化为json数组-------JSONArray对象得到数组
//			List<Map<String, Object>> list = JSONUtil.parseJSON2Map(jsonArray);
//
//	      return CommonUtils.createExcel(fileName, title, header, seq, list);
        return null;
    }


}
