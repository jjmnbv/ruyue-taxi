package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.QueryIdle;
import com.szyciov.op.param.QueryIdleParam;
import com.szyciov.op.param.VerifyIdleParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
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
 * 驾驶行为 怠速
 *
 * @author yougengxian_lc
 */
@Controller
@RequestMapping("/AlarmIdle")
public class AlarmIdleController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String apikey = SystemConfig.getSystemProperty("vmsApikey");


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
        Map<String, Object> model = new HashMap<String, Object>();

        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);
        model.put("resultList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=核查结果", HttpMethod.GET,
                userToken, null, List.class));
        model.put("timeList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET,
                userToken, null, List.class));
        model.put("postageList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=邮费承担", HttpMethod.GET,
                userToken, null, List.class));


        return new ModelAndView("resource/alarmIdle/index", model);
    }

    /**
     * 分页显示数据
     *
     * @param queryIdleParam
     * @param request
     * @param response
     * @return
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getAlarmIdleByPage")
    @ResponseBody
    public PageBean getAlarmIdleByPage(@RequestBody QueryIdleParam queryIdleParam, HttpServletRequest request,
                                       HttpServletResponse response) throws ParseException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=utf-8");
        PageBean pageBean = new PageBean();

        String usertoken = getUserToken();
        List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryIdleParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        //调用接口查询

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryIdle?apikey=" + apikey + "&" + ReflectClassField.getMoreFieldsValue(queryIdleParam), HttpMethod.GET,
                usertoken, queryIdleParam, Map.class);

        //获取list数据
        List<QueryIdle> list = (List<QueryIdle>) map.get("idle");

        //分页处理
        pageBean.setsEcho(queryIdleParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 核查
     *
     * @param verifyIdleParam
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getVerifyIdle")
    @ResponseBody
    public Map<String, Object> getVerifyIdle(@RequestBody VerifyIdleParam verifyIdleParam, HttpServletRequest request,
                                             HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        OpUser user = getLoginOpUser(request);
        verifyIdleParam.setApikey(apikey);
        verifyIdleParam.setOperateId(user.getId());
        verifyIdleParam.setOperateStaff(user.getNickname());

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/VerifyIdle",
                HttpMethod.POST, userToken, verifyIdleParam, Map.class);

        return map;
    }

    // 导出
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/Export")
    @ResponseBody
    public void export(QueryIdleParam queryIdleParam, HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String fileName = "";
        String title = "怠速";
        // 表头
        List<String> titleList = new ArrayList<String>();
        Map<String, String> header = this.getIdleTitle(titleList);
        if (null == header || header.isEmpty()) {
            return;
        }

        // 查询数据
        String userToken = getUserToken(request);
        queryIdleParam.setApikey(apikey);
        queryIdleParam.setiDisplayStart(0);
        queryIdleParam.setiDisplayLength(9999);

        String usertoken = getUserToken(request);
        List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryIdleParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryIdle?" + ReflectClassField.getMoreFieldsValue(queryIdleParam),
                HttpMethod.GET, userToken, null, Map.class);

        JSONArray idleArray = JSONArray.fromObject(trackRecordMap.get("idle"));// 转化为json数组-------JSONArray对象得到数组
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(idleArray);

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
                if ("startTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("endTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("location".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("idleTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("timeRange".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("updateTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("oilCharge".equals(key)) {
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
    private Map<String, String> getIdleTitle(List<String> headerList) {
        Map<String, String> title = new HashMap<String, String>();

        title.put("plate", "车牌");
        headerList.add("车牌");
        title.put("imei", "设备IMEI");
        headerList.add("设备IMEI");
        title.put("department", "服务车企");
        headerList.add("服务车企");
        title.put("idleTime", "怠速时长");
        headerList.add("怠速时长");
        title.put("startTime", "开始时间");
        headerList.add("开始时间");
        title.put("endTime", "结束时间");
        headerList.add("结束时间");
        title.put("location", "地址");
        headerList.add("地址");
        title.put("timeRange", "违规级别");
        headerList.add("违规级别");
        title.put("oilCharge", "核查结果");
        headerList.add("核查结果");

        return title;
    }


}

