package com.szyciov.organ.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.UserQueryParam;
import com.szyciov.util.Constants;
import com.szyciov.util.CreateImageCode;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UNID;
import com.szyciov.util.UserTokenManager;

@Service("userService")
public class UserService {
	/**
	 * 机构端超级管理员
	 */
	private static final String SUPERUSER = "1";
	
	private TemplateHelper templateHelper = new TemplateHelper();

	/**
	 * web登录接口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,Object> logininfo = getPubLoginfo(request);
		String usertoken = null;
		try{
			//账户密码登录
			Map<String, String> param = new HashMap<String, String>();
			String userName = (String) request.getParameter("username");
			String password = (String) request.getParameter("password");
			String imgcode = (String) request.getParameter("code");
			Object code = request.getSession().getAttribute("code");
			logininfo.put("userid", userName);
			if(code!=null){
				String codestr = (String) code;
				if(!codestr.equalsIgnoreCase(imgcode)){
					model.put("message", "图片验证码校验失败！");
					logininfo.put("loginstatus", "1");
					return new ModelAndView("login", model);
				}
			}
			//验证码用过就清除
			request.getSession().removeAttribute("code");
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
				//如果已登录，不需要再继续返回登录页
				usertoken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
				OrgUser suser = (OrgUser) request.getSession().getAttribute("user");
				if(!StringUtils.isBlank(usertoken)&&suser!=null){
					logininfo.put("userid", suser.getId());
					logininfo.put("loginstatus", "0");
					return goToResourceByUserType(suser,usertoken,request);
				}
				logininfo.put("loginstatus", "1");
				model.put("message", "用户名和密码不能为空！");
				return new ModelAndView("login", model);
			}
			if (userName.length() < 2) {
				model.put("message", "输入账户不能小于2个字符");
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
			if( password.length() < 6){
				model.put("message", "输入密码不能小于6个字符");
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
			param.put("userName", userName);
			param.put("password", password);
			Object oldusertoken = request.getSession().getAttribute("usertoken");
			if (authelicationUser(param)) {
				try {
				    usertoken = UserTokenManager.createUserToken(UserTokenManager.ORGWEB,userName, SystemConfig.getSystemProperty("securityKey"));
				    if(oldusertoken==null){
				    	//同一个session中usertoken存在，不做挤下线
				    	request.getSession().setAttribute(Constants.REQUEST_USER_TOKEN, usertoken);
				    }
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 获取user信息加入session
				OrgUser user = getByName(userName, usertoken);
				request.getSession().setAttribute("user", user);
				if(oldusertoken==null){
					saveUsertoken(usertoken,user.getId());
				}
				if(isUnbindAccount(userName,usertoken)){
					//不是主账号，并且没有关联
					if(hasMainAccount(userName,usertoken)){
						//机构有主账号
						//进来的是子账号，需要绑定才可以进入
						logininfo.put("loginstatus", "1");
						return go2ChildbindPage(user);
					}else{
						//没有主账号
						//设置成主账号
						setAsMainAccount(userName,usertoken);
					}
				}
				if(user!=null&&SUPERUSER.equalsIgnoreCase(user.getUserType())){
					updateFirstLogin(user,usertoken);
				}
				logininfo.put("userid", user.getId());
				logininfo.put("loginstatus", "0");
				return goToResourceByUserType(user,usertoken,request);
			} else {
				model.put("message", "账号或密码错误！");
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
		}catch(Exception e){
			logininfo.put("loginstatus", "1");
			String message = e.getMessage()==null?"":(e.getMessage().length()<=3800?e.getMessage():e.getMessage().substring(0,3800));
			logininfo.put("loginmessage", message);
			model.put("message", "登录失败！");
			return new ModelAndView("login", model);
		}finally {
			try{
				addUserLoginLog(logininfo,usertoken);
			}catch(Exception e){}
		}
		
	}
	
	/**
	 * 保存最新的usertoken
	 * @param usertoken
	 * @param userid
	 * @return
	 */
	private Map<String,Object> saveUsertoken(String usertoken,String userid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("usertoken", usertoken);
		params.put("userid", userid);
		params.put("usertype", UserTokenManager.ORGWEB);
		return templateHelper.dealRequestWithToken("/User/SaveUsertoken", HttpMethod.POST, usertoken, params, Map.class);
	}
	
