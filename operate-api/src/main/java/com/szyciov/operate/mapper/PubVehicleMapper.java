package com.szyciov.operate.mapper;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.entity.PubVehicleScope;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.op.vo.vehiclemanager.VehicleExportVo;
import com.szyciov.op.vo.vehiclemanager.VehicleIndexVo;
import com.szyciov.operate.dto.VehicleQueryDto;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface PubVehicleMapper {

	void createPubVehicle(PubVehicle pubVehicle);
	
	int updatePubVehicle(PubVehicle pubVehicle);
	
	int checkPubVehicle(PubVehicle pubVehicle);
	
	void deletePubVehicle(String id);
	
	int checkDelete(@Param("id") String id,@Param("platformType") String platformType);

	List<VehicleIndexVo> getPubVehicleListByQuery(VehicleIndexQueryParam param);
	
	int getPubVehicleListCountByQuery(VehicleIndexQueryParam param);
	
	List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle); 
	
	List<PubVehicle> getServiceCars(String leasesCompanyId);
	
	List<City> getCity(@Param("queryText")String queryText,@Param("platformType")String platformType);
	
	PubVehicle getById(String id);
	
	List<City> getPubCityaddr();
	
	List<Dictionary> getPlateNoProvince();
	
	List<Dictionary> getPlateNoCity(String id);
	
	List<VehicleExportVo> exportExcel(VehicleIndexQueryParam param);
	
	PubDictionary getPlateCode(String plateOne);
	
	String getPlateCity(PubDictionary plateTow);
	
	PubCityAddr getCityCode(String city);
	
	String getVehclineId(PubVehicle vehcline);

	List<VehicleQueryDto> listVehicleAndBindInfo(PubVehicleQueryParam pubVehicleQueryParam);

	Integer getlistVehicleAndBindInfoCount(PubVehicleQueryParam pubVehicleQueryParam);
	List<PubVehicleScope> getVehicleidByVehicleScope(String id);

	int updateVehicleById(PubVehicle pubVehicle);

	int updateDriverId(PubVehicle pubVehicle);

    List<Map<String, Object>> getPubVehicleSelectByUser(Map<String, Object> param);

}