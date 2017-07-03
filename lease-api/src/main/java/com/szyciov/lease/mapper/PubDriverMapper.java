package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.lease.param.ServiceOrgQueryParam;
import com.szyciov.org.entity.OrgOrganDriverRef;
import com.szyciov.org.entity.OrgUser;

public interface PubDriverMapper {

	void createPubDriver(PubDriver pubDriver);
	
	int checkPubDriver(PubDriver pubDriver);
	
	PubDriver getByPubDriverId(String id);
	
	void updatePubDriver(PubDriver pubDriver);
	
	void resetPassword(PubDriver pubDriver);
	
	int checkDelete(String id);
	
	void deletePubDriver(String id);
	
	List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam);
	
	List<PubDriver> getPubDriverListByBound(PubDriverQueryParam pubDriverQueryParam);
	
	int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam);
	
	PubDriver getVehicleInfo(String id);
	
	List<OrgOrgan> getServiceOrgListByQuery(ServiceOrgQueryParam serviceOrgQueryParam);
	
	int getServiceOrgListCountByQuery(ServiceOrgQueryParam serviceOrgQueryParam);
	
	List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam);
	
	PubDriver getById(String id);
	
	int judgeBinding(String id);
	
//	PubDriver unwrapVel(String id);
	
//	int judgeUnwrapRecord(String id);
	
	void createOrgOrganDriverRef(OrgOrganDriverRef orgOrganDriverRef);
	
	List<OrgOrganDriverRef> getOrgOrganDriverRef(String id);
	
	void deleteOrgOrganDriverRef(String id);
	
	List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam);
	
	List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName);
	
	List<PubDriver> exportData(PubDriverQueryParam pubDriverQueryParam);
	
	int checkPubDriverPhone(PubDriver pubDriver);
	
	LeLeasescompany getLeLeasescompany(String id);
	
	int checkUnbundling(String driverId);
	
	OrgUser getOrgUserUsertype(String id);


	int updatePubDriverStatus(PubDriver driver);


	int cleanPubDriverOnlineTime (PubDriver driver);
	
	List<Map<String, Object>> getQueryJobNum(PubDriverQueryParam pubDriverQueryParam);
}