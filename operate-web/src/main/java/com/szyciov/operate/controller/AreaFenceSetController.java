package com.szyciov.operate.controller;

import com.alibaba.fastjson.JSON;
import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.CityTreeNode;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.*;
import com.szyciov.util.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

/**
 * 区域栅栏设置控制层
 * <p>
 * Created by liubangwei_lc on 2017/7/12
 */
@Controller
public class AreaFenceSetController extends BaseController {

    private TemplateHelper templateHelper = new TemplateHelper();
    private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
    private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
    private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


    @RequestMapping(value = "/AreaFenceSet/Index")
    public ModelAndView getAreaFenceSetIndex(HttpServletRequest request, HttpServletResponse response)
            throws NoSuchAlgorithmException, ParseException {
        Map<String, Object> model = new HashMap<>();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
        model.put("opUserCompany", opUserCompany);
        ModelAndView mv = new ModelAndView("resource/areaFenceSet/index", model);
        return mv;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AreaFenceSet/queryAreaFenceSetList")
    @ResponseBody
    public PageBean getqueryAreaFenceSetList(@RequestBody QueryFenceParam queryFenceParam, HttpServletRequest request,
                                             HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PageBean pageBean = new PageBean();
        queryFenceParam.setApikey(vmsApikey);

        // 调用
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/SystemSet/QueryAreaFence?"
                        + ReflectClassField.getMoreFieldsValue(queryFenceParam),
                HttpMethod.GET, null, null, Map.class);
        List<QueryAreaFence> list = (List<QueryAreaFence>) map.get("areaFence");
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalRecords(i);
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setAaData(list);
        pageBean.setsEcho(queryFenceParam.getsEcho());
        return pageBean;
    }

    /**
     * 启用 停用
     *
     * @param setAreaFenceParam
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/UpdateFenceStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateFenceStatus(@RequestBody SetAreaFenceParam setAreaFenceParam, HttpServletRequest request,
                                                 HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        setAreaFenceParam.setApikey(vmsApikey);
        setAreaFenceParam.setOperateType(3);
        OpUser loginOpUser = getLoginOpUser(request);
        setAreaFenceParam.setOperateId(loginOpUser.getId());
        setAreaFenceParam.setOperateStaff(loginOpUser.getNickname());

        // 调用启用区域栅栏信息接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/SetAreaFence", HttpMethod.POST, null, setAreaFenceParam,
                Map.class);

        return map;
    }

    /**
     * 跳转到修改或者增加页面
     */
    @RequestMapping(value = "/AreaFenceSet/AreaFenceSetEdit/{id}")
    public ModelAndView showEditOrAdd(@PathVariable("id") String id, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", id);
        ModelAndView mv = new ModelAndView("resource/areaFenceSet/showEdit", model);
        return mv;
    }

