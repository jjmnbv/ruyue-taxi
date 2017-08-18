package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.org.param.UserQueryParam;

public interface UserMapper {

	String getPasswordByName(String loginname);

	OrgUser getByLoginName(String loginName);

	List<Map<String, Object>> getMenuInfo(OrgUser orgUser);

	List<Map<String, Object>> getNavInfo(OrgUser orgUser);

	boolean hasFeature(Map<String, Object> param);

	List<TreeNode> getDeptUserTreeNodes(Map<String,Object> param);

	Map<String, Object> getUserInfo(String userid);

	boolean hasChildDept(String deptid);

	int getUserListCountByQuery(UserQueryParam userparam);

	List<Map<String,Object>> getUserListByQuery(UserQueryParam userparam);

	int getDeptListCountByQuery(UserQueryParam userparam);

	List<Map<String, Object>> getDeptListByQuery(UserQueryParam userparam);

	boolean existDept(Map<String, Object> deptinfo);

	boolean hasResId(String resid);

	String getParentResid(Map<String, Object> deptinfo);

	void addDept(Map<String, Object> deptinfo);

	List<Map<String, Object>> getRoles();

	List<Map<String, Object>> getValiableCompanyInfo(OrgUser user);

	List<Map<String, Object>> getSpecialstateDrivers(Map<String,Object> param);

	List<Map<String, Object>> getUseCarRules(String organid);

	boolean existUser(String account);

	void addUser(Map<String, Object> userinfo);

	void addRole(Map<String, Object> userinfo);

	void addCarRules(List<Map<String,Object>> userruleobjs);

	void updateUser(Map<String, Object> userinfo);

	void deleteCarRules(String userid);

	void updateRole(Map<String, Object> userinfo);

	boolean existUserExceptSelf(Map<String, Object> params);

	boolean hasUser(Map<String, Object> param);

	void deleteDept(Map<String, Object> param);

	void deleteUser(Map<String, Object> param);

	Map<String, Object> getDept(Map<String, Object> deptinfo);

	void updateDept(Map<String, Object> deptinfo);

	List<Map<String, Object>> getDeptChildren(Map<String, Object> deptinfo);

	String getValiableDeptResid(String account);

	List<String> getValiableDepts(String resid);

	void deleteUserRole(Map<String, Object> userinfo);

	void addUserRole(Map<String, Object> userinfo);

	void addDrivers(List<Map<String, Object>> userdriverrefinfos);

	void deleteDrivers(Map<String, Object> deletepa);

	List<Map<String, Object>> getUserDrivers(Map<String, Object> param);

	Map<String, Object> getOrgOrgan(String organid);

	void resetPwd(Map<String, Object> param);

	void changeUserDept(Map<String, Object> params);

	boolean isUnbindAccount(String userName);

	boolean hasMainAccount(String userName);

	void setAsMainAccount(String userName);

	void updateFirstLogin(OrgUser user);

	String getRoleIdByRoleName(String rolecaption);

	void addUserLoginLog(Map<String, Object> params);

	Map<String, Object> getLastLoginLog(Map<String, Object> params);

	OrgUser getOldUser(String account);

	void updateNewBalance(Map<String, Object> updatebalancep);

	void updateBalanceExpenses(Map<String, Object> updatebalancep);

	boolean hasUsertoken(Map<String, Object> params);

	void updateUsertoken(Map<String, Object> params);

	void addUsertoken(Map<String, Object> params);

	String getUserToken(String account);

    PeUser getPeUserByAccount(String account);

	void saveUserInfo2PeUser(Map<String, Object> userinfo);

	void resetPwd4PeUser(Map<String, Object> params);
	void resetPwd4OrgUser(Map<String, Object> params);
	
	void updateUserInfo2PeUserPassword(Map<String, Object> userinfo);
	
	void updateUserInfo2OrgUserPassword(Map<String, Object> userinfo);

	List<Map<String, Object>> querySignCompanyByOrganid(@Param("id") String organid);

	String queryOperateid();

	void updateExpireInviteInfos();

	Map<String, Object> getInviteInfoByInvitee(String inviteephone);

	void updateInviteState(Map<String, Object> inviteparam);

	PeUser getUser4Op(String account);
}
