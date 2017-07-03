package com.szyciov.organ.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.User;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.organ.dao.SupplierManagementDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("SupplierManagementService")
public class SupplierManagementService {
	private SupplierManagementDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	@Resource(name = "SupplierManagementDao")
	public void setDao(SupplierManagementDao dao) {
		this.dao = dao;
	}
	public OrgUser getById(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return dao.getById(map);
	};
	
	public List<OrgUser> getByList(String id,String organid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("organid", organid);
		return dao.getByList(map);
	};
	
	public Map<String, String> addLink(OrgUser orgUser){
		Map<String, String> map = new HashMap<>();
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "帐号关联成功");
		dao.addLink(orgUser.getOrgOrganCompanyRefId());
		updatePassword(orgUser);
		return map;
	};
	
	public Map<String, String> removeLink(String id){
		Map<String, String> map = new HashMap<>();
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "解除关联成功");
		dao.removeLink(id);
		//删除  用车规则（服务车型在这个租赁下面的）
		deleteRuleByLeasesCompanyId(getLeasesCompanyId(id).getCompanyId());
		return map;
	};
	
	public List<OrgUser> getChildCccount(String id,String organid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("organid", organid);
		return dao.getChildCccount(map);
	};
	
	public int checkPassword(OrgUser orgUser){
		orgUser.setUserPassword(PasswordEncoder.encode(orgUser.getUserPassword()));
		return dao.checkPassword(orgUser);
	};
	
	public Map<String, Object> checkPasswords(OrgUser orgUser){
		orgUser.setUserPassword(PasswordEncoder.encode(orgUser.getUserPassword()));
		int count = dao.checkPassword(orgUser);
		if(count < 1){
			OrgUser o = getById(orgUser.getId());
			OrgUser ou = new OrgUser();
			ou.setId(o.getId());
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//				System.out.println(formatter.parse(formatter.format(getNextDay(o.getUpdateTime()))));
				Date date = new Date();
				if(date.getTime() < (formatter.parse(formatter.format(getNextDay(o.getUpdateTime())))).getTime() && o.getLogontimes()<5){
					ou.setLogontimes(o.getLogontimes()+1);
					addLogontimes(ou);
				}
				if(date.getTime() > (formatter.parse(formatter.format(getNextDay(o.getUpdateTime())))).getTime()){
					ou.setLogontimes(0);
					resetLogontimes(ou);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String,Object>();
		OrgUser o = getById(orgUser.getId());
		if(o.getLogontimes() == 5 && count > 0){
			count = 0;
		}
		map.put("count", count);
		map.put("logontimes",5-o.getLogontimes());
		return map;
	};
	
	public void updatePassword(OrgUser orgUser){
		orgUser.setUserPassword(PasswordEncoder.encode(orgUser.getUserPassword()));
		dao.updatePassword(orgUser);
		dao.updatePePassword(orgUser);
		orgUser.setLogontimes(0);
		resetLogontimes(orgUser);
	};
	
	public void updateMainaccount(String id){
		dao.updateMainaccount(id);
	};
	
	public void updateChildMainaccount(String id){
		dao.updateChildMainaccount(id);
	};
	
	public Map<String, String> removeLink(String id,String orgOrganCompanyRefId){
		Map<String, String> map = new HashMap<>();
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "解除关联成功");
		// id  是解除的主账号id
		updateMainaccount(id);
		//orgOrganCompanyRefId  是子账号升为主账号id
		updateChildMainaccount(orgOrganCompanyRefId);
		//删除  用车规则（服务车型在这个租赁下面的）
		deleteRuleByLeasesCompanyId(getLeasesCompanyId(id).getCompanyId());
		return map;
	};
	
	public void addLogontimes(OrgUser orgUser){
		dao.addLogontimes(orgUser);
	};
	//在一个时间加一天
	public Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
	
	public void resetLogontimes(OrgUser orgUser){
		dao.resetLogontimes(orgUser);
	};
	
	public OrgOrgan getOrgorganPhone(String id){
		return dao.getOrgorganPhone(id);
	};
	
	public Map<String, String> updateAll(OrgOrgan orgOrgan){
		updateOrg(orgOrgan);
//		OrgOrganCompanyRef orgOrganCompanyRef = new OrgOrganCompanyRef();
//		orgOrganCompanyRef.setUserId(orgOrgan.getUserId());
//		orgOrganCompanyRef.setOrganId(orgOrgan.getId());
//		OrgOrganCompanyRef orgOrganCompanyRef1 = getCmpId(orgOrganCompanyRef);
//		LeLeasescompany leLeasescompany = new LeLeasescompany();
//		leLeasescompany.setId(orgOrganCompanyRef1.getCompanyId());
//		leLeasescompany.setPhone(orgOrgan.getPhone());
//		updateCmp(leLeasescompany);
		Map<String, String> map = new HashMap<String, String>();
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "更改成功");
		//  需要给  租赁用户  发推送消息  le_usernews
		OrgOrgan o = getLeuser(orgOrgan.getId());
//		String title = "机构联系方式变更";
//		String content = o.getFullName()+"联系方式变更为"+orgOrgan.getPhone();
		String userid = o.getUserId();
		String userNewsTbName = UserNewsParam.LE_USERNEWS_TABNAME;
		UserNewsParam unp = new UserNewsParam();
		UserNews un = new UserNews();
//			un.setId(GUIDGenerator.newGUID());
		un.setUserid(userid);
		un.setType(un.USER_NEWS_TYPE_SYSTEM);
		//"标题为：“机构联系方式变更” 内容为：“xxxxx公司联系方式变更为138xxxxxxxx”"
		String content = "{"+"\"type\":"+"\"机构联系方式变更\","+"\"title\":"+"\"机构联系方式变更\","+"\"content\":"+"\""+o.getFullName()+"联系方式变更为"+orgOrgan.getPhone()+"\""+"}";
		un.setContent(content);
//			un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
		unp.setUserNewsTbName(userNewsTbName);
		unp.setUserNews(un);
		returns(unp);
		return map;
	}
	public JSONObject returns(UserNewsParam unp){
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				unp,JSONObject.class);
	}
	public void updateOrg(OrgOrgan orgOrgan){
		dao.updateOrg(orgOrgan);
	};
	
	public OrgOrganCompanyRef getCmpId(OrgOrganCompanyRef orgOrganCompanyRef){
		return dao.getCmpId(orgOrganCompanyRef);
	};
	
	public void updateCmp(LeLeasescompany leLeasescompany){
		dao.updateCmp(leLeasescompany);
	};
	
	public OrgOrgan getLeuser(String id){
		return dao.getLeuser(id);
	};
	
	public void deleteRuleByLeasesCompanyId(String id){
		dao.deleteRuleByLeasesCompanyId(id);
	}
	
	public OrgOrganCompanyRef getLeasesCompanyId(String id){
		return dao.getLeasesCompanyId(id);
	};
}
