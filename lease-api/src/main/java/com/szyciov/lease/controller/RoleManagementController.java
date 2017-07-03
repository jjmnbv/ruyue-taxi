package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.param.RoleManagementQueryParam;
import com.szyciov.lease.service.RoleManagementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 角色管理模块控制器
 */
@Controller
public class RoleManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(RoleManagementController.class);

	public RoleManagementService roleManagementService;

	@Resource(name = "roleManagementService")
	public void setEmployeeService(RoleManagementService roleManagementService) {
		this.roleManagementService = roleManagementService;
	}

	/** 
	 * <p>分页查询角色管理模块信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/RoleManagement/GetRoleManagementByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getRoleManagementByQuery(@RequestBody RoleManagementQueryParam queryParam) {
		return roleManagementService.getRoleManagementByQuery(queryParam);
	}

	/** 
	 * <p>增加一条记录</p>
	 *
	 * @param roleManagement
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/Create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestBody RoleManagement roleManagement) {
		return roleManagementService.createRoleManagement(roleManagement);
	}

	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/RoleManagement/Delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteRoleManagement(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		return roleManagementService.deleteRoleManagement(id);
	}
	
	/** 
	 * <p>更新一条记录</p>
	 *
	 * @param roleManagement
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateRoleManagement(@RequestBody RoleManagement roleManagement) {
		return roleManagementService.updateRoleManagement(roleManagement);
	}

	/** 
	 * <p>根据序列Id查询记录</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RoleManagement getById(@PathVariable String id) {
		logger.log(Level.INFO, "api getById id:" + id);
		return roleManagementService.getById(id);
	}
	
	/** 
	 * <p>根据序列Id查询记录</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/GetDataTreeById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDataTreeById(@PathVariable String id){
		return roleManagementService.getDataTreeById(id);
	}
	
	
	@RequestMapping(value = "api/RoleManagement/AssignDataAuthority", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignDataAuthority(@RequestBody Map<String,Object> params) {
		return roleManagementService.assignDataAuthority(params);
	}
	
	/**
	 * 获取功能菜单树形结构
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/GetMenuTreeById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getMenuTreeById(@PathVariable String id){
		return roleManagementService.getMenuTreeById(id);
	}
	
	@RequestMapping(value = "api/RoleManagement/AssignFunctionAuthority", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignFunctionAuthority(@RequestBody Map<String,Object> params) {
		return roleManagementService.assignFunctionAuthority(params);
	}
	
	/**
	 * 判断是否拥有权限
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/HasFeature", method = RequestMethod.POST)
	@ResponseBody
	public boolean hasFeature(@RequestBody Map<String,Object> params) {
		return roleManagementService.hasFeature(params);
	}
	
	/**
	 * 查询租赁公司下的所有角色名称(select2)
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/RoleManagement/GetLeRolemanagementBySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getLeRolemanagementBySelect(@RequestBody RoleManagement object) {
		return roleManagementService.getLeRolemanagementBySelect(object);
	}
}
