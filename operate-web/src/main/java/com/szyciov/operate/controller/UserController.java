package com.szyciov.operate.controller;

import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.UserSpecialEnum;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.UserQueryParam;
import com.szyciov.operate.service.UserService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * opuser也就是运管端web端的用户控制器
 * @author zhu
 *
 */
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
		String usertoken = getUserToken(request);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("rolesinfo", userService.getRolesInfo(usertoken));
		return new ModelAndView("resource/user/index", model);
	}
	
	@RequestMapping(value = "/User/ChangePwdIndex")
	public ModelAndView changePwdIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		return new ModelAndView("resource/changepwd/index", model);
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
		OpUser cuser = getLoginOpUser(request);
		if(cuser!=null){
			params.put("userid", cuser.getId());
			params.put("loginname", cuser.getAccount());
			params.put("email", cuser.getEmail());
		}
		return userService.changePwd(params,usertoken);
	}
	/** start */
	@RequestMapping(value = "User/UpdateLogontimes")
	@ResponseBody
	public int updateLogontimes(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser cuser = getLoginOpUser(request);
		return userService.updateLogontimes(cuser,usertoken);
	}
	
	@RequestMapping("User/GetByIds")
	@ResponseBody
	public OpUser getByIds(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = getUserToken(request);
		OpUser cuser = getLoginOpUser(request);
		return userService.getById(cuser.getId(), userToken);
	}
	/** end */
	
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
		return userService.getUserByQuery(queryParam,usertoken);
	}
	
	@RequestMapping("/User/Create")
	@ResponseBody
	public Map<String, String> create(@RequestBody OpUser user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OpUser cuser = getLoginOpUser(request);
		user.setOperateid(cuser.getOperateid());
		return userService.createUser(user, userToken);
	}
	
	@RequestMapping("User/GetById")
	@ResponseBody
	public OpUser getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.info("web getById id:" + id);
		String userToken = getUserToken(request);
		return userService.getById(id, userToken);
	}
	
	@RequestMapping("/User/Update")
	@ResponseBody
	public Map<String, String> update(@RequestBody OpUser user, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OpUser cuser = getLoginOpUser(request);
		if(StringUtils.isBlank(user.getId())){
			user.setId(cuser.getId());
		}
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
		OpUser cuser = getLoginOpUser(request);
		Map<String,String> params = new HashMap();
		//创建账户的人必须是超管
		if(cuser!=null){
			params.put("userid", id);
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
	 * 用户提醒消息
	 * @return
	 */
	@RequestMapping(value = "User/WarnMessage", method = RequestMethod.POST)
	@ResponseBody
	public List<RedisMessage> warnMessage(HttpServletRequest request, HttpServletResponse response) {
		 List<RedisMessage> list = new ArrayList<RedisMessage>();
		//获取所有客服人员
		String userToken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		String roleId = null;
		String userId = null;
		//如果为超管，则设置角色ID为admin
		if(UserSpecialEnum.ADMIN.code.equals(opUser.getUsertype())){
			roleId = RedisKeyEnum.MESSAGE_OPERATE_ROLE_ADMIN.code;
		}else {
			//角色ID
			 roleId = opUser.getRoleid();
			 //获取userid 报警专用
			 userId = opUser.getId();
		}
		roleId = RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code+roleId;
		userId = RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code+userId;
		list.addAll(userService.warnMessage(userToken,roleId));
		list.addAll(userService.warnMessage(userToken,userId));
        logger.info("warnMessage--获取用户信息，用户：{}，角色：{}", new String[]{GsonUtil.toJson(opUser),roleId});

        return list;
	}

	@RequestMapping(value = "User/GetImgCode")
	public void getImgCode(HttpServletRequest request, HttpServletResponse response) {
		 userService.getImgCode(request,response);
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
		OpUser cuser = getLoginOpUser(request);
		OpUser newuser = userService.getById(cuser.getId(), userToken);
		Map<String,Object> res = new HashMap<String,Object>();
		String roletype_caption = "1".equalsIgnoreCase(newuser.getUsertype())?"系统管理员":newuser.getRoletypecaption();
		res.put("roletype_caption", roletype_caption);
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
	 * 根据登录名查询菜单权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "User/GetMenuByLoginName")
	@ResponseBody
	public Map<String, Object> getMenuByLoginName(HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		return userService.getUserMenu(user.getAccount(), userToken);
	}
}
