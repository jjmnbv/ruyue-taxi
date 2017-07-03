package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.Menu;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.operate.mapper.RoleManagementMapper;
import com.szyciov.param.QueryParam;

@Repository("RoleManagementDao")
public class RoleManagementDao {
	public RoleManagementDao() {
	}

	private RoleManagementMapper mapper;

	@Resource
	public void setMapper(RoleManagementMapper mapper) {
		this.mapper = mapper;
	}

	public List<RoleManagement> getRoleManagementListByQuery(QueryParam queryParam) {
		return mapper.getRoleManagementListByQuery(queryParam);
	}

	public int getRoleManagementListCountByQuery(QueryParam queryParam) {
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
	
	public List<Map<String,Object>> getAvailableLeList(){
		return mapper.getAvailableLeList();
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

	public List<Dictionary> getRoleTypes() {
		return mapper.getRoleTypes();
	}

	public boolean hasUser4ThisRole(String id) {
		return mapper.hasUser4ThisRole(id);
	}

	public List<String> listRoleId(String cityCode,String functionName){
		return mapper.listRoleId(cityCode,functionName);
	}
	public List<String> allListRoleId(){
		return mapper.allListRoleId();
	}
	
	public List<RoleManagement> getRolemanagementByName(RoleManagement object) {
		return mapper.getRolemanagementByName(object);
	}


}
