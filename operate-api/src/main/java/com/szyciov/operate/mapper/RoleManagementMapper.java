package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.Menu;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.param.QueryParam;
import org.apache.ibatis.annotations.Param;

public interface RoleManagementMapper {
	@SuppressWarnings("rawtypes")
	void createCompany(Map map);

	List<RoleManagement> getRoleManagementListByQuery(QueryParam queryParam);

	int getRoleManagementListCountByQuery(QueryParam queryParam);

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
	
	List<Map<String,Object>> getAvailableLeList();

	void deleteDataAuthorityByRole(String roleid);

	void addDataAuthority(List<Map<String, String>> newparam);

	List<String> getMenuFunctionIdByRoleId(String id);

	List<Menu> getAvailableMenuList();

	void deleteFunctionAuthorityByRole(String roleid);

	void addFunctionAuthority(List<Map<String, String>> newparam);

	boolean hasFeature(Map<String, Object> params);

	List<Dictionary> getRoleTypes();

	boolean hasUser4ThisRole(String id);

	List<String> listRoleId(@Param("cityCode") String cityCode,@Param("functionName") String functionName);
	public List<String> allListRoleId();
	
	List<RoleManagement> getRolemanagementByName(RoleManagement object);

}