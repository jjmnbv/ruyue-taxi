package com.szyciov.operate.mapper;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.*;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.param.LeLeasescompanyQueryParam;
import com.szyciov.op.entity.PubDriver;
import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.PubVehicleScope;
public interface LeLeasescompanyMapper {
    
	List<LeLeasescompany> getLeLeasescompanyListByQuery(LeLeasescompanyQueryParam queryParam);
	
	int getLeLeasescompanyListCountByQuery(LeLeasescompanyQueryParam queryParam);
	
	List<LeLeasescompany> getNameOrCity(LeLeasescompanyQueryParam queryParam);
	
	List<LeLeasescompany> getCityOrName(LeLeasescompanyQueryParam queryParam);
	
	void enable(String id);
	
	void disable(String id);
	
	LeLeasescompany getById(String id);
	
	void disableToc(String id);
	
	void examineToc(String id);
	
	void resetPassword(User user);
	
	List<City> getPubCityaddr();
	
	int checkNameOrShortName(LeLeasescompany leLeasescompany);
	
	void createLeLeasescompany(LeLeasescompany leLeasescompany);
	
	void createLeUser(User user);
	
	void updateLeLeasescompany(LeLeasescompany leLeasescompany);
	
	List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam);
	
	int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam);
	
	PubDriver getVehicleInfo(String id);
	
	List<PubVehicleScope> getPubVehicleScope(String id);
	
	List<Dictionary> getDictionaryByType(String queryType);
	
	List<PubDriver> getCity(String leasesCompanyId);
	
	PubDriver getByIdPubDriver(String id);
	
	List<OpVehiclemodels> getOpVehiclemodels();
	
	void createOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef);
	
	void updateOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef);
	
	int checkOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef);
	
	List<LeLeasescompany> exportData(LeLeasescompanyQueryParam leLeasescompanyQueryParam);
	
	void updateLeUser (User user);
	
	PubCityAddr getCityId(String cityName);
	
	void updateLeUsers(User user);
	
	LeLeasescompany getCreateFristData();
	
	void deleteVehiclemodelsid(String id);

	LeLeasescompany getLeLeasescompanyById(String leaseid);
	
	List<Map<String, Object>> getQueryKeyword(PubDriverQueryParam pubDriverQueryParam);
	
	int checkLeLeasescompany();
}
