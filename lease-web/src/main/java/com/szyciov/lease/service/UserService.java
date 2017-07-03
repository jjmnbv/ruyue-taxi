package com.szyciov.lease.service;

import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.UserQueryParam;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.*;
import com.szyciov.util.message.RedisListMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service("userService")
public class UserService {
	private TemplateHelper templateHelper = new TemplateHelper();

	public boolean authelicationUser(Map<String, String> param) {
		String userName = (String) param.get("userName");
		String rawPassword = (String) param.get("password");
		String encodedPassword = getPasswordByName(userName);
		if (null == encodedPassword) {
			return false;
		}
		return PasswordEncoder.matches(rawPassword, encodedPassword);
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

	public User getByName(String loginName, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, User.class, loginName);
	}

	public PageBean getUserByQuery(UserQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/GetUserByQuery", HttpMethod.POST, usertoken, queryParam, PageBean.class);
	}

	public Map<String, String> createUser(User user, String userToken) {
		return templateHelper.dealRequestWithToken("/User/Create", HttpMethod.POST, userToken, user, Map.class);
	}

	public User getById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetById/{id}", HttpMethod.GET, userToken, null, User.class, id);
	}

	public Map<String, String> updateUser(User user, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/Update", HttpMethod.POST, usertoken, user, Map.class);
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
//			String imgcode = (String) request.getParameter("code");
//			Object code = request.getSession().getAttribute("code");
//			if(code!=null){
//				String codestr = (String) code;
//				if(!codestr.equalsIgnoreCase(imgcode)){
//					model.put("message", "图片验证码校验失败！");
//					return new ModelAndView("login", model);
//				}
//			}
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
				//如果已登录，不需要再继续返回登录页
				usertoken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
				User suser = (User) request.getSession().getAttribute("user");
				if(!StringUtils.isBlank(usertoken)&&suser!=null){
					model = getUserMenu(suser.getAccount(), usertoken);
					if(model==null){
						model = new HashMap<String, Object>();
					}
					if(suser!=null){
						model.put("nickname", suser.getNickname());
						String remember = (String) request.getParameter("remember");
						boolean needremember = "1".equalsIgnoreCase(remember)?true:false;
						if(needremember){
							model.put("account", suser.getAccount());
						}
						logininfo.put("userid", suser.getId());
						logininfo.put("loginstatus", "0");
					}
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
			//判断登入的超管用户是否被禁用账号
					String ok = forbidUser(usertoken,userName);
					if(ok != null){
						model.put("message", "此租赁公司超管已被禁用");
						logininfo.put("userid", userName);
						logininfo.put("loginstatus", "1");
						return new ModelAndView("login", model);
					}
					
			param.put("userName", userName);
			param.put("password", password);
			if (authelicationUser(param)) {
				try {
				    usertoken = UserTokenManager.createUserToken(UserTokenManager.LEASEUSER,userName, SystemConfig.getSystemProperty("securityKey"));
				    request.getSession().setAttribute(Constants.REQUEST_USER_TOKEN, usertoken);
				} catch (Exception e) {
					e.printStackTrace();
				}

				model = getUserMenu(userName, usertoken);

				// 获取user信息加入session
				User user = getByName(userName, usertoken);
				request.getSession().setAttribute("user", user);

				
				//增加租赁端超管登陆后 修改le_user的firsttime
				String id = user.getId();
				templateHelper.dealRequestWithToken("/LeLeasescompany/updataLeUserFristtime/{id}", HttpMethod.GET, usertoken, null,
						void.class,id);
				

				if(model==null){
					model = new HashMap<String, Object>();
				}
				if(user!=null){
					model.put("nickname", user.getNickname());
					String remember = (String) request.getParameter("remember");
					boolean needremember = "1".equalsIgnoreCase(remember)?true:false;
					if(needremember){
						model.put("account", user.getAccount());
					}
					logininfo.put("userid", user.getId());
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
			model.put("message", "登录失败！");
			logininfo.put("loginstatus", "1");
			String message = e.getMessage()==null?"":(e.getMessage().length()<=3800?e.getMessage():e.getMessage().substring(0,3800));
			logininfo.put("loginmessage", message);
			return new ModelAndView("login", model);
		}finally {
			try{
				addUserLoginLog(logininfo, usertoken);
			}catch(Exception e){}
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
	
	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		CreateImageCode vCode = new CreateImageCode(100,48,4,10);
        request.getSession().setAttribute("code", vCode.getCode());
        try {
			vCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Object> changePwd(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/User/ChangePassword", HttpMethod.POST, usertoken, params, Map.class);
	}


	//判断此用户的超管账号用没有被禁用
	public String forbidUser(String usertoken,String loginName){
		return templateHelper.dealRequestWithToken("/User/forbidUser/{loginName}", HttpMethod.GET, usertoken, null, String.class,loginName);
	}


	/**
	 * 获取该角色提示信息
	 * @param userToken
	 * @param roleId		角色ID
	 * @return
	 */
	public List<RedisMessage> warnMessage(String userToken, String roleId) {

		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
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


		Set<String> set = JedisUtil.getKeys("*"+roleId+"*");

		for(String key:set){
			String str = RedisListMessage.getInstance().getMessageByKey(key);
//			list.add(JSONObject.fromObject());
			list.add(GsonUtil.fromJson(str, RedisMessage.class));
		}


		return list ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLeUserAccountBySelect(User user, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetLeUserAccountBySelect", HttpMethod.POST, userToken, user, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLeUserNicknameBySelect(User user, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetLeUserNicknameBySelect", HttpMethod.POST, userToken, user, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLeRolename(RoleManagement object, String userToken) {
		return templateHelper.dealRequestWithToken("/User/GetLeRolename", HttpMethod.POST, userToken, object, List.class);
	}

}
