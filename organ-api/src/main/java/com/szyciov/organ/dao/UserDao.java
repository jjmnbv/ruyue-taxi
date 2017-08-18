package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.op.entity.PeUser;
import org.springframework.stereotype.Repository;

import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.org.param.UserQueryParam;
import com.szyciov.organ.mapper.UserMapper;

@Repository("UserDao")
public class UserDao {
	
	private UserMapper mapper;

	@Resource
	public void setMapper(UserMapper mapper) {
		this.mapper = mapper;
	}

	public String getPasswordByName(String loginname) {
		return mapper.getPasswordByName(loginname);
	}

	public OrgUser getByLoginName(String loginName) {
		return mapper.getByLoginName(loginName);
	}

	public List<Map<String,Object>> getMenuInfo(OrgUser orgUser) {
		return mapper.getMenuInfo(orgUser);
	}

	public List<Map<String, Object>> getNavInfo(OrgUser orgUser) {
		return mapper.getNavInfo(orgUser);
	}

	public boolean hasFeature(Map<String, Object> param) {
		return mapper.hasFeature(param);
	}

	public List<TreeNode> getDeptUserTreeNodes(Map<String,Object> param) {
		return mapper.getDeptUserTreeNodes(param);
	}

	public Map<String, Object> getUserInfo(String userid) {
		return mapper.getUserInfo(userid);
	}

	public boolean hasChildDept(String deptid) {
		return mapper.hasChildDept(deptid);
	}

	public int getUserListCountByQuery(UserQueryParam userparam) {
		return mapper.getUserListCountByQuery(userparam);
	}

	public List<Map<String,Object>> getUserListByQuery(UserQueryParam userparam) {
		return mapper.getUserListByQuery(userparam);
	}

	public int getDeptListCountByQuery(UserQueryParam userparam) {
		return mapper.getDeptListCountByQuery(userparam);
	}

	public List<Map<String, Object>> getDeptListByQuery(UserQueryParam userparam) {
		return mapper.getDeptListByQuery(userparam);
	}

	public boolean hasResId(String resid) {
		return mapper.hasResId(resid);
	}

	public String getParentResid(Map<String, Object> deptinfo) {
		return mapper.getParentResid(deptinfo);
	}

	public boolean existDept(Map<String, Object> deptinfo) {
		return mapper.existDept(deptinfo);
	}

	public void addDept(Map<String, Object> deptinfo) {
		mapper.addDept(deptinfo);
	}

	public List<Map<String, Object>> getRoles() {
		return mapper.getRoles();
	}

	public List<Map<String, Object>> getValiableCompanyInfo(OrgUser user) {
		return mapper.getValiableCompanyInfo(user);
	}

	public List<Map<String, Object>> getSpecialstateDrivers(Map<String,Object> param) {
		return mapper.getSpecialstateDrivers(param);
	}

	public List<Map<String, Object>> getUseCarRules(String organid) {
		return mapper.getUseCarRules(organid);
	}

	public boolean existUser(String account) {
		return mapper.existUser(account);
	}
	
	public boolean existUserExceptSelf(Map<String,Object> params) {
		return mapper.existUserExceptSelf(params);
	}

	public void addUser(Map<String, Object> userinfo) {
		mapper.addUser(userinfo);
	}

	public void addRole(Map<String, Object> userinfo) {
		mapper.addRole(userinfo);
	}

	public void addCarRules(List<Map<String,Object>> userruleobjs) {
		mapper.addCarRules(userruleobjs);
	}

	public void addDrivers(List<Map<String, Object>> userdriverrefinfos) {
		mapper.addDrivers(userdriverrefinfos);
	}

	public void updateUser(Map<String, Object> userinfo) {
		mapper.updateUser(userinfo);
	}

	public void updateRole(Map<String, Object> userinfo) {
		mapper.updateRole(userinfo);
	}

	public void deleteCarRules(String userid) {
		mapper.deleteCarRules(userid);
	}

	public void deleteDrivers(Map<String, Object> deletepa) {
		mapper.deleteDrivers(deletepa);
	}

	public boolean hasUser(Map<String, Object> param) {
		return mapper.hasUser(param);
	}

	public void deleteDept(Map<String, Object> param) {
		mapper.deleteDept(param);
	}

	public void deleteUser(Map<String, Object> param) {
		mapper.deleteUser(param);
	}

