package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.param.LeLeasescompanyQueryParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.op.entity.PubDriver;
import net.sf.json.JSONObject;

@Controller
public class LeLeasescompanyController extends BaseController {
    private static final Logger logger = Logger.getLogger(LeLeasescompanyController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/LeLeasescompany/Index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeLeasescompanyQueryParam queryParam = new LeLeasescompanyQueryParam();
		queryParam.setKey(this.getLoginOpUser(request).getId());
		queryParam.setUsertype(this.getLoginOpUser(request).getUsertype());
		List<LeLeasescompany> list = templateHelper.dealRequestWithToken("/LeLeasescompany/GetNameOrCity", HttpMethod.POST, userToken,
				queryParam,List.class);
		List<LeLeasescompany> list1 = templateHelper.dealRequestWithToken("/LeLeasescompany/GetCityOrName", HttpMethod.POST, userToken,
				queryParam,List.class);
		mav.addObject("list",list);
		mav.addObject("list1",list1);
		mav.setViewName("resource/leLeasescompany/index");
		return mav;
	}
	
	@RequestMapping("/LeLeasescompany/getLeLeasescompanyByQuery")
	@ResponseBody
	public PageBean getLeLeasescompanyByQuery(@RequestBody LeLeasescompanyQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setKey(this.getLoginOpUser(request).getId());
		queryParam.setUsertype(this.getLoginOpUser(request).getUsertype());
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompanyByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/LeLeasescompany/Enable")
	@ResponseBody
	public Map<String, String> enable(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/Enable/{id}", HttpMethod.GET, userToken,
				null,Map.class,id);
	}
	
	@RequestMapping("/LeLeasescompany/Disable")
	@ResponseBody
	public Map<String, String> disable(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/Disable/{id}", HttpMethod.GET, userToken,
				null,Map.class,id);
	}
	
	@RequestMapping("/LeLeasescompany/DisableToc")
	@ResponseBody
	public Map<String, String> disableToc(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/DisableTocs/{id}", HttpMethod.GET, userToken,
				null,Map.class,id);
	}
	
	@RequestMapping("/LeLeasescompany/ExamineToc")
	@ResponseBody
	public Map<String, String> examineToc(@RequestParam String id,@RequestParam String state, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(state.equals("0")){
			return templateHelper.dealRequestWithToken("/LeLeasescompany/ExamineToc/{id}", HttpMethod.GET, userToken,
					null,Map.class,id);
		}else{
			Map<String, String> map = templateHelper.dealRequestWithToken("/LeLeasescompany/DisableToc/{id}", HttpMethod.GET, userToken,
					null,Map.class,id);
//			LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetById/{id}", HttpMethod.GET, userToken,
//					null,LeLeasescompany.class,id);
//			map.put("ResultSign", "Successful");
//			map.put("MessageKey", leLeasescompany.getAccount()+"账号未通过审核");
			return map;
		}
		
	}
	
	@RequestMapping("/LeLeasescompany/ResetPassword")
	@ResponseBody
	public Map<String, String> resetPassword(@RequestParam String id,@RequestParam String account, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/ResetPassword/{id}/{account}", HttpMethod.GET, userToken,
				null,Map.class,id,account);
	}
	
	@RequestMapping("/LeLeasescompany/AddIndex")
	@ResponseBody
	public ModelAndView addIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<City> pubCityaddr = templateHelper.dealRequestWithToken("/LeLeasescompany/GetPubCityaddr", HttpMethod.GET,
				userToken, null, List.class);
		mav.addObject("pubCityaddr", pubCityaddr);
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
			LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetById/{id}", HttpMethod.GET, userToken,
					null,LeLeasescompany.class,request.getParameter("id"));
			mav.addObject("leLeasescompany", leLeasescompany);
			mav.setViewName("resource/leLeasescompany/editOrDetails");
		}else{
			mav.setViewName("resource/leLeasescompany/add");
		}
		return mav;
	}
	@RequestMapping("/LeLeasescompany/UploadFile")
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
	
	@RequestMapping(value = "/LeLeasescompany/GetFristTime")
	@ResponseBody
	public int getFristTime(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetById/{id}", HttpMethod.GET, userToken,
				null,LeLeasescompany.class,id);
		return leLeasescompany.getFirstTime();
	}
	
	@RequestMapping(value = "/LeLeasescompany/Edit")
	@ResponseBody
	public Map<String, String> edit(@RequestBody LeLeasescompany leLeasescompany,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(leLeasescompany.getId() != null && !leLeasescompany.getId().equals("")){
			leLeasescompany.setUpdater(getLoginOpUser(request).getId());
			leLeasescompany.setPhone(request.getParameter("phone"));
			return templateHelper.dealRequestWithToken("/LeLeasescompany/UpdateLeLeasescompany", HttpMethod.POST, userToken, leLeasescompany,
					Map.class);
		}else{
			leLeasescompany.setId(GUIDGenerator.newGUID());
			leLeasescompany.setCreater(getLoginOpUser(request).getId());
			leLeasescompany.setUpdater(getLoginOpUser(request).getId());
			return templateHelper.dealRequestWithToken("/LeLeasescompany/CreateLeLeasescompany", HttpMethod.POST, userToken, leLeasescompany,
					Map.class);
		}
	}
	
	@RequestMapping(value = "/LeLeasescompany/CheckNameOrShortName")
	@ResponseBody
	public int checkNameOrShortName(@RequestBody LeLeasescompany leLeasescompany,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/CheckNameOrShortName", HttpMethod.POST, userToken, leLeasescompany,
				int.class);
	}
	
	@RequestMapping(value = "/LeLeasescompany/DriverInformation")
	@ResponseBody
	public ModelAndView driverInformation(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = request.getParameter("id");
		List<Dictionary> workStatus = templateHelper.dealRequestWithToken("/LeLeasescompany/GetDictionaryByType/{type}", HttpMethod.GET	,
				userToken, null, List.class,"服务状态");
		mav.addObject("workStatus", workStatus);
		List<PubDriver> city = templateHelper.dealRequestWithToken("/LeLeasescompany/GetCity/{leasesCompanyId}", HttpMethod.GET	,
				userToken, null, List.class,id);
		List<Dictionary> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
				userToken, null, List.class,"租赁公司");
		mav.addObject("city", city);
