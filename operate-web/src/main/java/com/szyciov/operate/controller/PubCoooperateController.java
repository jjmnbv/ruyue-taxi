package com.szyciov.operate.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.DisableCoooperateParam;
import com.szyciov.op.param.QueryPubCoooperateParam;
import com.szyciov.op.param.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperate.QueryCooagreementViewVo;
import com.szyciov.util.BaseController;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("coooperate")
public class PubCoooperateController extends BaseController {
    private TemplateHelper templateHelper = new TemplateHelper();

    @RequestMapping("")
    public String goCoooperate() {
        return "resource/pubCoooperate/coooperate";
    }

    @RequestMapping("cooagreementView/{coooperateId}")
    public ModelAndView goCooagreementView(@PathVariable String coooperateId) {
        ModelAndView mav = new ModelAndView("resource/pubCoooperate/cooagreementView");
        QueryCooagreementViewVo vo = templateHelper.dealRequestWithToken("/coooperate/queryCooagreementView/" + coooperateId, HttpMethod.GET, getUserToken(),
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
        OpUser user = getLoginOpUser();
        model.setCompanyid(user.getOperateid());

        PagingResponse res = templateHelper.dealRequestWithToken("/coooperate/queryPubCoooperateData", HttpMethod.POST, getUserToken(),
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
    public List queryLeasecompany() {
        OpUser user = getLoginOpUser();
        String companyid = user.getOperateid();
        List list = templateHelper.dealRequestWithToken("/coooperate/queryLeasecompany?companyid=" + companyid, HttpMethod.GET, getUserToken(),
                companyid, List.class);
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
        OpUser user = getLoginOpUser();
        model.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/coooperate/reviewLeasecompany", HttpMethod.POST, getUserToken(),
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
        OpUser user = getLoginOpUser();
        model.setUpdater(user.getId());
        JSONObject obj = templateHelper.dealRequestWithToken("/coooperate/disableCoooperate", HttpMethod.POST, getUserToken(),
                model, JSONObject.class);
        return obj;
    }

}
