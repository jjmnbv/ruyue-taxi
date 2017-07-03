package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class LeVehiclemodelsController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeVehiclemodelsController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/LeVehiclemodels/Index")
	public ModelAndView getLeVehiclemodelsIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
        mav.setViewName("resource/leVehiclemodels/index");  
        return mav; 
	}

	@RequestMapping("/LeVehiclemodels/GetLeVehiclemodelsByQuery")
	@ResponseBody
	public PageBean getLeVehiclemodelsByQuery(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		queryParam.setKey(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/GetLeVehiclemodelsByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/LeVehiclemodels/CreateLeVehiclemodels")
	@ResponseBody
	public Map<String, String> createLeVehiclemodels(@RequestBody LeVehiclemodels leVehiclemodels, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leVehiclemodels.setId(GUIDGenerator.newGUID());
		leVehiclemodels.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		leVehiclemodels.setCreater(this.getLoginLeUser(request).getId());
		leVehiclemodels.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/CreateLeVehiclemodels", HttpMethod.POST, userToken, leVehiclemodels,
				Map.class);
	}
	
	@RequestMapping("/LeVehiclemodels/UpdateLeVehiclemodels")
	@ResponseBody
	public Map<String, String> updatePubVehcline(@RequestBody LeVehiclemodels leVehiclemodels, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leVehiclemodels.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		leVehiclemodels.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/UpdateLeVehiclemodels", HttpMethod.POST, userToken, leVehiclemodels,
				Map.class);
	}
	
	@RequestMapping("/LeVehiclemodels/GetById")
	@ResponseBody
	public LeVehiclemodels getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/GetById/{id}", HttpMethod.GET, userToken, null,
				LeVehiclemodels.class, id);
	}
	
	@RequestMapping("/LeVehiclemodels/DeleteLeVehiclemodels")
	@ResponseBody
	public Map<String, String> deleteLeVehiclemodels(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/DeleteLeVehiclemodels/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}
	@RequestMapping("/LeVehiclemodels/UploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		 Map<String, Object> res = FileUtil.upload2FileServer(request,response);
//		 res.put("basepath", SystemConfig.getSystemProperty("fileserver"));
//		 return res;
		
		response.setContentType("text/html;charset=utf-8");
		JSONObject result = new JSONObject();
		
		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("file");
		if (!mulfile.isEmpty()) { 
			String fileName = mulfile.getOriginalFilename();
			String fileName1 = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			Map<String, Object> res = new HashMap<>();
			if(fileName1.equals("jpg") || fileName1.equals("png") || fileName1.equals("jpeg") || fileName1.equals("gif")){
				long size = mulfile.getSize();
				if(size > 5*1024*1024L){
					result.put("error", "您选择的图片大小超过限制5M，上传失败，请压缩后重新上传。");
				}else{
					res = FileUtil.upload2FileServer(request,response);
		//			res.put("basepath", SystemConfig.getSystemProperty("fileserver"));
					result.put("status", res.get("status"));
					result.put("message", res.get("message"));
					result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
				}
			}else{
				result.put("error", "请上传正确的文件格式");
			}
		}
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping("/LeVehiclemodels/GetPubVehcline")
	@ResponseBody
	public JSONObject getPubVehcline(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/GetPubVehcline/{leasesCompanyId}", HttpMethod.GET, userToken, null,
				JSONObject.class,leasesCompanyId);
	}
	
	@RequestMapping("/LeVehiclemodels/SaveLeVehclineModelsRefMapper")
	@ResponseBody
	public Map<String, String> saveLeVehclineModelsRefMapper(@RequestBody Map map,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		map.put("creater", this.getLoginLeUser(request).getId());
		map.put("updater", this.getLoginLeUser(request).getId());
		map.put("leasesCompanyId", this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/LeVehclineModelsRef/CreateLeVehclineModelsRef", HttpMethod.POST, userToken, map,
				Map.class);
	}
	
	@RequestMapping(value = "/LeVehiclemodels/CheckLeVehiclemodelsName")
	@ResponseBody
	public int checkOrgOrganFullName(@RequestParam String id,@RequestParam String name,HttpServletRequest request) {
		LeVehiclemodels leVehiclemodels = new LeVehiclemodels();
		leVehiclemodels.setId(id);
		leVehiclemodels.setName(name);
		leVehiclemodels.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/CheckLeVehiclemodels", HttpMethod.POST, userToken, leVehiclemodels,
				int.class);
	}
	
	@RequestMapping(value = "/LeVehiclemodels/CheckDisable")
	@ResponseBody
	public int checkDisable(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/CheckDisable/{id}", HttpMethod.GET, userToken, null,
				int.class,id);
	}
	
	@RequestMapping("/LeVehiclemodels/UpdateEnableOrDisable")
	@ResponseBody
	public Map<String, String> updateEnableOrDisable(@RequestParam String modelstatus,@RequestParam String id,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		LeVehiclemodels leVehiclemodels = new LeVehiclemodels(); 
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leVehiclemodels.setUpdater(this.getLoginLeUser(request).getId());
		leVehiclemodels.setId(id);
		leVehiclemodels.setModelstatus(modelstatus);
		return templateHelper.dealRequestWithToken("/LeVehiclemodels/UpdateEnableOrDisable", HttpMethod.POST, userToken, leVehiclemodels,
				Map.class);
	}
}
