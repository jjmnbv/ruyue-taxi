package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.MenuBar;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.UserQueryParam;

public interface UserMapper {

	List<User> getUserListByQuery(UserQueryParam userqueryParam);

	int getUserListCountByQuery(UserQueryParam userqueryParam);

	void createUser(User user);

	String getPasswordByName(String loginname);

	User getByLoginName(String loginname);

	List<MenuBar> getUserMenuList(User user);

	User getById(String id);

	void updateUser(User user);

	void deleteUser(String id);

	List<RoleManagement> getRoleManagementByUserId(String userid);

	List<RoleManagement> getValiableRoles(String leasecompanyid);

	void deleteRoleById(String userid);

	void addRole4User(Map<String, Object> params);
	
	String forbidUser(String loginName);
	
	List<Map<String, String>> getLeUserAccountBySelect(User user);
	
	List<Map<String, String>> getLeUserNicknameBySelect(User user);
	
	List<Map<String, String>> getLeRolename(RoleManagement object);

	void addUserLoginLog(Map<String, Object> params);
	
	String getTocstateByLeasescompanyid(String leasescompanyid);
	
	int getTocOrderCountByLeasescompanyid(String leasescompanyid);

}
