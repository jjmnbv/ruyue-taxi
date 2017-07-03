package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.MenuBar;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.op.param.UserQueryParam;

public interface UserMapper {

	List<OpUser> getUserListByQuery(UserQueryParam userqueryParam);

	int getUserListCountByQuery(UserQueryParam userqueryParam);

	void createUser(OpUser OpUser);

	String getPasswordByName(String loginname);

	OpUser getByLoginName(String loginname);

	List<MenuBar> getUserMenuList(OpUser OpUser);

	OpUser getById(String id);

	void updateUser(OpUser OpUser);

	void deleteUser(String id);

	List<RoleManagement> getRoleManagementByUserId(String userid);

	List<RoleManagement> getValiableRoles();

	void deleteRoleById(String userid);

	void addRole4User(Map<String, Object> params);

	List<Map<String, Object>> getRolesInfo();
	
	void updateLogontimes(OpUser OpUser);
	
	void resetLogontimes(OpUser OpUser);

	void addUserLoginLog(Map<String, Object> params);
	
}
