package com.szyciov.organ.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.org.entity.OrgUser;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

@Controller
public class SupplierManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(SupplierManagementController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("SupplierManagement/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = getLoginOrgUser(request).getId();
		String organid = getLoginOrgUser(request).getOrganId();
		OrgUser orgUser = templateHelper.dealRequestWithToken("/SupplierManagement/GetById/{id}", HttpMethod.GET, userToken, null,
				OrgUser.class,id);
		orgUser.setOpenTime(getStringDate(orgUser.getCreateTime()));
		mav.addObject("orgUser", orgUser);
		List<Map> orgUserList = templateHelper.dealRequestWithToken("/SupplierManagement/GetByList/{id}/{organid}", HttpMethod.GET, userToken, null,
				List.class,id,organid);
		for(int i=0;i<orgUserList.size();i++){
			String date = getStringDate(Long.valueOf(orgUserList.get(i).get("createTime").toString()));
			orgUserList.get(i).put("openTime", date);
		}
		mav.addObject("orgUserList", orgUserList);
		mav.addObject("count",orgUserList.size());
		List<Map> childCccount = templateHelper.dealRequestWithToken("/SupplierManagement/GetChildCccount/{id}/{organid}", HttpMethod.GET, userToken, null,
				List.class,id,organid);
		for(int i=0;i<childCccount.size();i++){
			String date = getStringDate(Long.valueOf(childCccount.get(i).get("createTime").toString()));
			childCccount.get(i).put("openTime", date);
		}
		mav.addObject("childCccount", childCccount);
		mav.setViewName("resource/supplierManagement/index");
		return mav;
	}
	//  毫秒转日期 1478162376000  1478083223000 1478083223000 2016-11-02 18:40:23 
	/**
	   * 
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH mm
	   */
	public static String getStringDate(Date date) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   String dateString = formatter.format(date);
	   return dateString;
	}
	
	public static String getStringDate(long now) {
		Date d = new Date(now);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(now);
//		return formatter.format(calendar.getTime());
		return formatter.format(d);
	}
	
	@RequestMapping("SupplierManagement/GetById")
	@ResponseBody
	public OrgUser getById(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = templateHelper.dealRequestWithToken("/SupplierManagement/GetById/{id}", HttpMethod.GET, userToken, null,
				OrgUser.class,id);
		orgUser.setOpenTime(getStringDate(orgUser.getCreateTime()));
		return orgUser;
	}
	
	@RequestMapping("SupplierManagement/AddLink")
	@ResponseBody
	public Map<String, String> addLink(@RequestParam String orgOrganCompanyRefId,@RequestParam String id,@RequestParam String userpassword,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = new OrgUser();
		orgUser.setId(id);
		orgUser.setUserPassword(userpassword);
		orgUser.setOrgOrganCompanyRefId(orgOrganCompanyRefId);
		return templateHelper.dealRequestWithToken("/SupplierManagement/AddLink", HttpMethod.POST, userToken, orgUser,
				Map.class);
	}
	
	@RequestMapping("SupplierManagement/RemoveLink")
	@ResponseBody
	public Map<String, String> removeLink(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/SupplierManagement/RemoveLink/{id}", HttpMethod.GET, userToken, null,
				Map.class,id);
	}
	@RequestMapping("SupplierManagement/CheckPassword")
	@ResponseBody
	public int checkPassword(@RequestParam String id,@RequestParam String userpassword,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = new OrgUser();
		orgUser.setId(id);
		orgUser.setUserPassword(userpassword);
		return templateHelper.dealRequestWithToken("/SupplierManagement/CheckPassword", HttpMethod.POST, userToken, orgUser,
				int.class);
	}
	@RequestMapping("SupplierManagement/RemoveLink1")
	@ResponseBody
	public Map<String, String> removeLink(@RequestParam String id,@RequestParam String orgOrganCompanyRefId,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/SupplierManagement/RemoveLink/{id}/{orgOrganCompanyRefId}", HttpMethod.GET, userToken, null,
				Map.class,id,orgOrganCompanyRefId);
	}
}
