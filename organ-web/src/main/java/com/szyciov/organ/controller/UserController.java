package com.szyciov.organ.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.org.param.UserQueryParam;
import com.szyciov.organ.service.UserService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelImp;
import com.szyciov.util.PageBean;

@Controller
public class UserController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	/**
	 * 超管管理员
	 */
	private static final String SUPER = "1";
	
	public UserService userService;
	
	@Resource(name = "userService")
	public void setEmployeeService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "User/GetImgCode")
	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		 userService.getImgCode(request,response);
	}
	
	@RequestMapping(value = "User/Login")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {
		return userService.doLogin(request,response);
	}

	@RequestMapping("/User/Logout")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute(Constants.REQUEST_USER_TOKEN, "");
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.removeAttribute("user");  
			session.removeAttribute(Constants.REQUEST_USER_TOKEN);
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "User/Index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.goToResourceByUserType(user,usertoken,request);
	}
	
	@RequestMapping(value = "User/GetMenuInfo")
	@ResponseBody
	public Map<String,Object> getMenuInfo(HttpServletRequest request, HttpServletResponse response) {
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.getMenuInfo(user,usertoken,request);
	}
	
	@RequestMapping(value = "User/GetNavInfo")
	@ResponseBody
	public Map<String,Object> getNavInfo(HttpServletRequest request, HttpServletResponse response) {
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.getNavInfo(user,usertoken,request);
	}
	
	
	@RequestMapping(value = "User/Info")
	@ResponseBody
	public ModelAndView getUserManagerInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> extrainfo = new HashMap<String,Object>();
		OrgUser user = getLoginOrgUser(request);
		if(user!=null&&SUPER.equals(user.getUserType())){
			extrainfo.put("issuper", true);
		}else{
			extrainfo.put("issuper", false);
		}
		return new ModelAndView("resource/user/index", extrainfo);
	}
	
	@RequestMapping(value = "User/GetDeptUserTreeNodes")
	@ResponseBody
	public Map<String,Object> getDeptUserTreeNodes(HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("organId", user.getOrganId());
		param.put("account", user.getAccount());
		if(user!=null&&SUPER.equals(user.getUserType())){
			param.put("issuper", true);
		}else{
			param.put("issuper", false);
		}
		return userService.getDeptUserTreeNodes(param,usertoken);
	}
	
	@RequestMapping(value = "User/GetDeptComboTreeNodes")
	@ResponseBody
	public List<Map<String,Object>> getDeptComboTreeNodes(HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("organId", user.getOrganId());
		param.put("iscombo", "1");
		param.put("account", user.getAccount());
		if(user!=null&&SUPER.equals(user.getUserType())){
			param.put("issuper", true);
		}else{
			param.put("issuper", false);
		}
		return userService.getDeptComboTreeNodes(param,usertoken);
	}
	
	@RequestMapping(value = "User/GetUserInfo")
	@ResponseBody
	public Map<String,Object> getUserInfo(@RequestParam String userid,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		return userService.getUserInfo(userid,usertoken);
	}
	
	@RequestMapping(value = "User/HasChildDept")
	@ResponseBody
	public boolean hasChildDept(@RequestParam String deptid,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		return userService.hasChildDept(deptid,usertoken);
	}
	
	@RequestMapping(value = "User/GetUserByQuery")
	@ResponseBody
	public PageBean getUserByQuery(@RequestBody UserQueryParam userparam,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		OrgUser orguser = getLoginOrgUser(request);
		userparam.setOrganid(orguser.getOrganId());
		return userService.getUserByQuery(userparam,usertoken);
	}
	
	@RequestMapping(value = "User/GetDeptByQuery")
	@ResponseBody
	public PageBean getDeptByQuery(@RequestBody UserQueryParam userparam,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		OrgUser orguser = getLoginOrgUser(request);
		userparam.setOrganid(orguser.getOrganId());
		return userService.getDeptByQuery(userparam,usertoken);
	}
	
	@RequestMapping(value = "User/AddDept")
	@ResponseBody
	public Map<String,Object> addDept(@RequestBody Map<String,Object> deptinfo,HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		deptinfo.put("organid", user.getOrganId());
		deptinfo.put("userid", user.getId());
		return userService.addDept(deptinfo,usertoken);
	}
	
	
	@RequestMapping(value = "User/GetRoles")
	@ResponseBody
	public List<Map<String,Object>> getRoles(HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.getRoles(usertoken);
	}
	
	@RequestMapping(value = "User/GetValiableCompanyInfo")
	@ResponseBody
	public List<Map<String,Object>> getValiableCompanyInfo(HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.getValiableCompanyInfo(user,usertoken);
	}
	
	
	@RequestMapping(value = "User/GetSpecialstateDrivers")
	@ResponseBody
	public List<Map<String,Object>> getSpecialstateDrivers(@RequestParam String companyid,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		OrgUser orguser = getLoginOrgUser(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyid", companyid);
		param.put("organid", orguser.getOrganId());
		return userService.getSpecialstateDrivers(param,usertoken);
	}
	
	@RequestMapping(value = "User/GetUseCarRules")
	@ResponseBody
	public List<Map<String,Object>> getUseCarRules(HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.getUseCarRules(orguser,usertoken);
	}
	
	
	@RequestMapping(value = "User/AddUser")
	@ResponseBody
	public Map<String,Object> addUser(@RequestBody Map<String,Object> userinfo, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		userinfo.put("organid", orguser.getOrganId());
		userinfo.put("updater", orguser.getId());
		return userService.addUser(userinfo,usertoken);
	}
	
	
	@RequestMapping(value = "User/UpdateUser")
	@ResponseBody
	public Map<String,Object> updateUser(@RequestBody Map<String,Object> userinfo, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		userinfo.put("organid", orguser.getOrganId());
		userinfo.put("updater", orguser.getId());
		return userService.updateUser(userinfo,usertoken);
	}
	
	@RequestMapping(value = "User/UpdateDept")
	@ResponseBody
	public Map<String,Object> updateDept(@RequestBody Map<String,Object> deptinfo, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		deptinfo.put("updater", orguser.getId());
		deptinfo.put("organid", orguser.getOrganId());
		return userService.updateDept(deptinfo,usertoken);
	}
	
	@RequestMapping(value = "User/DeleteDept")
	@ResponseBody
	public Map<String,Object> deleteDept(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.deleteDept(param,usertoken);
	}
	
	@RequestMapping(value = "User/DeleteUser")
	@ResponseBody
	public Map<String,Object> deleteUser(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.deleteUser(param,usertoken);
	}
	
	@RequestMapping(value = "User/GetUserDrivers")
	@ResponseBody
	public Map<String,Object> getUserDrivers(@RequestParam Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.getUserDrivers(param,usertoken);
	}
	
	@RequestMapping(value = "User/ResetPwd")
	@ResponseBody
	public Map<String,Object> resetPwd(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.resetPwd(param,usertoken);
	}
	
	@RequestMapping(value = "User/ChangeUsersDept")
	@ResponseBody
	public Map<String,Object> changeUsersDept(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.changeUsersDept(param,usertoken);
	}
	
	@RequestMapping(value = "User/CheckValid")
	@ResponseBody
	public Map<String,Object> checkValid(HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return userService.checkValid(orguser.getAccount(),usertoken);
	}
	
	@RequestMapping(value = "User/DeleteUsers")
	@ResponseBody
	public Map<String,Object> deleteUsers(@RequestBody Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
		OrgUser orguser = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		param.put("organid", orguser.getOrganId());
		param.put("updater", orguser.getId());
		return userService.deleteUsers(param,usertoken);
	}
	
	@RequestMapping(value="User/ImportUser")
	@ResponseBody
	public Map<String,Object> importUser(HttpServletRequest request, HttpServletResponse response){
		MultipartRequest mreq = (MultipartRequest) request;
		request.getParameterNames();
		mreq.getFileNames();
		MultipartFile mulfile = mreq.getFile("file");
		ExcelImp importutil = new ExcelImp();
		//初始化进度为0
		request.getSession().setAttribute("user_progress", 0);
		Map<String,Object> res = new HashMap<String,Object>();
		try {
			Map<Integer, Map> userinfo = importutil.excelImp(mulfile);
			if(userinfo==null||userinfo.size()<=0){
				res.put("status", "fail");
				res.put("message", "没有解析出可导入的员工");
				return res;
			}
			String usertoken = getUserToken(request);
			OrgUser orguser = getLoginOrgUser(request);
			Map<String,Object> params = new HashMap<String,Object>();
			String deptid = request.getParameter("deptid");
			params.put("deptid", deptid);
			String rulesid = request.getParameter("rulesid");
			if(StringUtils.isNotBlank(rulesid)){
				params.put("rulesid", rulesid);
			}
			params.put("organid", orguser.getOrganId());
			params.put("userinfo", userinfo);
			request.getSession().setAttribute("user_progress", 40);
			res = userService.doImportUser(params,usertoken);
			if("success".equalsIgnoreCase((String)res.get("status"))){
				request.getSession().setAttribute("user_progress", 100);
			}else{
				request.getSession().setAttribute("user_progress", -1);
			}
		} catch (Exception e) {
			res.put("status", "fail");
			request.getSession().setAttribute("user_progress", -1);
			res.put("message", "导入出错");
		}
		return res;
	}
	
	@RequestMapping(value="User/GetImportUserProgress")
	@ResponseBody
	public Map<String,Object> getImportUserProgress(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		Object user_progressobj = request.getSession().getAttribute("user_progress");
//		System.out.println(user_progressobj);
		if(user_progressobj==null){
			res.put("user_progress",0);
			return res;
		}
		int user_progress = (int) user_progressobj;
		//文件解析中
		int userp = Integer.parseInt(request.getParameter("user_progress"));
		if(user_progress<0){
			//异常
			res.put("status", "fail");
			res.put("user_progress",userp);
			request.getSession().setAttribute("user_progress", 0);
			return res;
		}
		if(user_progress==100){
			res.put("user_progress",100);
			//恢复进度为0
			request.getSession().setAttribute("user_progress", 0);
			return res;
		}
		
		if(user_progress>userp){
			userp = user_progress;
		}
		
		if(userp<40){
			res.put("user_progress",userp+3);
		}else{
			if(userp<98){
				res.put("user_progress",userp+1);
			}else{
				res.put("user_progress",userp);
			}
		}
		return res;
	}
	
	@RequestMapping(value="User/GetFileSize")
	@ResponseBody
	public Map<String,Object> getFileSize(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> res = new HashMap<String,Object>();
		MultipartRequest mreq = (MultipartRequest) request;
		MultipartFile mulfile = mreq.getFile("file");
		if(mulfile!=null){
			long sizekb = mulfile.getSize();
			res.put("status", "success");
			res.put("size", sizekb);
		}else{
			res.put("status", "fail");
		}
		return res;
	}
	
	@RequestMapping(value="User/GetCurrentUserInfo")
	@ResponseBody
	public Map<String,Object> getCurrentUserInfo(HttpServletRequest request, HttpServletResponse response){
		OrgUser user = getLoginOrgUser(request);
		if(SUPER.equalsIgnoreCase(user.getUserType())){
			//超管不弹窗
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status", "super");
			return res;
		}
		String usertoken = getUserToken(request);
		Map<String,Object> res = userService.getUserInfo(user.getId(), usertoken);
		return res;
	}
	
}


