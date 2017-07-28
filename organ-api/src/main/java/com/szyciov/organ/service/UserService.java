package com.szyciov.organ.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.op.entity.PeUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.szyciov.message.UserMessage;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.org.param.UserQueryParam;
import com.szyciov.organ.dao.UserDao;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PYTools;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UNID;

@Service("userService")
public class UserService {
	private UserDao dao;
	
	//资源id的分割符
	private static final String SEPARATOR = "$";

	@Resource(name = "UserDao")
	public void setDao(UserDao dao) {
		this.dao = dao;
	}

	public String getPasswordByName(String loginname) {
		return dao.getPasswordByName(loginname);
	}

	public OrgUser getByLoginName(String loginName) {
		return dao.getByLoginName(loginName);
	}

	public List<Map<String,Object>> getMenuInfo(OrgUser orgUser) {
		return dao.getMenuInfo(orgUser);
	}

	public List<Map<String, Object>> getNavInfo(OrgUser orgUser) {
		return dao.getNavInfo(orgUser);
	}

	public boolean hasFeature(Map<String, Object> param) {
		return dao.hasFeature(param);
	}

	public List<TreeNode> getDeptUserTreeNodes(Map<String,Object> param) {
		List<TreeNode> res = dao.getDeptUserTreeNodes(param);
		boolean issuper = (boolean) param.get("issuper");
		if(!issuper){
			String account = (String) param.get("account");
			String resid = dao.getValiableDeptResid(account);
			if(StringUtils.isBlank(resid)){
				//没有权限查看res
				res = new ArrayList<TreeNode>();
				return res;
			}
			List<String> validdeptids = dao.getValiableDepts(resid);
			List<TreeNode> ress = new ArrayList<TreeNode>();
			for(int i=0;i<res.size();i++){
				TreeNode node = res.get(i);
				String type = node.getType();
				if("1".equalsIgnoreCase(type)){
					//公司
					ress.add(node);
				}else if("2".equalsIgnoreCase(type)){
					//部门
					if(!validdeptids.contains(node.getId())){
						continue;
					}
					if(!validdeptids.contains(node.getpId())){
						//没有父级查看权限。只有本级。所以本级就是最高级
						node.setpId("-1");
					}
					ress.add(node);
				}else{
					//人员
					if(validdeptids.contains(node.getpId())){
						ress.add(node);
					}
				}
			}
			return ress;
		}
		return res;
	}

	public Map<String,Object> getUserInfo(String userid) {
		Map<String,Object> res = new HashMap<String,Object>();
		try{
			Map<String,Object> userinfo = dao.getUserInfo(userid);
			res.put("status", "success");
			if(userinfo!=null&&userinfo.get("img")!=null&&StringUtils.isNotBlank((String)userinfo.get("img"))){
				userinfo.put("imgpath",userinfo.get("img"));
				userinfo.put("img", SystemConfig.getSystemProperty("fileserver")+File.separator+userinfo.get("img"));
			}
			res.put("userinfo", userinfo);
		}catch(Exception e){
			res.put("status", "fail");
		}
		return res;
	}

	public boolean hasChildDept(String deptid) {
		return dao.hasChildDept(deptid);
	}

