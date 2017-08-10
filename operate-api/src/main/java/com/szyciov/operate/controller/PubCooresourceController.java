package com.szyciov.operate.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.param.*;
import com.szyciov.op.vo.pubCooresource.QueryHavingCooLeasecompanyVo;
import com.szyciov.operate.service.PubCooresourceService;
import com.szyciov.util.ApiExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ZF on 2017/7/25.
 */
@RequestMapping(value = "api/PubCooresource")
@RestController
public class PubCooresourceController extends ApiExceptionHandle {
    @Autowired
    private PubCooresourceService pubCooresourceService;

    /**
     * 查询联营数据
     *
     * @param model 查询条件
     * @return 联营信息
     */
    @RequestMapping(value = "queryPubCooresourceData", method = RequestMethod.POST)
    public PagingResponse queryPubCooresourceData(@RequestBody QueryPubCooresourceParam model) {
        return this.pubCooresourceService.queryPubCooresourceData(model);
    }

    /**
     * 查询战略伙伴
     *
     * @param companyid 运管ID
     * @return 战略伙伴集合
     */
    @RequestMapping(value = "queryHavingCooLeasecompany", method = RequestMethod.GET)
    public List<QueryHavingCooLeasecompanyVo> queryHavingCooLeasecompany(String companyid) {
        return this.pubCooresourceService.queryHavingCooLeasecompany(companyid);
    }

    /**
     * 查询资源信息
     *
     * @param param 查询条件
     * @return 资源信息列表
     */
    @RequestMapping(value = "queryPubCooresourceInfoData", method = RequestMethod.POST)
    public PagingResponse queryPubCooresourceInfoData(@RequestBody QueryPubCooresourceInfoParam param) {
        return this.pubCooresourceService.queryPubCooresourceInfoData(param);
    }

    /**
     * 资源信息司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    @RequestMapping(value = "queryPubCooresourceInfoDriverSelect", method = RequestMethod.POST)
    public List<Select2Entity> queryPubCooresourceInfoDriverSelect(@RequestBody QueryPubCooresourceInfoDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceService.queryPubCooresourceInfoDriverSelect(param);
        return list;
    }


    /**
     * 查询资源管理
     *
     * @param param 查询条件
     * @return 资源管理列表
     */
    @RequestMapping(value = "queryPubCooresourceManageData", method = RequestMethod.POST)
    public PagingResponse queryPubCooresourceManageData(@RequestBody QueryPubCooresourceManageParam param) {
        return this.pubCooresourceService.queryPubCooresourceManageData(param);
    }


    /**
     * 资源管理司机下拉框数据获取
     *
     * @param param 查询条件
     * @return select2
     */
    @RequestMapping(value = "queryPubCooresourceManageDriverSelect", method = RequestMethod.POST)
    public List<Select2Entity> queryPubCooresourceManageDriverSelect(@RequestBody QueryPubCooresourceManageDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceService.queryPubCooresourceManageDriverSelect(param);
        return list;
    }

    /**
     * 资源管理服务车型下拉框
     *
     * @return select2
     */
    @RequestMapping(value = "queryPubCooresourceManageVehicleModelSelect", method = RequestMethod.GET)
    public List<Select2Entity> queryPubCooresourceManageVehicleModelSelect() {
        List<Select2Entity> list = this.pubCooresourceService.queryPubCooresourceManageVehicleModelSelect();
        return list;
    }


    /**
     * 资源管理下更改车型
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "updateVehicleModel", method = RequestMethod.POST)
    public JSONObject updateVehicleModel(@RequestBody UpdatePubVehicleModelsParam param) {
        JSONObject result = new JSONObject();
        try {
            this.pubCooresourceService.updateVehicleModel(param);
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return checkResult(result);
    }
}
