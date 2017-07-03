package com.szyciov.operate.mapper;


import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.param.PubDriverQueryParam;
import com.szyciov.op.vo.pubdriver.ExportDriverVo;
import com.szyciov.org.entity.OrgOrganDriverRef;

import java.util.List;
import java.util.Map;

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
	

	List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam);
	
	PubDriver getById(String id);
	
	int judgeBinding(String id);

	List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam);
	
	List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName);
	
	List<ExportDriverVo> exportData(PubDriverQueryParam pubDriverQueryParam);
	
	int checkPubDriverPhone(PubDriver pubDriver);
	

	int checkUnbundling(String driverId);
	
	int updatePubDriverStatus(PubDriver driver);

	int cleanPubDriverOnlineTime (PubDriver driver);
}