package com.szyciov.lease.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganCreditRecord;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.mapper.OrgOrganMapper;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.org.entity.OrgUser;

@Repository("OrgOrganDao")
public class OrgOrganDao {
	public OrgOrganDao() {
	}

	private OrgOrganMapper mapper;

	@Resource
	public void setMapper(OrgOrganMapper mapper) {
		this.mapper = mapper;
	}

	public void createOrgOrgan(OrgOrgan orgOrgan) {
		mapper.createOrgOrgan(orgOrgan);
	}

	public void updateOrgOrgan(OrgOrgan orgOrgan) {
		mapper.updateOrgOrgan(orgOrgan);
	}
	
	public int checkOrgOrgan(OrgOrgan orgOrgan) {
		return mapper.checkOrgOrgan(orgOrgan);
	}
	
	public int checkAccount(OrgOrgan orgOrgan){
		return mapper.checkAccount(orgOrgan);
	};
	
	public void updateAccount(OrgUser orgUser){
		mapper.updateAccount(orgUser);
	};
	
	public OrgOrgan getByOrgOrganId(String id) {
		return mapper.getByOrgOrganId(id);
	}
	
	public List<OrgOrgan> getOrgOrganListByQuery(OrgOrganQueryParam orgOrganQueryParam) {
		return mapper.getOrgOrganListByQuery(orgOrganQueryParam);
	}

	public int getOrgOrganListCountByQuery(OrgOrganQueryParam orgOrganQueryParam) {
		return mapper.getOrgOrganListCountByQuery(orgOrganQueryParam);
	}
	
	public List<Map<String, Object>> getOrgOrganShortName(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.getOrgOrganShortName(orgOrganQueryParam);
	};
	
	public List<Map<String, Object>> getOrgOrganCity(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.getOrgOrganCity(orgOrganQueryParam);
	};
	
	public List<OrgOrgan> exportData(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.exportData(orgOrganQueryParam);
	};
	
	public int getFristTime(OrgOrgan orgOrgan){
		return mapper.getFristTime(orgOrgan);
	}
	
	public List<Map<String, Object>> getOrgOrganByName(HashMap<String, Object> params) {
		return mapper.getOrgOrganByName(params);
	}
	
	public void updateLineOfCredit(OrgOrganCompanyRef orgOrganCompanyRef){
		mapper.updateLineOfCredit(orgOrganCompanyRef);
	};
	
	public PubCityAddr getCityId(String cityName){
		return mapper.getCityId(cityName);
	};
	
	public int checkOrganbill(OrgOrgan orgOrgan){
		return mapper.checkOrganbill(orgOrgan);
	};
	
	public OrgOrgan getCreateFristData(String id){
		return mapper.getCreateFristData(id);
	};
	
	public List<OrgOrganCreditRecord> getOrganCreditRecordList(OrgOrganQueryParam orgOrganQueryParam) {
		return mapper.getOrganCreditRecordList(orgOrganQueryParam);
	}

	public int getOrganCreditRecordListCount(OrgOrganQueryParam orgOrganQueryParam) {
		return mapper.getOrganCreditRecordListCount(orgOrganQueryParam);
	}
	
	public void insertOrganCreditRecord(OrgOrganCreditRecord orgOrganCreditRecord) {
		mapper.insertOrganCreditRecord(orgOrganCreditRecord);
	}
	
	public int checkOrgOrganAccout(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.checkOrgOrganAccout(orgOrganQueryParam);
	};
	
	public void CrerateChInfo(Map<String,String> map){
		mapper.CrerateChInfo(map);
	};
	
	public int CheckChInfo(String id){
		return mapper.CheckChInfo(id);
	};
	
	public void DeleteChInfo(String id){
		mapper.DeleteChInfo(id);
	};
}
