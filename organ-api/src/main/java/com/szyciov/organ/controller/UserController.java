package com.szyciov.organ.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.org.param.UserQueryParam;
import com.szyciov.organ.service.UserService;
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
	 * <p>根据用户名获取密码</p>
	 *
	 * @param loginName 用户名
	 * @return
	 */
	@RequestMapping(value = "api/User/GetPasswordByName/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String getPasswordByName(@PathVariable String loginName) {
		return userService.getPasswordByName(loginName);
	}

	@RequestMapping(value = "api/User/GetByLoginName/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public OrgUser getByLoginName(@PathVariable String loginName) {
		return userService.getByLoginName(loginName);
	}
	
	@RequestMapping(value = "api/User/GetMenuInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getMenuInfo(@RequestBody OrgUser OrgUser) {
		return userService.getMenuInfo(OrgUser);
	}
	
	@RequestMapping(value = "api/User/GetNavInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getNavInfo(@RequestBody OrgUser OrgUser) {
		return userService.getNavInfo(OrgUser);
	}
	
	
	@RequestMapping(value = "api/RoleManagement/HasFeature", method = RequestMethod.POST)
	@ResponseBody
	public boolean hasFeature(@RequestBody Map<String,Object> param) {
		return userService.hasFeature(param);
	}
	
	/**
	 * 根据用户获取部门用户树形结构
	 * @param OrgUser
	 * @return
	 */
	@RequestMapping(value = "api/User/GetDeptUserTreeNodes", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> getDeptUserTreeNodes(@RequestBody Map<String,Object> param) {
		return userService.getDeptUserTreeNodes(param);
	}
	
	/**
	 * 获取用户信息
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "api/User/GetUserInfo/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getUserInfo(@PathVariable String userid) {
		return userService.getUserInfo(userid);
	}
	
	/**
	 * 判断部门下有没有部门
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "api/User/HasChildDept/{deptid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean hasChildDept(@PathVariable String deptid) {
		return userService.hasChildDept(deptid);
	}
	
	@RequestMapping(value = "api/User/GetUserByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserByQuery(@RequestBody UserQueryParam userparam) {
		return userService.getUserByQuery(userparam);
	}
	
	@RequestMapping(value = "api/User/GetDeptByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDeptByQuery(@RequestBody UserQueryParam userparam) {
		return userService.getDeptByQuery(userparam);
	}
	
	@RequestMapping(value = "api/User/AddDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addDept(@RequestBody Map<String,Object> deptinfo) {
		return userService.addDept(deptinfo);
	}
	
	
	@RequestMapping(value = "api/User/GetRoles", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getRoles() {
		return userService.getRoles();
	}
	
	
	@RequestMapping(value = "api/User/GetValiableCompanyInfo", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getValiableCompanyInfo(@RequestBody OrgUser user) {
		return userService.getValiableCompanyInfo(user);
	}
	
	
	@RequestMapping(value = "api/User/GetSpecialstateDrivers", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getSpecialstateDrivers(@RequestBody Map<String,Object> param) {
		return userService.getSpecialstateDrivers(param);
	}
	
	@RequestMapping(value = "api/User/GetUseCarRules/{organid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getUseCarRules(@PathVariable String organid) {
		return userService.getUseCarRules(organid);
	}
	
	
	@RequestMapping(value = "api/User/AddUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUser(@RequestBody Map<String,Object> userinfo) {
		return userService.addUser(userinfo);
	}
	
	@RequestMapping(value = "api/User/UpdateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUser(@RequestBody Map<String,Object> userinfo) {
		return userService.updateUser(userinfo);
	}
	
	@RequestMapping(value = "api/User/UpdateDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateDept(@RequestBody Map<String,Object> deptinfo) {
		return userService.updateDept(deptinfo);
	}
	
	@RequestMapping(value = "api/User/DeleteDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteDept(@RequestBody Map<String,Object> param) {
		return userService.deleteDept(param);
	}
	
	@RequestMapping(value = "api/User/DeleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUser(@RequestBody Map<String,Object> param) {
		return userService.deleteUser(param);
	}
	
	@RequestMapping(value = "api/User/GetUserDrivers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserDrivers(@RequestBody Map<String,Object> param) {
		return userService.getUserDrivers(param);
	}
	
	@RequestMapping(value = "api/User/ResetPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPwd(@RequestBody Map<String,Object> param) {
		return userService.resetPwd(param);
	}
	
	@RequestMapping(value = "api/User/ChangeUsersDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeUsersDept(@RequestBody Map<String,Object> param) {
		return userService.changeUsersDept(param);
	}
	
	@RequestMapping(value = "api/User/DeleteUsers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteUsers(@RequestBody Map<String,Object> param) {
		return userService.deleteUsers(param);
	}
	
	@RequestMapping(value = "api/User/HasMainAccount/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public boolean hasMainAccount(@PathVariable String userName) {
		return userService.hasMainAccount(userName);
	}
	
	
	@RequestMapping(value = "api/User/IsUnbindAccount/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public boolean isUnbindAccount(@PathVariable String userName) {
		return userService.isUnbindAccount(userName);
	}
	
	@RequestMapping(value = "api/User/SetAsMainAccount/{userName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> setAsMainAccount(@PathVariable String userName) {
		return userService.setAsMainAccount(userName);
	}
	
	@RequestMapping(value = "api/User/UpdateFirstLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateFirstLogin(@RequestBody OrgUser user) {
		return userService.updateFirstLogin(user);
	}
	
	@RequestMapping(value = "api/User/DoImportUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doImportUser(@RequestBody Map<String,Object> params) {
		return userService.doImportUser(params);
	}
	
	@RequestMapping(value = "api/User/AddUserLoginLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addUserLoginLog(@RequestBody Map<String,Object> params) {
		return userService.addUserLoginLog(params);
	}
	
	@RequestMapping(value = "api/User/GetLastLoginLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getLastLoginLog(@RequestBody Map<String,Object> params) {
		return userService.getLastLoginLog(params);
	}
	
	@RequestMapping(value = "api/User/SaveUsertoken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveUsertoken(@RequestBody Map<String,Object> params) {
		return userService.saveUsertoken(params);
	}
	
	@RequestMapping(value = "api/User/CheckValid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkValid(@RequestBody Map<String,Object> params) {
		return userService.checkValid(params);
	}
	
}
