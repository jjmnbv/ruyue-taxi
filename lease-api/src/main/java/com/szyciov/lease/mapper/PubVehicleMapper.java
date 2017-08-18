package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.dto.VehicleQueryDto;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.entity.PubVehicleScope;
import com.szyciov.lease.param.PubVehicleQueryParam;

public interface PubVehicleMapper {

	void createPubVehicle(PubVehicle pubVehicle);
	
	void updatePubVehicle(PubVehicle pubVehicle);
	
	int checkPubVehicle(PubVehicle pubVehicle);
	
	void deletePubVehicle(String id);
	
	int checkDelete(String id);
	
	List<PubVehicle> getPubVehicleListByQuery(PubVehicleQueryParam pubVehicleQueryParam);
	
	int getPubVehicleListCountByQuery(PubVehicleQueryParam pubVehicleQueryParam);
	
	List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle); 
	
	List<PubVehicle> getServiceCars(String leasesCompanyId);
	
	List<PubVehicle> getCity(String leasesCompanyId);
	
	PubVehicle getById(String id);
	
	List<City> getPubCityaddr();
	
	List<Dictionary> getPlateNoProvince();
	
	List<Dictionary> getPlateNoCity(String id);
	
	List<PubVehicle> exportExcel(PubVehicleQueryParam pubVehicleQueryParam);
	
	PubDictionary getPlateCode(String plateOne);
	
	String getPlateCity(PubDictionary plateTow);
	
	PubCityAddr getCityCode(String city);
	
	String getVehclineId(PubVehicle vehcline);

	List<VehicleQueryDto> listVehicleAndBindInfo(PubVehicleQueryParam pubVehicleQueryParam);

	Integer getlistVehicleAndBindInfoCount(PubVehicleQueryParam pubVehicleQueryParam);
	
	List<PubVehicleScope> getVehicleidByVehicleScope(List<PubVehicle> list);

	int updateVehicleById(PubVehicle pubVehicle);

	int updateDriverId(PubVehicle pubVehicle);

}