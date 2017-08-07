package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrgOrganCompanyRefDao;
import com.szyciov.lease.dao.OrgOrganDao;
import com.szyciov.lease.dao.OrgUserDao;
import com.szyciov.lease.dao.PubDriverDao;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganCreditRecord;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.message.UserMessage;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.MD5;
import com.szyciov.util.PageBean;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.UNID;
import com.szyciov.util.sequence.CompanyRegSequence;

@Service("OrgOrganService")
public class OrgOrganService {
	private OrgOrganDao dao;
	@Resource(name = "OrgOrganDao")
	public void setDao(OrgOrganDao dao) {
		this.dao = dao;
	}
	
	private OrgUserDao orgUserDao;
	@Resource(name = "OrgUserDao")
	public void setOrgUserDao(OrgUserDao orgUserDao) {
		this.orgUserDao = orgUserDao;
	}
	
	private OrgOrganCompanyRefDao orgOrganCompanyRefDao;
	@Resource(name = "OrgOrganCompanyRefDao")
	public void setOrgOrganCompanyRefDao(OrgOrganCompanyRefDao orgOrganCompanyRefDao) {
		this.orgOrganCompanyRefDao = orgOrganCompanyRefDao;
	}
	
	private PubDriverDao pubDriverDao;
	@Resource(name = "PubDriverDao")
	public void setPubDriverDaoDao(PubDriverDao pubDriverDao) {
		this.pubDriverDao = pubDriverDao;
	}
	
	public int checkOrgOrgan(OrgOrgan orgOrgan) {
		return dao.checkOrgOrgan(orgOrgan);
	}
	
	public int checkAccount(OrgOrgan orgOrgan){
		return dao.checkAccount(orgOrgan);
	};
	
