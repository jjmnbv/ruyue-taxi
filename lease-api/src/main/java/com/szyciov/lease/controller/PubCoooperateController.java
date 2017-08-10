package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.pubCoooperate.DriverInformationDto;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.service.PubCoooperateService;
import com.szyciov.op.param.PubCoooperateQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class PubCoooperateController extends BaseController {
    private static final Logger logger = Logger.getLogger(PubCoooperateController.class);

    public PubCoooperateService service;

    @Resource(name = "PubCoooperateService")
    public void setService(PubCoooperateService service) {
        this.service = service;
    }
    /**
     * <p>分页查询资源管理</p>
     *
     * @param queryParam 查询请求对象，封装需要查询的条件和页码等信息
     * @return 返回一页查询结果
     */
    @RequestMapping(value = "api/PubCoooperate/GetPubCoooperateByQuery", method = RequestMethod.POST)
    @ResponseBody
    public PageBean getPubCoooperateByQuery(@RequestBody PubCoooperateQueryParam queryParam) {
        return service.getPubCoooperateByQuery(queryParam);
    }
    /**
     * <p>加载战略伙伴</p>
     * @return 返回一个list
     */
    @RequestMapping(value = "api/PubCoooperate/GetCompanyNameList/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<PubCoooperate> getCompanyNameList(@PathVariable String id){
        return service.getCompanyNameList(id);
    };

    /**
     * <p>分页查询资源信息</p>
     *
     * @param queryParam 查询请求对象，封装需要查询的条件和页码等信息
     * @return 返回一页查询结果
     */
    @RequestMapping(value = "api/PubCoooperate/GetResourceInformationByQuery", method = RequestMethod.POST)
    @ResponseBody
    public PageBean getResourceInformationByQuery(@RequestBody PubCoooperateQueryParam queryParam) {
        return service.getResourceInformationByQuery(queryParam);
    }

    /**
     * <p>分页查询联盟资源</p>
     *
     * @param queryParam 查询请求对象，封装需要查询的条件和页码等信息
     * @return 返回一页查询结果
     */
    @RequestMapping(value = "api/PubCoooperate/GetDriverInformationByQuery", method = RequestMethod.POST)
    @ResponseBody
    public PageBean getDriverInformationByQuery(@RequestBody PubCoooperateQueryParam queryParam) {
        return service.getDriverInformationByQuery(queryParam);
    }
    /**
     * <p></p>
     *
     * @param queryParam 查询请求对象，封装需要查询的条件和页码等信息
     * @return
     */
    @RequestMapping(value = "api/PubCoooperate/GetModelsList", method = RequestMethod.POST)
    @ResponseBody
    public List<DriverInformationDto> getModelsList(@RequestBody PubCoooperateQueryParam queryParam) {
        return service.getModelsList(queryParam);
    }
    /**
     * <p></p>
     *
     * @param
     * @return
     */
    @RequestMapping(value = "api/PubCoooperate/GetOriginalModels", method = RequestMethod.POST)
    @ResponseBody
    public DriverInformationDto getOriginalModels(@RequestBody Map<String, String> map){
        return service.getOriginalModels(map);
    };

    /**
     * <p></p>
     *
     * @param
     * @return
     */
    @RequestMapping(value = "api/PubCoooperate/GetLeVehiclemodels", method = RequestMethod.POST)
    @ResponseBody
    public List<LeVehiclemodels> getLeVehiclemodels(@RequestBody Map<String, String> map){
        return service.getLeVehiclemodels(map);
    };
    /**
     * <p>新增</p>
     * @return 返回一个map的结果
     */
    @RequestMapping(value = "api/PubCoooperate/CreatePubVehicleModelsRef", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> createPubVehicleModelsRef(@RequestBody PubVehicleModelsRef p){
        return service.createPubVehicleModelsRef(p);
    };
    /**
     * <p>修改</p>
     * @return 返回一个map的结果
     */
    @RequestMapping(value = "api/PubCoooperate/UpdatePubVehicleModelsRef", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updatePubVehicleModelsRef(@RequestBody PubVehicleModelsRef p){
        return service.updatePubVehicleModelsRef(p);
    };
}
