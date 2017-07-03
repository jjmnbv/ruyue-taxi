package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.operate.service.RoleManagementService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

@Controller
public class RoleManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(RoleManagementController.class);

	public RoleManagementService roleManagementService;

	@Resource(name = "roleManagementService")
	public void setRoleManagementService(RoleManagementService roleManagementService) {
		this.roleManagementService = roleManagementService;
	}

	@RequestMapping("/RoleManagement/Index")
	public ModelAndView getRoleManagementIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> extrainfo = new HashMap<String,Object>();
		String userToken = getUserToken(request);
		List<Dictionary> roletypelist = roleManagementService.getRoleTypeList(userToken);
		extrainfo.put("roletypelist", roletypelist);
		return new ModelAndView("resource/rolemanagement/index", extrainfo);
	}

	@RequestMapping("RoleManagement/GetRoleManagementByQuery")
	@ResponseBody
	public PageBean getRoleManagementByQuery(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		return roleManagementService.getRoleManagementByQuery(queryParam, userToken);
	}

	@RequestMapping("/RoleManagement/Delete")
	@ResponseBody
	public Map<String, String> delete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = getUserToken(request);
		return roleManagementService.deleteRoleManagement(id, userToken);
	}

	@RequestMapping("RoleManagement/GetById")
	@ResponseBody
	public RoleManagement getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.log(Level.INFO, "web getById id:" + id);
		String userToken = getUserToken(request);
		return roleManagementService.getById(id, userToken);
	}


	@RequestMapping("/RoleManagement/Create")
	@ResponseBody
	public Map<String, String> create(@RequestBody RoleManagement roleManagement, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		roleManagement.setCreater(user.getId());
		roleManagement.setUpdater(user.getId());
		return roleManagementService.createRoleManagement(roleManagement, userToken);
	}

	@RequestMapping("/RoleManagement/Update")
	@ResponseBody
	public Map<String, String> update(@RequestBody RoleManagement roleManagement, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		roleManagement.setCreater(user.getId());
		roleManagement.setUpdater(user.getId());
		return roleManagementService.updateRoleManagement(roleManagement, userToken);
	}

	@RequestMapping("RoleManagement/GetDataTreeById")
	@ResponseBody
	public Map<String,Object> getDataTreeById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.log(Level.INFO, "web getById id:" + id);
		String userToken = getUserToken(request);
		return roleManagementService.getDataTreeById(id, userToken);
	}
	

	@RequestMapping(value = "RoleManagement/AssignDataAuthority", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignDataAuthority(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return roleManagementService.assignDataAuthority(params,userToken);
	}
	
	@RequestMapping("RoleManagement/GetMenuTreeById")
	@ResponseBody
	public Map<String,Object> getMenuTreeById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.log(Level.INFO, "web getById id:" + id);
		String userToken = getUserToken(request);
		return roleManagementService.getMenuTreeById(id, userToken);
	}
	
	@RequestMapping(value = "RoleManagement/AssignFunctionAuthority", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> assignFunctionAuthority(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return roleManagementService.assignFunctionAuthority(params,userToken);
	}
}
