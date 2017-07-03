package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.Menu;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.mapper.RoleManagementMapper;
import com.szyciov.lease.param.RoleManagementQueryParam;

@Repository("RoleManagementDao")
public class RoleManagementDao {
	public RoleManagementDao() {
	}

	private RoleManagementMapper mapper;

	@Resource
	public void setMapper(RoleManagementMapper mapper) {
		this.mapper = mapper;
	}

	public List<RoleManagement> getRoleManagementListByQuery(RoleManagementQueryParam queryParam) {
		return mapper.getRoleManagementListByQuery(queryParam);
	}

	public int getRoleManagementListCountByQuery(RoleManagementQueryParam queryParam) {
		return mapper.getRoleManagementListCountByQuery(queryParam);
	}
	
	public int getByRoleName(Map<String,String> params){
		return mapper.getByRoleName(params);
	}

	public void deleteRoleManagement(String id) {
		mapper.deleteRoleManagement(id);
	}

	public RoleManagement getById(String id) {
		return mapper.getById(id);
	}

	@SuppressWarnings("rawtypes")
	public void createRoleManagement(RoleManagement roleManagement) {
		mapper.createRoleManagement(roleManagement);
	}

	@SuppressWarnings("rawtypes")
	public void updateRoleManagement(RoleManagement roleManagement) {
		mapper.updateRoleManagement(roleManagement);
	}
	
	public List<OrgOrgan> getAvailableOrgList(String companyid){
		return mapper.getAvailableOrgList(companyid);
	}

	public void deleteDataAuthorityByRole(String roleid) {
		mapper.deleteDataAuthorityByRole(roleid);
	}

	public void addDataAuthority(List<Map<String, String>> newparam) {
		mapper.addDataAuthority(newparam);
	}

	public List<String> getMenuFunctionIdByRoleId(String id) {
		return mapper.getMenuFunctionIdByRoleId(id);
	}

	public List<Menu> getAvailableMenuList() {
		return mapper.getAvailableMenuList();
	}

	public void deleteFunctionAuthorityByRole(String roleid) {
		mapper.deleteFunctionAuthorityByRole(roleid);
	}

	public void addFunctionAuthority(List<Map<String, String>> newparam) {
		mapper.addFunctionAuthority(newparam);
	}

	public boolean hasFeature(Map<String, Object> params) {
		return mapper.hasFeature(params);
	}

	public boolean hasUser4ThisRole(String id) {
		return mapper.hasUser4ThisRole(id);
	}
	
	public List<Map<String, String>> getLeRolemanagementBySelect(RoleManagement object) {
		return mapper.getLeRolemanagementBySelect(object);
	}


	public List<String> listRoleId(String leaseId,String functionName){
		return mapper.listRoleId(leaseId,functionName);
	}
	public List<String> AllListRoleId(String leaseId){
		return mapper.AllListRoleId(leaseId);
	}
	
	public List<RoleManagement> getRolemanagementByName(RoleManagement object) {
		return mapper.getRolemanagementByName(object);
	}
}