	public Map<String, String> createOrgOrgan(OrgOrgan orgOrgan) {
		Map<String, String> ret = new HashMap<String, String>();
		//校验唯一：机构全称、机构简称、机构账号、邮箱、机构信用代码、工商执照编码是否存在
		int count = dao.checkOrgOrgan(orgOrgan);
		if(dao.checkOrgOrgan(orgOrgan)>0){
			//ret.put("ResultSign", "Error");
			//ret.put("MessageKey", "已存在，请勿重复添加");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "创建成功");
			OrgOrgan o = getCreateFristData(orgOrgan.getCompanyId());
			String regorder = "";
			if(o!=null) {
				if (StringUtils.isNotEmpty(o.getRegorder())) {
					regorder = o.getRegorder();
				}
			}

			regorder = CompanyRegSequence.getNextSeq(regorder);

			orgOrgan.setRegorder(regorder);
			dao.createOrgOrgan(orgOrgan);
			//随机生成密码，并发送随机密码到联系人手机
			List<String> list = new ArrayList<String>();
			list.add(orgOrgan.getPhone());
			String password = UNID.getPwdDefStr();
			//"模板：“您的超级管理员账号：xxxxxxx;随机密码：888888，请使用账号密码登录（租赁公司简称）”
//			String content = "您的超级管理员账号："+orgOrgan.getAccount()+";随机密码："+password+"，请使用账号密码登录（"+pubDriverDao.getLeLeasescompany(orgOrgan.getCompanyId()).getShortName()+"）";
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.orgorganservice.addorguser", orgOrgan.getAccount(),password);
			UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
			MessageUtil.sendMessage(um);
			//新增操作：保存成功时，需要对应产生“机构用户”的相关信息
			OrgUser orgUser = new OrgUser();
			orgUser.setId(GUIDGenerator.newGUID());
			orgUser.setOrganId(orgOrgan.getId());
			orgUser.setUserType("1");
			orgUser.setAccount(orgOrgan.getAccount());
			orgUser.setUserPassword(PasswordEncoder.encode(password));
			orgUser.setCustomerType(orgOrgan.getCustomertype());
			orgUserDao.createOrgUser(orgUser);
			
			//新增操作：保存成功时，需要对应产生“机构账户”的相关信息
			OrgOrganCompanyRef orgOrganCompanyRef = new OrgOrganCompanyRef();
			orgOrganCompanyRef.setId(GUIDGenerator.newGUID());
			orgOrganCompanyRef.setOrganId(orgOrgan.getId());
			orgOrganCompanyRef.setUserId(orgUser.getId());
			orgOrganCompanyRef.setCompanyId(orgOrgan.getCompanyId());
			orgOrganCompanyRef.setFirstTime("0");
			orgOrganCompanyRef.setLineOfCredit(orgOrgan.getLineOfCredit());
			orgOrganCompanyRef.setBalance(orgOrgan.getLineOfCredit());
			orgOrganCompanyRef.setActualBalance(0.0);
			orgOrganCompanyRefDao.createOrgOrganCompanyRef(orgOrganCompanyRef);
			
			//当为渠道客户的时候，ch_info生成一条相关信息
			Map<String, String> map = new HashMap<String,String>();
			String id = GUIDGenerator.newGUID();
			String organid = orgOrgan.getId();
			String channelkey = MD5.MD5Encode(new StringBuffer().append(id).append(organid).toString());
			String signprivatekey = MD5.MD5Encode(new StringBuffer().append(id).append(organid).append(channelkey).toString());
			map.put("id", id);
			map.put("organid", organid);
			map.put("channelkey", channelkey);
			map.put("signprivatekey", signprivatekey);
			dao.CrerateChInfo(map);
		}
		return ret;
	}
	public Map<String, String> updateOrgOrgan(OrgOrgan orgOrgan) {
		Map<String, String> ret = new HashMap<String, String>();
		//校验唯一：机构全称、机构简称、机构账号、邮箱、机构信用代码、工商执照编码是否存在
		int count = dao.checkOrgOrgan(orgOrgan);
		if(count>0){
//			ret.put("ResultSign", "Error");
//			ret.put("MessageKey", "已存在，不能修改");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
			OrgOrgan o = getByOrgOrganId(orgOrgan);
			dao.updateOrgOrgan(orgOrgan);
			OrgOrgan orgOrganInfo = dao.getByOrgOrganId(orgOrgan.getId());
			//修改  信用额度
			// 当下期信用额度为空并且此次修改的额度与使用的信用额度不相等，或者下期额度不为空并且此次修改的额度与下期额度不相等时才更新
			if (orgOrganInfo.getNextLineOfCredit() == null && orgOrgan.getLineOfCredit().doubleValue() != orgOrganInfo.getLineOfCredit().doubleValue() || orgOrganInfo.getNextLineOfCredit() != null && orgOrgan.getLineOfCredit().doubleValue() != orgOrganInfo.getNextLineOfCredit().doubleValue()) {
				OrgOrganCompanyRef orgOrganCompanyRef = new OrgOrganCompanyRef();
				orgOrganCompanyRef.setOrganId(orgOrgan.getId());
				orgOrganCompanyRef.setCompanyId(orgOrgan.getCompanyId());
				orgOrganCompanyRef.setNextLineOfCredit(orgOrgan.getLineOfCredit());
				updateLineOfCredit(orgOrganCompanyRef);
				// 插入信用额度变更记录
				insertOrganCreditRecord(orgOrganInfo,orgOrgan);
			}
			//修改操作：保存成功时，需要对应产生“机构账户”的相关信息
			if((orgOrgan.getAccount()!=null && !orgOrgan.getAccount().equals("")) || (orgOrgan.getPhone()!=null && !orgOrgan.getPhone().equals(""))){
//				OrgUser orgUser = new OrgUser();
//				orgUser.setOrganId(orgOrgan.getId());
//				orgUser.setAccount(orgOrgan.getAccount());
//				updateAccount(orgUser);
				if(!o.getAccount().equals(orgOrgan.getAccount()) || !o.getPhone().equals(orgOrgan.getPhone())){
					List<String> list = new ArrayList<String>();
					list.add(orgOrgan.getPhone());
					String password = UNID.getPwdDefStr();
					//"模板：“您的超级管理员账号：xxxxxxx;随机密码：888888，请使用账号密码登录（租赁公司简称）”
//					String content = "您的超级管理员账号："+orgOrgan.getAccount()+";随机密码："+password+"，请使用账号密码登录（"+pubDriverDao.getLeLeasescompany(orgOrgan.getCompanyId()).getShortName()+"）";
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.orgorganservice.addorguser", orgOrgan.getAccount(),password);
					UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
					MessageUtil.sendMessage(um);
					//修改对应的账号密码
					OrgUser ou = new OrgUser();
					ou.setId(o.getUserId());
					ou.setAccount(orgOrgan.getAccount());
					ou.setUserPassword(PasswordEncoder.encode(password));	
					orgUserDao.updateOrgUser(ou);
				}
			}
			//当为渠道客户的时候，ch_info生成一条相关信息
			if(orgOrgan.getCustomertype() != null && !orgOrgan.getCustomertype().equals("")){
				if(orgOrgan.getCustomertype().equals("1")){//渠道客户
					//根据OrganId判断ch_info是否有值
					int counts = dao.CheckChInfo(orgOrgan.getId());
					if(counts > 0){//有值
						
					}else{//没有值
						Map<String, String> map = new HashMap<String,String>();
						String id = GUIDGenerator.newGUID();
						String organid = orgOrgan.getId();
						String channelkey = MD5.MD5Encode(new StringBuffer().append(id).append(organid).toString());
						String signprivatekey = MD5.MD5Encode(new StringBuffer().append(id).append(organid).append(channelkey).toString());
						map.put("id", id);
						map.put("organid", organid);
						map.put("channelkey", channelkey);
						map.put("signprivatekey", signprivatekey);
						dao.CrerateChInfo(map);
					}
				}else{//非渠道客户
					//根据OrganId判断ch_info是否有值
					int counts = dao.CheckChInfo(orgOrgan.getId());
					if(counts > 0){
						//有值,删除OrganId下这条记录
						dao.DeleteChInfo(orgOrgan.getId());
					}
				}
			}
		}
		return ret;
	}
	
//	public void updateAccount(OrgUser orgUser){
//		dao.updateAccount(orgUser);
//	};
	
