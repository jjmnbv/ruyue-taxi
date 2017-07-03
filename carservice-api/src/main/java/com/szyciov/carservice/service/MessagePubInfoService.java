package com.szyciov.carservice.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.MessagePubInfoDao;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PushMessageLog;

@Service("MessagePubInfoService")
public class MessagePubInfoService {
	
	private MessagePubInfoDao dao;
	
	@Resource(name="MessagePubInfoDao")
	public void setMessagePubInfoDao(MessagePubInfoDao dao) {
		this.dao = dao;
	}
	
	public String getDriverPhoneById(String driverid) {
		return dao.getDriverPhoneById(driverid);
	}

	public LeasesCompany getLeaseCompanyById(String companyid) {
		return dao.getLeaseCompanyById(companyid);
	}
	
	public OrgUser getOrgUserById(String userid){
		return dao.getOrgUserById(userid);
	}
	
	public Map<String, Object> getOrgUserInfo(String userid) {
		return dao.getOrgUserInfo(userid);
	}

	public DriverInfo getDriverInfoById(String driverid) {
		return dao.getDriverInfoById(driverid);
	}
	
	public Map<String, Object> getDriverInfo(String driverid) {
		return dao.getDriverInfo(driverid);
	}

	public Map<String, Object> getCarInfo(String vehicleid) {
		return dao.getCarInfo(vehicleid);
	}

	public Map<String, Object> getOpInfo() {
		return dao.getOpInfo();
	}

	public Map<String, Object> getOpUserInfo(String userid) {
		return dao.getOpUserInfo(userid);
	}

	public Map<String, String> savePushMessage(PushMessageLog pushLog) {
		dao.savePushMessage(pushLog);
		Map<String,String> result=new HashMap<>();
		result.put("status", "0");
		return result;
	}
}
