package com.szyciov.operate.service;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.UserNews;
import com.szyciov.lease.entity.*;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.param.LeLeasescompanyQueryParam;
import com.szyciov.operate.dao.LeLeasescompanyDao;
import com.szyciov.operate.dao.PeUserDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import com.szyciov.util.sequence.CompanyRegSequence;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubVehicleScope;
@Service("LeLeasescompanyService")
public class LeLeasescompanyService {
	private LeLeasescompanyDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	@Resource(name = "LeLeasescompanyDao")
	public void setDao(LeLeasescompanyDao dao) {
		this.dao = dao;
	}
	private PeUserDao peUserDao;

	@Resource(name = "PeUserDao")
	public void setDao(PeUserDao dao) {
		this.peUserDao = dao;
	}
	public List<LeLeasescompany> getLeLeasescompanyListByQuery(LeLeasescompanyQueryParam queryParam){
		return dao.getLeLeasescompanyListByQuery(queryParam);
	};
	
	public int getLeLeasescompanyListCountByQuery(LeLeasescompanyQueryParam queryParam){
		return dao.getLeLeasescompanyListCountByQuery(queryParam);
	};
	
	public PageBean getLeLeasescompanyByQuery(LeLeasescompanyQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeLeasescompany> list = getLeLeasescompanyListByQuery(queryParam);
		for(LeLeasescompany l : list){
			if(l.getCompanyState() != null && !l.getCompanyState().equals("")){
				if(l.getCompanyState().equals("0")){
					l.setCompanyStateShow("正常");
				}
				if(l.getCompanyState().equals("1")){
					l.setCompanyStateShow("禁用");
				}
			}
			if(l.getTocState() != null && !l.getTocState().equals("")){
				if(l.getTocState().equals("0")){
					l.setTocState("未加入");
				}
				if(l.getTocState().equals("1")){
					l.setTocState("加入待审批");
				}
				if(l.getTocState().equals("2")){
					l.setTocState("已加入");
				}
				if(l.getTocState().equals("3")){
					l.setTocState("已退出");
				}
			}
		}
		int iTotalRecords = getLeLeasescompanyListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<LeLeasescompany> getNameOrCity(LeLeasescompanyQueryParam queryParam){
		return dao.getNameOrCity(queryParam);
	};
	
	public List<LeLeasescompany> getCityOrName(LeLeasescompanyQueryParam queryParam){
		return dao.getCityOrName(queryParam);
	};
	
	public Map<String, String> enable(String id){
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany leLeasescompany = getById(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号已启用");
		dao.enable(id);
		return ret;
	};
	
	public Map<String, String> disable(String id){
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany leLeasescompany = getById(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号已禁用");
		dao.disable(id);
		return ret;
	};
	
	public LeLeasescompany getById(String id){
		return dao.getById(id);
	};
	//禁用
	public Map<String, String> disableTocs(String id){
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany leLeasescompany = getById(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号已禁用Toc");
		dao.disableToc(id);
		return ret;
	}
	//审核不通过
	public Map<String, String> disableToc(String id){
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany leLeasescompany = getById(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号未通过审核");
		dao.disableToc(id);
		// 推送消息到    运管端 “贵公司加入toC业务的申请，已通过审核，详情请咨询{0}” 0：运管端客服电话
		OpPlatformInfo opf = peUserDao.getOpPlatforminfo();
		String userNewsTbName = UserNewsParam.LE_USERNEWS_TABNAME;
		UserNewsParam unp = new UserNewsParam();
		UserNews un = new UserNews();
//							un.setId(GUIDGenerator.newGUID());
		un.setUserid(leLeasescompany.getUserId());
		un.setType(un.USER_NEWS_TYPE_SYSTEM);
		String content = "{"+"\"type\":"+"\"toC审核未通过\","+"\"title\":"+"\"toC业务\","+"\"content\":"+"\"贵公司加入toC业务的申请，审核不通过，详情请咨询"+opf.getServcieTel()+"\""+"}";
		un.setContent(content);
//							un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
		unp.setUserNewsTbName(userNewsTbName);
		unp.setUserNews(un);
		returns(unp);
		return ret;
	};
	
	public Map<String, String> examineToc(String id){
		Map<String, String> ret = new HashMap<String, String>();
		LeLeasescompany leLeasescompany = getById(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号已通过审核");
		dao.examineToc(id);
		// 推送消息到    运管端 “贵公司加入toC业务的申请，已通过审核，详情请咨询{0}” 0：运管端客服电话
		OpPlatformInfo opf = peUserDao.getOpPlatforminfo();
		String userNewsTbName = UserNewsParam.LE_USERNEWS_TABNAME;
		UserNewsParam unp = new UserNewsParam();
		UserNews un = new UserNews();
//					un.setId(GUIDGenerator.newGUID());
		un.setUserid(leLeasescompany.getUserId());
		un.setType(un.USER_NEWS_TYPE_SYSTEM);
		String content = "{"+"\"type\":"+"\"toC审核通过\","+"\"title\":"+"\"toC业务\","+"\"content\":"+"\"贵公司加入toC业务的申请，已通过审核，详情请咨询"+opf.getServcieTel()+"\""+"}";
		un.setContent(content);
//					un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
		unp.setUserNewsTbName(userNewsTbName);
		unp.setUserNews(un);
		returns(unp);
		return ret;
	};
	public JSONObject returns(UserNewsParam unp){
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				unp,JSONObject.class);
	}
	public Map<String, String> resetPassword(String id,String account){
		User user = new User();
		String password = UNID.getUNID(null, 6);
		user.setAccount(account);
		user.setUserpassword(PasswordEncoder.encode(password));	
		dao.resetPassword(user);
		
		LeLeasescompany leLeasescompany =getById(id);
		//随机生成密码，并发送随机密码到联系人手机
		List<String> list = new ArrayList<String>();
		list.add(leLeasescompany.getPhone());
		//模板：“您的超级管理员账号XXXXXX密码已被重置为：{X}，请使用新密码登录，详情请咨询{0}”
		//0：运管端客服电话
		//X：密码是随机生成
		OpPlatformInfo opf = peUserDao.getOpPlatforminfo();
//		String content = "您的超级管理员账号："+leLeasescompany.getAccount()+"密码已被重置为："+password+",请使用新密码登录，详情请咨询"+opf.getServcieTel()+"";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.leleasescompanyservice.resetpassword", leLeasescompany.getAccount(),password,opf.getServcieTel());
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", account+"账号的密码已重置");
		return ret;
	};
	
	public List<City> getPubCityaddr(){
		return dao.getPubCityaddr();
	};
	
	public int checkNameOrShortName(LeLeasescompany leLeasescompany){
		return dao.checkNameOrShortName(leLeasescompany);
	};
	
	public Map<String, String> createLeLeasescompany(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		if(dao.checkLeLeasescompany()>1){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "租赁端当前仅允许创建一个服务车企作为B2B业务运营方");
			return ret;
		}
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", leLeasescompany.getAccount()+"账号对应的随机密码已发送至对方的手机号"+leLeasescompany.getPhone()+"，请提醒用户注意查收。");
		
		LeLeasescompany l = getCreateFristData();
		String regorder = "";
		if(l!=null) {
			if (StringUtils.isNotEmpty(l.getRegorder())) {
				regorder = l.getRegorder();
			}
		}
//		if(l.getRegorder() == null || l.getRegorder().equals("")){
//			l.setRegorder("");
//		}
		regorder = CompanyRegSequence.getNextSeq(regorder);
		
		leLeasescompany.setRegorder(regorder);
		dao.createLeLeasescompany(leLeasescompany);
		//随机生成密码，并发送随机密码到联系人手机
		List<String> list = new ArrayList<String>();
		list.add(leLeasescompany.getPhone());
		String password = UNID.getPwdDefStr();
		//您的超级管理员账号：xxxxxxx;随机密码：888888，请使用账号密码登录【租赁公司简称】
//		String content = "您的超级管理员账号："+leLeasescompany.getAccount()+";随机密码："+password+",请使用账号密码登录（"+leLeasescompany.getShortName()+"）";,leLeasescompany.getShortName()
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.leleasescompanyservice.addleuser", leLeasescompany.getAccount(),password);
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
		//新增操作：保存成功时，需要对应产生“租赁用户”的相关信息
		User user = new User();
		user.setId(GUIDGenerator.newGUID());
		user.setLeasescompanyid(leLeasescompany.getId());
		user.setSpecialstate("1");
		user.setAccount(leLeasescompany.getAccount());
		user.setUserpassword(PasswordEncoder.encode(password));
		user.setNickname(leLeasescompany.getContacts());
		user.setTelphone(leLeasescompany.getPhone());
		user.setEmail(leLeasescompany.getMail());
		createLeUser(user);
		return ret;
	};
	
	public void createLeUser(User user){
		dao.createLeUser(user);
	};
	
	public Map<String, String> updateLeLeasescompany(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		LeLeasescompany l = getById(leLeasescompany.getId());
		dao.updateLeLeasescompany(leLeasescompany);
		User user = new User();
		user.setLeasescompanyid(leLeasescompany.getId());
		user.setNickname(leLeasescompany.getContacts());
		user.setEmail(leLeasescompany.getMail());
		if(leLeasescompany.getAccount()!=null && !leLeasescompany.getAccount().equals("") && leLeasescompany.getPhone()!=null && !leLeasescompany.getPhone().equals("")){
			user.setTelphone(leLeasescompany.getPhone());
			user.setAccount(leLeasescompany.getAccount());
			if(!l.getAccount().equals(leLeasescompany.getAccount()) || !l.getPhone().equals(leLeasescompany.getPhone())){
				List<String> list = new ArrayList<String>();
				list.add(leLeasescompany.getPhone());
				String password = UNID.getPwdDefStr();
				//您的超级管理员账号：xxxxxxx;随机密码：888888，请使用账号密码登录【租赁公司简称】
//				String content = "您的超级管理员账号："+leLeasescompany.getAccount()+";随机密码："+password+",请使用账号密码登录（"+leLeasescompany.getShortName()+"）";
				String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.operate.service.leleasescompanyservice.addleuser", leLeasescompany.getAccount(),password);
				UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
				MessageUtil.sendMessage(um);
				//修改对应的账号密码
//				user.setAccount(leLeasescompany.getAccount());
				user.setUserpassword(PasswordEncoder.encode(password));
			}
		}
		updateLeUser(user);
		return ret;
	};
	
	public void updateLeUser (User user){
		dao.updateLeUser(user);
	};
	
	public List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListByQuery(pubDriverQueryParam);
	};
	
	public int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListCountByQuery(pubDriverQueryParam);
	};
	
	public PubDriver getVehicleInfo(String id){
		return dao.getVehicleInfo(id);
	};
	
	public PageBean getPubDriverByQuery(PubDriverQueryParam pubDriverQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubDriverQueryParam.getsEcho());
		List<PubDriver> list = getPubDriverListByQuery(pubDriverQueryParam);
		for(int i = 0;i<list.size();i++){
			// 0男 1女
//			if(list.get(i).getSex().equals("0")){
//				list.get(i).setSex("男");
//			}else{
//				list.get(i).setSex("女");
//			}
			
			//经营范围
			List<PubVehicleScope> gp = getPubVehicleScope(list.get(i).getId());
			String scope = "";
			if(gp!=null){
				for(PubVehicleScope gpvs : gp){
					scope+=gpvs.getCityId()+"、";
					list.get(i).setPubVehicleScope(scope.substring(0, scope.length()-1));
				}
			}else{
				list.get(i).setPubVehicleScope("");
			}
			if(list.get(i).getVehicleType().equals("1")){
				if(list.get(i).getDistributionVel()==null || list.get(i).getDistributionVel().equals("")){
					list.get(i).setDistributionVel("/");
				}
			}else{
				if(list.get(i).getDistributionVel()==null || list.get(i).getDistributionVel().equals("")){
					list.get(i).setDistributionVel("未分配");
				}
			}
//			PubDriver p = getVehicleInfo(list.get(i).getId());
//			if(p != null){
//				list.get(i).setVehicleInfo(p.getVehicleInfo());
//			}else{
//				list.get(i).setVehicleInfo("/");
//			}
			
		}
		int iTotalRecords = getPubDriverListCountByQuery(pubDriverQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<PubVehicleScope> getPubVehicleScope(String id){
		return dao.getPubVehicleScope(id);
	};
	
	public List<Dictionary> getDictionaryByType(String str) {
		return dao.getDictionaryByType(str);
	}
	
	public List<PubDriver> getCity(String leasesCompanyId){
		return dao.getCity(leasesCompanyId);
	};
	
	public PubDriver getByIdPubDriver(String id){
		PubDriver pubDriver = dao.getByIdPubDriver(id);
		String vehicleInfo = getVehicleInfo(id).getVehicleInfo();
		pubDriver.setVehicleInfo(vehicleInfo);
		String[] brandCars = vehicleInfo.split(" ");
		pubDriver.setBrandCars(brandCars[0]);
		return pubDriver;
	};
	
	public List<OpVehiclemodels> getOpVehiclemodels(){
		return dao.getOpVehiclemodels();
	};
	
	public Map<String, String> createOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		Map<String, String> ret = new HashMap<String, String>();
		if(!opVehclineModelsRef.getVehiclemodelsid().equals("")){
			if(checkOpVehclineModelsRef(opVehclineModelsRef)>0){
				updateOpVehclineModelsRef(opVehclineModelsRef);
			}else{
				dao.createOpVehclineModelsRef(opVehclineModelsRef);
			}
		}else{
			deleteVehiclemodelsid(opVehclineModelsRef.getVehicleid());
		}
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		return ret;
	};
	
	public void deleteVehiclemodelsid(String id){
		dao.deleteVehiclemodelsid(id);
	};
	
	public void updateOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		dao.updateOpVehclineModelsRef(opVehclineModelsRef);
	};
	
	public int checkOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		return dao.checkOpVehclineModelsRef(opVehclineModelsRef);
	};
	
