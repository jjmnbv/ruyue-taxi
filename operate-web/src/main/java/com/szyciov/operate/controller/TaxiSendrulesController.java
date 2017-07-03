package com.szyciov.operate.controller;

import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.operate.service.TaxiSendrulesService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出租车派单规则管理
 * @author shikang_pc
 *
 */
@Controller
public class TaxiSendrulesController extends BaseController {
	
	private TaxiSendrulesService sendrulesService;
	@Resource(name = "TaxiSendrulesService")
	public void setSendrulesService(TaxiSendrulesService sendrulesService) {
		this.sendrulesService = sendrulesService;
	}
	
	/**
	 * 出租车派单管理首页
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/Index")
	public ModelAndView taxiSendrulesIndex(HttpServletRequest request, HttpServletResponse response) {
		OpUser opUser = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.addObject("usertype", opUser.getUsertype());
		view.setViewName("resource/taxiSendrules/index");
		return view;
	}
	
	/**
	 * 分页查询出租车派单管理
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/GetTaxiSendrulesByQuery")
	@ResponseBody
	public PageBean getTaxiSendrulesByQuery(@RequestBody OpTaxiSendrulesQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return sendrulesService.getTaxiSendrulesByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询已有派单规则中国的所有城市(select2)
	 * @param cityname
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/GetTaxiSendrulesCityBySelect")
	@ResponseBody
	public List<Map<String, String>> getTaxiSendrulesCityBySelect(@RequestParam(value = "cityname", required = false) String cityname, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("cityname", cityname);
		return sendrulesService.getTaxiSendrulesCityBySelect(params, userToken);
	}
	
	/**
	 * 跳转到派单规则编辑页
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/EditRulesPage")
	public ModelAndView editRulesPage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/taxiSendrules/editRules");
		
		String userToken = getUserToken(request);
		//查询平台出租车司机+加入toC的出租车司机
		int taxiDriverCount = sendrulesService.getTaxiDriverCount(userToken);
		view.addObject("taxiDriverCount", taxiDriverCount);
		
		if(StringUtils.isBlank(id)) { //新增数据
			return view;
		} else { //修改数据
			OpTaxisendrules sendrules = sendrulesService.getTaxiSendrulesById(id, userToken);
			view.addObject("sendrules", sendrules);
			return view;
		}
	}
	
	/**
	 * 添加出租车派单规则
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/AddTaxiSendRules")
	@ResponseBody
	public Map<String, String> addTaxiSendRules(@RequestBody OpTaxisendrules object, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		if(!"1".equals(opUser.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setCreater(opUser.getId());
		object.setUpdater(opUser.getId());
		return sendrulesService.addOpTaxiSendrules(object, userToken);
	}
	
	/**
	 * 修改出租车派单规则
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/EditTaxiSendRules")
	@ResponseBody
	public Map<String, String> editTaxiSendRules(@RequestBody OpTaxisendrules object, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		if(!"1".equals(opUser.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setUpdater(opUser.getId());
		return sendrulesService.editOpTaxiSendrules(object, userToken);
	}
	
	/**
	 * 修改出租车派单规则启用禁用状态
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/UpdateTaxiSendrulesState")
	@ResponseBody
	public Map<String, String> updateTaxiSendrulesState(@RequestBody OpTaxisendrules object, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		if(!"1".equals(opUser.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setUpdater(opUser.getId());
		return sendrulesService.updateTaxiSendrulesState(object, userToken);
	}
	
	/**
	 * 跳转到出租车派单规则历史记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/TaxiSendrulesHistoryIndex/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView taxiSendrulesHistoryIndex(@PathVariable String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String userToken = getUserToken(request);
		view.addObject("taxiSendrules", sendrulesService.getTaxiSendrulesById(id, userToken));
		view.setViewName("resource/taxiSendrules/history");
		return view;
	}
	
	/**
	 * 分页查询出租车派单规则历史记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiSendrules/GetTaxiSendrulesHistoryByQuery")
	@ResponseBody
	public PageBean getTaxiSendrulesHistoryByQuery(@RequestBody OpTaxiSendrulesHistoryQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return sendrulesService.getTaxiSendrulesHistoryByQuery(queryParam, userToken);
	}
	
}
