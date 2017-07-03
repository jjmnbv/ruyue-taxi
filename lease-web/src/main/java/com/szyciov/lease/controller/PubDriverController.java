package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.lease.param.PubDriverSelectParam;
import com.szyciov.lease.param.ServiceOrgQueryParam;
import com.szyciov.param.PhoneAuthenticationParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.RequestUtils;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class PubDriverController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubDriverController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubDriver/Index")
	public ModelAndView getPubDriverIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> workStatus = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=服务状态", HttpMethod.GET	,
				userToken, null, List.class,"服务状态");
		List<Dictionary> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
				userToken, null, List.class,"租赁公司");
		mav.addObject("workStatus", workStatus);
		mav.addObject("belongleasecompany", belongleasecompany);
        mav.setViewName("resource/pubDriver/index");  
        return mav; 
	}
	
	@RequestMapping(value = "/PubDriver/GetCity")
	@ResponseBody
	public List<Map<String, Object>> getCity(@RequestParam String queryCity,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam pubDriverQueryParam = new PubDriverQueryParam();
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		pubDriverQueryParam.setLeasesCompanyId(leasesCompanyId);
		pubDriverQueryParam.setQueryCity(queryCity);
		return templateHelper.dealRequestWithToken("/PubDriver/GetCity", HttpMethod.POST	,
				userToken, pubDriverQueryParam, List.class);
	}
	
	@RequestMapping(value = "/PubDriver/GetSpecialDri")
	@ResponseBody
	public List<Map<String, Object>> getSpecialDri(@RequestParam String queryServiceOrg,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam pubDriverQueryParam = new PubDriverQueryParam();
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		pubDriverQueryParam.setLeasesCompanyId(leasesCompanyId);
		pubDriverQueryParam.setQueryServiceOrg(queryServiceOrg);
		return templateHelper.dealRequestWithToken("/PubDriver/GetSpecialDri", HttpMethod.POST	,
				userToken, pubDriverQueryParam, List.class);
	}

	@RequestMapping("/PubDriver/GetPubDriverByQuery")
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubDriverQueryParam pubDriverQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubDriverQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubDriver/GetPubDriverByQuery", HttpMethod.POST, userToken,
				pubDriverQueryParam,PageBean.class);
	}
	
	@RequestMapping("/PubDriver/GetServiceOrgByQuery")
	@ResponseBody
	public PageBean getServiceOrgByQuery(@RequestBody ServiceOrgQueryParam serviceOrgQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		serviceOrgQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
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
		//查归属车企
		List<Dictionary> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
				userToken, null, List.class,"租赁公司");
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
//		List<OrgOrgan> orgOrganShortName = templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganShortName/{leasesCompanyId}", HttpMethod.GET,
//				userToken, null, List.class,leasesCompanyId);
//		List<OrgOrgan> orgOrganCity = templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganCity/{leasesCompanyId}", HttpMethod.GET,
//				userToken, null, List.class,leasesCompanyId);
		mav.addObject("pubCityaddr", pubCityaddr);
		mav.addObject("driversType", driversType);
		mav.addObject("belongleasecompany", belongleasecompany);
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
		pubDriver.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubDriver.setCreater(this.getLoginLeUser(request).getId());
		pubDriver.setUpdater(this.getLoginLeUser(request).getId());
		
		//验证手机实名制认证
		Map<String,String> ret = phoneAuthentication(request,pubDriver);
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
		pubDriver.setUpdater(this.getLoginLeUser(request).getId());
		pubDriver.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		
		//验证手机实名制认证
		Map<String,String> ret = phoneAuthentication(request,pubDriver);
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
	
