package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.PubAdImage;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.PubAdimageService;
import com.szyciov.operate.service.PubDictionaryService;
import com.szyciov.param.PubAdimageQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.FileUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;

import net.sf.json.JSONObject;

/**
 * 广告图片信息管理
 */
@Controller
public class PubAdimageController extends BaseController {
	
	private PubAdimageService adimageService;
	@Resource(name = "PubAdimageService")
	public void setAdimageService(PubAdimageService adimageService) {
		this.adimageService = adimageService;
	}
	
	private PubDictionaryService dictionaryService;
	@Resource(name = "PubDictionaryService")
	public void setDictionaryService(PubDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	/**
	 * 跳转到广告配置主页面
	 * @return
	 */
	@RequestMapping(value = "PubAdvise/Index")
	public ModelAndView getPubAdviseIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubAdvise/index");
		//从字典表中查询终端类型
		List<PubDictionary> apptypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		view.addObject("apptypeList", apptypeList);
		view.addObject("usertype", user.getUsertype());
		return view;
	}
	
	/**
	 * 分页查询广告图片信息
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdimage/GetPubAdimageByQuery")
	@ResponseBody
	public PageBean getPubAdimageByQuery(@RequestBody PubAdimageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return adimageService.getPubAdimageByQuery(queryParam, userToken);
	}
	
	/**
	 * 跳转到广告配置修改页
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdvise/GetEditPubAdvisePage")
	@ResponseBody
	public ModelAndView getEditPubAdvisePage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubAdvise/editPubAdvise");
		//从字典表中查询终端类型
		List<PubDictionary> apptypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		//查询广告配置详情
		PubAdImage adimage = null;
		if(StringUtils.isNotBlank(id)) {
			adimage = new PubAdImage();
			adimage.setId(id);
			List<PubAdImage> adimageList = adimageService.getPubAdimageList(adimage, userToken);
			if(null != adimageList && !adimageList.isEmpty()) {
				view.addObject("adimage", adimageList.get(0));
			}
		}
		view.addObject("apptypeList", apptypeList);
		return view;
	}
	
	/**
	 * 添加广告图片信息
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdimage/CreatePubAdimage")
	@ResponseBody
	public Map<String, String> createPubAdimage(@RequestBody PubAdImage object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setCreater(user.getId());
		object.setUpdater(user.getId());
		return adimageService.createPubAdimage(object, userToken);
	}
	
	/**
	 * 修改广告图片信息
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdimage/EditPubAdimage")
	@ResponseBody
	public Map<String, String> editPubAdimage(@RequestBody PubAdImage object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setUpdater(user.getId());
		return adimageService.editPubAdimage(object, userToken);
	}
	
	/**
	 * 跳转到广告详情页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdvise/GetPubAdviseByDetail/{id}")
	public ModelAndView getPubAdviseByDetail(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubAdvise/detailPubAdvise");
		//查询广告配置详情
		PubAdImage adimage = new PubAdImage();
		adimage.setId(id);
		List<PubAdImage> adimageList = adimageService.getPubAdimageList(adimage, userToken);
		if(null != adimageList && !adimageList.isEmpty()) {
			view.addObject("adimage", adimageList.get(0));
		}
		view.addObject("basepath", SystemConfig.getSystemProperty("fileserver"));
		return view;
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdimage/UploadFile")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		JSONObject result = new JSONObject();
		Map<String, Object> res = FileUtil.upload2FileServer(request,response);
		if(null != res && !res.isEmpty()) {
			result.put("status", res.get("status"));
			result.put("message", res.get("message"));
			result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
		} else {
			result.put("status", "fail");
		}
		response.getWriter().write(result.toString());
	}
	
	/**
	 * 修改广告配置状态
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubAdimage/EditPubAdimageUsestate")
	@ResponseBody
	public Map<String, String> editPubAdimageUsestate(@RequestBody PubAdImage object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setUpdater(user.getId());
		if(object.getUsestate().equals("0")) {
			return adimageService.editPubAdviseToStart(object, userToken);
		} else {
			return adimageService.editPubAdimage(object, userToken);
		}
	}
	
	/**
	 * 跳转到引导页配置主页面
	 * @return
	 */
	@RequestMapping(value = "PubGuide/Index")
	public ModelAndView getPubGuideIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubGuide/index");
		//从字典表中查询终端类型
		List<PubDictionary> apptypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		view.addObject("apptypeList", apptypeList);
		view.addObject("usertype", user.getUsertype());
		return view;
	}
	
	/**
	 * 跳转到引导页配置修改页
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubGuide/GetEditPubGuidePage")
	@ResponseBody
	public ModelAndView getEditPubGuidePage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/pubGuide/editPubGuide");
		//从字典表中查询终端类型
		List<PubDictionary> apptypeList = dictionaryService.getPubDictionary("终端类型", userToken);
		//查询广告配置详情
		PubAdImage adimage = null;
		if(StringUtils.isNotBlank(id)) {
			adimage = new PubAdImage();
			adimage.setId(id);
			List<PubAdImage> adimageList = adimageService.getPubAdimageList(adimage, userToken);
			if(null != adimageList && !adimageList.isEmpty()) {
				view.addObject("adimage", adimageList.get(0));
			}
		}
		view.addObject("apptypeList", apptypeList);
		return view;
	}

}