	/**
	 * 检测用户登录ip是否是不一致
	 * @param request
	 * @param usertoken
	 * @return
	 */
	private boolean checkLastLogin(HttpServletRequest request,String account,String usertoken){
		boolean flag = false;
		String addr = request.getRemoteAddr();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		String userName = (String) request.getParameter("username");
		Map<String,Object> lastlogininfo = templateHelper.dealRequestWithToken("/User/GetLastLoginLog", HttpMethod.POST, usertoken, params, Map.class);
		//username是空意味着已经登陆过了不需要弹窗提示
		if(lastlogininfo!=null&&StringUtils.isNotBlank(userName)){
			String lastaddr = (String) lastlogininfo.get("ipaddr");
			if(StringUtils.isNotBlank(lastaddr)&&!lastaddr.equalsIgnoreCase(addr)){
				//需要提示用户，上次登录ip不一致
				flag = true;
			}
		}
		return flag;
	}
	
	
	/**
	 * 获取登录日志的公共信息
	 * @param request
	 * @return
	 */
	private Map<String,Object> getPubLoginfo(HttpServletRequest request){
		String addr = request.getRemoteAddr();
		String useragent = request.getHeader("User-Agent");
		String browsertype = "未知";
		if(StringUtils.isNotBlank(useragent)){
			if(useragent.toLowerCase().indexOf("firefox")>0){
				//火狐浏览器
				browsertype = "firefox";
			}else if(useragent.toLowerCase().indexOf("chrome")>0){
				//chrome
				browsertype = "chrome";
			}else if(useragent.toLowerCase().indexOf("msie")>0){
				//ie
				browsertype = "ie";
			}else if(useragent.toLowerCase().indexOf("mozilla")>0){
				//MOZILLA内核
				browsertype = "mozilla内核浏览器";
			}else{
				browsertype = "其他的浏览器";
			}
		}
		Map<String,Object> loginlog = new HashMap<String,Object>();
		loginlog.put("id", GUIDGenerator.newGUID());
		loginlog.put("device", "0");
		loginlog.put("version", null);
		loginlog.put("phonebrand", null);
		loginlog.put("phonemodel", null);
		loginlog.put("ipaddr", addr);
		loginlog.put("browsertype", browsertype);
		return loginlog;
	}
	/**
	 * 添加登录日志
	 * @param loginfo
	 * @param usertoken
	 * @return
	 */
	private Map<String,Object> addUserLoginLog(Map<String,Object> loginfo,String usertoken){
		return templateHelper.dealRequestWithToken("/User/AddUserLoginLog", HttpMethod.POST, usertoken, loginfo, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> updateFirstLogin(OrgUser user,String usertoken){
		return templateHelper.dealRequestWithToken("/User/UpdateFirstLogin", HttpMethod.POST, usertoken, user, Map.class);
	}
	
	/**
	 * 子账户登录界面信息设置
	 * @param user
	 * @return
	 */
	private ModelAndView go2ChildbindPage(OrgUser user) {
		Map<String, Object> model = new HashMap<String, Object>();
		if(user==null){
			model.put("message", "登录失败，暂时无法获取用户信息");
			return new ModelAndView("login", model);
		}
		model.put("menuinfo", new ArrayList<Map<String, Object>>());
		model.put("navinfo", new ArrayList<Map<String, Object>>());
		model.put("username", user.getNickName());
		model.put("shouldlogout", true);
		return new ModelAndView("resource/index", model);
	}


	/**
	 * 将用户所在账户设置成为主账号
	 * @param userName
	 * @param usertoken
	 * @return
	 */
	private Map<String,Object> setAsMainAccount(String userName, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/SetAsMainAccount/{userName}", HttpMethod.GET, usertoken, null, Map.class, userName);
	}


	/**
	 * 判断某用户所在机构是否拥有主账号
	 * @param userName
	 * @param usertoken
	 * @return
	 */
	private boolean hasMainAccount(String userName, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/HasMainAccount/{userName}", HttpMethod.GET, usertoken, null, boolean.class, userName);
	}


	/**
	 * 判断用户是否是主账号或者是否已关联
	 * @param userName
	 * @param userToken
	 * @return
	 */
	private boolean isUnbindAccount(String userName,String userToken) {
		return templateHelper.dealRequestWithToken("/User/IsUnbindAccount/{userName}", HttpMethod.GET, userToken, null, boolean.class, userName);
	}



	/**
	 * 根据登录名获取机构用户对象
	 * @param loginName
	 * @param userToken
	 * @return
	 */
	public OrgUser getByName(String loginName, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, OrgUser.class, loginName);
	}
	
	/**
	 * 验证密码
	 * @param param
	 * @return
	 */
	public boolean authelicationUser(Map<String, String> param) {
		String userName = (String) param.get("userName");
		String rawPassword = (String) param.get("password");
		String encodedPassword = getPasswordByName(userName);
		if (null == encodedPassword) {
			return false;
		}
		return PasswordEncoder.matches_PWD(rawPassword, encodedPassword);
	}
	
	/**
	 * 根据密码
	 * @param loginName
	 * @return
	 */
	public String getPasswordByName(String loginName) {
		String password = templateHelper.dealRequestWithToken("/User/GetPasswordByName/{loginName}", HttpMethod.GET, null, null, String.class, loginName);
		if (StringUtils.isBlank(password)) {
			return null;
		}
		return password.length() >= 2 ? StringUtils.substring(password, 1, password.length() - 1) : password;
	}
	
	/**
	 * 跳转到首页
	 * @param user
	 * @return
	 */
	public ModelAndView goToResourceByUserType(OrgUser user,String usertoken,HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		if(user==null){
			model.put("message", "登录失败，暂时无法获取用户信息");
			return new ModelAndView("login", model);
		}
		String remember = (String) request.getParameter("remember");
		boolean needremember = "1".equalsIgnoreCase(remember)?true:false;
		List<Map<String,Object>> menuinfo  = templateHelper.dealRequestWithToken("/User/GetMenuInfo", HttpMethod.POST, usertoken, user, List.class);
		model.put("menuinfo", menuinfo);
		List<Map<String,Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST, usertoken, user, List.class);
		model.put("navinfo", navinfo);
		model.put("username", user.getAccount());
		// 未读消息数量
		int unReadNum = templateHelper.dealRequestWithToken("/Message/GetUnReadNewsCountByUserId?userId={userId}",
				HttpMethod.GET, usertoken, null, Integer.class, user.getId());
		model.put("unReadNum", unReadNum);
		boolean differentip = checkLastLogin(request,user.getAccount(),usertoken);
		if(differentip){
			model.put("differentip", differentip);
			model.put("different4account",user.getAccount());
		}
		if(needremember){
			model.put("account", user.getAccount());
		}
//		Map<String,Object> logininfo = getPubLoginfo(request);
//		logininfo.put("loginstatus", "0");
//		logininfo.put("userid", user.getId());
//		addUserLoginLog(logininfo,usertoken);
		return new ModelAndView("resource/index", model);
	}

