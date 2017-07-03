package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.MenuBar;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.op.param.UserQueryParam;
import com.szyciov.operate.mapper.UserMapper;

@Repository("UserDao")
public class UserDao {
	
	private UserMapper mapper;

	@Resource
	public void setMapper(UserMapper mapper) {
		this.mapper = mapper;
	}

	public List<OpUser> getUserListByQuery(UserQueryParam userqueryParam) {
		return mapper.getUserListByQuery(userqueryParam);
	}

	public int getUserListCountByQuery(UserQueryParam userqueryParam) {
		return mapper.getUserListCountByQuery(userqueryParam);
	}

	public void createUser(OpUser OpUser) {
		mapper.createUser(OpUser);
	}

	public void updateUser(OpUser OpUser) {
		mapper.updateUser(OpUser);
	}

	public void deleteUser(String id) {
		mapper.deleteUser(id);
	}

	public String getPasswordByName(String loginname) {
		return mapper.getPasswordByName(loginname);
	}

	public OpUser getByLoginName(String loginname) {
		return mapper.getByLoginName(loginname);
	}

	public List<MenuBar> getUserMenuList(OpUser OpUser) {
		return mapper.getUserMenuList(OpUser);
	}

	public OpUser getById(String id) {
		return mapper.getById(id);
	}

	public List<RoleManagement> getRoleManagementByUserId(String userid) {
		return mapper.getRoleManagementByUserId(userid);
	}

	public List<RoleManagement> getValiableRoles() {
		return mapper.getValiableRoles();
	}

	public void deleteRoleById(String userid) {
		mapper.deleteRoleById(userid);
	}

	public void addRole4User(Map<String, Object> params) {
		mapper.addRole4User(params);
	}

	public List<Map<String, Object>> getRolesInfo() {
		return mapper.getRolesInfo();
	}
	
	public void updateLogontimes(OpUser OpUser){
		mapper.updateLogontimes(OpUser);
	};
	
	public void resetLogontimes(OpUser OpUser){
		mapper.resetLogontimes(OpUser);
	}

	public void addUserLoginLog(Map<String, Object> params) {
		mapper.addUserLoginLog(params);
	}
}
