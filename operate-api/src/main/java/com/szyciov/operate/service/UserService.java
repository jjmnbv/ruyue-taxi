package com.szyciov.operate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.entity.MenuBar;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.RoleManagement;
import com.szyciov.op.param.UserQueryParam;
import com.szyciov.operate.dao.UserDao;
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
		List<OpUser> list = getUserListByQuery(userqueryParam);
		int iTotalRecords = getUserListCountByQuery(userqueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<OpUser> getUserListByQuery(UserQueryParam userqueryParam){
		return dao.getUserListByQuery(userqueryParam);
	}

	public int getUserListCountByQuery(UserQueryParam userqueryParam){
		return dao.getUserListCountByQuery(userqueryParam);
	}
	
	public Map<String, String> createUser(OpUser OpUser) {
		Map<String,String> res = new HashMap<String,String>();
		String reg = "^(?![0-9]+$)(?![a-z]+$)(?![!@#%&$()*+]+$)[a-z0-9!@#%&$()*+]{8,16}$";  
		if(getByLoginName(OpUser.getAccount())!=null){
			res.put("status", "fail");
			res.put("message", "账号已存在");
			return res;
		}
		if(!Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(OpUser.getUserpassword()).matches()){
			res.put("status", "fail");
			res.put("message", "密码必须由8到16位字母、数字和符号(!@#%&$()*+)组成");
			return res;
		}
		
		OpUser.setId(GUIDGenerator.newGUID());
		encodePwd(OpUser);
		dao.createUser(OpUser);
		res.put("status", "success");
		res.put("message", "添加成功");
		return res;
	}

	public Map<String, Object> changePassword(Map<String, String> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		String password = param.get("password");
		String oldpassword = param.get("oldpassword");
		String password2=param.get("password2");
		String oldpassworden = PasswordEncoder.encode(oldpassword);
		String loginname = param.get("loginname");
		String userid = param.get("userid");
		String email = param.get("email");
		String reg = "^(?![0-9]+$)(?![a-z]+$)(?![!@#%&$()*+]+$)[a-z0-9!@#%&$()*+]{8,16}$";  
		OpUser user = getById(userid);
		OpUser o = getById(userid);
		
		if(StringUtils.isBlank(password)||StringUtils.isBlank(oldpassword)||StringUtils.isBlank(password2)){
			res.put("status", "error");
			if(StringUtils.isBlank(oldpassword))
			    res.put("message", "原密码不可为空");
			if(StringUtils.isBlank(password))
				res.put("message", "新密码不可为空");
			if(StringUtils.isBlank(password))
				res.put("message", "请重新输入新密码");
			return res;
		}
		
		if(!Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(password).matches()){
			res.put("status", "error");
			res.put("message", "密码必须为8到16位字母、数字和符号(!@#%&$()*+)组成");
			return res;
		}
         if(password.equals(oldpassword)){
        	 res.put("status", "error");
 			 res.put("message", "新密码不可与原密码相同");
 			 return res;
		}
		
		if(!password.equals(password2)){
			res.put("status", "error");
			res.put("message", "两次密码输入不一致");
			return res;
		}
		
		
		if(user==null||StringUtils.isBlank(user.getUserpassword())||!StringUtils.equals(user.getUserpassword(), oldpassworden)){
			
//			int count = 0;
			OpUser ou = new OpUser();
			ou.setId(o.getId());
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//				System.out.println(formatter.parse(formatter.format(getNextDay(o.getUpdateTime()))));
				Date date = new Date();
				if(date.getTime() < (formatter.parse(formatter.format(getNextDay(o.getUpdatetime())))).getTime() && o.getLogontimes()<5){
					ou.setLogontimes(o.getLogontimes()+1);
					updateLogontimes(ou);
				}
				if(date.getTime() > (formatter.parse(formatter.format(getNextDay(o.getUpdatetime())))).getTime()){
					ou.setLogontimes(0);
					resetLogontimes(ou);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			OpUser oo = getById(userid);
//			if(o.getLogontimes() == 5){
//				count = 0;
//			}
//			res.put("count", count);
			res.put("logontimes",5-oo.getLogontimes());
			
//			res.put("status", "fail");
//			res.put("message", "账号密码错误");
			return res;
		}
		if(o.getLogontimes()==5){
			res.put("logontimes",0);
			return res;
		}else{
			String encodedPassword = PasswordEncoder.encode(password);
			OpUser userupdate = new OpUser(); 
			// 更新用户密码
			userupdate.setAccount(loginname);
			userupdate.setUserpassword(encodedPassword);
			userupdate.setId(userid);
			userupdate.setEmail(email);
			dao.updateUser(userupdate);
			res.put("status", "success");
			res.put("message", "更新成功");
			userupdate.setLogontimes(0);
			resetLogontimes(userupdate);
			return res;
		}
	}
	
	//在一个时间加一天
	public Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
	
	public Map<String, String> deleteUser(String id) {
		Map<String,String> res = new HashMap<String,String>();
		dao.deleteUser(id);
		res.put("status", "success");
		res.put("message", "删除成功");
		return res;
	}
	
	private void encodePwd(OpUser OpUser){
		if(OpUser==null){
			return ;
		}
		String pwd = OpUser.getUserpassword();
		if(pwd==null||"".equalsIgnoreCase(pwd)){
			return ;
		}
		OpUser.setUserpassword(PasswordEncoder.encode(pwd));
	}

	public Map<String, String> updateUser(OpUser OpUser) {
		Map<String,String> res = new HashMap<String,String>();
		String reg = "^(?![0-9]+$)(?![a-z]+$)(?![!@#%&$()*+]+$)[a-z0-9!@#%&$()*+]{8,16}$";  
		
		if(!Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(OpUser.getUserpassword()).matches()){
			res.put("status", "fail");
			res.put("message", "密码必须为8到16位字母、数字和符号(!@#%&$()*+)组成");
			return res;
		}
		
		String userid = OpUser.getId();
		OpUser dbuser = getById(userid);
		if(!dbuser.getUserpassword().equals(OpUser.getUserpassword())){
			//加密密码
			encodePwd(OpUser);
		}
		dao.updateUser(OpUser);
		res.put("status", "success");
		res.put("message", "更新成功");
		return res;
	}

	public OpUser getById(String id) {
		return dao.getById(id);
	}

	public OpUser getByLoginName(String loginname) {
		return dao.getByLoginName(loginname);
	}

	public Map<String, Object> getUserMenu(Map<String, String> param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		OpUser OpUser = getByLoginName(param.get("loginname"));
		if(OpUser==null){
			return ret;
		}
		List<MenuBar> menuBars = dao.getUserMenuList(OpUser);
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
		List<RoleManagement> valiableroles = dao.getValiableRoles();
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

	public List<Map<String,Object>> getRolesInfo() {
		return dao.getRolesInfo();
	}
	
	public void updateLogontimes(OpUser OpUser){
		dao.updateLogontimes(OpUser);
	};
	
	public void resetLogontimes(OpUser OpUser){
		dao.resetLogontimes(OpUser);
	}

	public Map<String, Object> addUserLoginLog(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		//dao.addUserLoginLog(params);
		res.put("status", "success");
		return res;
	}
}
