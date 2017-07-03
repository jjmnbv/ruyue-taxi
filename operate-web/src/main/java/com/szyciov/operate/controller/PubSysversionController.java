package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.PubDictionaryService;
import com.szyciov.operate.service.PubSysversionService;
import com.szyciov.param.PubSysversionQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;

/**
 * 版本说明管理
 */
@Controller
public class PubSysversionController extends BaseController {
	
	private PubSysversionService sysversionService;
	@Resource(name = "PubSysversionService")
	public void setSysversionService(PubSysversionService sysversionService) {
		this.sysversionService = sysversionService;
	}
	
	private PubDictionaryService dictionaryService;
	@Resource(name = "PubDictionaryService")
	public void setDictionaryService(PubDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	/**
	 * 跳转到版本说明主页面
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/Index")
	@ResponseBody
	public ModelAndView getPubSysversionIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubSysversion/index");
		//从字典表中查询App类型
		List<PubDictionary> platformtypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		//查询版本说明中已存在的系统类型
		List<PubDictionary> systemtypeList = sysversionService.getSystemtypeByList(userToken);
		view.addObject("platformtypeList", platformtypeList);
		view.addObject("systemtypeList", systemtypeList);
		view.addObject("usertype", user.getUsertype());
		return view;
	}
	
	/**
	 * 查询版本说明中已存在的系统版本
	 * @param curversion
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/GetCurversionByList")
	@ResponseBody
	public List<Map<String, String>> getCurversionByList(@RequestParam(value = "curversion", required = false) String curversion, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubSysVersion object = new PubSysVersion();
		object.setCurversion(curversion);
		return sysversionService.getCurversionByList(object, userToken);
	}
	
	/**
	 * 分页查询版本说明数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/GetPubSysversionByQuery")
	@ResponseBody
	public PageBean getPubSysversionByQuery(@RequestBody PubSysversionQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return sysversionService.getPubSysversionByQuery(queryParam, userToken);
	}
	
	/**
	 * 跳转到版本说明编辑页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/GetEditPubSysversionPage")
	@ResponseBody
	public ModelAndView getEditPubSysversionPage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubSysversion/editPubSysversion");
		//从字典表中查询App类型
		List<PubDictionary> platformtypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		//从字典表中查询系统类型
		List<PubDictionary> systemtypeList = dictionaryService.getPubDictionary("系统类型", userToken);
		//根据id查询版本说明详情
		if(StringUtils.isNotBlank(id)) {
			List<PubSysVersion> sysVersionList = sysversionService.getPubSysversionById(id, userToken);
			if(null != sysVersionList && !sysVersionList.isEmpty()) {
				view.addObject("sysversion", sysVersionList.get(0));
			}
		}
		view.addObject("platformtypeList", platformtypeList);
		view.addObject("systemtypeList", systemtypeList);
		return view;
	}
	
	/**
	 * 添加版本说明
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/CreatePubSysversion")
	@ResponseBody
	public Map<String, String> createPubSysversion(@RequestBody PubSysVersion object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return sysversionService.createPubSysversion(object, userToken);
	}
	
	/**
	 * 修改版本说明
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/EditPubSysversion")
	@ResponseBody
	public Map<String, String> editPubSysversion(@RequestBody PubSysVersion object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return sysversionService.editPubSysversion(object, userToken);
	}
	
	/**
	 * 删除版本说明
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubSysversion/DeletePubSysversion")
	@ResponseBody
	public Map<String, String> deletePubSysversion(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubSysVersion object = new PubSysVersion();
		object.setId(id);
		object.setStatus(2);
		return sysversionService.deletePubSysversion(object, userToken);
	}
	
}
