package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.MenuBar;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.mapper.UserMapper;
import com.szyciov.lease.param.UserQueryParam;

@Repository("UserDao")
public class UserDao {
	
	private UserMapper mapper;

	@Resource
	public void setMapper(UserMapper mapper) {
		this.mapper = mapper;
	}

	public List<User> getUserListByQuery(UserQueryParam userqueryParam) {
		return mapper.getUserListByQuery(userqueryParam);
	}

	public int getUserListCountByQuery(UserQueryParam userqueryParam) {
		return mapper.getUserListCountByQuery(userqueryParam);
	}

	public void createUser(User user) {
		mapper.createUser(user);
	}

	public void updateUser(User user) {
		mapper.updateUser(user);
	}

	public void deleteUser(String id) {
		mapper.deleteUser(id);
	}

	public String getPasswordByName(String loginname) {
		return mapper.getPasswordByName(loginname);
	}

	public User getByLoginName(String loginname) {
		return mapper.getByLoginName(loginname);
	}

	public List<MenuBar> getUserMenuList(User user) {
		return mapper.getUserMenuList(user);
	}

	public User getById(String id) {
		return mapper.getById(id);
	}

	public List<RoleManagement> getRoleManagementByUserId(String userid) {
		return mapper.getRoleManagementByUserId(userid);
	}

	public List<RoleManagement> getValiableRoles(String leasecompanyid) {
		return mapper.getValiableRoles(leasecompanyid);
	}

	public void deleteRoleById(String userid) {
		mapper.deleteRoleById(userid);
	}

	public void addRole4User(Map<String, Object> params) {
		mapper.addRole4User(params);
	}
	public String forbidUser(String loginName) {
		return mapper.forbidUser(loginName);
	}
	
	public List<Map<String, String>> getLeUserAccountBySelect(User user) {
		return mapper.getLeUserAccountBySelect(user);
	}
	
	public List<Map<String, String>> getLeUserNicknameBySelect(User user) {
		return mapper.getLeUserNicknameBySelect(user);
	}
	
	public List<Map<String, String>> getLeRolename(RoleManagement object) {
		return mapper.getLeRolename(object);
	}

	public void addUserLoginLog(Map<String, Object> params) {
		mapper.addUserLoginLog(params);
	}
	
    public String getTocstateByLeasescompanyid(String leasescompanyid) {
    	return mapper.getTocstateByLeasescompanyid(leasescompanyid);
    }
	
	public int getTocOrderCountByLeasescompanyid(String leasescompanyid) {
		return mapper.getTocOrderCountByLeasescompanyid(leasescompanyid);
	}
}
