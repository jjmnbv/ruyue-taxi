package com.szyciov.lease.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganCreditRecord;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.org.entity.OrgUser;

public interface OrgOrganMapper {

	void createOrgOrgan(OrgOrgan orgOrgan);

	void updateOrgOrgan(OrgOrgan orgOrgan);
	
	int checkOrgOrgan(OrgOrgan orgOrgan);
	
	OrgOrgan getByOrgOrganId(String id);
	
	List<OrgOrgan> getOrgOrganListByQuery(OrgOrganQueryParam orgOrganQueryParam);

	int getOrgOrganListCountByQuery(OrgOrganQueryParam orgOrganQueryParam);
	
	List<Map<String, Object>> getOrgOrganShortName(OrgOrganQueryParam orgOrganQueryParam);
	
	List<Map<String, Object>> getOrgOrganCity(OrgOrganQueryParam orgOrganQueryParam);
	
	List<OrgOrgan> exportData(OrgOrganQueryParam orgOrganQueryParam);
	
	int getFristTime(OrgOrgan orgOrgan);
	
	List<Map<String, Object>> getOrgOrganByName(HashMap<String, Object> params);
	
	int checkAccount(OrgOrgan orgOrgan);
	
	void updateAccount(OrgUser orgUser);
	
	void updateLineOfCredit(OrgOrganCompanyRef orgOrganCompanyRef);
	
	PubCityAddr getCityId(String cityName);
	
	int checkOrganbill(OrgOrgan orgOrgan);
	
	OrgOrgan getCreateFristData(String id);
	
	List<OrgOrganCreditRecord> getOrganCreditRecordList(OrgOrganQueryParam orgOrganQueryParam);

	int getOrganCreditRecordListCount(OrgOrganQueryParam orgOrganQueryParam);
	
	void insertOrganCreditRecord(OrgOrganCreditRecord orgOrganCreditRecord);
	
	int checkOrgOrganAccout(OrgOrganQueryParam orgOrganQueryParam);
	
	void CrerateChInfo(Map<String,String> map);
	
	int CheckChInfo(String id);
	
	void DeleteChInfo(String id);
}