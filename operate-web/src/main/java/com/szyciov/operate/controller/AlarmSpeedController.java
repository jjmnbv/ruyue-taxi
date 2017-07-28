package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.entity.QueryOverspeed;
import com.szyciov.op.param.*;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 驾驶行为 超速
 *
 * @author liubangwei_lc
 */
@Controller
@RequestMapping("/AlarmSpeed")
public class AlarmSpeedController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String apikey = SystemConfig.getSystemProperty("vmsApikey");


    @RequestMapping("/Index")
    public ModelAndView getAlarmSpeedIndex(HttpServletRequest request) throws NoSuchAlgorithmException, ParseException {

        Map<String, Object> model = new HashMap<String, Object>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);

        model.put("timeList", templateHelper.dealRequestWithFullUrlToken(
                baseApiUrl + "/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET, userToken, null, List.class));
        model.put("serviceStatus", templateHelper.dealRequestWithFullUrlToken(
                baseApiUrl + "/Dictionary/GetDictionaryByType?type=服役状态", HttpMethod.GET, userToken, null, List.class));

        return new ModelAndView("resource/alarmSpeed/index", model);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/getAlarmSpeedByPage")
    @ResponseBody
    public PageBean getAlarmSpeedByPage(@RequestBody QueryOverspeedParam queryOverspeedParam, HttpServletRequest request,
                                        HttpServletResponse response) throws ParseException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=utf-8");
        PageBean pageBean = new PageBean();

        String usertoken = getUserToken();
        List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryOverspeedParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryOverspeed?apikey=" + apikey + "&"
                        + ReflectClassField.getMoreFieldsValue(queryOverspeedParam), HttpMethod.GET, usertoken,
                queryOverspeedParam, Map.class);

        List<QueryOverspeed> list = (List<QueryOverspeed>) map.get("overspeed");

        pageBean.setsEcho(queryOverspeedParam.getsEcho());
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setiTotalRecords(i);
        pageBean.setAaData(list);

        return pageBean;
    }


    /**
     * 导出
     * @param queryOverspeedParam
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/Export")
    @ResponseBody
    public void export(QueryOverspeedParam queryOverspeedParam, HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String fileName = "";
        String title = "超速";
        // 表头
        List<String> titleList = new ArrayList<String>();
        Map<String, String> header = this.getOverSpeedTitle(titleList);
        if (null == header || header.isEmpty()) {
            return;
        }
        // 查询数据
        String userToken = getUserToken(request);
        queryOverspeedParam.setApikey(apikey);
        queryOverspeedParam.setiDisplayStart(0);
        queryOverspeedParam.setiDisplayLength(9999);

        String usertoken = getUserToken(request);
        List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
        // 转换字典值
        List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
        queryOverspeedParam.setOrganizationId(
                (!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

        Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryOverspeed?" + ReflectClassField.getMoreFieldsValue(queryOverspeedParam),
                HttpMethod.GET, userToken, null, Map.class);

        JSONArray overspeedArray = JSONArray.fromObject(trackRecordMap.get("overspeed"));// 转化为json数组-------JSONArray对象得到数组
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(overspeedArray);

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
                if ("address".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("overspeedTime".equals(key)) {
                    if (StringUtils.isBlank(data)) {
                        dataList.add("");
                    } else {
                        dataList.add(data);
                    }
                }
                if ("overspeed".equals(key)) {
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
    private Map<String, String> getOverSpeedTitle(List<String> headerList) {
        Map<String, String> title = new HashMap<String, String>();

        title.put("plate", "车牌");
        headerList.add("车牌");
        title.put("imei", "IMEI");
        headerList.add("IMEI");
        title.put("department", "服务车企");
        headerList.add("服务车企");
        title.put("overspeedTime", "超速时长");
        headerList.add("超速时长");
        title.put("overspeed", "超速里程(km)");
        headerList.add("超速里程(km)");
        title.put("startTime", "开始时间");
        headerList.add("开始时间");
        title.put("endTime", "结束时间");
        headerList.add("结束时间");
        title.put("address", "地址");
        headerList.add("地址");


        return title;
    }

    /**
     * 跳转行程详情
     *
     * @param eqpId
     * @param trackId
     * @param trackStatus
     * @param model
     * @return
     */
    @RequestMapping("/selectTrack/{eqpId}/{trackId}/{trackStatus}")
    public String selectTrack(@PathVariable("eqpId") String eqpId, @PathVariable("trackId") String trackId,
                              @PathVariable("trackStatus") String trackStatus, Model model, HttpServletRequest request) {
        String url = "";
        if (trackStatus != null && trackStatus.equals("1")) {
            QueryTrackDetailParam queryParam = new QueryTrackDetailParam();

            queryParam.setTrackId(trackId);
            queryParam.setApikey(apikey);
            QueryTrackDetail trackDetail = getTrackDetail(queryParam, request);
            QueryAlarmTimesCount alarmTimesCount = getAlarmTimesCount(trackId,apikey,request);
            model.addAttribute("trackDetail", trackDetail);
            model.addAttribute("alarmTimesCount",alarmTimesCount);
            url =  "resource/trackInfo/currentVehcTrack";
        } else {
            QueryTrackDetailParam queryParam = new QueryTrackDetailParam();
            queryParam.setTrackId(trackId);
            queryParam.setApikey(apikey);
            QueryTrackDetail trackDetail = getTrackDetail(queryParam, request);
            QueryAlarmTimesCount alarmTimesCount = getAlarmTimesCount(trackId,apikey,request);
            model.addAttribute("trackDetail", trackDetail);
            model.addAttribute("alarmTimesCount",alarmTimesCount);
            url =  "resource/trackInfo/historyVehcTrack";
        }
        model.addAttribute("trackStatus",trackStatus);
        model.addAttribute("eqpId",eqpId);
        model.addAttribute("trackId",trackId);
        return url;

    }


    /**
     * 获取行程数据
     * @param queryParam
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public QueryTrackDetail getTrackDetail(QueryTrackDetailParam queryParam, HttpServletRequest request) {

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Monitor/QueryTrackDetails?" + ReflectClassField.getMoreFieldsValue(queryParam),
                HttpMethod.GET, null, queryParam, Map.class);
        JSONArray trackInfo = JSONArray.fromObject(map.get("trackInfo"));
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(trackInfo);
        QueryTrackDetail trackDetail = new QueryTrackDetail();

        for (Map<String, Object> m : list) {
            trackDetail.setPlate(m.get("plate").toString());
            trackDetail.setImei(m.get("imei").toString());
            trackDetail.setTrackMileage(new BigDecimal(m.get("trackMileage").toString()));
            trackDetail.setMaxSpeed(new BigDecimal(m.get("maxSpeed").toString()));
            trackDetail.setStrokeFuel(new BigDecimal(m.get("strokeFuel").toString()));
            trackDetail.setAvgTrackSpeed(new BigDecimal(m.get("avgTrackSpeed").toString()));
            trackDetail.setTrackStartTime(m.get("trackStartTime").toString());
            trackDetail.setStrokeEndTime(m.get("strokeEndTime").toString());
            trackDetail.setStartLongitude(new BigDecimal(m.get("startLongitude").toString()));
            trackDetail.setStartLatitude(new BigDecimal(m.get("startLatitude").toString()));
            trackDetail.setTrackStartAddress(m.get("trackStartAddress").toString());
            trackDetail.setEndLongitude(new BigDecimal(m.get("endLongitude").toString()));
            trackDetail.setEndLatitude(new BigDecimal(m.get("endLatitude").toString()));
            trackDetail.setEndAddress(m.get("endAddress").toString());
            trackDetail.setTotalTimeText(m.get("totalTimeText").toString());
            trackDetail.setFuelConsumption(new BigDecimal(m.get("fuelConsumption").toString()));
            trackDetail.setCumulativeOil(Integer.parseInt(m.get("cumulativeOil").toString()));
            trackDetail.setIdleTimeText(m.get("idleTimeText").toString());
            trackDetail.setMileage0020(new BigDecimal(m.get("mileage0020").toString()));
            trackDetail.setMileage2040(new BigDecimal(m.get("mileage2040").toString()));
            trackDetail.setMileage4060(new BigDecimal(m.get("mileage4060").toString()));
            trackDetail.setMileage6090(new BigDecimal(m.get("mileage6090").toString()));
            trackDetail.setMileage90120(new BigDecimal(m.get("mileage90120").toString()));
            trackDetail.setMileage120(new BigDecimal(m.get("mileage120").toString()));
            trackDetail.setIdleFuel(new BigDecimal(m.get("idleFuel").toString()));
            trackDetail.setRunTimeText(m.get("runTimeText").toString());
            trackDetail.setDepartmentName(m.get("departmentName").toString());
            trackDetail.setSpeedingCount(Integer.parseInt(m.get("speedingCount").toString()));
            trackDetail.setIdlingCount(Integer.parseInt(m.get("idlingCount").toString()));
            trackDetail.setCollisionCount(Integer.parseInt(m.get("collisionCount").toString()));
            trackDetail.setPowerfailureCount(Integer.parseInt(m.get("powerfailureCount").toString()));
            trackDetail.setWatertempCount(Integer.parseInt(m.get("watertempCount").toString()));
            trackDetail.setWorkStatusText(m.get("workStatusText").toString());
            trackDetail.setRotation(new BigDecimal(m.get("rotation").toString()));
            trackDetail.setSpeed(new BigDecimal(m.get("speed").toString()));
            trackDetail.setTemperatrue(new BigDecimal(m.get("temperatrue").toString()));
            trackDetail.setVoltage(new BigDecimal(m.get("voltage").toString()));
            trackDetail.setTotalMileage(new BigDecimal(m.get("totalMileage").toString()));
            trackDetail.setFaultCode(m.get("faultCode").toString());
            trackDetail.setUpdateTime(m.get("updateTime").toString());


        }
        return trackDetail;
    }

    /**
     * 报警次数
     * @param trackId
     * @param apikey
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public QueryAlarmTimesCount getAlarmTimesCount(String trackId,String apikey,HttpServletRequest request){
        CommonParam param = new CommonParam();
        param.setId(trackId);
        param.setApikey(apikey);
        Map<String, Object> map = new HashMap<>();
        try {
            map= templateHelper.dealRequestWithFullUrlToken(
                    vmsApiUrl + "/Monitor/AlarmTimesCount?" + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, null,
                    param, Map.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        JSONArray trackInfo = JSONArray.fromObject(map.get("alarmTimesCount"));
        List<Map<String, Object>> list = JSONUtil.parseJSON2Map(trackInfo);
        QueryAlarmTimesCount alarmTimesCount = new QueryAlarmTimesCount();
        for (Map<String, Object> m : list) {
            alarmTimesCount.setAlarmAccelerateTimes(Integer.parseInt(m.get("alarmAccelerateTimes").toString()));
            alarmTimesCount.setAlarmDecelerateTimes(Integer.parseInt(m.get("alarmDecelerateTimes").toString()));
            alarmTimesCount.setAlarmSharpTurnTimes(Integer.parseInt(m.get("alarmSharpTurnTimes").toString()));
        }
        return alarmTimesCount;
    }

}