	/**
	 * 获取菜单信息
	 * @param user
	 * @param usertoken
	 * @param request
	 * @return
	 */
	public Map<String,Object> getMenuInfo(OrgUser user, String usertoken, HttpServletRequest request) {
		Map<String,Object> res = new HashMap<String,Object>();
		if(user==null||usertoken==null){
			res.put("status", "fail");
			res.put("message", "用户没有登录");
			return res;
		}
		List<Map<String,Object>> menuinfio = templateHelper.dealRequestWithToken("/User/GetMenuInfo", HttpMethod.POST, usertoken, user, List.class);
		res.put("status", "success");
		res.put("menuinfo", menuinfio);
		String currentpath = request.getHeader("referer");
		res.put("currentpath", currentpath);
		return res;
	}

	/**
	 * 获取导航菜单信息
	 * @param user
	 * @param usertoken
	 * @param request
	 * @return
	 */
	public Map<String, Object> getNavInfo(OrgUser user, String usertoken, HttpServletRequest request) {
		Map<String,Object> res = new HashMap<String,Object>();
		if(user==null){
			res.put("status", "fail");
			res.put("message", "用户没有登录");
			return res;
		}
		List<Map<String,Object>> navinfio = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST, usertoken, user, List.class);
		res.put("status", "success");
		res.put("navinfio", navinfio);
		String currentpath = request.getHeader("referer");
		res.put("currentpath", currentpath);
		return res;
	}

	/**
	 * 获取部门和员工的树形信息
	 * @param user
	 * @param usertoken
	 * @return
	 */
	public Map<String, Object> getDeptUserTreeNodes(Map<String,Object> param, String usertoken) {
		Map<String,Object> res = new HashMap<String,Object>();
		if(param==null){
			res.put("status", "fail");
			res.put("message", "用户没有登录");
			return res;
		}
		List<Map<String,Object>> deptuserinfio = templateHelper.dealRequestWithToken("/User/GetDeptUserTreeNodes", HttpMethod.POST, usertoken, param, List.class);
		res.put("status", "success");
		res.put("deptuserinfio", deptuserinfio);
		return res;
	}

	public Map<String, Object> getUserInfo(String userid, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUserInfo/{userid}", HttpMethod.GET, null, null, Map.class, userid);
	}

	public boolean hasChildDept(String deptid, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/HasChildDept/{deptid}", HttpMethod.GET, null, null, Boolean.class, deptid);
	}

	public PageBean getUserByQuery(UserQueryParam userparam, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUserByQuery", HttpMethod.POST, usertoken, userparam, PageBean.class);
	}

	public PageBean getDeptByQuery(UserQueryParam userparam, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetDeptByQuery", HttpMethod.POST, usertoken, userparam, PageBean.class);
	}

	public Map<String, Object> addDept(Map<String, Object> deptinfo, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/AddDept", HttpMethod.POST, usertoken, deptinfo, Map.class);
	}

	public List<Map<String,Object>> getDeptComboTreeNodes(Map<String,Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetDeptUserTreeNodes", HttpMethod.POST, usertoken, param, List.class);
	}

	public List<Map<String, Object>> getRoles(String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetRoles", HttpMethod.POST, usertoken, null, List.class);
	}

	public List<Map<String, Object>> getValiableCompanyInfo(OrgUser user, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetValiableCompanyInfo", HttpMethod.POST, usertoken, user, List.class);
	}

	public List<Map<String, Object>> getSpecialstateDrivers(Map<String,Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetSpecialstateDrivers", HttpMethod.POST, usertoken, param, List.class);
	}

	public List<Map<String, Object>> getUseCarRules(OrgUser orguser, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUseCarRules/{organid}", HttpMethod.GET, null, null, List.class, orguser.getOrganId());
	}

	public Map<String, Object> addUser(Map<String, Object> userinfo, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/AddUser", HttpMethod.POST, usertoken, userinfo, Map.class);
	}

	public Map<String, Object> updateUser(Map<String, Object> userinfo, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/UpdateUser", HttpMethod.POST, usertoken, userinfo, Map.class);
	}

	public Map<String, Object> deleteDept(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/DeleteDept", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> deleteUser(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/DeleteUser", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> updateDept(Map<String, Object> deptinfo, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/UpdateDept", HttpMethod.POST, usertoken, deptinfo, Map.class);
	}

	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		CreateImageCode vCode = new CreateImageCode(100,48,4,10);
        request.getSession().setAttribute("code", vCode.getCode());
        try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> getUserDrivers(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUserDrivers", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> resetPwd(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/ResetPwd", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> changeUsersDept(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/ChangeUsersDept", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> deleteUsers(Map<String, Object> param, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/DeleteUsers", HttpMethod.POST, usertoken, param, Map.class);
	}

	public Map<String, Object> doImportUser(Map<String, Object> params,String usertoken) {
		return templateHelper.dealRequestWithToken("/User/DoImportUser", HttpMethod.POST, usertoken, params, Map.class);
	}

	public Map<String, Object> checkValid(String account, String usertoken) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("usertoken", usertoken);
		return templateHelper.dealRequestWithToken("/User/CheckValid", HttpMethod.POST, usertoken, params, Map.class);
	}
	
}
