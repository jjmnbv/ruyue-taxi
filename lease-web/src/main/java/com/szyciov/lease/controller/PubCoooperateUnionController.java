package com.szyciov.lease.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.PubCooagreement;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryCooagreementViewVo;
import com.szyciov.op.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ZF on 2017/8/1.
 */
@RequestMapping(value = "PubCoooperateUnion")
@Controller
public class PubCoooperateUnionController extends BaseController {
    private TemplateHelper templateHelper = new TemplateHelper();

    @RequestMapping("Index")
    public String goCoooperate() {
        return "resource/pubCoooperateUnion/index";
    }

    @RequestMapping("updateResource/{coooId}")
    public ModelAndView goUpdateResource(@PathVariable String coooId, String query) {
        ModelAndView mav = new ModelAndView("resource/pubCoooperateUnion/updateResource");

        PubCoooperate res = templateHelper.dealRequestWithToken("/PubCoooperateUnion/selectPubCoooopertateByPrimaryKey/" + coooId, HttpMethod.GET, getUserToken(),
                null, PubCoooperate.class);

        mav.addObject("coooId", coooId);
        mav.addObject("query", query);
        mav.addObject("cooo", res);
        return mav;
    }


    /**
     * 查询合作运营信息
     *
     * @param model 查询条件
     * @return 合作运营信息
     */
    @RequestMapping(value = "queryPubCoooperateData", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubCoooperateData(@RequestBody QueryPubCoooperateParam model) {
        User user = getLoginLeUser();
        model.setCompanyid(user.getLeasescompanyid());

        PagingResponse res = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryPubCoooperateData", HttpMethod.POST, getUserToken(),
                model, PagingResponse.class);

        return res;
    }

