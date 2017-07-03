package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.dao.PubDriverAccountDao;
import com.szyciov.lease.dao.PubDriverDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.lease.param.ServiceOrgQueryParam;
import com.szyciov.message.UserMessage;
import com.szyciov.org.entity.OrgOrganDriverRef;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UNID;

import net.sf.json.JSONObject;

@Service("PubDriverService")
public class PubDriverService {
	private PubDriverDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	@Resource(name = "PubDriverDao")
	public void setDao(PubDriverDao dao) {
		this.dao = dao;
	}
	
	private PubDriverAccountDao orgDriverAccountDao;
	@Resource(name = "PubDriverAccountDao")
	public void setDao(PubDriverAccountDao dao) {
		this.orgDriverAccountDao = dao;
	}
	public Map<String, String> createPubDriver(PubDriver pubDriver) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkPubDriver(pubDriver);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "已存在，请勿重复添加");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "创建成功");//PasswordEncoder.encode(
			String password =UNID.getUNID(null, 6);
			pubDriver.setUserPassword(PasswordEncoder.encode(password));
			//判断是出租车就没有司机身份
			if(pubDriver.getVehicleType().equals("1")){
				pubDriver.setIdEntityType("2");
			}
			dao.createPubDriver(pubDriver);
			//添加司机账户
			PubDriverAccount orgDriverAccount = new PubDriverAccount();
			orgDriverAccount.setId(GUIDGenerator.newGUID());
			orgDriverAccount.setDriverid(pubDriver.getId());
			orgDriverAccount.setLeasescompanyid(pubDriver.getLeasesCompanyId());
			orgDriverAccount.setBalance(0);
			orgDriverAccountDao.createOrgDriverAccount(orgDriverAccount);
			
			List<String> list = new ArrayList<String>();
			list.add(pubDriver.getPhone());
			//"模板：“您的司机账号：XXXXXX;随机密码：888888，请使用账号密码登录司机端（租赁公司简称）”
//			String content = "您的司机账号"+pubDriver.getPhone()+";随机密码："+password+"，请使用账号密码登录司机端（"+getLeLeasescompany(pubDriver.getLeasesCompanyId()).getShortName()+"）";
			String content =SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.pubdriverservice.adddriver", pubDriver.getPhone(),password);
			UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
			MessageUtil.sendMessage(um);
			if(pubDriver.getServiceOrgId() != null){
				String[] serviceOrgIdTemp = pubDriver.getServiceOrgId().split(",");
				for(int i=0;i<serviceOrgIdTemp.length;i++){
					OrgOrganDriverRef orgOrganDriverRef = new OrgOrganDriverRef();
					orgOrganDriverRef.setId(GUIDGenerator.newGUID());
					orgOrganDriverRef.setOrganId(serviceOrgIdTemp[i]);
					orgOrganDriverRef.setDriverId(pubDriver.getId());
					createOrgOrganDriverRef(orgOrganDriverRef);
				}
			}
		}
		return ret;
	}
	
	public Map<String, String> updatePubDriver(PubDriver pubDriver){
		Map<String, String> ret = new HashMap<String, String>();
		//重新判断
//		String workStatus = dao.getByPubDriverId(pubDriver.getId()).getWorkStatus();
//		if(workStatus.equals("1")){
//			ret.put("ResultSign", "Error");
//			ret.put("MessageKey", "服务中的司机不可修改");
//		}else{
		List<OrgOrganDriverRef> oodr = getOrgOrganDriverRef(pubDriver.getId());
		PubDriver p = getById(pubDriver.getId());
		int count = checkPubDriver(pubDriver);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "已存在，不能修改");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
			//判断是出租车就没有司机身份
			dao.updatePubDriver(pubDriver);
			if(pubDriver.getIdEntityType().equals("1")){
				if(pubDriver.getServiceOrgId() != null){
					deleteOrgOrganDriverRef(pubDriver.getId());
					String[] serviceOrgIdTemp = pubDriver.getServiceOrgId().split(",");
					for(int i=0;i<serviceOrgIdTemp.length;i++){
						OrgOrganDriverRef orgOrganDriverRef = new OrgOrganDriverRef();
						orgOrganDriverRef.setId(GUIDGenerator.newGUID());
						orgOrganDriverRef.setOrganId(serviceOrgIdTemp[i]);
						orgOrganDriverRef.setDriverId(pubDriver.getId());
						createOrgOrganDriverRef(orgOrganDriverRef);
					}
				}
			}else{
				deleteOrgOrganDriverRef(pubDriver.getId());
			}
			//0-普通司机，1-特殊司机  司机身份类型
			if(pubDriver.getIdEntityType().equals("0")){
				if(p.getIdEntityType().equals("1")){
					for(int i=0;i<oodr.size();i++){
//						LeLeasescompany l = getLeLeasescompany(leLeasescompany.getUserId());
//						OpUser ou = getOpUser();
						OrgUser orgUser = getOrgUserUsertype(oodr.get(i).getOrganId());
						String userNewsTbName = UserNewsParam.ORG_USERNEWS_TABNAME;
						UserNewsParam unp = new UserNewsParam();
						UserNews un = new UserNews();
//						un.setId(GUIDGenerator.newGUID());
						un.setUserid(orgUser.getId());
						un.setType(un.USER_NEWS_TYPE_SYSTEM);
						//（租赁公司简称）提供的特殊司机{0}，已停止给贵司服务
						String content = "{"+"\"type\":"+"\"司机身份变更\","+"\"title\":"+"\"司机身份变更\","+"\"content\":"+"\"（"+oodr.get(i).getShortName()+"）提供的特殊司机"+pubDriver.getName()+"，已停止给贵司服务\""+"}";
						un.setContent(content);
//						un.setNewsstate(un.USER_NEWS_STATE_UNREAD);
						unp.setUserNewsTbName(userNewsTbName);
						unp.setUserNews(un);
						returns(unp);
					}
				}
			}
		}