	public PageBean getUserByQuery(UserQueryParam userparam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(userparam.getsEcho());
		List<Map<String,Object>> list = getUserListByQuery(userparam);
		int iTotalRecords = getUserListCountByQuery(userparam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getUserListCountByQuery(UserQueryParam userparam) {
		return dao.getUserListCountByQuery(userparam);
	}

	private List<Map<String,Object>> getUserListByQuery(UserQueryParam userparam) {
		return dao.getUserListByQuery(userparam);
	}

	public PageBean getDeptByQuery(UserQueryParam userparam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(userparam.getsEcho());
		List<Map<String,Object>> list = getDeptListByQuery(userparam);
		int iTotalRecords = getDeptListCountByQuery(userparam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getDeptListCountByQuery(UserQueryParam userparam) {
		return dao.getDeptListCountByQuery(userparam);
	}

	private List<Map<String, Object>> getDeptListByQuery(UserQueryParam userparam) {
		return dao.getDeptListByQuery(userparam);
	}

	@Transactional
	public Map<String, Object> addDept(Map<String, Object> deptinfo) {
		String deptname = (String) deptinfo.get("deptname");
		String type = (String) deptinfo.get("type");
		Map<String,Object> res = new HashMap<String,Object>();
		if(StringUtils.isBlank(deptname)||StringUtils.isBlank(type)){
			//参数不完整
			res.put("status", "fail");
			res.put("message", "参数不完整");
			return res;
		}
		//判断部门存不存在
		if(existDept(deptinfo)){
			res.put("status", "fail");
			res.put("message", "部门已存在");
			return res;
		}
		
		String parentresid = null;
		try{
			if("1".equalsIgnoreCase(type)){
				String companyname = (String) deptinfo.get("companyname");
				parentresid = PYTools.getFirstPinYin(companyname);
			}else{
				parentresid = getParentResid(deptinfo);
			}
		}catch(Exception e){}
		
		if(StringUtils.isBlank(parentresid)){
			res.put("status", "fail");
			res.put("message", "获取资源出错");
			return res;
		}
		String resid = getUniqueResId(parentresid);
		deptinfo.put("resid", resid);
		deptinfo.put("id", GUIDGenerator.newGUID());
		dao.addDept(deptinfo);
		res.put("status", "success");
		res.put("message", "创建部门成功");
		return res;
	}
	
	/**
	 * 获取部门唯一编码
	 * @param parentresid
	 * @return
	 */
	private String getUniqueResId(String parentresid){
		if(parentresid==null||"".equalsIgnoreCase(parentresid.trim())){
			throw new RuntimeException("上级资源id为空,请先为上级添加一个唯一资源Id!");
		}
		String resid = parentresid+SEPARATOR+UNID.getUNID();
		int count = 0;
		while(hasResId(resid)){
			if(count>10){
				//10次还未创建成功就报错
				throw new RuntimeException("创建更新部门出错,部门资源id创建出问题!");
			}
			resid = parentresid+SEPARATOR+UNID.getUNID();
			count++;
		}
		return resid;
	}
	
	/**
	 * 判断公司的resid在数据库中是否已经存在
	 * @param resid
	 * @return
	 */
	private boolean hasResId(String resid){
		return dao.hasResId(resid);
	}

	private String getParentResid(Map<String, Object> deptinfo) {
		return dao.getParentResid(deptinfo);
	}

	/**
	 * 判断父部门下是否已经存在此部门
	 * @param deptinfo
	 * @return
	 */
	private boolean existDept(Map<String, Object> deptinfo) {
		return dao.existDept(deptinfo);
	}

	public List<Map<String, Object>> getRoles() {
		return dao.getRoles();
	}

	public List<Map<String, Object>> getValiableCompanyInfo(OrgUser user) {
		return dao.getValiableCompanyInfo(user);
	}

	public List<Map<String, Object>> getSpecialstateDrivers(Map<String,Object> param) {
		return dao.getSpecialstateDrivers(param);
	}

	public List<Map<String, Object>> getUseCarRules(String organid) {
		return dao.getUseCarRules(organid);
	}

	@Transactional
	public synchronized Map<String, Object> addUser(Map<String, Object> userinfo) {
		Map<String, Object> res = new HashMap<String,Object>();
		String account = (String) userinfo.get("phone");
		if(StringUtils.isBlank(account)){
			res.put("status", "fail");
			res.put("message", "添加员工的手机号不存在");
			return res;
		}
		//判断添加的员工在机构表中是否存在，离职的认为不存在
		boolean flag = dao.existUser(account);
		if(flag){
			res.put("status", "fail");
			res.put("message", account+"手机号码已经存在");
			return res;
		}
		String userid = GUIDGenerator.newGUID();
		userinfo.put("userid", userid);
//		String password = UNID.getUNID(null, 6);
		String password = UNID.getPwdDefStr();
		userinfo.put("srcpassword", password);
		userinfo.put("password", PasswordEncoder.encode(password));
		//添加员工
		dao.addUser(userinfo);
		//添加员工角色信息
		String userroleid = GUIDGenerator.newGUID();
		userinfo.put("userroleid", userroleid);
		dao.addRole(userinfo);
		//添加员工用车信息
		Object ruleobj = userinfo.get("rulesid");
		if(ruleobj!=null){
			List<String> rulesid = (List<String>) ruleobj;
			if(rulesid.size()>0){
				List<Map<String,Object>> userruleobjs = new ArrayList<Map<String,Object>>();
				for(int i=0;i<rulesid.size();i++){
					Map<String,Object> userruleobj = new HashMap<String,Object>();
					userruleobj.put("id", GUIDGenerator.newGUID());
					userruleobj.put("userid", userid);
					userruleobj.put("userulesid", rulesid.get(i));
					userruleobjs.add(userruleobj);
				}
				dao.addCarRules(userruleobjs);
			}
		}
		//添加员工特殊司机信息
		String specialstate = (String) userinfo.get("specialstate");
		Object driverobj = userinfo.get("drivers");
		if("1".equals(specialstate)&&driverobj!=null){
			List<String> driverids = (List<String>)driverobj;
			if(driverids.size()>0){
				List<Map<String,Object>> userdriverrefinfos = new ArrayList<Map<String,Object>>();
				for(int i=0;i<driverids.size();i++){
					Map<String,Object> userdriverrefinfo = new HashMap<String,Object>();
					userdriverrefinfo.put("id", GUIDGenerator.newGUID());
					userdriverrefinfo.put("userid", userid);
					userdriverrefinfo.put("driverid", driverids.get(i));
					userdriverrefinfo.put("updater", userinfo.get("updater"));
					userdriverrefinfos.add(userdriverrefinfo);
				}
				dao.addDrivers(userdriverrefinfos);
			}
		}
		//处理如果员工之前离职需要同步账户余额信息
		//todo
		OrgUser olduser = dao.getOldUser(account);
		if(olduser!=null){
			try{
				String olduserid = olduser.getId();
				Map<String,Object> updatebalancep = new HashMap<String,Object>();
				updatebalancep.put("olduserid", olduserid);
				updatebalancep.put("userid", userid);
				dao.updateNewBalance(updatebalancep);
				dao.updateBalanceExpenses(updatebalancep);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		res.put("status", "success");
		res.put("message", "员工添加成功");
		sendPwd2User(userinfo);
		return res;
	}

	private void sendPwd2User(Map<String, Object> userinfo) {
		String account = (String) userinfo.get("phone");
		String organid = (String) userinfo.get("organid");
		Map<String,Object> orgorganinfo = dao.getOrgOrgan(organid);
		String orgcompany = "";
		if(orgorganinfo!=null){
			orgcompany = "("+orgorganinfo.get("fullname")+")";
		}
		PeUser peuser = dao.getPeUserByAccount(account);
		if(peuser==null){
			//同步机构用户到个人用户
			userinfo.put("peuserid",GUIDGenerator.newGUID());
			dao.saveUserInfo2PeUser(userinfo);
			//String content = "您的机构端、乘客端账号："+account+"；随机密码："+userinfo.get("srcpassword")+"，请使用账号密码登录"+orgcompany;
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.organ.service.userservice.sendpwd2user", account,(String)userinfo.get("srcpassword"));
			List<String> users = new ArrayList<String>();
			users.add(account);
			UserMessage um = new UserMessage(users,content,UserMessage.ADDUSER);
			MessageUtil.sendMessage(um);
		}else{
			//当存在个人用户且密码为空的情况下同步随机密码，并发送短信
			if(peuser.getUserpassword() == null){
				peuser.setUserpassword("");
			}
			if(peuser.getUserpassword().equals("")){
				dao.updateUserInfo2PeUserPassword(userinfo);
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.organ.service.userservice.sendpwd2user", account,(String)userinfo.get("srcpassword"));
				List<String> users = new ArrayList<String>();
				users.add(account);
				UserMessage um = new UserMessage(users,content,UserMessage.ADDUSER);
				MessageUtil.sendMessage(um);
			}else{
				//修改机构用户的密码为已知的个人用户的密码
				userinfo.put("password", peuser.getUserpassword());
				dao.updateUserInfo2OrgUserPassword(userinfo);
			}
		}
	}

	@Transactional
	public Map<String, Object> updateUser(Map<String, Object> userinfo) {
		Map<String, Object> res = new HashMap<String,Object>();
		String account = (String) userinfo.get("phone");
		if(StringUtils.isBlank(account)){
			res.put("status", "fail");
			res.put("message", "更新员工的手机号不存在");
			return res;
		}
		//判断添加的员工在机构表中是否存在，离职的认为不存在
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("userid", userinfo.get("userid"));
		boolean flag = dao.existUserExceptSelf(params);
		if(flag){
			res.put("status", "fail");
			res.put("message", account+"手机号码已经存在");
			return res;
		}
		//更新员工
		dao.updateUser(userinfo);
		//更新员工角色信息
		String userroleid = GUIDGenerator.newGUID();
		userinfo.put("userroleid", userroleid);
		dao.deleteUserRole(userinfo);
		dao.addUserRole(userinfo);
		//更新员工用车信息
		String userid = (String) userinfo.get("userid");
		//先删除用户用车规则
		dao.deleteCarRules(userid);
		Object ruleobj = userinfo.get("rulesid");
		if(ruleobj!=null){
			List<String> rulesid = (List<String>) ruleobj;
			if(rulesid.size()>0){
				List<Map<String,Object>> userruleobjs = new ArrayList<Map<String,Object>>();
				for(int i=0;i<rulesid.size();i++){
					Map<String,Object> userruleobj = new HashMap<String,Object>();
					userruleobj.put("id", GUIDGenerator.newGUID());
					userruleobj.put("userid", userid);
					userruleobj.put("userulesid", rulesid.get(i));
					userruleobjs.add(userruleobj);
				}
				dao.addCarRules(userruleobjs);
			}
		}
		//更新员工特殊司机信息
		//先删除之前的特殊司机
		Map<String,Object> deletepa = new HashMap<String,Object>();
		deletepa.put("userid",userid);
		deletepa.put("updater",userinfo.get("updater"));
		dao.deleteDrivers(deletepa);
		String specialstate = (String) userinfo.get("specialstate");
		Object driverobj = userinfo.get("drivers");
		if("1".equals(specialstate)&&driverobj!=null){
			List<String> driverids = (List<String>)driverobj;
			if(driverids.size()>0){
				List<Map<String,Object>> userdriverrefinfos = new ArrayList<Map<String,Object>>();
				for(int i=0;i<driverids.size();i++){
					Map<String,Object> userdriverrefinfo = new HashMap<String,Object>();
					userdriverrefinfo.put("id", GUIDGenerator.newGUID());
					userdriverrefinfo.put("userid", userid);
					userdriverrefinfo.put("driverid", driverids.get(i));
					userdriverrefinfo.put("updater", userinfo.get("updater"));
					userdriverrefinfos.add(userdriverrefinfo);
				}
				dao.addDrivers(userdriverrefinfos);
			}
		}
		//处理如果员工之前离职需要同步账户余额信息
		//todo
		res.put("status", "success");
		res.put("message", "员工添加成功");
		return res;
	}

	public Map<String, Object> deleteDept(Map<String, Object> param) {
		Map<String, Object> res = new HashMap<String,Object>();
		boolean haschilddept = dao.hasChildDept((String) param.get("deptid"));
		if(haschilddept){
			res.put("status", "fail");
			res.put("message", "该部门下含有子部门无法被删除");
			return res;
		}
		boolean hasuser = dao.hasUser(param);
		if(hasuser){
			res.put("status", "fail");
			res.put("message", "该部门下含有员工无法被删除");
			return res;
		}
		dao.deleteDept(param);
		res.put("status", "success");
		res.put("message", "员工添加成功");
		return res;
	}

	
	public Map<String, Object> deleteUser(Map<String, Object> param) {
		Map<String, Object> res = new HashMap<String,Object>();
		String userid = (String) param.get("userid");
		Map<String, Object> userinfo = dao.getUserInfo(userid);
		dao.deleteUser(param);
		sendDeleteMessage2User(param,userinfo);
		res.put("status", "success");
		res.put("message", "员工添加成功");
		return res;
	}

	private void sendDeleteMessage2User(Map<String, Object> param,Map<String, Object> userinfo) {
		String organid = (String) param.get("organid");
		Map<String,Object> orgorganinfo = dao.getOrgOrgan(organid);
		String orgcompany = "";
		if(orgorganinfo!=null){
			orgcompany = "("+orgorganinfo.get("fullname")+")";
		}
//		String content = "机构管理员已删除您的员工信息，您的因公用车将被限制，如有异议请联系管理员"+orgcompany;
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.organ.service.userservice.senddeletemessage2user");
		List<String> users = new ArrayList<String>();
		users.add((String) userinfo.get("account"));
		UserMessage um = new UserMessage(users,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
	}

	@Transactional
	public Map<String, Object> updateDept(Map<String, Object> deptinfo) {
		//判断部门存不存在
		String deptname = (String) deptinfo.get("deptname");
		String type = (String) deptinfo.get("type");
		Map<String,Object> res1 = new HashMap<String,Object>();
		if(StringUtils.isBlank(deptname)||StringUtils.isBlank(type)){
			//参数不完整
			res1.put("status", "fail");
			res1.put("message", "参数不完整");
			return res1;
		}
		//判断部门存不存在
		if(existDept(deptinfo)){
			res1.put("status", "fail");
			res1.put("message", "部门已存在");
			return res1;
		}
		Map<String, Object> res = new HashMap<String,Object>();
		Map<String,Object> olddept = dao.getDept(deptinfo);
		if(olddept==null){
			res.put("status", "fail");
			res.put("message", "无法获取更新的部门信息");
			return res;
		}
		String parentid = (String) deptinfo.get("parentid");
		
		if(("1".equalsIgnoreCase(type)&&olddept.get("parentid")==null)||(parentid!=null&&olddept.get("parentid")!=null&&parentid.equalsIgnoreCase((String)olddept.get("parentid")))){
			//同一个部门，部门上级没有变化 只更新名称
			dao.updateDept(deptinfo);
		}else{
			try{
				String oldresid = (String) olddept.get("resid");
				//更新了上级
				if("1".equalsIgnoreCase(type)){
					//上级是公司
					String companyname = (String) deptinfo.get("companyname");
					String parentresid = PYTools.getFirstPinYin(companyname);
					String resid = getUniqueResId(parentresid);
					deptinfo.put("parentid", null);
					updateSelfAndChild(deptinfo, oldresid, resid);
				}else{
					//上级是部门
					Map<String,Object> pp = new HashMap<String,Object>();
					pp.put("deptid", parentid);
					Map<String,Object> newparentinfo = dao.getDept(pp);
					String parentresid = (String) newparentinfo.get("resid");
					if(parentresid.startsWith(oldresid)){
						//上级部门换成下级部门
						//更新下级部门
						Map<String,Object> xjbmparen = new HashMap<String,Object>();
						xjbmparen.put("deptid", parentid);
						xjbmparen.put("parentid", olddept.get("parentid"));
						String xjbmresid = (String) newparentinfo.get("resid");
						String newxjbmpresid = oldresid.substring(0, oldresid.lastIndexOf(SEPARATOR));

						String deptidp = (String) olddept.get("parentid");
						if(StringUtils.isNotBlank(deptidp)){
							Map<String,Object> deptidpa = new HashMap<String,Object>();
							deptidpa.put("deptid", deptidp);
							Map<String,Object> newpainfo = dao.getDept(deptidpa);
							newxjbmpresid = (String) newpainfo.get("resid");
						}
						String resid = getUniqueResId(newxjbmpresid);
						updateSelfAndChild(xjbmparen,xjbmresid,resid);
						//更新这次要更新的部门
						String residp = getUniqueResId(resid);
						updateSelfAndChild(deptinfo, oldresid, residp);
					}else{
						//换成其他部门
						String resid = getUniqueResId(parentresid);
						updateSelfAndChild(deptinfo,oldresid,resid);
					}
				}
			}catch(Exception e){
				res.put("status", "fail");
				res.put("message", "更新出错");
				return res;
			}
		}
		res.put("status", "success");
		res.put("message", "部门更新成功");
		return res;
	}

	/**
	 * 更新自己部门的资源id和子节点的资源id
	 * @param deptinfo 要更新的部门的最新信息，有新的节点等等
	 * @param oldresid 旧的部门的资源id
	 * @param resid 更新后部门的资源id
	 */
	private void updateSelfAndChild(Map<String, Object> deptinfo, String oldresid, String resid) {
		deptinfo.put("resid", resid);
		String[] arr = resid.split("\\"+SEPARATOR);//resid.split(SEPARATOR);
		deptinfo.put("level", arr.length-1);
		dao.updateDept(deptinfo);
		//更新子部门的资源id
		String startresid = oldresid + SEPARATOR+"%";
		deptinfo.put("startresid", startresid);
		List<Map<String,Object>> childdepts = dao.getDeptChildren(deptinfo);
		for(int i=0;i<childdepts.size();i++){
			Map<String,Object> childdept = childdepts.get(i);
			String childresid = (String) childdept.get("resid");
			Map<String,Object> pa = new HashMap<String,Object>();
			pa.put("updater", deptinfo.get("updater"));
			String newresid = childresid.replaceAll(oldresid, resid);
			pa.put("resid", newresid);
			String[] arrs = newresid.split("\\"+SEPARATOR);//newresid.split(SEPARATOR);
			pa.put("level", arrs.length-1);
			pa.put("deptid", childdept.get("id"));
			dao.updateDept(pa);
		}
	}

	public Map<String, Object> getUserDrivers(Map<String, Object> param) {
		Map<String,Object> ress = new HashMap<String,Object>();
		List<Map<String,Object>> res = dao.getUserDrivers(param);
		if(res!=null){
			Map<String,Object> tagmap = new HashMap<String,Object>();
			for(int i=0;i<res.size();i++){
				Map<String,Object> obj = res.get(i);
				String companyid = (String) obj.get("companyid");
				if(!tagmap.containsKey(companyid)){
					List<Map<String,Object>> driverinfo = new ArrayList<Map<String,Object>>();
					tagmap.put(companyid, driverinfo);
				}
				List<Map<String,Object>> driverinfo = (List<Map<String, Object>>) tagmap.get(companyid);
				driverinfo.add(obj);
			}
			ress.put("driverinfos", tagmap);
		}
		ress.put("status", "success");
		ress.put("message", "获取成功");
		return ress;
	}

	public Map<String, Object> resetPwd(Map<String, Object> param) {
		Map<String,Object> ress = new HashMap<String,Object>();
		String password = UNID.getUNID(null, 6);
		param.put("srcpassword", password);
		param.put("password", PasswordEncoder.encode(password));
		dao.resetPwd(param);
		Map<String, Object> userinfo = dao.getUserInfo((String) param.get("userid"));
		if(userinfo!=null&&userinfo.size()>0){
			//重置个人用户的密码
			String account = (String) userinfo.get("account");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("password",PasswordEncoder.encode(password));
			params.put("account",account);
			dao.resetPwd4PeUser(params);
			dao.resetPwd4OrgUser(params);
		}
		sendNewPwd2User(param);
		ress.put("status", "success");
		ress.put("message", "重置成功");
		return ress;
	}

	private void sendNewPwd2User(Map<String, Object> param) {
		String userid = (String) param.get("userid");
		String organid = (String) param.get("organid");
		Map<String,Object> orgorganinfo = dao.getOrgOrgan(organid);
		String orgcompany = "";
		String organPhone="";
		if(orgorganinfo!=null){
			orgcompany = "("+orgorganinfo.get("fullname")+")";
			organPhone=orgorganinfo.get("phone").toString();
		}
		Map<String, Object> userinfo = dao.getUserInfo(userid);
		String account = (String) userinfo.get("account");
//		String content = "您的账号"+account+"密码已重置为："+param.get("srcpassword")+"，请使用新密码登录,如有异议请联系管理员"+orgcompany;
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.organ.service.userservice.sendnewpwd2user",account,(String)param.get("srcpassword"),organPhone);
		List<String> users = new ArrayList<String>();
		users.add(account);
		UserMessage um = new UserMessage(users,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
	}

	@Transactional
	public Map<String, Object> changeUsersDept(Map<String, Object> param) {
		Map<String,Object> ress = new HashMap<String,Object>();
		List<String> userids = (List<String>) param.get("userids");
		String deptid = (String) param.get("deptid");
		for(int i=0;i<userids.size();i++){
			String userid = userids.get(i);
			Map<String,Object> userinfo = new HashMap<String,Object>();
			userinfo.put("userid", userid);
			userinfo.put("deptid", deptid);
			dao.changeUserDept(userinfo);
		}
		ress.put("status", "success");
		ress.put("message", "批量更新部门成功");
		return ress;
	}

	@Transactional
	public Map<String, Object> deleteUsers(Map<String, Object> param) {
		Map<String,Object> ress = new HashMap<String,Object>();
		List<String> userids = (List<String>) param.get("userids");
		String organid = (String) param.get("organid");
		for(int i=0;i<userids.size();i++){
			String userid = userids.get(i);
			Map<String,Object> userinfo = new HashMap<String,Object>();
			userinfo.put("userid", userid);
			userinfo.put("organid", organid);
			deleteUser(userinfo);
		}
		ress.put("status", "success");
		ress.put("message", "批量删除人员成功");
		return ress;
	}

	public boolean hasMainAccount(String userName) {
		return dao.hasMainAccount(userName);
	}

	public boolean isUnbindAccount(String userName) {
		return dao.isUnbindAccount(userName);
	}

	public Map<String, Object> setAsMainAccount(String userName) {
		Map<String,Object> ress = new HashMap<String,Object>();
		dao.setAsMainAccount(userName);
		ress.put("status", "success");
		ress.put("message", "批量删除人员成功");
		return ress;
	}

	public Map<String, Object> updateFirstLogin(OrgUser user) {
		Map<String,Object> ress = new HashMap<String,Object>();
		dao.updateFirstLogin(user);
		ress.put("status", "success");
		ress.put("message", "更新成功");
		return ress;
	}

	public Map<String, Object> doImportUser(Map<String, Object> params) {
		String deptid = (String) params.get("deptid");
		String organid = (String) params.get("organid");
		Object rulesid = params.get("rulesid");
		Map<String,Object> userinfo = (Map<String, Object>)params.get("userinfo");
		Iterator userit = userinfo.keySet().iterator();
		Map<String,Object> res = new HashMap<String,Object>();
		TransactionStatus  sta = tm.getTransaction(def);
		List<Map<String,Object>> userinfos = new ArrayList<Map<String,Object>>();
		try{
			while(userit.hasNext()){
				String key = (String) userit.next();
				Map<String,Object> userobj = (Map<String, Object>) userinfo.get(key);
				userobj.put("deptid", deptid);
				userobj.put("organid", organid);
				userobj.put("rulesid", rulesid);
				String password = UNID.getUNID(null, 6);
				userobj.put("srcpassword",password);
				userobj.put("password", PasswordEncoder.encode(password));
				Map<String,Object> ress = validateUserInfo(key,userobj);
				if("fail".equalsIgnoreCase((String)ress.get("status"))){
					tm.rollback(sta);
					return ress;
				}
				userinfos.add(userobj);
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "导入异常");
			tm.rollback(sta);
			return res;
		}
		tm.commit(sta);
		try{
			//发送短信通知导入成功的用户
			senSMS2Users(userinfos);
		}catch(Exception e){
			//发送短信如果异常，正常导入，谁没有接到短信，直接充值密码
			e.printStackTrace();
		}
		res.put("status", "success");
		res.put("message", "导入成功");
		return res;
	}
	
	/**
	 * 发送短信到导入用户
	 * @param userinfos
	 */
	private void senSMS2Users(List<Map<String, Object>> userinfos) {
		for(int i=0;i<userinfos.size();i++){
			sendPwd2User(userinfos.get(i));
		}
	}

	DataSourceTransactionManager tm;
	DefaultTransactionDefinition def;
	@Resource(name = "transactionManager")
	public void setTransaction(DataSourceTransactionManager tm){
		def = new DefaultTransactionDefinition(); 
		this.tm = tm;
	}
	
	private Map<String,Object> validateUserInfo(String rownum,Map<String,Object> userobj){
		Map<String,Object> res = new HashMap<String,Object>();
		if(userobj==null){
			res.put("status", "success");
		}
		try{
			//角色判断校验
			String rolecaption = (String) userobj.get("角色");
			if(StringUtils.isBlank(rolecaption)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行角色为空");
				return res;
			}
			String roleid = dao.getRoleIdByRoleName(rolecaption);
			if(StringUtils.isBlank(roleid)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行角色信息已不存在");
				return res;
			}
			
			//昵称校验
			String nickname = (String) userobj.get("员工姓名");
			if(StringUtils.isBlank(nickname)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行员工姓名为空");
				return res;
			}
			
			//手机号验重
			String phone = (String) userobj.get("手机号");
			if(StringUtils.isBlank(phone)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行手机号为空");
				return res;
			}
			userobj.put("phone", phone);
			boolean flag = dao.existUser(phone);
			if(flag){
				res.put("status", "fail");
				res.put("message",  "导入失败，"+nickname+"("+phone+")因电话号码已经存在。");
				return res;
			}
			
			//分类校验
			String typecaption = (String) userobj.get("分类");
			if(StringUtils.isBlank(typecaption)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行分类为空");
				return res;
			}
			String type = "特殊".equalsIgnoreCase(typecaption)?"1":"0";
			
			//性别校验
			String sexcaption = (String) userobj.get("性别");
			if(StringUtils.isBlank(sexcaption)){
				res.put("status", "fail");
				res.put("message", "第"+rownum+"行性别为空");
				return res;
			}
			String sex = "男".equalsIgnoreCase(sexcaption)?"0":"1";
			
			//添加员工
			Map<String,Object> userinfo = new HashMap<String,Object>();
			userinfo.put("role", roleid);
			userinfo.put("nickname", nickname);
			userinfo.put("phone", phone);
			userinfo.put("specialstate", type);
			userinfo.put("sex", sex);
			userinfo.put("organid", userobj.get("organid"));
			userinfo.put("deptid", userobj.get("deptid"));
			userinfo.put("password", userobj.get("password"));
			String userid = GUIDGenerator.newGUID();
			userinfo.put("userid", userid);
			dao.addUser(userinfo);
			//userobj在发送短信的时候会判断要不要创建个人用户信息所以需要保存用户信息
			userobj.put("sex", sex);
			userobj.put("nickname", nickname);
			//添加员工角色信息
			String userroleid = GUIDGenerator.newGUID();
			userinfo.put("userroleid", userroleid);
			dao.addRole(userinfo);
			
			//添加用车规则
			Object ruleobj = userobj.get("rulesid");
			if(ruleobj!=null){
				String[] rulesid = ((String)ruleobj).split(",");
				if(rulesid.length>0){
					List<Map<String,Object>> userruleobjs = new ArrayList<Map<String,Object>>();
					for(int i=0;i<rulesid.length;i++){
						Map<String,Object> userruleobj = new HashMap<String,Object>();
						userruleobj.put("id", GUIDGenerator.newGUID());
						userruleobj.put("userid", userid);
						userruleobj.put("userulesid", rulesid[i]);
						userruleobjs.add(userruleobj);
					}
					dao.addCarRules(userruleobjs);
				}
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "第"+rownum+"导入异常，导入失败");
		}
		res.put("status", "success");
		return res;
	}

	public Map<String, Object> addUserLoginLog(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		dao.addUserLoginLog(params);
		res.put("status", "success");
		return res;
	}

	public Map<String, Object> getLastLoginLog(Map<String, Object> params) {
		return dao.getLastLoginLog(params);
	}

	public Map<String, Object> saveUsertoken(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		boolean flag = dao.hasUsertoken(params);
		if(flag){
			dao.updateUsertoken(params);
		}else{
			params.put("id",GUIDGenerator.newGUID());
			dao.addUsertoken(params);
		}
		res.put("status", "success");
		return res;
	}

	public Map<String, Object> checkValid(Map<String, Object> params) {
		String account = (String) params.get("account");
		String usertoken = (String) params.get("usertoken");
		Map<String,Object> res = new HashMap<String,Object>();
		String dbusertoken = dao.getUserToken(account);
		if(StringUtils.isNotBlank(dbusertoken)&&!dbusertoken.equalsIgnoreCase(usertoken)){
			res.put("pushdown", true);
		}else{
			res.put("pushdown", false);
		}
		res.put("status", "success");
		return res;
	}
	
	
}
