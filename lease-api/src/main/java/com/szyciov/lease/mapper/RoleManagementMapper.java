package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.Menu;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.param.RoleManagementQueryParam;
import org.apache.ibatis.annotations.Param;

public interface RoleManagementMapper {
	@SuppressWarnings("rawtypes")
	void createCompany(Map map);

	List<RoleManagement> getRoleManagementListByQuery(RoleManagementQueryParam queryParam);

	int getRoleManagementListCountByQuery(RoleManagementQueryParam queryParam);

	void deleteRoleManagement(String id);
	
	/**
	 * 根据名称查询角色是否存在
	 * @param roleManagement
	 * @return
	 */
	int getByRoleName(Map<String,String> params);
	
	RoleManagement getById(String id);

	void createRoleManagement(RoleManagement roleManagement);

	void updateRoleManagement(RoleManagement roleManagement);
	
	List<OrgOrgan> getAvailableOrgList(String companyid);

	void deleteDataAuthorityByRole(String roleid);

	void addDataAuthority(List<Map<String, String>> newparam);

	List<String> getMenuFunctionIdByRoleId(String id);

	List<Menu> getAvailableMenuList();

	void deleteFunctionAuthorityByRole(String roleid);

	void addFunctionAuthority(List<Map<String, String>> newparam);

	boolean hasFeature(Map<String, Object> params);

	boolean hasUser4ThisRole(String id);
	
	List<Map<String, String>> getLeRolemanagementBySelect(RoleManagement object);

	List<String> listRoleId(@Param("leaseId") String leaseId,@Param("functionName") String functionName);
	List<String> AllListRoleId(@Param("leaseId") String leaseId);
	
	List<RoleManagement> getRolemanagementByName(RoleManagement object);
}