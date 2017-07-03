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
import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PYTools;
import com.szyciov.util.PageBean;
import com.szyciov.util.PinyinUtils;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
public class OrgOrganController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgOrganController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OrgOrgan/Index")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        mav.setViewName("resource/orgOrgan/index");  
        return mav; 
	}
	
	@RequestMapping("/OrgOrgan/GetOrgOrganByQuery")
	@ResponseBody
	public PageBean getOrgOrganByQuery(@RequestBody OrgOrganQueryParam orgOrganQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgOrganQueryParam.setAccount(this.getLoginLeUser(request).getAccount());
		orgOrganQueryParam.setSpecialState(this.getLoginLeUser(request).getSpecialstate());
		orgOrganQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganByQuery", HttpMethod.POST, userToken,
				orgOrganQueryParam,PageBean.class);
	}
	@RequestMapping(value = "/OrgOrgan/GetOrgOrganShortName")
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganShortName(@RequestParam String queryShortName,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrganQueryParam orgOrganQueryParam = new OrgOrganQueryParam();
		orgOrganQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		orgOrganQueryParam.setQueryShortName(queryShortName);
		orgOrganQueryParam.setAccount(this.getLoginLeUser(request).getAccount());
		orgOrganQueryParam.setSpecialState(this.getLoginLeUser(request).getSpecialstate());
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganShortName", HttpMethod.POST,
				userToken, orgOrganQueryParam, List.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/GetOrgOrganCity")
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganCity(@RequestParam String queryCity,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrganQueryParam orgOrganQueryParam = new OrgOrganQueryParam();
		orgOrganQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		orgOrganQueryParam.setQueryCity(queryCity);
		orgOrganQueryParam.setAccount(this.getLoginLeUser(request).getAccount());
		orgOrganQueryParam.setSpecialState(this.getLoginLeUser(request).getSpecialstate());
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetOrgOrganCity", HttpMethod.POST,
				userToken, orgOrganQueryParam, List.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/AddIndex")
	@ResponseBody
	public ModelAndView addIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(request.getParameter("id")!=null){
			OrgOrgan o = new OrgOrgan();
			String companyId = this.getLoginLeUser(request).getLeasescompanyid();
			o.setId((String)request.getParameter("id"));
			o.setCompanyId(companyId);
			OrgOrgan orgOrgan = templateHelper.dealRequestWithToken("/OrgOrgan/GetByOrgOrganId", HttpMethod.POST, userToken, o,
					OrgOrgan.class);
			mav.addObject("orgOrgan", orgOrgan);
		}
		List<City> pubCityaddr = templateHelper.dealRequestWithToken("/PubVehicle/GetPubCityaddr", HttpMethod.GET,
				userToken, null, List.class);
//		List<Dictionary> billType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=结算方式", HttpMethod.GET,
//				userToken, null, List.class,"结算方式");
		mav.addObject("pubCityaddr", pubCityaddr);
//		mav.addObject("billType", billType);
        mav.setViewName("resource/orgOrgan/editOrgOrgan");  
        return mav; 
	}
	@RequestMapping("/OrgOrgan/UploadFile")
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
	
	@RequestMapping("/OrgOrgan/CreateOrgOrgan")
	@ResponseBody
	public Map<String, String> createOrgOrgan(@RequestBody OrgOrgan orgOrgan, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BadHanyuPinyinOutputFormatCombination {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		orgOrgan.setId(GUIDGenerator.newGUID());
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		orgOrgan.setCreater(this.getLoginLeUser(request).getId());
		orgOrgan.setUpdater(this.getLoginLeUser(request).getId());
		String initials= PinyinUtils.getPinYin(orgOrgan.getFullName()).substring(0,1);
		orgOrgan.setInitials(initials);
		if(orgOrgan.getCustomertype().equals("1")){
			orgOrgan.setLineOfCredit(0.0);
		}
		return templateHelper.dealRequestWithToken("/OrgOrgan/CreateOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				Map.class);
	}
	
	@RequestMapping("/OrgOrgan/UpdateOrgOrgan")
	@ResponseBody
	public Map<String, String> updateOrgOrgan(@RequestBody OrgOrgan orgOrgan, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BadHanyuPinyinOutputFormatCombination{
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		orgOrgan.setUpdater(this.getLoginLeUser(request).getId());
		if(orgOrgan.getFullName() != null && !orgOrgan.getFullName().equals("")){
			String initials= PinyinUtils.getPinYin(orgOrgan.getFullName()).substring(0,1);
			orgOrgan.setInitials(initials);
		}
		if(orgOrgan.getCustomertype() != null && !orgOrgan.getCustomertype().equals("")){
			if(orgOrgan.getCustomertype().equals("1")){
				orgOrgan.setLineOfCredit(0.0);
			}
		}
		return templateHelper.dealRequestWithToken("/OrgOrgan/UpdateOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				Map.class);
	}
	
	@RequestMapping("/OrgOrgan/ResetPassword")
	@ResponseBody
	public Map<String, String> resetPassword(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/ResetPassword/{id}", HttpMethod.GET, userToken, null,
				Map.class, id);
	}
	
	@RequestMapping(value = "/OrgOrgan/Detailed")
	@ResponseBody
	public ModelAndView detailed(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrgan o = new OrgOrgan();
		String companyId = this.getLoginLeUser(request).getLeasescompanyid();
		o.setId((String)request.getParameter("id"));
		o.setCompanyId(companyId);
		OrgOrgan orgOrgan = templateHelper.dealRequestWithToken("/OrgOrgan/GetByOrgOrganId", HttpMethod.POST, userToken, o,
				OrgOrgan.class);
		mav.addObject("orgOrgan", orgOrgan);
        mav.setViewName("resource/orgOrgan/particulars");  
        return mav; 
	}
	
	@RequestMapping("/OrgOrgan/ExportData")
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
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		String queryShortName = request.getParameter("queryShortName");
		String queryCity = request.getParameter("queryCity");
		String cooperationStatus = request.getParameter("cooperationStatus");
		OrgOrganQueryParam oo = new OrgOrganQueryParam();
		oo.setLeasesCompanyId(leasesCompanyId);
		oo.setQueryShortName(queryShortName);
		oo.setQueryCity(queryCity);
		oo.setCooperationStatus(cooperationStatus);
		List<Map> orgOrgan = templateHelper.dealRequestWithToken("/OrgOrgan/ExportData", HttpMethod.POST,
				userToken, oo, List.class);
		for(int i=0;i<orgOrgan.size();i++){
			if(orgOrgan.get(i).get("fullName") != null){
				colData1.add(orgOrgan.get(i).get("fullName"));
			}else{
				colData1.add("");
			}
			if(orgOrgan.get(i).get("shortName") != null){
				colData2.add(orgOrgan.get(i).get("shortName"));
			}else{
				colData2.add("");
			}
			if(orgOrgan.get(i).get("citycaption") != null){
				colData3.add(orgOrgan.get(i).get("citycaption"));
			}else{
				colData3.add("");
			}
			if(orgOrgan.get(i).get("address") != null){
				colData4.add(orgOrgan.get(i).get("address"));
			}else{
				colData4.add("");
			}
			if(orgOrgan.get(i).get("email") != null){
				colData5.add(orgOrgan.get(i).get("email"));
			}else{
				colData5.add("");
			}
			if(orgOrgan.get(i).get("phone") != null){
				colData6.add(orgOrgan.get(i).get("phone"));
			}else{
				colData6.add("");
			}
			if(orgOrgan.get(i).get("contacts") != null){
				colData7.add(orgOrgan.get(i).get("contacts"));
			}else{
				colData7.add("");
			}
			if(orgOrgan.get(i).get("lineOfCredit") != null){
				colData8.add(orgOrgan.get(i).get("lineOfCredit"));
			}else{
				colData8.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("机构用户.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("所属城市");
		colName.add("机构简称");
		colName.add("机构全称");
		colName.add("机构地址");
		colName.add("邮箱");
		colName.add("联系人");
		colName.add("联系方式");
		colName.add("信用额度");
		excel.setColName(colName);
		colData.put("机构全称", colData1);
		colData.put("机构简称", colData2);
		colData.put("所属城市", colData3);
		colData.put("机构地址", colData4);
		colData.put("邮箱", colData5);
		colData.put("联系方式", colData6);
		colData.put("联系人", colData7);
		colData.put("信用额度", colData8);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(6);
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
	
	@RequestMapping(value = "/OrgOrgan/GetFristTime")
	@ResponseBody
	public int getFristTime(@RequestParam String id,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetFristTime", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckAccount")
	@ResponseBody
	public int checkAccount(@RequestParam String id,@RequestParam String account,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setAccount(account);
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckAccount", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganFullName")
	@ResponseBody
	public int checkOrgOrganFullName(@RequestParam String id,@RequestParam String fullName,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setFullName(fullName);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganShortName")
	@ResponseBody
	public int checkOrgOrganShortName(@RequestParam String id,@RequestParam String shortName,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setShortName(shortName);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganEmail")
	@ResponseBody
	public int checkOrgOrganEmail(@RequestParam String id,@RequestParam String email,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setEmail(email);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganCreditCode")
	@ResponseBody
	public int checkOrgOrganCreditCode(@RequestParam String id,@RequestParam String creditCode,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setCreditCode(creditCode);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganBusinessLicense")
	@ResponseBody
	public int checkOrgOrganBusinessLicense(@RequestParam String id,@RequestParam String businessLicense,HttpServletRequest request) {
		OrgOrgan orgOrgan = new OrgOrgan();
		orgOrgan.setId(id);
		orgOrgan.setBusinessLicense(businessLicense);
		orgOrgan.setCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrgan", HttpMethod.POST, userToken, orgOrgan,
				int.class);
	}
	
	/**
	 * 根据机构名称获取机构列表（包含机构ID与机构名称）
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/OrgOrgan/GetOrgOrganByName")
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganByName(@RequestParam(value = "organName", required = false) String organName,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		return templateHelper.dealRequestWithToken(
				"/OrgOrgan/GetOrgOrganByName/?organName={organName}&leasescompanyid=" + user.getLeasescompanyid(), 
				HttpMethod.GET, userToken, null,
				List.class, organName, user.getLeasescompanyid());
	}
	
	@RequestMapping(value = "/OrgOrgan/GetCityId")
	@ResponseBody
	public PubCityAddr getCityId(@RequestParam String cityName,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetCityId/{cityName}", HttpMethod.GET, userToken, null,
				PubCityAddr.class,cityName);
	}
	
	@RequestMapping(value = "/OrgOrgan/GetAddress")
	@ResponseBody
	public JSONObject getAddress(@RequestBody BaiduApiQueryParam param,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/BaiduApi/GetAddress", HttpMethod.POST, userToken, param,
				JSONObject.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/OrganCreditRecordDetail")
	public ModelAndView getOrganCreditRecordDetail(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "shortName", required = true) String shortName, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("organId", organId);
		mav.addObject("shortName", shortName);
		mav.setViewName("resource/orgOrgan/organCreditRecordDetail");
		return mav;
	}
	
	@RequestMapping("/OrgOrgan/GetOrganCreditRecord")
	@ResponseBody
	public PageBean getOrganCreditRecord(@RequestBody OrgOrganQueryParam orgOrganQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgOrganQueryParam.setOrganId(request.getParameter("organId"));
		orgOrganQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrgOrgan/GetOrganCreditRecord", HttpMethod.POST, userToken,
				orgOrganQueryParam,PageBean.class);
	}
	
	@RequestMapping(value = "/OrgOrgan/CheckOrgOrganAccout")
	@ResponseBody
	public int checkOrgOrganAccout(@RequestParam String id,HttpServletRequest request) {
		OrgOrganQueryParam o = new OrgOrganQueryParam();
		o.setOrganId(id);
		o.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgOrgan/CheckOrgOrganAccout", HttpMethod.POST, userToken, o,
				int.class);
	}
}
