package com.szyciov.lease.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.param.RoleManagementQueryParam;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("roleManagementService")
public class RoleManagementService {
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getRoleManagementByQuery(RoleManagementQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/GetRoleManagementByQuery", HttpMethod.POST,userToken, queryParam, PageBean.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> deleteRoleManagement(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/Delete/{id}", HttpMethod.DELETE, userToken, null,Map.class, id);
	}

	public RoleManagement getById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/GetById/{id}", HttpMethod.GET, userToken, null,RoleManagement.class, id);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> createRoleManagement(RoleManagement roleManagement, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/Create", HttpMethod.POST, userToken, roleManagement,Map.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> updateRoleManagement(RoleManagement roleManagement, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/Update", HttpMethod.POST, userToken, roleManagement,Map.class);
	}

	public List<Dictionary> getRoleTypeList(String userToken) {
		return templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type={queryType}", HttpMethod.GET, userToken, null, List.class, "角色类型");
	}

	public Map<String, Object> getDataTreeById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/GetDataTreeById/{id}", HttpMethod.GET, userToken, null,Map.class, id);
	}

	public Map<String, String> assignDataAuthority(Map<String, Object> params,String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/AssignDataAuthority", HttpMethod.POST, userToken, params ,Map.class);
	}

	public Map<String, Object> getMenuTreeById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/GetMenuTreeById/{id}", HttpMethod.GET, userToken, null,Map.class, id);
	}

	public Map<String, String> assignFunctionAuthority(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/AssignFunctionAuthority", HttpMethod.POST, userToken, params ,Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getLeRolemanagementBySelect(RoleManagement object, String userToken) {
		return templateHelper.dealRequestWithToken("/RoleManagement/GetLeRolemanagementBySelect", HttpMethod.POST, userToken, object, List.class);
	}

}
