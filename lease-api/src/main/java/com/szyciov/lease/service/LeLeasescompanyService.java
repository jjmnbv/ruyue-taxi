package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.dao.LeLeasescompanyDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UNID;
import net.sf.json.JSONObject;

@Service("LeLeasescompanyService")
public class LeLeasescompanyService {
	private LeLeasescompanyDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	@Resource(name = "LeLeasescompanyDao")
	public void setDao(LeLeasescompanyDao dao) {
		this.dao = dao;
	}
	public Map<String, String> updateLeLeasescompany(LeLeasescompany leLeasescompany) {
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany l = getLeLeasescompany(leLeasescompany.getUserId());
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		dao.updateLeLeasescompany(leLeasescompany);
		if(l!=null){
			if(!leLeasescompany.getPhone().equals(l.getPhone())){
				// 推送消息到 运管端
				OpUser ou = getOpUser();
				String userNewsTbName = UserNewsParam.OP_USERNEWS_TABNAME;
				UserNewsParam unp = new UserNewsParam();
				UserNews un = new UserNews();
	//			un.setId(GUIDGenerator.newGUID());
				un.setUserid(ou.getId());
				un.setType(un.USER_NEWS_TYPE_SYSTEM);
				//"标题为：“机构联系方式变更” 内容为：“xxxxx公司联系方式变更为138xxxxxxxx”"
				String content = "{"+"\"type\":"+"\"租赁公司联系方式变更\","+"\"title\":"+"\"租赁公司联系方式变更\","+"\"content\":"+"\""+l.getName()+"联系方式变更为"+leLeasescompany.getPhone()+"\""+"}";
				un.setContent(content);
	//			un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
				unp.setUserNewsTbName(userNewsTbName);
				unp.setUserNews(un);
				returns(unp);
			}
		}
		return ret;
	}
	
	public LeLeasescompany getLeLeasescompany(String id){
		return dao.getLeLeasescompany(id);
	};
	
	public void updatePassword(User user){
		LeLeasescompany ll = getLeLeasescompany(user.getId());
		String password = user.getUserpassword();
		//随机生成密码，并发送随机密码到联系人手机
//		List<String> list = new ArrayList<String>();
//		list.add(ll.getPhone());
//		//您的超级管理员账号：xxxxxxx;随机密码：888888，请使用账号密码登录【租赁公司简称】
//		String content = "您的超级管理员账号："+user.getAccount()+";密码："+password+"请使用账号密码登录("+ll.getShortName()+")";
//		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
//		MessageUtil.sendMessage(um);
		user.setUserpassword(PasswordEncoder.encode(password));
		dao.updatePassword(user);
	};
	
	public Map<String, String> updateToC(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "申请成功，等待审核");
		dao.updateToC(leLeasescompany);
		// 推送消息到 运管端
		LeLeasescompany l = getLeLeasescompany(leLeasescompany.getUserId());
		OpUser ou = getOpUser();
		String userNewsTbName = UserNewsParam.OP_USERNEWS_TABNAME;
		UserNewsParam unp = new UserNewsParam();
		UserNews un = new UserNews();
//		un.setId(GUIDGenerator.newGUID());
		un.setUserid(ou.getId());
		un.setType(un.USER_NEWS_TYPE_SYSTEM);
		String content = "{"+"\"type\":"+"\"加入toC审核\","+"\"title\":"+"\"toC业务\","+"\"content\":"+"\"（"+l.getName()+"），申请加入toC业务，请审核"+l.getPhone()+"\""+"}";
		un.setContent(content);
//		un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
		unp.setUserNewsTbName(userNewsTbName);
		unp.setUserNews(un);
		returns(unp);
		return ret;
	};
	public JSONObject returns(UserNewsParam unp){
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				unp,JSONObject.class);
	}
	public Map<String, String> outToC(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "退出成功");
		dao.outToC(leLeasescompany);
		// 修改联系方式  推送消息到 运管端
		LeLeasescompany l = getLeLeasescompany(leLeasescompany.getUserId());
		OpUser ou = getOpUser();
		String userNewsTbName = UserNewsParam.OP_USERNEWS_TABNAME;
		UserNewsParam unp = new UserNewsParam();
		UserNews un = new UserNews();
//		un.setId(GUIDGenerator.newGUID());
		un.setUserid(ou.getId());
		un.setType(un.USER_NEWS_TYPE_SYSTEM);
		String content = "{"+"\"type\":"+"\"退出toC提醒\","+"\"title\":"+"\"toC业务\","+"\"content\":"+"\"（"+l.getName()+"），已退出toC业务，详情请咨询"+l.getPhone()+"\""+"}";
		un.setContent(content);
//		un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
		unp.setUserNewsTbName(userNewsTbName);
		unp.setUserNews(un);
		returns(unp);
		return ret;
	};
	
	public LeCompanyAgreement getLeCompanyAgreement(String id){
		return dao.getLeCompanyAgreement(id);
	};
	
	public void updataLeUserFristtime(String id){
		dao.updataLeUserFristtime(id);
	};
	
	public OpUser getOpUser(){
		return dao.getOpUser();
	};
}
