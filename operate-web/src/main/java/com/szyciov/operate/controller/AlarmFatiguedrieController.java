package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.entity.QueryFatigueDriving;
import com.szyciov.op.param.CommonParam;
import com.szyciov.op.param.QueryFatigueDetail;
import com.szyciov.op.param.QueryFatigueDrivingParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 疲劳驾驶控制层
 */
@Controller
public class AlarmFatiguedrieController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String apikey = SystemConfig.getSystemProperty("vmsApikey");


    @RequestMapping(value = "/AlarmFatiguedrie/Index")
    public ModelAndView getAlarmAccelerateIndex(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, ParseException {
        Map<String, Object> model = new HashMap<String, Object>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);

        model.put("opUserCompany", opUserCompany);
        model.put("durationList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=超时时长范围", HttpMethod.GET,
                userToken, null, List.class));
        model.put("serviceList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=服役状态", HttpMethod.GET,
                userToken, null, List.class));
        model.put("alarmTypeList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=疲劳报警类型", HttpMethod.GET,
                userToken, null, List.class));
        model.put("vehclepropList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=车辆属性", HttpMethod.GET,
                userToken, null, List.class));


        ModelAndView mv = new ModelAndView("resource/alarmFatiguedrie/index", model);
        return mv;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AlarmFatiguedrie/queryAlarmFatiguedrieList")
    @ResponseBody
    public PageBean getqueryAlarmFatiguedrieList(@RequestBody QueryFatigueDrivingParam queryFatigueDrivingParam, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PageBean pageBean = new PageBean();
        String userToken = getUserToken();
        queryFatigueDrivingParam.setApikey(apikey);
        //调用查询疲劳驾驶信息接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryFatigueDriving?"
                        + ReflectClassField.getMoreFieldsValue(queryFatigueDrivingParam),
                HttpMethod.GET, userToken, null, Map.class);
        List<QueryFatigueDriving> list = (List<QueryFatigueDriving>) map.get("fatigueDriving");
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalRecords(i);
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setAaData(list);
        return pageBean;

    }

    /**
     * 导出
     * @param queryFatigueDrivingParam
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AlarmFatiguedrie/Export")
    public void export(QueryFatigueDrivingParam queryFatigueDrivingParam, HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String fileName = "";
        String title = "疲劳驾驶";
        // 表头
        List<String> titleList = new ArrayList<String>();
        Map<String, String> header = this.getfatigueDrivingTitle(titleList);
        if (null == header || header.isEmpty()) {
            return;
        }
        // 查询数据
        String userToken = getUserToken();
        queryFatigueDrivingParam.setApikey(apikey);
        queryFatigueDrivingParam.setiDisplayStart(0);
        queryFatigueDrivingParam.setiDisplayLength(9999);

        String usertoken = getUserToken();
        List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryFatigueDrivingParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryFatigueDriving?" + ReflectClassField.getMoreFieldsValue(queryFatigueDrivingParam),
                HttpMethod.GET, userToken, null, Map.class);

        JSONArray fatigueDriving = JSONArray.fromObject(trackRecordMap.get("fatigueDriving"));// 转化为json数组-------JSONArray对象得到数组
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(fatigueDriving);

        // 构建需要Excel表格数据
        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            List<Object> dataList = new ArrayList<Object>();
            for (Map<String, Object> map : list) {
                Object obj = map.get(key);
                String data = null;
                if (null != obj) {
                    data = obj.toString();
                }
                // 车牌
                if ("plate".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }

                if ("imei".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }

                if ("department".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("alarmTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }

                if ("alarmLocation".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("timeoutTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("alarmType".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }

            }

            colData.put(value, dataList);
        }

        // 创建 Excel
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        fileName += title + sdfDate.format(new Date()) + ".xls";
        Excel excel = new Excel();
        File tempFile = new File(fileName);
        excel.setColName(titleList);
        excel.setColData(colData);
        ExcelExport ee = new ExcelExport(request, response, excel);
        ee.createExcel(tempFile);
        // 防止页面跳转
        try {
            response.sendRedirect("javascript:void(0);");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出怠速表头构建<一句话功能简述> <功能详细描述>
     *
     * @param headerList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Map<String, String> getfatigueDrivingTitle(List<String> headerList) {
        Map<String, String> title = new HashMap<String, String>();

        title.put("plate", "车牌");
        headerList.add("车牌");
        title.put("imei", "IMEI");
        headerList.add("IMEI");
        title.put("department", "服务车企");
        headerList.add("服务车企");
        title.put("alarmType", "报警类型");
        headerList.add("报警类型");
        title.put("alarmTime", "报警时间");
        headerList.add("报警时间");
        title.put("timeoutTime", "超时时长");
        headerList.add("超时时长");
        title.put("alarmLocation", "报警地点");
        headerList.add("报警地点");


        return title;
    }

    /**
     * 疲劳驾驶详情
     * @param id
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/AlarmFatiguedrie/toFatigueDetail/{id}")
    public String toFatigueDetail(@PathVariable("id")String id, HttpServletRequest request, Model model){

        QueryFatigueDrivingParam param = new QueryFatigueDrivingParam();
        param.setId(id);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryFatigueDriving?apikey=" + apikey + "&"
                + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, null, null, Map.class);

        JSONArray jsonArray = JSONArray.fromObject(map.get("fatigueDriving"));
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(jsonArray);
        QueryFatigueDriving queryFatigueDriving = new QueryFatigueDriving();

        for (Map<String, Object> m : list) {
            queryFatigueDriving.setPlate(m.get("plate").toString());
            queryFatigueDriving.setImei(m.get("imei").toString());
            queryFatigueDriving.setDepartment(m.get("department").toString());
            queryFatigueDriving.setAlarmType(m.get("alarmType").toString());
            queryFatigueDriving.setAlarmTime(m.get("alarmTime").toString());
            queryFatigueDriving.setTimeoutTime(m.get("timeoutTime").toString());
            queryFatigueDriving.setAlarmLocation(m.get("alarmLocation").toString());
        }

        model.addAttribute("queryFatigueDriving",queryFatigueDriving);
        model.addAttribute("id",id);
        return "resource/alarmFatiguedrie/fatigueDetail";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/AlarmFatiguedrie/getFatigueDetail")
    @ResponseBody
    public PageBean getFatigueDetail(CommonParam param, HttpServletRequest request){
        PageBean pageBean = new PageBean();

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryFatigueDetail?apikey=" + apikey + "&"
                + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, null, null, Map.class);

        List<QueryFatigueDetail> list = (List<QueryFatigueDetail>) map.get("queryFatigueDetails");
        int i = (int) map.get("countFatigueDetail");
        pageBean.setsEcho(param.getsEcho());
        pageBean.setiTotalRecords(i);
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setAaData(list);
        return pageBean;
    }


}
