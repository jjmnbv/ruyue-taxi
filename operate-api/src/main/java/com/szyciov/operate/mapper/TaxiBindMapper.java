package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.op.param.TaxiBindQueryParam;

public interface TaxiBindMapper {
	
	List<Map<String, Object>> getVehicleInfoListByQuery(TaxiBindQueryParam queryParam);
	
	int getVehicleInfoListCountByQuery(TaxiBindQueryParam queryParam);
	
	List<Map<String, Object>> getCityaddr();
	
	List<Map<String, Object>> getOndutyDriver(@Param(value = "driver") String driver);
	
	List<Map<String, Object>> getVehcbrandVehcline(@Param(value = "vehcbrandname") String vehcbrandname);
		
	List<Map<String, Object>> getDriverByNameOrPhone(Map<String, String> map);
	
	List<Map<String, Object>> getDriverByJobnum(Map<String, String> map);
	
	List<Map<String, Object>> getUnbindDriverListByQuery(PubDriverVehicleRefQueryParam queryParam);
	
	int getUnbindDriverListCountByQuery(PubDriverVehicleRefQueryParam queryParam);
	
	void updateDriverWorkStatus(Map<String, Object> map);
	
	void updateVehicleBindState(Map<String, Object> map);
	
	void updateVehicleBindStateAllUnbind(Map<String, String> map);
	
	void updateDriverid(Map<String, String> map);
	
	void createPubDriverVehicleRef(PubDriverVehicleRef pubDriverVehicleRef);
	
	void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind);
	
	int checkBindstate(Map<String, String> map);
	
	List<Map<String, Object>> getDriverByVehicleid(String vehicleid);
		
	List<Map<String, Object>> getBindDriverByVehicleid(String vehicleid);
	
	void updatePubDriverVehicleRef(Map<String, String> map);
	
	Map<String, Object> getBindDriverStateByVehicleid(Map<String, String> map);
	
	Map<String, Object> getDriverByDriverId(String id);
	
	int getUncompleteCountByDriverId(Map<String, String> map);	
	
	List<Map<String, Object>> getOperateRecordListByVehicleid(TaxiBindQueryParam queryParam);
	
	int getOperateRecordListCountByVehicleid(TaxiBindQueryParam queryParam);
	
	List<Map<String, Object>> listVehicleBindInfoOfOnline(String vehicleid);
	
	void createDrivershiftProcessed(PeDrivershiftProcessed peDrivershiftProcessed);
	
	Map<String, Object> getOpPlatformInfo();
	
	Map<String, Object> getVehicleByVehicleId(String vehicleid);
	
	void updateOtherDriverWorkStatus(Map<String, String> map);
	
	PeDrivershiftPending getPendingInfo(PeDrivershiftPending peDrivershiftPending);
	
	int removeById(String id);
}
