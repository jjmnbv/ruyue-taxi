package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.operate.service.OpVehiclemodelsService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.FileUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;

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
	 * 跳转到服务车型信息主页面
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/Index")
	@ResponseBody
	public ModelAndView getOpVehiclemodelsIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.addObject("usertype", user.getUsertype());
		view.setViewName("resource/opVehiclemodels/index");
		return view;
	}
	
	/**
	 * 分页查询服务车型列表
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/GetOpVehiclemodelsByQuery")
	@ResponseBody
	public PageBean getOpVehiclemodelsByQuery(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opVehiclemodelsService.getOpVehiclemodelsByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询所有车系
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/GetPubVehcline")
	@ResponseBody
	public JSONObject getPubVehcline(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opVehiclemodelsService.getPubVehcline(userToken);
	}
	
	/**
	 * 上传车型logo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/UploadFile")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			return;
		}
		JSONObject result = new JSONObject();
		MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");
		String maxFileSizeStr = SystemConfig.getSystemProperty("maxFileSize");
		int maxFileSize = 5;
		if(file!=null){
			long filesize = file.getSize();
			long filesizem = filesize/(1024*1024);
			if(maxFileSizeStr != null && !"".equals(maxFileSizeStr)) {
				maxFileSize = Integer.parseInt(maxFileSizeStr);
			}

			if(filesizem>maxFileSize){
				result.put("status", "fail");
				result.put("message", "文件大小超过限制，请确保文件大小小于"+maxFileSize+"M!");
				response.getWriter().write(result.toString());
				return ;
			}
		}
		Map<String, Object> res = FileUtil.upload2FileServer(request,response);
		if(null != res && !res.isEmpty()) {
			result.put("status", res.get("status"));
			result.put("message", res.get("message"));
			result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
		} else {
			result.put("status", "fail");
			if(result.has("type")){
				if("filetoolarge".equals(result.getString("type"))){
					//文件太大
					result.put("message", "文件大小超过限制，请确保文件大小小于"+maxFileSize+"M!");
				}
			}else{
				result.put("message", "上传失败!");
			}
		}
		response.getWriter().write(result.toString());
	}
	
	/**
	 * 添加车型
	 * @param opVehiclemodels
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/CreateOpVehiclemodels")
	@ResponseBody
	public Map<String, String> createOpVehiclemodels(@RequestBody OpVehiclemodels opVehiclemodels, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		opVehiclemodels.setCreater(user.getId());
		opVehiclemodels.setUpdater(user.getId());
		return opVehiclemodelsService.createOpVehiclemodels(opVehiclemodels, userToken);
	}
	
	/**
	 * 修改车型
	 * @param opVehiclemodels
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/UpdateOpVehiclemodels")
	@ResponseBody
	public Map<String, String> updateOpVehiclemodels(@RequestBody OpVehiclemodels opVehiclemodels, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		opVehiclemodels.setUpdater(user.getId());
		return opVehiclemodelsService.updateOpVehiclemodels(opVehiclemodels, userToken);
	}
	
	/**
	 * 查询车型服务详情
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/GetOpVehiclemodelsById")
	@ResponseBody
	public OpVehiclemodels getOpVehiclemodelsById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return opVehiclemodelsService.getOpVehiclemodelsById(id, userToken);
	}
	
	/**
	 * 删除车型服务
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpVehiclemodels/DeleteOpVehiclemodels")
	@ResponseBody
	public Map<String, String> deleteOpVehiclemodels(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		OpVehiclemodels object = new OpVehiclemodels();
		object.setId(id);
		object.setUpdater(user.getId());
		return opVehiclemodelsService.deleteOpVehiclemodels(object, userToken);
	}
	
	@RequestMapping("OpVehiclemodels/SaveLeVehclineModelsRef")
	@ResponseBody
	public Map<String, String> saveLeVehclineModelsRef(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = this.getUserToken(request);
		params.put("creater", this.getLoginOpUser(request).getNickname());
		params.put("updater", this.getLoginOpUser(request).getNickname());
		return opVehiclemodelsService.saveLeVehclineModelsRef(params, userToken);
	}
	
	@RequestMapping("OpVehiclemodels/ChangeState")
	@ResponseBody
	public Map<String, String> changeState(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = this.getUserToken(request);
		params.put("creater", this.getLoginOpUser(request).getNickname());
		params.put("updater", this.getLoginOpUser(request).getNickname());
		return opVehiclemodelsService.changeState(params, userToken);
	}
	
	@RequestMapping("OpVehiclemodels/HasBindLeaseCars")
	@ResponseBody
	public Map<String, String> hasBindLeaseCars(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = this.getUserToken(request);
		params.put("creater", this.getLoginOpUser(request).getNickname());
		params.put("updater", this.getLoginOpUser(request).getNickname());
		return opVehiclemodelsService.hasBindLeaseCars(params, userToken);
	}
}