	public Map<String, Object> getDept(Map<String, Object> deptinfo) {
		return mapper.getDept(deptinfo);
	}

	public void updateDept(Map<String, Object> deptinfo) {
		mapper.updateDept(deptinfo);
	}

	public List<Map<String, Object>> getDeptChildren(Map<String, Object> deptinfo) {
		return mapper.getDeptChildren(deptinfo);
	}

	public String getValiableDeptResid(String account) {
		return mapper.getValiableDeptResid(account);
	}

	public List<String> getValiableDepts(String resid) {
		return mapper.getValiableDepts(resid);
	}

	public void deleteUserRole(Map<String, Object> userinfo) {
		mapper.deleteUserRole(userinfo);
	}

	public void addUserRole(Map<String, Object> userinfo) {
		mapper.addUserRole(userinfo);
	}

	public List<Map<String, Object>> getUserDrivers(Map<String, Object> param) {
		return mapper.getUserDrivers(param);
	}

	public Map<String, Object> getOrgOrgan(String organid) {
		return mapper.getOrgOrgan(organid);
	}

	public void resetPwd(Map<String, Object> param) {
		mapper.resetPwd(param);
	}

	public void changeUserDept(Map<String, Object> params) {
		mapper.changeUserDept(params);
	}

	public boolean isUnbindAccount(String userName) {
		return mapper.isUnbindAccount(userName);
	}

	public boolean hasMainAccount(String userName) {
		return mapper.hasMainAccount(userName);
	}

	public void setAsMainAccount(String userName) {
		mapper.setAsMainAccount(userName);
	}

	public void updateFirstLogin(OrgUser user) {
		mapper.updateFirstLogin(user);
	}

	public String getRoleIdByRoleName(String rolecaption) {
		return mapper.getRoleIdByRoleName(rolecaption);
	}

	public void addUserLoginLog(Map<String, Object> params) {
		mapper.addUserLoginLog(params);
	}

	public Map<String, Object> getLastLoginLog(Map<String, Object> params) {
		return mapper.getLastLoginLog(params);
	}

	public OrgUser getOldUser(String account) {
		return mapper.getOldUser(account);
	}

	public void updateNewBalance(Map<String, Object> updatebalancep) {
		 mapper.updateNewBalance(updatebalancep);
	}

	public void updateBalanceExpenses(Map<String, Object> updatebalancep) {
		 mapper.updateBalanceExpenses(updatebalancep);
	}

	public boolean hasUsertoken(Map<String, Object> params) {
		return mapper.hasUsertoken(params);
	}

	public void updateUsertoken(Map<String, Object> params) {
		mapper.updateUsertoken(params);
	}

	public void addUsertoken(Map<String, Object> params) {
		mapper.addUsertoken(params);
	}

	public String getUserToken(String account) {
		return mapper.getUserToken(account);
	}

    public PeUser getPeUserByAccount(String account) {
		return mapper.getPeUserByAccount(account);
    }

	public void saveUserInfo2PeUser(Map<String, Object> userinfo) {
		mapper.saveUserInfo2PeUser(userinfo);
	}

	public void resetPwd4PeUser(Map<String, Object> params) {
		mapper.resetPwd4PeUser(params);
	}
	public void resetPwd4OrgUser(Map<String, Object> params) {
		mapper.resetPwd4OrgUser(params);
	}
	public void updateUserInfo2PeUserPassword(Map<String, Object> userinfo) {
		mapper.updateUserInfo2PeUserPassword(userinfo);
	}
	public void updateUserInfo2OrgUserPassword(Map<String, Object> userinfo) {
		mapper.updateUserInfo2OrgUserPassword(userinfo);
	}

	public List<Map<String, Object>> querySignCompanyByOrganid(String organid) {
		return mapper.querySignCompanyByOrganid(organid);
	}

	public String queryOperateid() {
		return mapper.queryOperateid();
	}

	public void updateExpireInviteInfos() {
       mapper.updateExpireInviteInfos();		
	}

	public Map<String, Object> getInviteInfoByInvitee(String account) {
		return mapper.getInviteInfoByInvitee(account);
	}

	public void updateInviteState(Map<String, Object> inviteparam) {
		 mapper.updateInviteState(inviteparam);
	}

	public PeUser getUser4Op(String inviterphone) {
		return mapper.getUser4Op(inviterphone);
	}
}
