package com.szyciov.operate.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.*;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by ZF on 2017/7/25.
 */
@RequestMapping(value = "PubCooresource")
@Controller
public class PubCooresourceController extends BaseController {
    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 联营资源界面
     *
     * @return 界面JSP地址
     */
    @RequestMapping(value = "Index")
    public String goCooresource() {
        return "resource/pubCooresource/index";
    }

    /**
     * 联营资源信息界面
     *
     * @return 界面JSP地址
     */
    @RequestMapping(value = "info/{coooId}")
    public ModelAndView goCooresourceInfo(@PathVariable String coooId) {
        ModelAndView mav = new ModelAndView("resource/pubCooresource/cooresourceInfo");
        mav.addObject("coooId", coooId);
        return mav;
    }

    /**
     * 联营资源管理界面
     *
     * @return 界面JSP地址
     */
    @RequestMapping(value = "manage/{coooId}")
    public ModelAndView goCooresourceManage(@PathVariable String coooId) {
        ModelAndView mav = new ModelAndView("resource/pubCooresource/cooresourceManage");
        mav.addObject("coooId", coooId);
        return mav;
    }

    /**
     * 查询联营数据
     *
     * @param model 查询条件
     * @return 联营信息
     */
    @RequestMapping(value = "queryPubCooresourceData", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubCooresourceData(@RequestBody QueryPubCooresourceParam model) {
        OpUser user = getLoginOpUser();
        model.setCompanyid(user.getOperateid());

        PagingResponse res = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceData", HttpMethod.POST, getUserToken(),
                model, PagingResponse.class);

        return res;
    }

    /**
     * 查询战略伙伴
     *
     * @return 战略伙伴集合
     */
    @RequestMapping(value = "queryHavingCooLeasecompany", method = RequestMethod.GET)
    @ResponseBody
    public List queryHavingCooLeasecompany(String keyword) {
        OpUser user = getLoginOpUser();
        String companyid = user.getOperateid();
        List list = templateHelper.dealRequestWithToken("/PubCooresource/queryHavingCooLeasecompany?companyid=" + companyid + "&keyword=" + keyword, HttpMethod.GET, getUserToken(),
                companyid, List.class);
        return list;
    }


    /**
     * 查询资源信息
     *
     * @param param 查询条件
     * @return 资源信息列表
     */
    @RequestMapping(value = "info/queryPubCooresourceInfoData/{coooId}", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubCooresourceInfoData(@PathVariable String coooId, @RequestBody QueryPubCooresourceInfoParam param) {
        param.setCoooId(coooId);

        PagingResponse res = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceInfoData", HttpMethod.POST, getUserToken(),
                param, PagingResponse.class);

        return res;
    }

    /**
     * 资源信息司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    @RequestMapping(value = "info/queryPubCooresourceInfoDriverSelect/{coooId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryPubCooresourceInfoDriverSelect(@PathVariable String coooId, QueryPubCooresourceInfoDriverSelectParam param) {
        param.setCoooId(coooId);

        List list = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceInfoDriverSelect", HttpMethod.POST, getUserToken(),
                param, List.class);

        return list;
    }


    /**
     * 查询资源管理
     *
     * @param param 查询条件
     * @return 资源管理列表
     */
    @RequestMapping(value = "manage/queryPubCooresourceManageData/{coooId}", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubCooresourceManageData(@PathVariable String coooId, @RequestBody QueryPubCooresourceManageParam param) {
        param.setCoooId(coooId);
        PagingResponse res = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceManageData", HttpMethod.POST, getUserToken(),
                param, PagingResponse.class);
        return res;
    }


    /**
     * 资源管理司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    @RequestMapping(value = "manage/queryPubCooresourceManageDriverSelect/{coooId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryPubCooresourceManageDriverSelect(@PathVariable String coooId, QueryPubCooresourceManageDriverSelectParam param) {
        param.setCoooId(coooId);
        List list = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceManageDriverSelect", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;
    }

    /**
     * 资源管理服务车型下拉框
     *
     * @return select2
     */
    @RequestMapping(value = "manage/queryPubCooresourceManageVehicleModelSelect", method = RequestMethod.GET)
    @ResponseBody
    public List<Select2Entity> queryPubCooresourceManageVehicleModelSelect() {
        List list = templateHelper.dealRequestWithToken("/PubCooresource/queryPubCooresourceManageVehicleModelSelect", HttpMethod.GET, getUserToken(),
                null, List.class);
        return list;
    }

    /**
     * 资源管理下更改车型
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "manage/updateVehicleModel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateVehicleModel(@RequestBody UpdatePubVehicleModelsParam param) {
        OpUser user = getLoginOpUser();
        param.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/PubCooresource/updateVehicleModel", HttpMethod.POST, getUserToken(),
                param, JSONObject.class);
        return obj;
    }
}