//		}
		return ret;
	};
	public JSONObject returns(UserNewsParam unp){
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				unp,JSONObject.class);
	}
	public int checkPubDriver(PubDriver pubDriver){
		return dao.checkPubDriver(pubDriver);
	}
	
	public PubDriver getByPubDriverId(String id){
		return dao.getByPubDriverId(id);
	}
	
	public Map<String, String> resetPassword(String id,String leasesCompanyId){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "重置成功");
		PubDriver pubDriver = new PubDriver();
		String password = UNID.getUNID(null, 6);
		pubDriver.setId(id);
		pubDriver.setUserPassword(PasswordEncoder.encode(password));
		dao.resetPassword(pubDriver);
		PubDriver pub = getById(id);
		List<String> list = new ArrayList<String>();
		list.add(pub.getPhone());
		//"模板：“您的司机账号XXXXXX密码已重置为：{X}，请使用新密码登录,详情请咨询{0}（租赁公司简称）”
		//X：密码是随机生成
		//0：租赁端客服电话"
//		String content = "您的司机账号"+pub.getPhone()+"密码已重置为："+password+",请使用新密码登录,详情请咨询"+getLeLeasescompany(leasesCompanyId).getServicesPhone()+"（"+getLeLeasescompany(leasesCompanyId).getShortName()+"）";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.pubdriverservice.resetpassword", pub.getPhone(),password,getLeLeasescompany(leasesCompanyId).getServicesPhone());
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
		return ret;
	};
	
	public int checkDelete(String id){
		return dao.checkDelete(id);
	};
	
	public Map<String, String> deletePubDriver(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "请解绑车辆在删除");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deletePubDriver(id);
			
			//推送消息       您已经和 xxx 车辆解绑，如有疑问，请和管理员联系您已经和 xxx 车辆解绑，如有疑问，请和管理员联系
	 		List<String> list = new ArrayList<String>();
	 		PubDriver p = getByPubDriverId(id);
	 		list.add(p.getPhone());
