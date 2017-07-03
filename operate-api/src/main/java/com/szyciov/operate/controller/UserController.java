package com.szyciov.operate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.UserQueryParam;
import com.szyciov.operate.service.UserService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 用户管理模块控制器
 */
@Controller
public class UserController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	public UserService userService;

	@Resource(name = "userService")
	public void setEmployeeService(UserService userService) {
		this.userService = userService;
	}
	
	/** 
	 * <p>分页查询用户模块信息</p>
	 *
	 * @param userqueryParam
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/User/GetUserByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserByQuery(@RequestBody UserQueryParam userqueryParam)  {
		return userService.getUserByQuery(userqueryParam);
	}

	/** 
	 * <p>增加一条记录</p>
	 *
	 * @param OpUser
	 * @return
	 */
	@RequestMapping(value = "api/User/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createUser(@RequestBody OpUser OpUser) {
		return userService.createUser(OpUser);
	}
	

	/** 
	 * <p>修改用户密码</p>
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/User/ChangePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePassword(@RequestBody Map<String, String> param) {
		return userService.changePassword(param);
	}
	
	/** 
	 * <p>修改密码错误次数</p>
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/User/UpdateLogontimes", method = RequestMethod.POST)
	@ResponseBody
	public void updateLogontimes(@RequestBody OpUser ou) {
		userService.updateLogontimes(ou);
	}

	/**
	 * <p>
	 * 删除一条记录
	 * </p>
	 *
	 * @param id
	 *            待删除记录对应的序列号
	 * @return 返回执行结果
	 */
	@RequestMapping(value = "api/User/Delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> deleteUser(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		return userService.deleteUser(id);
	}

	/** 
	 * <p>更新一条记录</p>
	 *
	 * @param OpUser
	 * @return
	 */
	@RequestMapping(value = "api/User/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateUser(@RequestBody OpUser OpUser) {
		return userService.updateUser(OpUser);
	}

	/** 
	 * <p>根据序列Id查询记录</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/User/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpUser getById(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		return userService.getById(id);
	}

	/**
	 * <p>根据用户id获取角色相关的列表等信息</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/User/GetRolesInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getRolesInfo(@RequestBody Map<String, Object> param) {
		return userService.getRolesInfo(param);
	}
	
	
	/**
	 * 分配角色
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/User/AssignRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignRole(@RequestBody Map<String,Object> params) {
		return userService.assignRole(params);
	}
	
	
	/** 
	 * <p>根据用户名获取用户信息</p>
	 *
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "api/User/GetByLoginName/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public OpUser getByLoginName(@PathVariable String loginName) {
		logger.log(Level.INFO, "getByLoginName:" + loginName);
		return userService.getByLoginName(loginName);
	}

	/** 
	 * <p>根据用户名获取用户的菜单信息</p>
	 *
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "api/User/GetUserMenu/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserMenu(@PathVariable("loginName") String loginName) {
		Map<String, String> param = new HashMap<String, String>();

		if (StringUtils.isBlank(loginName)) {
			return null;
		}

		param.put("loginname", loginName);

		return userService.getUserMenu(param);
	}

	/**
	 * <p>根据用户名获取密码</p>
	 *
	 * @param loginName 用户名
	 * @return
	 */
	@RequestMapping(value = "api/User/getPasswordByName/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String getPasswordByName(@PathVariable String loginName) {
		return userService.getPasswordByName(loginName);
	}

	/**
	 * 获取运管端的角色类型有哪些
	 * 字典表查询
	 * @return
	 */
	@RequestMapping(value = "api/User/GetRolesInfo", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getRolesInfo() {
		return userService.getRolesInfo();
	}
	
	@RequestMapping(value = "api/User/AddUserLoginLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUserLoginLog(@RequestBody Map<String,Object> params) {
		return userService.addUserLoginLog(params);
	}
}
