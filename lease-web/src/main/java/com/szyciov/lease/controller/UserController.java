package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.UserSpecialEnum;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.UserQueryParam;
import com.szyciov.lease.service.UserService;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserService userService;
	
	@Resource(name = "userService")
	public void setEmployeeService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/User/Index")
	public ModelAndView getDictionaryManagementIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		User user = getLoginLeUser(request);
		RoleManagement object = new RoleManagement();
		object.setLeasescompanyid(user.getLeasescompanyid());
		List<Map<String, String>> rolenameList =  userService.getLeRolename(object, userToken);
		
		ModelAndView view = new ModelAndView();
		view.addObject("rolenameList", rolenameList);
		view.setViewName("resource/user/index");
		return view;
	}
	
	@RequestMapping(value = "User/Login")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {
		return userService.doLogin(request,response);
	}
	
	
	@RequestMapping(value = "User/ChangePwd")
	@ResponseBody
	public Map<String,Object> changePwd(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		if(user!=null){
			params.put("userid", user.getId());
			params.put("loginname", user.getAccount());
			params.put("email", user.getEmail());
			params.put("leasescompanyid", user.getLeasescompanyid());
		}
		return userService.changePwd(params,usertoken);
	}
	
	@RequestMapping(value = "User/GetImgCode")
	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		 userService.getImgCode(request,response);
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
	
	@RequestMapping("User/GetUserByQuery")
	@ResponseBody
	public PageBean getUserByQuery(@RequestBody UserQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		if(user!=null){
			queryParam.setLeasescompanyid(user.getLeasescompanyid());
		}
		return userService.getUserByQuery(queryParam,usertoken);
	}
	
	@RequestMapping("/User/Create")
	@ResponseBody
	public Map<String, String> create(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		User cuser = getLoginLeUser(request);
		//创建账户的人必须是超管
		if(cuser!=null){
			user.setLeasescompanyid(cuser.getLeasescompanyid());
		}else{
			Map<String,String> res = new HashMap<String,String>();
			res.put("status", "fail");
			res.put("message", "无法获取创建者信息");
			return res;
		}
		return userService.createUser(user, userToken);
	}
	
	@RequestMapping("User/GetById")
	@ResponseBody
	public User getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.info("web getById id:" + id);
		String userToken = getUserToken(request);
		return userService.getById(id, userToken);
	}
	
	@RequestMapping("/User/Update")
	@ResponseBody
	public Map<String, String> update(@RequestBody User user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		return userService.updateUser(user, userToken);
	}
	
	@RequestMapping("/User/Delete")
	@ResponseBody
	public Map<String, String> delete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = getUserToken(request);
		return userService.deleteUser(id, userToken);
	}


	/**
	 * <p>根据用户id获取角色相关的列表等信息</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "User/GetRolesInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRolesInfo(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		User cuser = getLoginLeUser(request);
		Map<String,String> params = new HashMap();
		//创建账户的人必须是超管
		if(cuser!=null){
			params.put("userid", id);
			params.put("leasecompanyid",cuser.getLeasescompanyid());
		}
		return userService.getRolesInfo(params,userToken);
	}
	
	/**
	 * 分配角色
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "User/AssignRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignRole(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return userService.assignRole(params,userToken);
	}

	
	/**
	 * 获取当前登录用户信息
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "User/GetCurrentUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCurrentUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		User cuser = getLoginLeUser(request);
		User newuser = userService.getById(cuser.getId(), userToken);
		Map<String,Object> res = new HashMap<String,Object>();
		String roletype_caption = "1".equalsIgnoreCase(newuser.getSpecialstate())?"系统管理员":newuser.getRoletypecaption();
		res.put("roletype_caption", roletype_caption);
		res.put("id", newuser.getId());
		res.put("nickname_caption", newuser.getNickname());
		res.put("account_caption", newuser.getAccount());
		res.put("account_caption", newuser.getAccount());
		res.put("telphone", newuser.getTelphone());
		res.put("rolename_caption", newuser.getRolename());
		res.put("email_caption", newuser.getEmail());
		res.put("email", newuser.getEmail());
		return res;
	}




	/**
	 * 用户提醒消息
	 * @return
	 */
	@RequestMapping(value = "User/WarnMessage", method = RequestMethod.POST)
	@ResponseBody
	public List<RedisMessage> warnMessage(HttpServletRequest request, HttpServletResponse response) {
		List<RedisMessage> list = new ArrayList<RedisMessage>();
		String userToken = getUserToken(request);
		User user = getLoginLeUser(request);
		String roleId = null;
		String userId = null;
		logger.info("用户：{}，角色ID：{}",user.getNickname(),user.getRoleid());
		//如果为超管，则设置角色ID为admin
		if(UserSpecialEnum.ADMIN.code.equals(user.getSpecialstate())){
			roleId = RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code+user.getLeasescompanyid();
		}else {
			//角色ID
			roleId = user.getRoleid();
			userId = user.getId();
		}
		  roleId = RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code+roleId;
		  userId = RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code+userId;
		 list.addAll(userService.warnMessage(userToken,roleId));
		 list.addAll(userService.warnMessage(userToken,userId));
		return list;
	}
	
	/**
	 * 根据登录名查询菜单权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "User/GetMenuByLoginName")
	@ResponseBody
	public Map<String, Object> getMenuByLoginName(HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		User user = getLoginLeUser(request);
		return userService.getUserMenu(user.getAccount(), userToken);
	}
	
	/**
	 * 查询租赁公司下所有账号(select2)
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "User/GetLeUserAccountBySelect")
	@ResponseBody
	public List<Map<String, String>> getLeUserAccountBySelect(@RequestParam(value = "account", required = false) String account, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		User user = getLoginLeUser(request);
		user.setAccount(account);
		return userService.getLeUserAccountBySelect(user, userToken);
	}
	
	/**
	 * 查询租赁公司下所有用户姓名和手机号
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "User/GetLeUserNicknameBySelect")
	@ResponseBody
	public List<Map<String, String>> getLeUserNicknameBySelect(@RequestParam(value = "nickname", required = false) String nickname, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		User user = getLoginLeUser(request);
		user.setNickname(nickname);
		return userService.getLeUserNicknameBySelect(user, userToken);
	}

}


