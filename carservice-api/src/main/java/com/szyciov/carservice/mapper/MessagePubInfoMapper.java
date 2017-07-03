package com.szyciov.carservice.mapper;

import java.util.Map;

import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PushMessageLog;

public interface MessagePubInfoMapper {
	
	public String getDriverPhoneById(String driverid);

	public LeasesCompany getLeaseCompanyById(String companyid);

	public OrgUser getOrgUserById(String userid);
	
	public Map<String, Object> getOrgUserInfo(String userid);

	public DriverInfo getDriverInfoById(String driverid);
	
	public Map<String, Object> getDriverInfo(String driverid);

	public Map<String, Object> getCarInfo(String vehicleid);

	public Map<String, Object> getOpInfo();

	public Map<String, Object> getOpUserInfo(String userid);

	public void savePushMessage(PushMessageLog pushLog);
}