    /**
     * 查询合作运营合作方下拉
     *
     * @return 合作方下拉集合
     */
    @RequestMapping(value = "queryLeasecompany", method = RequestMethod.GET)
    @ResponseBody
    public List queryLeasecompany() {
        User user = getLoginLeUser();
        String companyid = user.getLeasescompanyid();
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryLeasecompany?companyid=" + companyid, HttpMethod.GET, getUserToken(),
                companyid, List.class);
        return list;
    }

    /**
     * 查看协议
     *
     * @param coooperateId 合作ID
     * @return 协议内容
     */
    @RequestMapping("cooagreementView/{coooperateId}")
    public ModelAndView goCooagreementView(@PathVariable String coooperateId) {
        ModelAndView mav = new ModelAndView("resource/pubCoooperateUnion/cooagreementView");
        QueryCooagreementViewVo vo = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryCooagreementView/" + coooperateId, HttpMethod.GET, getUserToken(),
                null, QueryCooagreementViewVo.class);
        mav.addObject("vo", vo);
        return mav;
    }

    /**
     * 终止合作
     *
     * @param model 数据
     */
    @RequestMapping(value = "disableCoooperate")
    @ResponseBody
    public JSONObject disableCoooperate(@RequestBody DisableCoooperateParam model) {
        User user = getLoginLeUser();
        model.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/PubCoooperateUnion/disableCoooperate", HttpMethod.POST, getUserToken(),
                model, JSONObject.class);
        return obj;
    }


    /**
     * 调整资源界面查询品牌车系下拉框
     *
     * @param param 查询条件
     * @return 品牌车系列表
     */
    @RequestMapping(value = "queryResourceVehclineSelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryResourceVehclineSelect2(QueryResourceCitySelect2Param param) {
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryResourceVehclineSelect2", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;
    }

    /**
     * 调整资源界面查询服务城市下拉框
     *
     * @param coooId 合作ID
     * @return 服务车市列表
     */
    @RequestMapping(value = "queryResourceCitySelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryResourceCitySelect2(String coooId) {
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryResourceCitySelect2/" + coooId, HttpMethod.GET, getUserToken(),
                null, List.class);
        return list;
    }

    /**
     * 调整资源界面查询所有资源信息
     *
     * @param param 查询条件
     * @return 资源信息
     */
    @RequestMapping(value = "queryResource", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryResource(@RequestBody QueryResourceParam param) {
        PagingResponse page = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryResource", HttpMethod.POST, getUserToken(),
                param, PagingResponse.class);
        return page;
    }

    /**
     * 获取资源管理的服务车型
     *
     * @param coooId 合作ID
     * @return 服务车型结合
     */
    @RequestMapping(value = "queryCooVehicleModeSelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryCooVehicleModeSelect2(String coooId) {
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryCooVehicleModeSelect2/" + coooId, HttpMethod.GET, getUserToken(),
                null, List.class);
        return list;
    }

    /**
     * 获取已加入的车辆资源
     *
     * @param coooId 联盟ID
     * @return 车辆资源字符串
     */
    @RequestMapping(value = "queryCooVehicleId", method = RequestMethod.GET)
    @ResponseBody
    public String queryCooVehicleId(String coooId) {
        String s = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryCooVehicleId/" + coooId, HttpMethod.GET, getUserToken(),
                null, String.class);
        return s;
    }

    /**
     * 更新加入资源的车辆
     *
     * @param param 参数
     */
    @RequestMapping(value = "updateResource", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateResource(@RequestBody UpdateResourceParam param) {
        User user = getLoginLeUser();
        param.setUpdater(user.getId());
        JSONObject result = templateHelper.dealRequestWithToken("/PubCoooperateUnion/updateResource", HttpMethod.POST, getUserToken(),
                param, JSONObject.class);
        return result;
    }

    /**
     * 申请合作
     */
    @RequestMapping(value = "apply", method = RequestMethod.GET)
    public String goApply() {
        return "resource/pubCoooperateUnion/applyCoooperate";
    }


    /**
     * 根据名称判断租赁公司是否存在
     *
     * @param param 参数
     * @return 0
     */
    @RequestMapping(value = "queryApplyLeaseCompany", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryApplyLeaseCompany(@RequestBody QueryApplyLeaseCompanyParam param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        JSONObject s = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyLeaseCompany", HttpMethod.POST, getUserToken(),
                param, JSONObject.class);
        return s;
    }


    /**
     * 查询申请合作时的可选车辆资源
     *
     * @param param 参数
     * @return 可选资源
     */
    @RequestMapping(value = "queryApplyResource", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryApplyResource(@RequestBody QueryApplyResourceParam param) {
        User user = getLoginLeUser();
        param.setLeasescompanyid(user.getLeasescompanyid());
        PagingResponse page = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyResource", HttpMethod.POST, getUserToken(),
                param, PagingResponse.class);
        return page;
    }

    /**
     * 查询申请合作时品牌车系下拉框
     *
     * @param param 参数
     * @return 品牌车系
     */
    @RequestMapping(value = "queryApplyResourceVehclineSelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryApplyResourceVehclineSelect2(QueryApplyResourceVehclineSelect2Param param) {
        User user = getLoginLeUser();
        param.setLeasescompanyid(user.getLeasescompanyid());
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyResourceVehclineSelect2", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;
    }

    /**
     * 查询车辆城市下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    @RequestMapping(value = "queryApplyResourceCitySelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryApplyResourceCitySelect2(QueryApplyResourceCitySelect2Param param) {
        User user = getLoginLeUser();
        param.setLeasescompanyid(user.getLeasescompanyid());
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyResourceCitySelect2", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;
    }

    /**
     * 查询服务车型下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    @RequestMapping(value = "queryApplyCooVehicleModeSelect2", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryApplyCooVehicleModeSelect2(QueryApplyResourceCitySelect2Param param) {
        User user = getLoginLeUser();
        param.setLeasescompanyid(user.getLeasescompanyid());
        List list = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyCooVehicleModeSelect2", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;
    }

    /**
     * 获取协议
     *
     * @param param 参数
     * @return 协议
     */
    @RequestMapping(value = "cooagreementApply", method = RequestMethod.GET)
    public ModelAndView cooagreementApply(QueryApplyCooagreementParam param) {
        ModelAndView mav = new ModelAndView("resource/pubCoooperateUnion/cooagreementApplyView");
        PubCooagreement cooa = templateHelper.dealRequestWithToken("/PubCoooperateUnion/queryApplyCooagreement", HttpMethod.POST, getUserToken(),
                param, PubCooagreement.class);

        mav.addObject("cooa", cooa);

        return mav;
    }


    /**
     * 新增合作协议
     *
     * @param param model
     */
    @RequestMapping(value = "addApplyCoooperate", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addApplyCoooperate(@RequestBody AddApplyCoooperateParam param) {
        User user = getLoginLeUser();
        param.setCompanyid(user.getLeasescompanyid());
        param.setUpdater(user.getId());
        JSONObject s = templateHelper.dealRequestWithToken("/PubCoooperateUnion/addApplyCoooperate", HttpMethod.POST, getUserToken(),
                param, JSONObject.class);

        return s;
    }

}