    /**
     * 获取城市树
     *
     * @param tag
     * @param afsId
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/GetCity", method = RequestMethod.POST)
    @ResponseBody
    public List<CityTreeNode> getAllCity(String id, String afsId, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<CityTreeNode> treeNodeList = new ArrayList<>();
        String userToken = getUserToken();
        CommonParam param = new CommonParam();

        Map resultMap = request.getParameterMap();
        if (resultMap.size() != 2) {
            id = "";
            afsId = "";
            //获取id和afsId的值
            for (Object key : resultMap.keySet()) {
                String s = key.toString();
                Map maps = (Map) JSON.parse(s);  //json转换为map
                id = maps.get("id").toString();
                afsId = maps.get("afsId").toString();

            }
        }

        if (id != null && id != "") {
            param.setId(id);  //设置父节点ID
            param.setKey(afsId);  //栅栏ID
            Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/getChildrenByAreaFenceSet?apikey="
                            + vmsApikey + "&" + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, userToken,
                    null, Map.class);
            treeNodeList = (List<CityTreeNode>) map.get("treeNodeList");
            return treeNodeList;
        } else {
            param.setKey(afsId);
            Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                    vmsApiUrl + "/SystemSet/getParentByAreaFenceSet?apikey=" + vmsApikey + "&"
                            + ReflectClassField.getMoreFieldsValue(param),
                    HttpMethod.GET, userToken, null, Map.class);
            treeNodeList = (List<CityTreeNode>) map.get("treeNodeList");
            return treeNodeList;
        }
    }


    /**
     * 获取允许运行城市
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/GetAllowCity")
    @ResponseBody
    public List<CityTreeNode> getAllowCity(HttpServletRequest request) throws IOException {
        List<CityTreeNode> treeNodeList = new ArrayList<>();
        CommonParam param = new CommonParam();
        String userToken = getUserToken();

        Map resultMap = request.getParameterMap();

        String id = "";
        String afsId = "";
        //获取id和afsId的值
        for (Object key : resultMap.keySet()) {
            String s = key.toString();
            Map maps = (Map) JSON.parse(s);  //json转换为map
            if (maps.size() == 1) {
                afsId = maps.get("afsId").toString();
            } else {
                id = maps.get("id").toString();
                afsId = maps.get("afsId").toString();
            }
        }

        if (id != null && id != "") {
            param.setId(id);  //设置父节点ID
            param.setKey(afsId);  //栅栏ID
            Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/getAllowChildren?apikey="
                            + vmsApikey + "&" + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, userToken,
                    null, Map.class);
            treeNodeList = (List<CityTreeNode>) map.get("treeAllowNodeList");
            return treeNodeList;
        } else {
            param.setKey(afsId);
            Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                    vmsApiUrl + "/SystemSet/getAllowParent?apikey=" + vmsApikey + "&"
                            + ReflectClassField.getMoreFieldsValue(param),
                    HttpMethod.GET, userToken, null, Map.class);
            treeNodeList = (List<CityTreeNode>) map.get("treeAllowNodeList");
            return treeNodeList;
        }
    }


    /**
     * 实体回显
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/GetById")
    @ResponseBody
    public QueryAreaFence getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        CommonParam param = new CommonParam();
        QueryAreaFence queryAreaFence = new QueryAreaFence();
        param.setId(id);
        String userToken = getUserToken();

        //调用接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/getAreaFence?apikey="
                        + vmsApikey + "&" + ReflectClassField.getMoreFieldsValue(param), HttpMethod.GET, userToken,
                null, Map.class);

        //转换接口数据
        Object areaFence = map.get("areaFence");
        LinkedHashMap<String, Object> areaFenceMap = (LinkedHashMap<String, Object>) areaFence;
        queryAreaFence.setAllowRunCity(String.valueOf(areaFenceMap.get("allowRunCity")));
        queryAreaFence.setSwitchState(Integer.parseInt(String.valueOf(areaFenceMap.get("switchState"))));
        queryAreaFence.setName(String.valueOf(areaFenceMap.get("name")));
        return queryAreaFence;
    }

    /**
     * 更新
     *
     * @param setAreaFenceParam
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/Update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody SetAreaFenceParam setAreaFenceParam, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {

        setAreaFenceParam.setApikey(vmsApikey);
        setAreaFenceParam.setOperateType(2);// 操作类型为修改
        OpUser loginOpUser = getLoginOpUser(request);
        setAreaFenceParam.setOperateId(loginOpUser.getId());
        setAreaFenceParam.setOperateStaff(loginOpUser.getNickname());

        // 调用新增区域栅栏信息接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/SetAreaFence", HttpMethod.POST, null, setAreaFenceParam,
                Map.class);
        return map;
    }

    /**
     * 添加
     *
     * @param setAreaFenceParam
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/Add")
    @ResponseBody
    public Map<String, Object> add(@RequestBody SetAreaFenceParam setAreaFenceParam, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        setAreaFenceParam.setApikey(vmsApikey);
        setAreaFenceParam.setOperateType(1);// 操作类型为新增
        OpUser loginOpUser = getLoginOpUser(request);
        setAreaFenceParam.setOperateId(loginOpUser.getId());
        setAreaFenceParam.setOperateStaff(loginOpUser.getNickname());

        // 调用新增区域栅栏信息接口
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/SetAreaFence", HttpMethod.POST, null, setAreaFenceParam,
                Map.class);
        return map;
    }

    /**
     * 删除
     *
     * @param map
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/Delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, String> map, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {

        SetAreaFenceParam setAreaFenceParam = new SetAreaFenceParam();
        setAreaFenceParam.setId(map.get("id"));
        setAreaFenceParam.setApikey(vmsApikey);
        // 调用删除区域栅栏信息接口
        Map<String, Object> result = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DelAreaFence", HttpMethod.POST, null, setAreaFenceParam,
                Map.class);
        return result;
    }

    /**
     * 跳转车辆详情页面
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/AreaFenceSetInfo/{id}")
    public ModelAndView showDetail(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", id);
        ModelAndView mv = new ModelAndView("resource/areaFenceSet/detail", model);
        return mv;
    }

    /**
     * 受监控车辆列表
     *
     * @param queryParam
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AreaFenceSet/GetAreaFenToVechList")
    @ResponseBody
    public PageBean getGetAreaFenToVechList(@RequestBody QueryControlledEqpParam queryParam,
                                            HttpServletRequest request) throws IOException {
        PageBean pageBean = new PageBean();

        queryParam.setApikey(vmsApikey);
        queryParam.setFenceType(2);// 设置围栏的类型为区域
        // 调用
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/SystemSet/QueryControlledEqp?"
                        + ReflectClassField.getMoreFieldsValue(queryParam),
                HttpMethod.GET, getUserToken(), null, Map.class);
        List<QueryControlledEqp> list = (List<QueryControlledEqp>) map.get("controlledEqp");
        int i = (int) map.get("iTotalRecords");
        pageBean.setiTotalRecords(i);
        pageBean.setiTotalDisplayRecords(i);
        pageBean.setAaData(list);
        pageBean.setsEcho(queryParam.getsEcho());
        return pageBean;
    }

    /**
     * 跳转车辆分配页面
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/AreaFenceSet/AreaFenceToVehc/{id}")
    public ModelAndView showAreaFenceToVehc(@PathVariable("id") String id, HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", id);
        model.put("workStatusList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl + "/Dictionary/GetDictionaryByType?type=设备状态",
                HttpMethod.GET, null, null, List.class));
        ModelAndView mv = new ModelAndView("resource/areaFenceSet/areaFenceToVehc", model);
        return mv;
    }

    /**
     * 添加车辆列表
     *
     * @param queryParam
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/AreaFenceSet/getVehcList")
    @SuppressWarnings("unchecked")
    public PageBean getVehcList(@RequestBody QueryVehcAndEqpParam queryParam, HttpServletRequest request) {
        PageBean pageBean = new PageBean();
        queryParam.setApikey(vmsApikey);

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
                vmsApiUrl + "/Common/QueryVehcAndEqp?"
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
    @RequestMapping("/AreaFenceSet/distributionEqp")
    @SuppressWarnings("unchecked")
    public Map<String, Object> distributionEqp(@RequestBody QueryControlledEqpParam queryParam,
                                               HttpServletRequest request) {
        OpUser loginOpUser = getLoginOpUser(request);
        queryParam.setOperateId(loginOpUser.getId());
        queryParam.setOperateStaff(loginOpUser.getNickname());
        queryParam.setApikey(vmsApikey);
        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DistributionEqp", HttpMethod.POST,
                null, queryParam, Map.class);

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
    @RequestMapping("/AreaFenceSet/removeEqp")
    @SuppressWarnings("unchecked")
    public Map<String, Object> removeEqp(@RequestBody QueryControlledEqpParam queryParam, HttpServletRequest request) {

        OpUser loginOpUser = getLoginOpUser(request);
        queryParam.setOperateId(loginOpUser.getId());
        queryParam.setOperateStaff(loginOpUser.getNickname());
        queryParam.setApikey(vmsApikey);

        Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/SystemSet/DelDistributionEqp",
                HttpMethod.POST, null, queryParam, Map.class);

        return map;
    }

}
