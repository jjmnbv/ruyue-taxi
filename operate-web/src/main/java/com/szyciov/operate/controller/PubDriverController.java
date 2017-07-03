package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
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

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.param.PubDriverQueryParam;
import com.szyciov.op.param.PubDriverSelectParam;
import com.szyciov.op.param.ServiceOrgQueryParam;
import com.szyciov.operate.service.PubDriverService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PubDriverController extends BaseController {

	private PubDriverService driverService;
	@Resource(name = "PubDriverService")
	public void setDriverService(PubDriverService driverService) {
		this.driverService = driverService;
	}
	private TemplateHelper templateHelper = new TemplateHelper();




	@RequestMapping(value = "/PubDriver/Index")
	public ModelAndView getPubDriverIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> workStatus = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=服务状态", HttpMethod.GET	,
				userToken, null, List.class,"服务状态");
		mav.addObject("workStatus", workStatus);
		mav.setViewName("resource/pubDriver/index");
		return mav;
	}

	@RequestMapping(value = "/PubDriver/GetCity")
	@ResponseBody
	public List<Map<String, Object>> getCity(@RequestParam String queryCity,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam pubDriverQueryParam = new PubDriverQueryParam();
		pubDriverQueryParam.setPlatformType(PlatformTypeByDb.OPERATING.code);
		pubDriverQueryParam.setQueryCity(queryCity);
		return templateHelper.dealRequestWithToken("/PubDriver/GetCity", HttpMethod.POST	,
				userToken, pubDriverQueryParam, List.class);
	}

	@RequestMapping(value = "/PubDriver/GetSpecialDri")
	@ResponseBody
	public List<Map<String, Object>> getSpecialDri(@RequestParam String queryServiceOrg,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam pubDriverQueryParam = new PubDriverQueryParam();
//		pubDriverQueryParam.setLeasesCompanyId(leasesCompanyId);
		pubDriverQueryParam.setQueryServiceOrg(queryServiceOrg);
		pubDriverQueryParam.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/GetSpecialDri", HttpMethod.POST	,
				userToken, pubDriverQueryParam, List.class);
	}

	@RequestMapping("/PubDriver/GetPubDriverByQuery")
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubDriverQueryParam pubDriverQueryParam, HttpServletRequest request,
										HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubDriverQueryParam.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/GetPubDriverByQuery", HttpMethod.POST, userToken,
				pubDriverQueryParam,PageBean.class);
	}

	@RequestMapping("/PubDriver/GetServiceOrgByQuery")
	@ResponseBody
	public PageBean getServiceOrgByQuery(@RequestBody ServiceOrgQueryParam serviceOrgQueryParam, HttpServletRequest request,
										 HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/GetServiceOrgByQuery", HttpMethod.POST, userToken,
				serviceOrgQueryParam,PageBean.class);
	}

	@RequestMapping(value = "/PubDriver/AddIndex")
	public ModelAndView AddIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver pubDriver = new PubDriver();
		if(request.getParameter("id")!=null){
			pubDriver = templateHelper.dealRequestWithToken("/PubDriver/GetById/{id}", HttpMethod.GET, userToken, null,
					PubDriver.class, (String)request.getParameter("id"));
			mav.addObject("pubDriver", pubDriver);
		}
		List<City> pubCityaddr = templateHelper.dealRequestWithToken("/PubVehicle/GetPubCityaddr", HttpMethod.GET,
				userToken, null, List.class);
		List<Dictionary> driversType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=驾驶类型", HttpMethod.GET,
				userToken, null, List.class,"驾驶类型");
//		List<OrgOrgan> orgOrganShortName = templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganShortName/{leasesCompanyId}", HttpMethod.GET,
//				userToken, null, List.class,leasesCompanyId);
//		List<OrgOrgan> orgOrganCity = templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganCity/{leasesCompanyId}", HttpMethod.GET,
//				userToken, null, List.class,leasesCompanyId);
		mav.addObject("pubCityaddr", pubCityaddr);
		mav.addObject("driversType", driversType);
