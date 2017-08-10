package com.szyciov.lease.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.lease.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.op.entity.OpUser;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 战略合作-联盟资源管理-战略伙伴
 * Created by 李帅 on 2017/8/7.
 */
@Controller
@RequestMapping(value = "PubCoooperatePartner")
public class PubCoooperatePartnerController extends BaseController {
    private TemplateHelper templateHelper = new TemplateHelper();

    @RequestMapping("Index")
    public String goCoooperate() {
        return "resource/pubCoooperatePartner/index";
    }

    @RequestMapping("cooagreementView/{coooperateId}")
    public ModelAndView goCooagreementView(@PathVariable String coooperateId) {
        ModelAndView mav = new ModelAndView("resource/pubCoooperatePartner/cooagreementView");
        QueryCooagreementViewVo vo = templateHelper.dealRequestWithToken("/PubCoooperatePartner/queryCooagreementView/" + coooperateId, HttpMethod.GET, getUserToken(),
                null, QueryCooagreementViewVo.class);
        mav.addObject("vo", vo);
        return mav;
    }

    /**
     * 获取战略伙伴列表
     *
     * @param model 查询条件
     * @return 战略伙伴结合
     */
    @RequestMapping(value = "queryPubCoooperateData", method = RequestMethod.POST)
    @ResponseBody
    public PagingResponse queryPubCoooperateData(@RequestBody QueryPubCoooperateParam model) {
        User user = getLoginLeUser();
        model.setCompanyid(user.getLeasescompanyid());
        PagingResponse res = templateHelper.dealRequestWithToken("/PubCoooperatePartner/queryPubCoooperateData", HttpMethod.POST, getUserToken(),
                model, PagingResponse.class);

        return res;
    }

    /**
     * 查询战略伙伴
     *
     * @return 战略伙伴集合
     */
    @RequestMapping(value = "queryLeasecompany", method = RequestMethod.GET)
    @ResponseBody
    public List queryLeasecompany(QueryPubCoooperatePartnerLeaseCompanyParam param) {
        User user = getLoginLeUser();
        String companyid = user.getLeasescompanyid();
        param.setCompanyid(companyid);
        List list = templateHelper.dealRequestWithToken("/PubCoooperatePartner/queryLeasecompany", HttpMethod.POST, getUserToken(),
                param, List.class);
        return list;

    }


    /**
     * 审核战略伙伴
     *
     * @param model 审核所需的数据
     */
    @RequestMapping(value = "reviewLeasecompany")
    @ResponseBody
    public JSONObject reviewLeasecompany(@RequestBody ReviewLeasecompanyParam model) {
        User user = getLoginLeUser();
        model.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/PubCoooperatePartner/reviewLeasecompany", HttpMethod.POST, getUserToken(),
                model, JSONObject.class);
        return obj;
    }


    /**
     * 禁用战略伙伴
     *
     * @param model 禁用所需数据
     */
    @RequestMapping(value = "disableCoooperate")
    @ResponseBody
    public JSONObject disableCoooperate(@RequestBody DisableCoooperateParam model) {
        User user = getLoginLeUser();
        model.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/PubCoooperatePartner/disableCoooperate", HttpMethod.POST, getUserToken(),
                model, JSONObject.class);
        return obj;
    }
}
