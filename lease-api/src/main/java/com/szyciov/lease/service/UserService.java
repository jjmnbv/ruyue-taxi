package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.entity.MenuBar;
import com.szyciov.lease.dao.UserDao;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.UserQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;

@Service("userService")
public class UserService {
	private UserDao dao;

	@Resource(name = "UserDao")
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	public PageBean getUserByQuery(UserQueryParam userqueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(userqueryParam.getsEcho());
		List<User> list = getUserListByQuery(userqueryParam);
		int iTotalRecords = getUserListCountByQuery(userqueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<User> getUserListByQuery(UserQueryParam userqueryParam){
		return dao.getUserListByQuery(userqueryParam);
	}

	public int getUserListCountByQuery(UserQueryParam userqueryParam){
		return dao.getUserListCountByQuery(userqueryParam);
	}
	
	public Map<String, String> createUser(User user) {
		Map<String,String> res = new HashMap<String,String>();
		if(user.getLeasescompanyid()==null||"".equalsIgnoreCase(user.getLeasescompanyid())){
			res.put("status", "fail");
			res.put("message", "创建账户必须有所属公司");
			return res;
		}
		if(getByLoginName(user.getAccount())!=null){
			res.put("status", "fail");
			res.put("message", "账号已存在");
			return res;
		}
		user.setId(GUIDGenerator.newGUID());
		encodePwd(user);
		dao.createUser(user);
		res.put("status", "success");
		res.put("message", "添加成功");
		return res;
	}

	public Map<String, String> changePassword(Map<String, String> param) {
		Map<String,String> res = new HashMap<String,String>();
		String password = param.get("password");
		String leasescompanyid = param.get("leasescompanyid");
		String oldpassword = param.get("oldpassword");
		String oldpassworden = PasswordEncoder.encode(oldpassword);
		String loginname = param.get("loginname");
		String userid = param.get("userid");
		String email = param.get("email");
		User user = getById(userid);
		if(user==null||StringUtils.isBlank(user.getUserpassword())||!StringUtils.equals(user.getUserpassword(), oldpassworden)){
			res.put("status", "fail");
			res.put("message", "账号密码错误");
			return res;
		}
		String encodedPassword = PasswordEncoder.encode(password);
		User userupdate = new User(); 
		// 更新用户密码
		userupdate.setAccount(loginname);
		userupdate.setLeasescompanyid(leasescompanyid);
		userupdate.setUserpassword(encodedPassword);
		userupdate.setEmail(email);
		userupdate.setId(userid);
		dao.updateUser(userupdate);
		res.put("status", "success");
		res.put("message", "更新成功");
		return res;
	}

	public Map<String, String> deleteUser(String id) {
		Map<String,String> res = new HashMap<String,String>();
		dao.deleteUser(id);
		res.put("status", "success");
		res.put("message", "删除成功!");
		return res;
	}
	
	private void encodePwd(User user){
		if(user==null){
			return ;
		}
		String pwd = user.getUserpassword();
		if(pwd==null||"".equalsIgnoreCase(pwd)){
			return ;
		}
		user.setUserpassword(PasswordEncoder.encode(pwd));
	}

	public Map<String, String> updateUser(User user) {
		Map<String,String> res = new HashMap<String,String>();
		String userid = user.getId();
		User dbuser = getById(userid);
		if(!dbuser.getUserpassword().equals(user.getUserpassword())){
			//加密密码
			encodePwd(user);
		}
		dao.updateUser(user);
		res.put("status", "success");
		res.put("message", "更新成功");
		return res;
	}

	public User getById(String id) {
		return dao.getById(id);
	}

	public User getByLoginName(String loginname) {
		return dao.getByLoginName(loginname);
	}

	public Map<String, Object> getUserMenu(Map<String, String> param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		User user = getByLoginName(param.get("loginname"));
		if(user==null){
			return ret;
		}
		List<MenuBar> menuBars = dao.getUserMenuList(user);
		
		for (MenuBar menu : menuBars) {
			// toC订单
			if ("06".equals(menu.getId())) {
				boolean remove = false;

				String tocState = dao.getTocstateByLeasescompanyid(user.getLeasescompanyid());
				int tocOrderCount = dao.getTocOrderCountByLeasescompanyid(user.getLeasescompanyid());
				if (StringUtils.isNotBlank(tocState) && "2".equals(tocState) || tocOrderCount > 0) {
					remove = false;
				} else {
					remove = true;
				}
				
				if (remove) {
					menuBars.remove(menu);
				}
				break;
			}
		}

		List<MenuBar> treeNodes = new ArrayList<MenuBar>();
		if (menuBars == null || menuBars.size() < 1) {
			ret.put("menulist", treeNodes);
			return ret;
		}
		boolean firstflag = true;
		String home_href = "";
		for (MenuBar node : menuBars) {
			boolean canAdd = true;
			if(firstflag&&StringUtils.isNotBlank(node.getUrl())){
				home_href = node.getApplicationdomain()+node.getUrl();
				firstflag = false;
			}
			if (node.getParentid() != null) {
				String parentId = node.getParentid();
				for (MenuBar node2 : menuBars) {
					if (parentId.equalsIgnoreCase(node2.getId())) {
						if (node2.getChildren() == null) {
							node2.setChildren(new ArrayList<MenuBar>());
						}
						node2.getChildren().add(node);
						canAdd = false;
					}
				}
			}
			
			if (canAdd) {
				treeNodes.add(node);
			}
		}
		ret.put("menulist", treeNodes);
		ret.put("home_href", home_href);
		return ret;
	}

	public String getPasswordByName(String loginname) {
		return dao.getPasswordByName(loginname);
	}

	public Map<String, Object> getRolesInfo(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		String userid = (String) param.get("userid");
		List<RoleManagement> rolelist = dao.getRoleManagementByUserId(userid);
		if(rolelist!=null&&rolelist.size()>0){
			//目前只有一个角色
			RoleManagement roleobj = rolelist.get(0);
			res.put("rolename", roleobj.getRolename());
			res.put("roleid", roleobj.getId());
		}
		String leasecompanyid = (String) param.get("leasecompanyid");
		List<RoleManagement> valiableroles = dao.getValiableRoles(leasecompanyid);
		res.put("rolelist", valiableroles);
		return res;
	}

	@Transactional
	public Map<String, String> assignRole(Map<String, Object> params) {
		Map<String, String> res = new HashMap<String, String>();
		try{
			//删除原有角色
			String userid = (String) params.get("userid");
			dao.deleteRoleById(userid);
			//添加现在权限
			params.put("id", GUIDGenerator.newGUID());
			dao.addRole4User(params);
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "分配角色失败");
			return res;
		}
		res.put("status", "success");
		res.put("message", "分配角色成功");
		return res;
	}
	public String forbidUser(String loginName) {
		return dao.forbidUser(loginName);
	}
	
	public List<Map<String, String>> getLeUserAccountBySelect(User user) {
		return dao.getLeUserAccountBySelect(user);
	}
	
	public List<Map<String, String>> getLeUserNicknameBySelect(User user) {
		return dao.getLeUserNicknameBySelect(user);
	}
	
	public List<Map<String, String>> getLeRolename(RoleManagement object) {
		return dao.getLeRolename(object);
	}

	public Map<String, Object> addUserLoginLog(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		dao.addUserLoginLog(params);
		res.put("status", "success");
		return res;
	}
}