//	@RequestMapping("/PubDriver/UnwrapVel")
//	@ResponseBody
//	public PubDriver unwrapVel(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		return templateHelper.dealRequestWithToken("/PubDriver/UnwrapVel/{id}", HttpMethod.GET, userToken, null,
//				PubDriver.class, id);
//	}
	
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
	
	@RequestMapping("/PubDriver/ResetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestParam String id,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		return templateHelper.dealRequestWithToken("/PubDriver/ResetPassword/{id}/{leasesCompanyId}", HttpMethod.GET, userToken, null,
				Map.class, id,leasesCompanyId);
	}
	
	@RequestMapping("/PubDriver/CheckPubDriverJobNum")
	@ResponseBody
	public int checkPubDriverJobNum(@RequestParam String id,@RequestParam String jobNum,HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriver p = new PubDriver();
		p.setId(id);
		p.setJobNum(jobNum);
		p.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
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
		p.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
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
		p.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubDriver/CheckPubDriver", HttpMethod.POST, userToken, p,
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
		p.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
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
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		List<Object> colData12 = new ArrayList<Object>();
		List<Object> colData13 = new ArrayList<Object>();
		List<Object> colData14 = new ArrayList<Object>();
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		String queryKeyword = request.getParameter("queryKeyword");
		String queryWorkStatus = request.getParameter("queryWorkStatus");
		String queryCity = request.getParameter("queryCity");
		String queryJobStatus = request.getParameter("queryJobStatus");
		String queryIdEntityType = request.getParameter("queryIdEntityType");
		String queryServiceOrg = request.getParameter("queryServiceOrg");
		String queryVehicleType = request.getParameter("queryVehicleType");
		String queryBoundState = request.getParameter("queryBoundState");
		String queryJobNum = request.getParameter("queryJobNum");
		String belongleasecompanyQuery = request.getParameter("belongleasecompanyQuery");
		PubDriverQueryParam pd = new PubDriverQueryParam();
		pd.setLeasesCompanyId(leasesCompanyId);
		pd.setQueryKeyword(queryKeyword);
		pd.setQueryWorkStatus(queryWorkStatus);
		pd.setQueryCity(queryCity);
		pd.setQueryJobStatus(queryJobStatus);
		pd.setQueryIdEntityType(queryIdEntityType);
		pd.setQueryServiceOrg(queryServiceOrg);
		pd.setQueryVehicleType(queryVehicleType);
		pd.setQueryBoundState(queryBoundState);
		pd.setQueryJobNum(queryJobNum);
		pd.setBelongleasecompanyQuery(belongleasecompanyQuery);
		List<Map> pubDriver = templateHelper.dealRequestWithToken("/PubDriver/ExportData", HttpMethod.POST,
				userToken, pd, List.class);
		for(int i=0;i<pubDriver.size();i++){
			if(pubDriver.get(i).get("jobNum") != null){
				colData1.add(pubDriver.get(i).get("jobNum"));
			}else{
				colData1.add("");
			}
			if(pubDriver.get(i).get("name") != null){
				colData2.add(pubDriver.get(i).get("name"));
			}else{
				colData2.add("");
			}
			if(pubDriver.get(i).get("phone") != null){
				colData3.add(pubDriver.get(i).get("phone"));
			}else{
				colData3.add("");
			}
			if(pubDriver.get(i).get("sex") != null){
				colData4.add(pubDriver.get(i).get("sex"));
			}else{
				colData4.add("");
			}
			if(pubDriver.get(i).get("vehicleType") != null){
				if(pubDriver.get(i).get("vehicleType").equals("1")){
					colData12.add("出租车");
				}else{
					colData12.add("网约车");
				}
			}else{
				colData12.add("");
			}
			if(pubDriver.get(i).get("idEntityType") != null){
				if(pubDriver.get(i).get("idEntityType").toString().equals("0")){
					colData5.add("普通司机");
				}else if(pubDriver.get(i).get("idEntityType").toString().equals("1")){
					colData5.add("特殊司机");
				}else{
					colData5.add("/");
				}
			}else{
				colData5.add("");
			}
			if(pubDriver.get(i).get("serviceOrgName") != null){
				colData13.add(pubDriver.get(i).get("serviceOrgName"));
			}else{
				colData13.add("/");
			}
			if(pubDriver.get(i).get("idCardNum") != null){
				colData6.add(pubDriver.get(i).get("idCardNum"));
			}else{
				colData6.add("");
			}
			if(pubDriver.get(i).get("driversTypeName") != null){
				colData7.add(pubDriver.get(i).get("driversTypeName"));
			}else{
				colData7.add("");
			}
			if(pubDriver.get(i).get("driverYears") != null){
				colData8.add(pubDriver.get(i).get("driverYears"));
			}else{
				colData8.add("");
			}
			if(pubDriver.get(i).get("cityName") != null){
				colData9.add(pubDriver.get(i).get("cityName"));
			}else{
				colData9.add("");
			}
			if(pubDriver.get(i).get("boundState") != null){
				if(pubDriver.get(i).get("boundState").equals("0")){
					colData10.add("未绑定");
				}else{
					colData10.add("已绑定");
				}
			}else{
				colData10.add("");
			}
			if(pubDriver.get(i).get("jobStatus") != null){
				colData11.add(pubDriver.get(i).get("jobStatus"));
			}else{
				colData11.add("");
			}
			//归属车企
			if(pubDriver.get(i).get("belongleasecompanyName") != null){
				colData14.add(pubDriver.get(i).get("belongleasecompanyName"));
			}else{
				colData14.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("司机管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("资格证号");
		colName.add("姓名");
		colName.add("手机号");
		colName.add("性别");
		colName.add("司机类型");
		colName.add("司机身份");
		colName.add("服务机构");
		colName.add("身份证号");
		colName.add("驾驶类型");
		colName.add("驾驶工龄");
		colName.add("归属车企");
		colName.add("登记城市");
		colName.add("绑定状态");
		colName.add("在职状态");
		excel.setColName(colName);
		colData.put("资格证号", colData1);
		colData.put("姓名", colData2);
		colData.put("手机号", colData3);
		colData.put("性别", colData4);
		colData.put("司机类型",colData12);
		colData.put("司机身份", colData5);
		colData.put("服务机构",colData13);
		colData.put("身份证号", colData6);
		colData.put("驾驶类型", colData7);
		colData.put("驾驶工龄", colData8);
		colData.put("归属车企", colData14);
		colData.put("登记城市", colData9);
		colData.put("绑定状态", colData10);
		colData.put("在职状态", colData11);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);

//		ee.setSheetMaxRow(colData.size());
		
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
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
		User user = getLoginLeUser(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setVehicletype(paramMap.get("vehicletype"));
		param.setPlatformtype(PlatformTypeByDb.LEASE.code);
		param.setLeasescompanyid(user.getLeasescompanyid());
        param.setCity(paramMap.get("city"));
        param.setBoundstate(paramMap.get("boundstate"));
		if(!paramMap.containsKey("toC")) {
			param.setToC("1");
		} else {
			param.setToC(paramMap.get("toC"));
		}
		param.setJobstatus(paramMap.get("jobstatus"));
		param.setBelongLeasecompany(paramMap.get("belongleasecompany"));
		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubDriver/listPubDriverBySelect",
				HttpMethod.POST, usertoken, param, JSONObject.class);

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
		User user = getLoginLeUser(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setPlatformtype(PlatformTypeByDb.LEASE.code);
		param.setLeasescompanyid(user.getLeasescompanyid());
        param.setCity(paramMap.get("city"));
        param.setBoundstate(paramMap.get("boundstate"));
		param.setVehicletype(paramMap.get("vehicletype"));
		param.setJobstatus(paramMap.get("jobstatus"));
		param.setBelongLeasecompany(paramMap.get("belongleasecompany"));

		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubDriver/listPubDriverBySelectJobNum",
				HttpMethod.POST, usertoken, param, JSONObject.class);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}

	@RequestMapping(value = "/PubDriver/GetQueryJobNum")
	@ResponseBody
	public List<Map<String, Object>> getQueryJobNum(@RequestParam String queryJobNum,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam pubDriverQueryParam = new PubDriverQueryParam();
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		pubDriverQueryParam.setLeasesCompanyId(leasesCompanyId);
		pubDriverQueryParam.setQueryJobNum(queryJobNum);
		return templateHelper.dealRequestWithToken("/PubDriver/GetQueryJobNum", HttpMethod.POST	,
				userToken, pubDriverQueryParam, List.class);
	}
	/**
	 * 手机实名制认证
	 * @param request
	 * @param pubDriver
	 * @return
	 */
	public Map<String,String> phoneAuthentication(HttpServletRequest request, PubDriver pubDriver){

		PhoneAuthenticationParam param = new PhoneAuthenticationParam();

		param.setCardNo(pubDriver.getIdCardNum());

		param.setIpAddr(RequestUtils.getClientIP(request));

		param.setMobile(pubDriver.getPhone());

		param.setRealName(pubDriver.getName());

		JSONObject jsonObject =  templateHelper.dealRequestWithTokenCarserviceApiUrl("/XunChengApi/realNameAuthentication",
				HttpMethod.POST, null, param,
				JSONObject.class);

		Map<String,String> ret = new HashMap<>();
		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			boolean phoneAuthenticationed =  jsonObject.getBoolean("data");
			if(!phoneAuthenticationed){
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "手机号码登记的姓名、身份证号码与所填写的姓名及身份证号码不一致，请检查后再保存");
			}else{
				return null;
			}
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", jsonObject.getString("message"));
		}
		return ret;
	}
}
