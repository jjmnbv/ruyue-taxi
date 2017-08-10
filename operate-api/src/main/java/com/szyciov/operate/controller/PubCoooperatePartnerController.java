package com.szyciov.operate.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.op.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.operate.service.PubCoooperatePartnerService;
import com.szyciov.util.ApiExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 战略合作协议
 * Created by ZF on 2017/7/24.
 */
@RequestMapping(value = "api/PubCoooperatePartner")
@RestController
public class PubCoooperatePartnerController extends ApiExceptionHandle {
    @Autowired
    private PubCoooperatePartnerService pubCoooperatePartnerService;

    /**
     * 获取战略伙伴列表
     *
     * @param model 查询条件
     * @return 战略伙伴结合
     */
    @RequestMapping(value = "queryPubCoooperateData", method = RequestMethod.POST)
    public PagingResponse queryPubCoooperateData(@RequestBody QueryPubCoooperateParam model) {
        return this.pubCoooperatePartnerService.queryPubCoooperateData(model);
    }

    /**
     * 查询战略伙伴
     *
     * @param param 运管ID
     * @return 战略伙伴集合
     */
    @RequestMapping(value = "queryLeasecompany", method = RequestMethod.POST)
    public List<Select2Entity> queryLeasecompany(@RequestBody QueryPubCoooperatePartnerLeaseCompanyParam param) {
        return this.pubCoooperatePartnerService.queryLeasecompany(param);
    }

    /**
     * 审核战略伙伴
     *
     * @param model 审核所需的数据
     */
    @RequestMapping(value = "reviewLeasecompany")
    public JSONObject reviewLeasecompany(@RequestBody ReviewLeasecompanyParam model) {

        JSONObject result = new JSONObject();
        try {
            this.pubCoooperatePartnerService.reviewLeasecompany(model);

            try{
                this.pubCoooperatePartnerService.sendMsg(model.getId(), model.getCoostate());
            }catch (Exception e){
            }
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return checkResult(result);
    }


    /**
     * 禁用战略伙伴
     *
     * @param model 禁用所需数据
     */
    @RequestMapping(value = "disableCoooperate")
    public JSONObject disableCoooperate(@RequestBody DisableCoooperateParam model) {
        JSONObject result = new JSONObject();
        try {
            this.pubCoooperatePartnerService.disableCoooperate(model);

            try{
                this.pubCoooperatePartnerService.sendMsg(model.getId(), 3);
            }catch (Exception e){
            }
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }

        return checkResult(result);
    }

    /**
     * 查看协议
     *
     * @param coooperateId 合作运营id
     * @return 协议内容
     */
    @RequestMapping(value = "queryCooagreementView/{coooperateId}")
    public QueryCooagreementViewVo queryCooagreementView(@PathVariable String coooperateId) {
        return this.pubCoooperatePartnerService.queryCooagreementView(coooperateId);
    }
}
