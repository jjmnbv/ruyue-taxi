package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.operate.service.OpVehiclemodelsService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 运营端服务车型信息管理
 */
@Controller
public class OpVehiclemodelsController extends BaseController {
	
	private OpVehiclemodelsService opVehiclemodelsService;
	@Resource(name = "OpVehiclemodelsService")
	public void setOpVehiclemodelsService(OpVehiclemodelsService opVehiclemodelsService) {
		this.opVehiclemodelsService = opVehiclemodelsService;
	}
	
	/**
	 * 分页查询服务车型列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/GetOpVehiclemodelsByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpVehiclemodelsByQuery(@RequestBody QueryParam queryParam) {
		return opVehiclemodelsService.getOpVehiclemodelsByQuery(queryParam);
	}
	
	/**
	 * 查询所有车系
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/GetPubVehcline", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getPubVehcline() {
		return opVehiclemodelsService.getPubVehcline();
	}
	
	/**
	 * 添加车型
	 * @param opVehiclemodels
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/CreateOpVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOpVehiclemodels(@RequestBody OpVehiclemodels opVehiclemodels) {
		return opVehiclemodelsService.createOpVehiclemodels(opVehiclemodels);
	}
	
	/**
	 * 修改车型
	 * @param opVehiclemodels
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/UpdateOpVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateOpVehiclemodels(@RequestBody OpVehiclemodels opVehiclemodels) {
		return opVehiclemodelsService.editOpVehiclemodels(opVehiclemodels);
	}
	
	/**
	 * 查询服务车型详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/GetOpVehiclemodelsById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpVehiclemodels getOpVehiclemodelsById(@PathVariable String id) {
		return opVehiclemodelsService.getOpVehiclemodelsById(id);
	}
	
	/**
	 * 删除服务车型
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OpVehiclemodels/DeleteOpVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteOpVehiclemodels(@RequestBody OpVehiclemodels object) {
		return opVehiclemodelsService.deleteOpVehiclemodels(object);
	}
	
	@RequestMapping(value = "api/OpVehiclemodels/getOpVehiclemodelsByList", method = RequestMethod.POST)
	@ResponseBody
	public List<OpVehiclemodels> getOpVehiclemodelsByList(@RequestBody OpVehiclemodels object) {
		return opVehiclemodelsService.getOpVehiclemodelsByList(object);
	}
	
	@RequestMapping(value = "api/OpVehiclemodels/SaveLeVehclineModelsRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveLeVehclineModelsRef(@RequestBody Map<String,Object> params) {
		return opVehiclemodelsService.saveLeVehclineModelsRef(params);
	}
	
	@RequestMapping(value = "api/OpVehiclemodels/ChangeState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeState(@RequestBody Map<String,Object> params) {
		return opVehiclemodelsService.changeState(params);
	}
	
	
	@RequestMapping(value = "api/OpVehiclemodels/HasBindLeaseCars", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> hasBindLeaseCars(@RequestBody Map<String,Object> params) {
		return opVehiclemodelsService.hasBindLeaseCars(params);
	}
}
