package com.szyciov.lease.controller;

import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.PubCooagreement;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.service.PubCoooperateUnionService;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryCooagreementViewVo;
import com.szyciov.util.ApiExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ZF on 2017/8/1.
 */
@RequestMapping(value = "api/PubCoooperateUnion")
@RestController
public class PubCoooperateUnionController extends ApiExceptionHandle {
    @Autowired
    private PubCoooperateUnionService pubCoooperateUnionService;

    /**
     * 查询合作运营信息
     *
     * @param param 查询条件
     * @return 合作运营信息
     */
    @RequestMapping(value = "queryPubCoooperateData", method = RequestMethod.POST)
    public PagingResponse queryPubCoooperateData(@RequestBody QueryPubCoooperateParam param) {
        PagingResponse page = this.pubCoooperateUnionService.queryPubCoooperateData(param);
        return page;
    }

    /**
     * 查询合作运营合作方下拉
     *
     * @param companyid 租赁ID
     * @return 合作方下拉集合
     */
    @RequestMapping(value = "queryLeasecompany", method = RequestMethod.GET)
    public List<Select2Entity> queryLeasecompany(String companyid) {
        List list = this.pubCoooperateUnionService.queryLeasecompany(companyid);
        return list;
    }


    /**
     * 查看协议
     *
     * @param coooperateId 合作ID
     * @return 协议信息
     */
    @RequestMapping(value = "queryCooagreementView/{coooperateId}")
    public QueryCooagreementViewVo queryCooagreementView(@PathVariable String coooperateId) {
        QueryCooagreementViewVo vo = this.pubCoooperateUnionService.queryCooagreementView(coooperateId);
        return vo;
    }


    /**
     * 终止合作
     *
     * @param param 参数
     */
    @RequestMapping(value = "disableCoooperate")
    public JSONObject disableCoooperate(@RequestBody DisableCoooperateParam param) {
        JSONObject result = new JSONObject();
        try {
            this.pubCoooperateUnionService.disableCoooperate(param);
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }

        return checkResult(result);
    }


    /**
     * 调整资源界面查询品牌车系下拉框
     *
     * @param param 查询条件
     * @return 品牌车系列表
     */
    @RequestMapping(value = "queryResourceVehclineSelect2", method = RequestMethod.POST)
    public List<Select2Entity> queryResourceVehclineSelect2(@RequestBody QueryResourceCitySelect2Param param) {
        List list = this.pubCoooperateUnionService.queryResourceVehclineSelect2(param);
        return list;
    }

    /**
     * 调整资源界面查询服务城市下拉框
     *
     * @param coooId 合作ID
     * @return 服务车市列表
     */
    @RequestMapping(value = "queryResourceCitySelect2/{coooId}", method = RequestMethod.GET)
    public List<Select2Entity> queryResourceCitySelect2(@PathVariable String coooId) {
        List list = this.pubCoooperateUnionService.queryResourceCitySelect2(coooId);
        return list;
    }

    /**
     * 调整资源界面查询所有资源信息
     *
     * @param param 查询条件
     * @return 资源信息
     */
    @RequestMapping(value = "queryResource", method = RequestMethod.POST)
    public PagingResponse queryResource(@RequestBody QueryResourceParam param) {
        PagingResponse page = this.pubCoooperateUnionService.queryResource(param);
        return page;
    }


    /**
     * 获取资源管理的服务车型
     *
     * @param coooId 合作ID
     * @return 服务车型结合
     */
    @RequestMapping(value = "queryCooVehicleModeSelect2/{coooId}", method = RequestMethod.GET)
    public List<Select2Entity> queryCooVehicleModeSelect2(@PathVariable String coooId) {
        List<Select2Entity> list = this.pubCoooperateUnionService.queryCooVehicleModeSelect2(coooId);
        return list;
    }

