package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.param.LeLeasescompanyQueryParam;
import com.szyciov.operate.mapper.LeLeasescompanyMapper;
import com.szyciov.op.entity.PubVehicleScope;
@Repository("LeLeasescompanyDao")
public class LeLeasescompanyDao {
	public LeLeasescompanyDao() {
	}

	private LeLeasescompanyMapper mapper;

	@Resource
	public void setMapper(LeLeasescompanyMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<LeLeasescompany> getLeLeasescompanyListByQuery(LeLeasescompanyQueryParam queryParam){
		return mapper.getLeLeasescompanyListByQuery(queryParam);
	};
	
	public int getLeLeasescompanyListCountByQuery(LeLeasescompanyQueryParam queryParam){
		return mapper.getLeLeasescompanyListCountByQuery(queryParam);
	};
	
	public List<LeLeasescompany> getNameOrCity(LeLeasescompanyQueryParam queryParam){
		return mapper.getNameOrCity(queryParam);
	};
	
	public List<LeLeasescompany> getCityOrName(LeLeasescompanyQueryParam queryParam){
		return mapper.getCityOrName(queryParam);
	};
	
	public void enable(String id){
		mapper.enable(id);
	};
	
	public void disable(String id){
		mapper.disable(id);
	};
	
	public LeLeasescompany getById(String id){
		return mapper.getById(id);
	};
	
	public void disableToc(String id){
		mapper.disableToc(id);
	};
	
	public void examineToc(String id){
		mapper.examineToc(id);
	};
	
	public void resetPassword(User user){
		mapper.resetPassword(user);
	};
	
	public List<City> getPubCityaddr(){
		return mapper.getPubCityaddr();
	};
	
	public int checkNameOrShortName(LeLeasescompany leLeasescompany){
		return mapper.checkNameOrShortName(leLeasescompany);
	};
	
	public void createLeLeasescompany(LeLeasescompany leLeasescompany){
		mapper.createLeLeasescompany(leLeasescompany);
	};
	
	public void createLeUser(User user){
		mapper.createLeUser(user);
	};
	
	public void updateLeLeasescompany(LeLeasescompany leLeasescompany){
		mapper.updateLeLeasescompany(leLeasescompany);
	};
	
	public List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getPubDriverListByQuery(pubDriverQueryParam);
	};
	
	public int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getPubDriverListCountByQuery(pubDriverQueryParam);
	};
	
	public PubDriver getVehicleInfo(String id){
		return mapper.getVehicleInfo(id);
	};
	
	public List<PubVehicleScope> getPubVehicleScope(String id){
		return mapper.getPubVehicleScope(id);
	};
	
	public List<Dictionary> getDictionaryByType(String str) {
		return mapper.getDictionaryByType(str);
	}
	
	public List<PubDriver> getCity(String leasesCompanyId){
		return mapper.getCity(leasesCompanyId);
	};
	
	public PubDriver getByIdPubDriver(String id){
		return mapper.getByIdPubDriver(id);
	};
	
	public List<OpVehiclemodels> getOpVehiclemodels(){
		return mapper.getOpVehiclemodels();
	};
	
	public void createOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		mapper.createOpVehclineModelsRef(opVehclineModelsRef);
	};
	
	public void updateOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		mapper.updateOpVehclineModelsRef(opVehclineModelsRef);
	};
	
	public int checkOpVehclineModelsRef(OpVehiclemodelsVehicleRef opVehclineModelsRef){
		return mapper.checkOpVehclineModelsRef(opVehclineModelsRef);
	};
	
	public List<LeLeasescompany> exportData(LeLeasescompanyQueryParam leLeasescompanyQueryParam){
		return mapper.exportData(leLeasescompanyQueryParam);
	};
	
	public void updateLeUser (User user){
		mapper.updateLeUser(user);
	};
	
	public PubCityAddr getCityId(String cityName){
		return mapper.getCityId(cityName);
	};
	
	public void updateLeUsers(User user){
		mapper.updateLeUsers(user);
	};

	public LeLeasescompany getCreateFristData(){
		return mapper.getCreateFristData();
	};
	
	public void deleteVehiclemodelsid(String id){
		mapper.deleteVehiclemodelsid(id);
	};



	/**
	 * 返回租赁公司对象
	 * @param leaseid	租赁公司ID
	 * @return
	 */
	public LeLeasescompany getLeLeasescompanyById(String leaseid){
		return mapper.getLeLeasescompanyById(leaseid);
	}
	
	public List<Map<String, Object>> getQueryKeyword(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getQueryKeyword(pubDriverQueryParam);
	};
	
	public int checkLeLeasescompany(){
		return mapper.checkLeLeasescompany();
	};
}
