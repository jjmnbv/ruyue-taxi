package com.szyciov.operate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserdisablelog;
import com.szyciov.op.param.PeUserQueryParam;
import com.szyciov.operate.dao.PeUserDao;
import com.szyciov.param.QueryParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.UNID;

@Service("PeUserService")
public class PeUserService {
	private PeUserDao dao;

	@Resource(name = "PeUserDao")
	public void setDao(PeUserDao dao) {
		this.dao = dao;
	}
	
	public List<PeUser> getPeUserListByQuery(PeUserQueryParam queryParam){
		return dao.getPeUserListByQuery(queryParam);
	};
	
	public int getPeUserListCountByQuery(PeUserQueryParam queryParam){
		return dao.getPeUserListCountByQuery(queryParam);
	};
	
	public PageBean getPeUserByQuery(PeUserQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PeUser> list = getPeUserListByQuery(queryParam);
		for(PeUser l : list){
			if(l.getSex() != null && !l.getSex().equals("")){
				if(l.getSex().equals("0")){
					l.setSex("男");
				}
				if(l.getSex().equals("1")){
					l.setSex("女");
				}
			}
			if(l.getDisablestate() != null && !l.getDisablestate().equals("")){
				if(l.getDisablestate().equals("0")){
					l.setDisablestateShow("启用");
				}
				if(l.getDisablestate().equals("1")){
					l.setDisablestateShow("禁用");
				}
			}
			l.setDisableRecord(disableRecord(l.getId()));
		}
		int iTotalRecords = getPeUserListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public Map<String, String> enable(String id){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "账号已启用");
		dao.enable(id);
		return ret;
	};

	public PeUser getById(String id){
		return dao.getById(id);
	};

	public Map<String, String> resetPassword(String id,String orgUserAccount){
		PeUser peUser = new PeUser();
		String password = UNID.getUNID(null, 6);
		String savePwd = PasswordEncoder.encode(password);
		peUser.setId(id);
		peUser.setUserpassword(savePwd);	
		dao.resetPassword(peUser);
		//判断是不是机构用户， 如果是机构用户，要同时重置org_user表
		if(orgUserAccount != null && !orgUserAccount.equals("") && !orgUserAccount.equals("null")){
			peUser.setOrgUserAccount(orgUserAccount);
			dao.resetOrgUserPassword(peUser);
		}
		//随机生成密码，并发送随机密码到联系人手机
		PeUser p = getById(id);
		//设置发送的手机号码
		List<String> list = new ArrayList<String>();
		list.add(p.getAccount());
		//模板：“您好约车的登录账号XXXXXXXXXXX密码已被重置为：{X}，请使用新密码登录，详情请咨询{0}”
		//0：运管端客服电话
		//X：密码是随机生成
		OpPlatformInfo op =getOpPlatforminfo();
//		String content = "您好约车的登录账号"+p.getAccount()+"密码已被重置为：("+password+")，请使用新密码登录，详情请咨询("+op.getServcieTel()+")";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.peuserservice.resetpassword", p.getAccount(),password,op.getServcieTel());
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "账号的密码已重置");
		return ret;
	};
	public int disableRecord(String id){
		return dao.disableRecord(id);
	};
	
	public List<PeUserdisablelog> getPeUserdisablelogListByQuery(QueryParam queryParam){
		return dao.getPeUserdisablelogListByQuery(queryParam);
	};
	
	public int getPeUserdisablelogListCountByQuery(QueryParam queryParam){
		return dao.getPeUserdisablelogListCountByQuery(queryParam);
	};
	
	public PageBean getPeUserdisablelogByQuery(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PeUserdisablelog> list = getPeUserdisablelogListByQuery(queryParam);
		int iTotalRecords = getPeUserdisablelogListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PeUser> exportData(PeUserQueryParam peUserQueryParam){
		List<PeUser> peUser = dao.exportData(peUserQueryParam);
		for(PeUser l : peUser){
			if(l.getSex() != null && !l.getSex().equals("")){
				if(l.getSex().equals("0")){
					l.setSex("男");
				}
				if(l.getSex().equals("1")){
					l.setSex("女");
				}
			}
			if(l.getDisablestate() != null && !l.getDisablestate().equals("")){
				if(l.getDisablestate().equals("0")){
					l.setDisablestate("启用");
				}
				if(l.getDisablestate().equals("1")){
					l.setDisablestate("禁用");
				}
			}
			l.setDisableRecord(disableRecord(l.getId()));
		}
		return peUser;
	};
	
	public Map<String, String> createPeUserdisablelog(PeUserdisablelog peUserdisablelog){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "账号已禁用");
		dao.createPeUserdisablelog(peUserdisablelog);
		disable(peUserdisablelog.getUserid());
		return ret;
	};
	
	public void disable(String id){
		dao.disable(id);
	};
	
	public OpPlatformInfo getOpPlatforminfo(){
		return dao.getOpPlatforminfo();
	};
}