//		mav.addObject("orgOrganShortName", orgOrganShortName);
//		mav.addObject("orgOrganCity", orgOrganCity);
		if(request.getParameter("particulars")!=null){
			mav.setViewName("resource/pubDriver/particulars");
		}else{
			mav.addObject("title", request.getParameter("title"));
			mav.setViewName("resource/pubDriver/editDriver");
		}
		return mav;
	}

	@RequestMapping("/PubDriver/CreatePubDriver")
	@ResponseBody
	public Map<String, String> createPubDriver(@RequestBody PubDriver pubDriver, HttpServletRequest request,
											   HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubDriver.setId(GUIDGenerator.newGUID());
		OpUser user = this.getLoginOpUser(request);
		pubDriver.setCreater(user.getId());
		pubDriver.setUpdater(user.getId());
		pubDriver.setLeasesCompanyId(user.getOperateid());
		pubDriver.setPlatformType(PlatformTypeByDb.OPERATING.code);
		//验证手机实名制认证
		Map<String,String> ret = this.driverService.phoneAuthentication(request,pubDriver);
		//如果验证失败，则直接返回
		if(ret!=null){
			return ret;
		}

		return templateHelper.dealRequestWithToken("/PubDriver/CreatePubDriver", HttpMethod.POST, userToken, pubDriver,
				Map.class);
	}






	@RequestMapping("/PubDriver/UpdatePubDriver")
	@ResponseBody
	public Map<String, String> updatePubDriver(@RequestBody PubDriver pubDriver, HttpServletRequest request,
											   HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = this.getLoginOpUser(request);
		pubDriver.setUpdater(user.getId());
		pubDriver.setPlatformType(PlatformTypeByDb.OPERATING.code);

		//验证手机实名制认证
		Map<String,String> ret = this.driverService.phoneAuthentication(request,pubDriver);
		//如果验证失败，则直接返回
		if(ret!=null){
			return ret;
		}

		return templateHelper.dealRequestWithToken("/PubDriver/UpdatePubDriver", HttpMethod.POST, userToken, pubDriver,
				Map.class);
	}

	@RequestMapping("/PubDriver/GetById")
	@ResponseBody
	public PubDriver getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/GetById/{id}", HttpMethod.GET, userToken, null,
				PubDriver.class, id);
	}


	//修改删除  验证是同意个方法  就调用一个
	@RequestMapping("/PubDriver/CheckDelete")
	@ResponseBody
	public int checkDelete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckDelete/{id}", HttpMethod.GET, userToken, null,
				int.class, id);
	}

	@RequestMapping("/PubDriver/DeletePubDriver")
	@ResponseBody
	public Map<String, String> deletePubVehcbrand(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/DeletePubDriver/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}

	@RequestMapping("/PubDriver/UploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		JSONObject result = new JSONObject();

		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("file");

		String fileName = mulfile.getOriginalFilename();
		String fileName1 = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		Map<String, Object> res = new HashMap<>();
		if(fileName1.equals("jpg") || fileName1.equals("png") || fileName1.equals("jpeg") || fileName1.equals("gif")){
			res = FileUtil.upload2FileServer(request,response);
			result.put("status", res.get("status"));
			result.put("message", res.get("message"));
			result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
		}else{
			result.put("status","erro");
			result.put("error", "请上传正确的文件格式");
		}
		if(mulfile.getSize()>5*1024*1024){
			result.put("status", "erro");
			result.put("error", "请上传小于1M的照片！");
		}
		response.getWriter().write(result.toString());
	}

	@RequestMapping("/PubDriver/ResetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestParam String id,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/ResetPassword/{id}", HttpMethod.GET, userToken, null,
				Map.class, id);
	}

	@RequestMapping("/PubDriver/CheckPubDriverJobNum")
	@ResponseBody
	public int checkPubDriverJobNum(@RequestParam String id,@RequestParam String jobNum,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver p = new PubDriver();
		p.setId(id);
		p.setJobNum(jobNum);
		p.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckPubDriver", HttpMethod.POST, userToken, p,
				int.class);
	}

	@RequestMapping("/PubDriver/CheckPubDriverPhone")
	@ResponseBody
	public int checkPubDriverPhone(@RequestParam String id,@RequestParam String phone,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver p = new PubDriver();
		p.setId(id);
		p.setPhone(phone);
		p.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckPubDriverPhone", HttpMethod.POST, userToken, p,
				int.class);
	}

	@RequestMapping("/PubDriver/CheckPubDriverDriversNum")
	@ResponseBody
	public int checkPubDriverDriversNum(@RequestParam String id,@RequestParam String driversNum,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver p = new PubDriver();
		p.setId(id);
		p.setDriversNum(driversNum);
		p.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckPubDriver",
				HttpMethod.POST, userToken, p,
				int.class);
	}

	@RequestMapping("/PubDriver/CheckPubDriverIdCardNum")
	@ResponseBody
	public int checkPubDriverIdCardNum(@RequestParam String id,@RequestParam String idCardNum,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver p = new PubDriver();
		p.setId(id);
		p.setIdCardNum(idCardNum);
		p.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckPubDriver", HttpMethod.POST, userToken, p,
				int.class);
	}

	@RequestMapping("/PubDriver/CheckUnbundling")
	@ResponseBody
	public int checkUnbundling(@RequestParam String id,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriver/CheckUnbundling/{driverId}", HttpMethod.GET, userToken, null,
				int.class,id);
	}

	@RequestMapping("/PubDriver/ExportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		String queryKeyword = request.getParameter("driverId");
		String queryWorkStatus = request.getParameter("queryWorkStatus");
		String queryCity = request.getParameter("queryCity");
		String queryJobStatus = request.getParameter("queryJobStatus");
		String queryVehicleType = request.getParameter("queryVehicleType");
		String queryBoundState = request.getParameter("queryBoundState");
		String jobNum = request.getParameter("jobNum");
		PubDriverQueryParam pd = new PubDriverQueryParam();
		pd.setDriverId(queryKeyword);
		pd.setQueryWorkStatus(queryWorkStatus);
		pd.setQueryCity(queryCity);
		pd.setQueryJobStatus(queryJobStatus);
		pd.setQueryVehicleType(queryVehicleType);
		pd.setQueryBoundState(queryBoundState);
		pd.setPlatformType(PlatformTypeByDb.OPERATING.code);
		pd.setJobNum(jobNum);

		Excel excel = this.driverService.exportExcel(pd,userToken);

		File tempFile = new File("司机管理.xls");

		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}


	/**
	 * 查询司机列表(select2)
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/PubDriver/listPubDriverBySelect")
	@ResponseBody
	public JSONArray listPubDriverBySelect(@RequestParam Map<String,String> paramMap, HttpServletRequest request) {

		String usertoken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setVehicletype(paramMap.get("vehicletype"));
		param.setUserid(user.getId());
		if(!paramMap.containsKey("toC")) {
			param.setToC("1");
		} else {
			param.setToC(paramMap.get("toC"));
		}
		param.setJobstatus(paramMap.get("jobstatus"));
        param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		JSONObject jsonObject = driverService.listPubDriverBySelect(param, usertoken);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}

	/**
	 * 查询司机列表(select2)
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/PubDriver/listPubDriverBySelectJobNum")
	@ResponseBody
	public JSONArray listPubDriverBySelectJobNum(@RequestParam Map<String,String> paramMap, HttpServletRequest request) {

		String usertoken = getUserToken(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		param.setJobstatus(paramMap.get("jobstatus"));

		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubDriver/listPubDriverBySelectJobNum",
				HttpMethod.POST, usertoken, param, JSONObject.class);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}


}