	public List<LeLeasescompany> exportData(LeLeasescompanyQueryParam leLeasescompanyQueryParam){
		List<LeLeasescompany> list = dao.exportData(leLeasescompanyQueryParam);
		for(LeLeasescompany l : list){
			if(l.getCompanyState() != null && !l.getCompanyState().equals("")){
				if(l.getCompanyState().equals("0")){
					l.setCompanyStateShow("正常");
				}
				if(l.getCompanyState().equals("1")){
					l.setCompanyStateShow("禁用");
				}
			}
			if(l.getTocState() != null && !l.getTocState().equals("")){
				if(l.getTocState().equals("0")){
					l.setTocState("未加入");
				}
				if(l.getTocState().equals("1")){
					l.setTocState("加入待审批");
				}
				if(l.getTocState().equals("2")){
					l.setTocState("已加入");
				}
				if(l.getTocState().equals("3")){
					l.setTocState("已退出");
				}
			}
		}
		return list;
	};
	
	public PubCityAddr getCityId(String cityName){
		return dao.getCityId(cityName);
	};
	
	public void updateLeUsers(User user){
		dao.updateLeUsers(user);
	};
	
	public LeLeasescompany getCreateFristData(){
		return dao.getCreateFristData();
	};
	
	public List<Map<String, Object>> getQueryKeyword(PubDriverQueryParam pubDriverQueryParam){
		return dao.getQueryKeyword(pubDriverQueryParam);
	};
}