//		List<OpVehiclemodels> opVehiclemodels = templateHelper.dealRequestWithToken("/LeLeasescompany/GetOpVehiclemodels", HttpMethod.POST	,
//				userToken, null, List.class);
		mav.addObject("id",id);
		mav.addObject("belongleasecompany", belongleasecompany);
//		mav.addObject("opVehiclemodels", opVehiclemodels);
		mav.setViewName("resource/leLeasescompany/driverInformation");
		return mav;
	}
	@RequestMapping(value = "/LeLeasescompany/GetOpVehiclemodels")
	@ResponseBody
	public List<OpVehiclemodels> getOpVehiclemodels(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetOpVehiclemodels", HttpMethod.POST	,
				userToken, null, List.class);
	}
	@RequestMapping(value = "/LeLeasescompany/GetPubDriverByQuery")
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubDriverQueryParam pubDriverQueryParam,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = request.getParameter("id");
		pubDriverQueryParam.setLeasesCompanyId(leasesCompanyId);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetPubDriverByQuery", HttpMethod.POST, userToken, pubDriverQueryParam,
				PageBean.class);
	}
	@RequestMapping(value = "/LeLeasescompany/GetByIdPubDriver")
	@ResponseBody
	public PubDriver getByIdPubDriver(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetByIdPubDriver/{id}", HttpMethod.GET, userToken, null,
				PubDriver.class,id);
	}
	
	@RequestMapping(value = "/LeLeasescompany/CreateOpVehclineModelsRef")
	@ResponseBody
	public Map<String, String> createOpVehclineModelsRef(@RequestBody OpVehiclemodelsVehicleRef opVehclineModelsRef,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		opVehclineModelsRef.setId(GUIDGenerator.newGUID());
		opVehclineModelsRef.setCreater(getLoginOpUser(request).getId());
		opVehclineModelsRef.setUpdater(getLoginOpUser(request).getId());
		return templateHelper.dealRequestWithToken("/LeLeasescompany/createOpVehclineModelsRef", HttpMethod.POST, userToken, opVehclineModelsRef,
				Map.class);
	}
	@RequestMapping("/LeLeasescompany/ExportData")
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
		LeLeasescompanyQueryParam lq = new LeLeasescompanyQueryParam();
		String queryName = request.getParameter("queryName");
		String queryCity = request.getParameter("queryCity");
		String queryCompanystate = request.getParameter("queryCompanystate");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		lq.setQueryName(queryName);
		lq.setQueryCity(queryCity);
		lq.setQueryCompanystate(queryCompanystate);
		lq.setStartTime(startTime);
		lq.setEndTime(endTime);
		lq.setKey(this.getLoginOpUser(request).getId());
		lq.setUsertype(this.getLoginOpUser(request).getUsertype());
		List<Map> leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/ExportData", HttpMethod.POST,
				userToken, lq, List.class);
		for(int i=0;i<leLeasescompany.size();i++){
			if(leLeasescompany.get(i).get("city") != null){
				colData1.add(leLeasescompany.get(i).get("city"));
			}else{
				colData1.add("");
			}
			if(leLeasescompany.get(i).get("name") != null){
				colData2.add(leLeasescompany.get(i).get("name"));
			}else{
				colData2.add("");
			}
			if(leLeasescompany.get(i).get("shortName") != null){
				colData3.add(leLeasescompany.get(i).get("shortName"));
			}else{
				colData3.add("");
			}
			if(leLeasescompany.get(i).get("account") != null){
				colData4.add(leLeasescompany.get(i).get("account"));
			}else{
				colData4.add("");
			}
			if(leLeasescompany.get(i).get("companyStateShow") != null){
				colData5.add(leLeasescompany.get(i).get("companyStateShow"));
			}else{
				colData5.add("");
			}
			if(leLeasescompany.get(i).get("tocState") != null){
				colData6.add(leLeasescompany.get(i).get("tocState"));
			}else{
				colData6.add("");
			}
			if(leLeasescompany.get(i).get("contacts") != null){
				colData7.add(leLeasescompany.get(i).get("contacts"));
			}else{
				colData7.add("");
			}
			if(leLeasescompany.get(i).get("phone") != null){
				colData8.add(leLeasescompany.get(i).get("phone"));
			}else{
				colData8.add("");
			}
			if(leLeasescompany.get(i).get("createTime") != null){
				Long times = (Long)leLeasescompany.get(i).get("createTime");
				Date date = new Date(times);
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				colData9.add(format.format(date));
			}else{
				colData9.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("客户管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("所属城市");
		colName.add("客户名称");
		colName.add("客户简称");
		colName.add("账号名称");
		colName.add("账号状态");
		colName.add("是否加入toC");
		colName.add("联系人");
		colName.add("联系方式");
		colName.add("创建日期");
		excel.setColName(colName);
		colData.put("所属城市", colData1);
		colData.put("客户名称", colData2);
		colData.put("客户简称", colData3);
		colData.put("账号名称", colData4);
		colData.put("账号状态", colData5);
		colData.put("是否加入toC", colData6);
		colData.put("联系人", colData7);
		colData.put("联系方式", colData8);
		colData.put("创建日期", colData9);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
	
	@RequestMapping(value = "/LeLeasescompany/GetCityId")
	@ResponseBody
	public PubCityAddr getCityId(@RequestParam String cityName,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetCityId/{cityName}", HttpMethod.GET, userToken, null,
				PubCityAddr.class,cityName);
	}
	
	@RequestMapping(value = "/LeLeasescompany/GetAddress")
	@ResponseBody
	public JSONObject getAddress(@RequestBody BaiduApiQueryParam param,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/BaiduApi/GetAddress", HttpMethod.POST, userToken, param,
				JSONObject.class);
	}
	
	@RequestMapping(value = "/LeLeasescompany/GetQueryKeyword")
	@ResponseBody
	public List<Map<String, Object>> getQueryKeyword(@RequestParam String val,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubDriverQueryParam p = new PubDriverQueryParam();
		String id = request.getParameter("id");
		p.setLeasesCompanyId(id);
		p.setQueryKeyword(val);
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetQueryKeyword", HttpMethod.POST, userToken, p,
				List.class);
	}
}
