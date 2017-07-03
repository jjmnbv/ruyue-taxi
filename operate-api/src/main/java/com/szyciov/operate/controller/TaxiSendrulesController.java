package com.szyciov.operate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.operate.service.TaxiSendrulesService;
import com.szyciov.util.PageBean;

/**
 * 出租车派单规则管理
 * @author shikang_pc
 *
 */
@Controller
public class TaxiSendrulesController {
	
	private TaxiSendrulesService sendrulesService;
	@Resource(name = "TaxiSendrulesService")
	public void setSendrulesService(TaxiSendrulesService sendrulesService) {
		this.sendrulesService = sendrulesService;
	}
	
	/**
	 * 分页查询出租车派单规则
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetTaxiSendrulesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiSendrulesByQuery(@RequestBody OpTaxiSendrulesQueryParam queryParam) {
		return sendrulesService.getTaxiSendrulesByQuery(queryParam);
	}
	
	/**
	 * 查询已有派单规则中国的所有城市(select2)
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetTaxiSendrulesCityBySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getTaxiSendrulesCityBySelect(@RequestBody Map<String, String> params) {
		return sendrulesService.getTaxiSendrulesCityBySelect(params);
	}
	
	/**
	 * 查询出租车派单规则详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetTaxiSendrulesById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpTaxisendrules getTaxiSendrulesById(@PathVariable String id) {
		return sendrulesService.getTaxiSendrulesById(id);
	}
	
	/**
	 * 查询平台出租车司机+加入toC的出租车司机
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetTaxiDriverCount", method = RequestMethod.POST)
	@ResponseBody
	public int getTaxiDriverCount() {
		return sendrulesService.getTaxiDriverCount();
	}
	
	/**
	 * 添加出租车派单规则
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/AddOpTaxiSendrules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addOpTaxiSendrules(@RequestBody OpTaxisendrules object) {
		return sendrulesService.addOpTaxiSendrules(object);
	}
	
	/**
	 * 修改出租车派单规则
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/EditOpTaxiSendrules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editOpTaxiSendrules(@RequestBody OpTaxisendrules object) {
		return sendrulesService.editOpTaxiSendrules(object);
	}
	
	/**
	 * 修改出租车派单规则启用禁用状态
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/UpdateTaxiSendrulesState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateTaxiSendrulesState(@RequestBody OpTaxisendrules object) {
		return sendrulesService.updateTaxiSendrulesState(object);
	}
	
	/**
	 * 分页查询出租车派单规则历史记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetTaxiSendrulesHistoryByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiSendrulesHistoryByQuery(@RequestBody OpTaxiSendrulesHistoryQueryParam queryParam) {
		return sendrulesService.getTaxiSendrulesHistoryByQuery(queryParam);
	}
	
	/**
	 * 获取运管端平台所属城市人工+系统派单规则数量
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/TaxiSendrules/GetOpSendmodelCountByUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> getOpSendmodelCountByUser(@RequestBody OpUser object) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("taxiSendruleCount", sendrulesService.getOpSendmodelCountByUser(object));
		return result;
	}
	
}
