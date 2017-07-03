package com.szyciov.carservice.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.MessagePubInfoMapper;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PushMessageLog;

@Repository("MessagePubInfoDao")
public class MessagePubInfoDao {
	
	private MessagePubInfoMapper mapper;

	@Resource
	public void setMessagePubInfoMapper(MessagePubInfoMapper mapper) {
		this.mapper = mapper;
	}

	public String getDriverPhoneById(String driverid) {
		return mapper.getDriverPhoneById(driverid);
	}

	public LeasesCompany getLeaseCompanyById(String companyid) {
		return mapper.getLeaseCompanyById(companyid);
	}

	public OrgUser getOrgUserById(String userid) {
		return mapper.getOrgUserById(userid);
	}
	
	public Map<String, Object> getOrgUserInfo(String userid) {
		return mapper.getOrgUserInfo(userid);
	}
	
	public DriverInfo getDriverInfoById(String driverid) {
		return mapper.getDriverInfoById(driverid);
	}
	
	public Map<String, Object> getDriverInfo(String driverid) {
		return mapper.getDriverInfo(driverid);
	}

	public Map<String, Object> getCarInfo(String vehicleid) {
		return mapper.getCarInfo(vehicleid);
	}

	public Map<String, Object> getOpInfo() {
		return mapper.getOpInfo();
	}

	public Map<String, Object> getOpUserInfo(String userid) {
		return mapper.getOpUserInfo(userid);
	}

	public void savePushMessage(PushMessageLog pushLog) {
		mapper.savePushMessage(pushLog);
	}
}