	public OrgOrgan getByOrgOrganId(OrgOrgan orgOrgan) {
//		int count = checkOrganbill(orgOrgan);
		OrgOrgan o = dao.getByOrgOrganId(orgOrgan.getId());
//		if(count>0){
//			//有账单  有账单  就返回  原有的信用额度
//			return o;
//		}else{
//			if(o.getNextLineOfCredit() == null || o.getNextLineOfCredit().equals("")){
//				o.setLineOfCredit(o.getLineOfCredit());
//			}else{
//				o.setLineOfCredit(o.getNextLineOfCredit());
//			}
//			return o;
//		}
//		if(o.getNextLineOfCredit() != null){
//			o.setLineOfCredit(o.getNextLineOfCredit());
//		}
		return o;
	}
	
	public int checkOrganbill(OrgOrgan orgOrgan){
		return dao.checkOrganbill(orgOrgan);
	};
	
	public Map<String, String> reassignProducts(OrgOrganCompanyRef orgOrganCompanyRef) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "分配成功");
		orgOrganCompanyRefDao.reassignProducts(orgOrganCompanyRef);
		return ret;
	}
	
	public Map<String, String> resetPassword(String id) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "重置成功");
		OrgUser orgUserId = orgUserDao.getOrgUserId(id);
		OrgUser orgUser = new OrgUser();
		orgUser.setId(orgUserId.getId());
		String password = UNID.getUNID(null, 6);
		orgUser.setUserPassword(PasswordEncoder.encode(password));
		orgUserDao.resetPassword(orgUser);
		//"模板：“您的超级管理员账号xxxxxx的密码已重置为：{X}，请使用新密码登录（租赁公司简称）”
		//注：X密码是随机生成"
		List<String> list = new ArrayList<String>();
		OrgOrgan o = new OrgOrgan();
		o.setId(id);
		OrgOrgan orgOrgan = getByOrgOrganId(o);
		list.add(orgOrgan.getPhone());