    /**
     * 获取已加入的车辆资源
     *
     * @param coooId 联盟ID
     * @return 车辆资源字符串
     */
    @RequestMapping(value = "queryCooVehicleId/{coooId}", method = RequestMethod.GET)
    public String queryCooVehicleId(@PathVariable String coooId) {
        return this.pubCoooperateUnionService.queryCooVehicleId(coooId);
    }

    /**
     * 更新加入资源的车辆
     *
     * @param param 参数
     */
    @RequestMapping(value = "updateResource", method = RequestMethod.POST)
    public JSONObject updateResource(@RequestBody UpdateResourceParam param) {

        JSONObject result = new JSONObject();
        try {
            this.pubCoooperateUnionService.updateResource(param);
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }

        return checkResult(result);
    }

    /**
     * 根据名称判断租赁公司是否可以合作
     *
     * @param param 参数
     * @return 0 可以
     */
    @RequestMapping(value = "queryApplyLeaseCompany", method = RequestMethod.POST)
    public JSONObject queryApplyLeaseCompany(@RequestBody QueryApplyLeaseCompanyParam param) {
        return this.pubCoooperateUnionService.queryApplyLeaseCompany(param);
    }


    /**
     * 查询申请合作时的可选车辆资源
     *
     * @param param 参数
     * @return 可选资源
     */
    @RequestMapping(value = "queryApplyResource", method = RequestMethod.POST)
    public PagingResponse queryApplyResource(@RequestBody QueryApplyResourceParam param) {
        PagingResponse page = this.pubCoooperateUnionService.queryApplyResource(param);
        return page;
    }

    /**
     * 查询申请合作时品牌车系下拉框
     *
     * @param param 参数
     * @return 品牌车系
     */
    @RequestMapping(value = "queryApplyResourceVehclineSelect2", method = RequestMethod.POST)
    public List<Select2Entity> queryApplyResourceVehclineSelect2(@RequestBody QueryApplyResourceVehclineSelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionService.queryApplyResourceVehclineSelect2(param);
        return list;
    }

    /**
     * 查询车辆城市下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    @RequestMapping(value = "queryApplyResourceCitySelect2", method = RequestMethod.POST)
    public List<Select2Entity> queryApplyResourceCitySelect2(@RequestBody QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionService.queryApplyResourceCitySelect2(param);
        return list;
    }

    /**
     * 查询服务车型下拉选择框
     *
     * @param param 参数
     * @return 城市下拉
     */
    @RequestMapping(value = "queryApplyCooVehicleModeSelect2", method = RequestMethod.POST)
    public List<Select2Entity> queryApplyCooVehicleModeSelect2(@RequestBody QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionService.queryApplyCooVehicleModeSelect2(param);
        return list;
    }

    /**
     * 获取协议
     *
     * @param param 参数
     * @return 协议
     */
    @RequestMapping(value = "queryApplyCooagreement", method = RequestMethod.POST)
    public PubCooagreement queryApplyCooagreement(@RequestBody QueryApplyCooagreementParam param) {
        PubCooagreement cooa = this.pubCoooperateUnionService.queryApplyCooagreement(param);
        return cooa;
    }

    /**
     * 新增合作协议
     *
     * @param param model
     */
    @RequestMapping(value = "addApplyCoooperate", method = RequestMethod.POST)
    public JSONObject addApplyCoooperate(@RequestBody AddApplyCoooperateParam param) {
        JSONObject result = new JSONObject();
        try {
            result = this.pubCoooperateUnionService.addApplyCoooperate(param);
        } catch (Exception e) {
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }

        return checkResult(result);
    }

    /**
     * 获取PubCoooperate
     *
     * @param coooId id
     * @return PubCoooperate
     */
    @RequestMapping(value = "selectPubCoooopertateByPrimaryKey/{coooId}", method = RequestMethod.GET)
    public PubCoooperate selectPubCoooopertateByPrimaryKey(@PathVariable String coooId) {
        PubCoooperate cooo = this.pubCoooperateUnionService.selectPubCoooopertateByPrimaryKey(coooId);
        return cooo;
    }

}