//			String content = "账号变动提醒";
	 		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.pubdriverservice.deletedriver");
			UserMessage um = new UserMessage(list,content,UserMessage.DELETEDRIVER);
			MessageUtil.sendMessage(um);
			
		}
		return ret;
	};
	
	public List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListByQuery(pubDriverQueryParam);
	};
	
	public List<PubDriver> getPubDriverByBound(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListByBound(pubDriverQueryParam);
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

		//如果查询状态不为空，则为表格初始化使用，直接返回空结果集
		if(StringUtils.isNotEmpty(pubDriverQueryParam.getQueryStatus())){
			pageBean.setiTotalDisplayRecords(0);
			pageBean.setiTotalRecords(0);
			pageBean.setAaData(new ArrayList());
		}else {

			List<PubDriver> list = getPubDriverListByQuery(pubDriverQueryParam);
			for (PubDriver l : list) {

				// 0男 1女
				if (l.getSex().equals("0")) {
					l.setSex("男");
				} else {
					l.setSex("女");
				}

				List<OrgOrganDriverRef> oodr = getOrgOrganDriverRef(l.getId());
				String shortName = "";
				if (oodr.size() > 0) {
					for (OrgOrganDriverRef o : oodr) {
						shortName += o.getShortName() + "|";
					}
					l.setServiceOrgName(shortName.substring(0, shortName.length() - 1));
				} else {
					l.setServiceOrgName("/");
				}

			}
			int iTotalRecords = getPubDriverListCountByQuery(pubDriverQueryParam);
			int iTotalDisplayRecords = iTotalRecords;
			pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
			pageBean.setiTotalRecords(iTotalRecords);
			pageBean.setAaData(list);
		}
		return pageBean;
	}
	public List<OrgOrgan> getServiceOrgListByQuery(ServiceOrgQueryParam serviceOrgQueryParam){
		return dao.getServiceOrgListByQuery(serviceOrgQueryParam);
	};
	
	public int getServiceOrgListCountByQuery(ServiceOrgQueryParam serviceOrgQueryParam){
		return dao.getServiceOrgListCountByQuery(serviceOrgQueryParam);
	};
	public PageBean getServiceOrgByQuery(ServiceOrgQueryParam serviceOrgQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(serviceOrgQueryParam.getsEcho());
		List<OrgOrgan> list = getServiceOrgListByQuery(serviceOrgQueryParam);
		int iTotalRecords = getServiceOrgListCountByQuery(serviceOrgQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam){
		return dao.getCity(pubDriverQueryParam);
	};
	
	public PubDriver getById(String id){
		PubDriver pubDriver = dao.getById(id);
		List<OrgOrganDriverRef> orgOrganDriverRef = getOrgOrganDriverRef(id);
		if(orgOrganDriverRef.size()>0){
			String serviceOrgId = "";
			String serviceOrgName = "";
			for(int i=0;i<orgOrganDriverRef.size();i++){
				serviceOrgId+=orgOrganDriverRef.get(i).getOrganId()+",";
				serviceOrgName+=orgOrganDriverRef.get(i).getFullName()+",";
			}
			pubDriver.setServiceOrgId(serviceOrgId.substring(0,(serviceOrgId.length()-1)));
			pubDriver.setServiceOrgName(serviceOrgName.substring(0,(serviceOrgName.length()-1)));
		}
		return pubDriver;
	};
	
	public int judgeBinding(String id){
		return dao.judgeBinding(id);
	};
	
//	public PubDriver unwrapVel(String id){
//		return dao.unwrapVel(id);
//	};
	
//	public int judgeUnwrapRecord(String id){
//		return dao.judgeUnwrapRecord(id);
//	};
	
	public void createOrgOrganDriverRef(OrgOrganDriverRef orgOrganDriverRef){
		dao.createOrgOrganDriverRef(orgOrganDriverRef);
	};
	
	public List<OrgOrganDriverRef> getOrgOrganDriverRef(String id){
		return dao.getOrgOrganDriverRef(id);
	};
	
	public void deleteOrgOrganDriverRef(String id){
		dao.deleteOrgOrganDriverRef(id);
	};
	
	public List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam){
		return dao.getSpecialDri(pubDriverQueryParam);
	};
	
	public List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName){
		return dao.getFullNameBySpecialDri(fullName);
	};
	
	public List<PubDriver> exportData(PubDriverQueryParam pubDriverQueryParam){
		List<PubDriver> list = dao.exportData(pubDriverQueryParam);
		for(PubDriver l:list){
			// 0男 1女
			if(l.getSex().equals("0")){
				l.setSex("男");
			}else{
				l.setSex("女");
			}
			//0普通 1 特殊
//			if(l.getIdEntityType().equals("0")){
//				l.setIdEntityType("普通司机");
//			}else{
//				l.setIdEntityType("特殊司机");
//			}
			//0在职 1 离职
			if(l.getJobStatus().equals("0")){
				l.setJobStatus("在职");
			}else{
				l.setJobStatus("离职");
			}
			List<OrgOrganDriverRef> oodr = getOrgOrganDriverRef(l.getId());
			String shortName = "";
			if(oodr.size() > 0){
				for(OrgOrganDriverRef o : oodr){
					shortName+=o.getShortName()+"|";
				}
				l.setServiceOrgName(shortName.substring(0, shortName.length()-1));
			}else{
				l.setServiceOrgName("/");
			}
		}
		return list;
	};
	public int checkPubDriverPhone(PubDriver pubDriver){
		return dao.checkPubDriverPhone(pubDriver);
	};
	
	public LeLeasescompany getLeLeasescompany(String id){
		return dao.getLeLeasescompany(id);
	};
	
	public int checkUnbundling(String driverId){
		return dao.checkUnbundling(driverId);
	};
	
	public OrgUser getOrgUserUsertype(String id){
		return dao.getOrgUserUsertype(id);
	};
	public List<Map<String, Object>> getQueryJobNum(PubDriverQueryParam pubDriverQueryParam){
		return dao.getQueryJobNum(pubDriverQueryParam);
	};


	/**
	 * 返回司机姓名+电话的字符串组合
	 * @param driverId	司机ID
	 * @return
	 */
	public String getNameAndPhoneStr(String driverId){
		PubDriver driver = 	this.getByPubDriverId(driverId);
		if(driver!=null){
			return driver.getName()+" "+driver.getPhone();
		}
		return null;
	}
}