//		String content = "您的超级管理员账号"+orgOrgan.getAccount()+"的密码已重置为："+password+"，请使用新密码登录（"+pubDriverDao.getLeLeasescompany(orgOrgan.getCompanyId()).getShortName()+"）";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.orgorganservice.resetpassword", orgOrgan.getAccount(),password);
		UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
		return ret;
	}
	
	public PageBean getOrgOrganByQuery(OrgOrganQueryParam orgOrganQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(orgOrganQueryParam.getsEcho());
		List<OrgOrgan> list = getOrgOrganListByQuery(orgOrganQueryParam);
		for(OrgOrgan o:list){
			if(o.getBillType() != null){
				if(o.getBillType().equals("1")){//季结
					o.setBillType("季结");
					o.setBillDateShow("每季"+o.getBillDate()+"号");
				}else{//月结
					o.setBillType("月结");
					o.setBillDateShow("每月"+o.getBillDate()+"号");
				}
			}
		}
		int iTotalRecords = getOrgOrganListCountByQuery(orgOrganQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<OrgOrgan> getOrgOrganListByQuery(OrgOrganQueryParam orgOrganQueryParam) {
		return dao.getOrgOrganListByQuery(orgOrganQueryParam);
	}

	public int getOrgOrganListCountByQuery(OrgOrganQueryParam orgOrganQueryParam) {
		return dao.getOrgOrganListCountByQuery(orgOrganQueryParam);
	}
	
	public List<Map<String, Object>> getOrgOrganShortName(OrgOrganQueryParam orgOrganQueryParam){
		return dao.getOrgOrganShortName(orgOrganQueryParam);
	};
	
	public List<Map<String, Object>> getOrgOrganCity(OrgOrganQueryParam orgOrganQueryParam){
		return dao.getOrgOrganCity(orgOrganQueryParam);
	};
	
	public List<OrgOrgan> exportData(OrgOrganQueryParam orgOrganQueryParam){
		List<OrgOrgan> list =  dao.exportData(orgOrganQueryParam);
		for(OrgOrgan o : list){
			if(o.getNextLineOfCredit() != null){
				o.setLineOfCredit(o.getNextLineOfCredit());
			}
		}
		return list;
	};
	
	public int getFristTime(OrgOrgan orgOrgan){
		return dao.getFristTime(orgOrgan);
	}
	
	public List<Map<String, Object>> getOrgOrganByName(HashMap<String, Object> params) {
		return dao.getOrgOrganByName(params);
	}
	
	public void updateLineOfCredit(OrgOrganCompanyRef orgOrganCompanyRef){
		dao.updateLineOfCredit(orgOrganCompanyRef);
	};
	
	public PubCityAddr getCityId(String cityName){
		return dao.getCityId(cityName);
	};
	
	public OrgOrgan getCreateFristData(String id){
		return dao.getCreateFristData(id);
	};
	
	public PageBean getOrganCreditRecord(OrgOrganQueryParam orgOrganQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(orgOrganQueryParam.getsEcho());
		List<OrgOrganCreditRecord> list = getOrganCreditRecordList(orgOrganQueryParam);
		int iTotalRecords = getOrganCreditRecordListCount(orgOrganQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<OrgOrganCreditRecord> getOrganCreditRecordList(OrgOrganQueryParam orgOrganQueryParam) {
		return dao.getOrganCreditRecordList(orgOrganQueryParam);
	}

	public int getOrganCreditRecordListCount(OrgOrganQueryParam orgOrganQueryParam) {
		return dao.getOrganCreditRecordListCount(orgOrganQueryParam);
	}
	
	public void insertOrganCreditRecord(OrgOrgan orgOrganInfo,OrgOrgan orgOrgan) {
		OrgOrganCreditRecord orgOrganCreditRecord = new OrgOrganCreditRecord();
		orgOrganCreditRecord.setId(GUIDGenerator.newGUID());
		orgOrganCreditRecord.setOrganId(orgOrgan.getId());
		orgOrganCreditRecord.setLeasesCompanyId(orgOrgan.getCompanyId());
		orgOrganCreditRecord.setOldCredit(orgOrganInfo.getLineOfCredit());
		orgOrganCreditRecord.setCurrentCredit(orgOrgan.getLineOfCredit());
		orgOrganCreditRecord.setCreater(orgOrgan.getUpdater());
		orgOrganCreditRecord.setUpdater(orgOrgan.getUpdater());
		dao.insertOrganCreditRecord(orgOrganCreditRecord);
	}
	
	public int checkOrgOrganAccout(OrgOrganQueryParam orgOrganQueryParam){
		return dao.checkOrgOrganAccout(orgOrganQueryParam);
	};
}
