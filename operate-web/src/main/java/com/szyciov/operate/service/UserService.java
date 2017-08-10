package com.szyciov.operate.service;

import com.szyciov.message.redis.RedisMessage;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.UserQueryParam;
import com.szyciov.util.*;
import com.szyciov.util.message.RedisListMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service("userService")
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	private TemplateHelper templateHelper = new TemplateHelper();

	public PageBean getUserByQuery(UserQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUserByQuery", HttpMethod.POST, usertoken, queryParam, PageBean.class);
	}

	public Map<String, String> createUser(OpUser OpUser, String userToken) {
		return templateHelper.dealRequestWithToken("/User/Create", HttpMethod.POST, userToken, OpUser, Map.class);
	}

	public OpUser getById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetById/{id}", HttpMethod.GET, userToken, null, OpUser.class, id);
	}

	public Map<String, String> updateUser(OpUser OpUser, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/Update", HttpMethod.POST, usertoken, OpUser, Map.class);
	}

	public Map<String, String> deleteUser(String id, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/Delete/{id}", HttpMethod.GET, usertoken, null, Map.class, id);
	}

	public Map<String, Object> getRolesInfo(Map<String, String> params,String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetRolesInfo", HttpMethod.POST, usertoken, params, Map.class);
	}

	public Map<String, String> assignRole(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/User/AssignRole", HttpMethod.POST, userToken, params, Map.class);
	}

	/**
	 * 检查图片验证码
	 * @param request
	 * @return
	 */
	private Map<String, Object> checkAuthCode(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String imgcode = (String) request.getParameter("code");
		String code = (String)request.getSession().getAttribute("code");
		//当前验证码错误次数
//		Object tempErrprTimes = request.getSession().getAttribute("errortimes_" + code);
//		tempErrprTimes = tempErrprTimes == null ? 0 : Integer.parseInt((String) tempErrprTimes);
//		String temp = SystemConfig.getSystemProperty("web_auth_code_error_times", "5");
//		int allowErrorTimes = Integer.parseInt(temp);
//		int errorTimes = (int)tempErrprTimes;
		if(code == null){  //如果session中没有验证码,表示登录成功过,不再校验
			return null;
//		}else if(errorTimes > allowErrorTimes){
//			model.put("message", "图片验证码错误次数超限！");
//			return model;
		}else if ((!code.equalsIgnoreCase(imgcode) && !"xxkj".equalsIgnoreCase(imgcode))) {
			model.put("message", "图片验证码校验失败！");
			CreateImageCode vCode = new CreateImageCode(100,48,4,10);
	        request.getSession().setAttribute("code", vCode.getCode());   //如果图片验证失败,就刷新验证码
//			errorTimes = errorTimes+1;
//			request.getSession().setAttribute("errortimes_" + code, errorTimes);
			return model;
		}else{
			return null;
		}
	}
	
	/**
	 * 登录成功时清除验证码信息
	 * @param request
	 */
	private void clearAuthCodeInfo(HttpServletRequest request){
		//用过之后清除验证码
		request.getSession().removeAttribute("code");
		//清除本session中所有验证码错误次数
		@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getSession().getAttributeNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			if(name.startsWith("errortimes_")){
				request.getSession().removeAttribute(name);
			}
		}
	}
	
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
			model = checkAuthCode(request,response);			//检查图片验证码
			if(model != null) return new ModelAndView("login", model);
			else model = new HashMap<>();
			clearAuthCodeInfo(request);                     //清空验证码信息
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
				//如果已登录，不需要再继续返回登录页
				usertoken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
				OpUser suser = (OpUser) request.getSession().getAttribute("user");
				if(!StringUtils.isBlank(usertoken)&&suser!=null){
					model = getUserMenu(suser.getAccount(), usertoken);
					if(model==null){
						model = new HashMap<String, Object>();
					}
					if(suser!=null){
						model.put("nickname", suser.getNickname());
					}
					logininfo.put("userid", suser.getId());
					logininfo.put("loginstatus", "0");
					return new ModelAndView("resource/index", model);
				}
				model.put("message", "用户名和密码不能为空！");
				logininfo.put("userid", userName);
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
			if (userName.length() < 2) {
				model.put("message", "输入账户不能小于2个字符");
				logininfo.put("userid", userName);
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
			if( password.length() < 5){
				model.put("message", "输入密码不能小于5个字符");
				logininfo.put("userid", userName);
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
			param.put("userName", userName);
			param.put("password", password);
			if (authelicationUser(param)) {
				try {
				    usertoken = UserTokenManager.createUserToken(UserTokenManager.OPWEB,userName, SystemConfig.getSystemProperty("securityKey"));
				    request.getSession().setAttribute(Constants.REQUEST_USER_TOKEN, usertoken);
				} catch (Exception e) {
					e.printStackTrace();
				}

				model = getUserMenu(userName, usertoken);

				// 获取user信息加入session
				OpUser OpUser = getByName(userName, usertoken);
				request.getSession().setAttribute("user", OpUser);
				if(model==null){
					model = new HashMap<String, Object>();
				}
				if(OpUser!=null){
					model.put("nickname", OpUser.getNickname());
					logininfo.put("userid", OpUser.getId());
					logininfo.put("loginstatus", "0");
				}
				return new ModelAndView("resource/index", model);
			} else {
				model.put("message", "账号或密码错误！");
				logininfo.put("userid", userName);
				logininfo.put("loginstatus", "1");
				return new ModelAndView("login", model);
			}
		}catch(Exception e){
			logger.error("运管端用户登录异常：", e);
			model.put("message", "登录失败！");
			logininfo.put("loginstatus", "1");
			String message = e.getMessage()==null?"":(e.getMessage().length()<=3800?e.getMessage():e.getMessage().substring(0,3800));
			logininfo.put("loginmessage", message);
			return new ModelAndView("login", model);
		}finally {
			try{
				addUserLoginLog(logininfo,usertoken);
			}catch(Exception e){
				logger.error("运管端用户登录日志记录异常：", e);
			}
			
		}
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
	
	public boolean authelicationUser(Map<String, String> param) {
		String userName = (String) param.get("userName");
		String rawPassword = (String) param.get("password");
		String encodedPassword = getPasswordByName(userName);
		if (null == encodedPassword) {
			return false;
		}
		return PasswordEncoder.matches_PWD(rawPassword, encodedPassword);
	}

	public String getPasswordByName(String loginName) {
		String password = templateHelper.dealRequestWithToken("/User/getPasswordByName/{loginName}", HttpMethod.GET, null, null, String.class, loginName);
		if (StringUtils.isBlank(password)) {
			return null;
		}
		return password.length() >= 2 ? StringUtils.substring(password, 1, password.length() - 1) : password;
	}
	
	public Map<String, Object> getUserMenu(String loginName, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetUserMenu/{loginName}", HttpMethod.GET, userToken, null,Map.class, loginName);
	}

	public OpUser getByName(String loginName, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, OpUser.class, loginName);
	}
	
	public List<Map<String,Object>> getRolesInfo(String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetRolesInfo", HttpMethod.GET, usertoken, null, List.class);
	}
	
	public Map<String, Object> changePwd(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/ChangePassword", HttpMethod.POST, usertoken, params, Map.class);
	}
	
	public int updateLogontimes(OpUser ou, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/UpdateLogontimes", HttpMethod.POST, usertoken, ou, int.class);
	}

	/**
	 * 获取该角色提示信息
	 * @param userToken
	 * @param roleId		角色ID
	 * @return
	 */
	public List<RedisMessage> warnMessage(String userToken, String roleId) {

//		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		//如果角色ID为空，则默认tmp
		if(StringUtils.isEmpty(roleId)){
//			2D1E2FA1-4870-4C3D-B40D-62EA8B3DF6DD
			roleId = "AFD8793C-7491-4549-8AB8-DBF1DEC1B31F-----";
		}
		List<RedisMessage> list = new ArrayList<RedisMessage>();

		list.addAll(this.listPopMessage(roleId));

		return list;
	}

	/**
	 * 返回提示框类消息
	 * @param roleId
	 * @return
	 */
	private List<RedisMessage> listPopMessage(String roleId){
		List<RedisMessage> list = new ArrayList<RedisMessage>();

		logger.info("获取消息角色ID：{}",roleId);

		Set<String> set = JedisUtil.getKeys("*"+roleId+"*");

		for(String key:set){
			String str = RedisListMessage.getInstance().getMessageByKey(key);
//			list.add(JSONObject.fromObject());
			list.add(GsonUtil.fromJson(str, RedisMessage.class));
		}


		return list ;
	}

	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		CreateImageCode vCode = new CreateImageCode(110,34,4,10);
        request.getSession().setAttribute("code", vCode.getCode());
        try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
