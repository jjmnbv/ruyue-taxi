package com.szyciov.operate.mapper;

import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.op.param.PubDriverVehicleBindQueryParam;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PubDriverVehicleRefMapper {
	
    int deleteByPrimaryKey(@Param(value = "id") String id);

    int save(PubDriverVehicleRef pubDriverVehicleRef);

    PubDriverVehicleRef selectByPrimaryKey(@Param(value = "id") String id);

    int updateByPrimaryKey(PubDriverVehicleRef pubDriverVehicleRef);


    List<VehicleBindInfoDto> listVehicleBindInfo(@Param(value = "vehicleid") String vehicleid,
                                                 @Param(value = "driverid") String driverid);


    int getRefCount(@Param(value = "driverid") String driverId, @Param(value = "vehicleid") String vcehicleId);
    
     
    
    List<Map<String, Object>> getDriverByNameOrPhone(Map<String, String> map);
    
    List<Map<String, Object>> getDriverByJobnum(Map<String, String> map);
    
    List<Map<String, Object>> getDriverInfoListByQuery(PubDriverVehicleRefQueryParam queryParam);
    
    int getDriverInfoListCountByQuery(PubDriverVehicleRefQueryParam queryParam);
    
    List<Map<String, Object>> getVehiclemodels();
    
    List<Map<String, Object>> getCityaddr();
    
    List<Map<String, Object>> getVehcbrand(@Param(value = "vehcbrandname") String vehcbrandname);
    
    
    List<Map<String, Object>> getVehcbrandByCity(Map<String, String> map);
    
    List<Map<String, Object>> getUnbandCarsListByCity(PubDriverVehicleRefQueryParam queryParam);
    
    int getUnbandCarsListCountByCity(PubDriverVehicleRefQueryParam queryParam);
    
    void updateDriverWorkStatus(Map<String, String> map);
    
    void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind);
    
    void createPubDriverVehicleRef(PubDriverVehicleRef pubDriverVehicleRef);
    
    void updateVehicleBindState(Map<String, Object> map);
    
    int getUncompleteCountByDriverId(Map<String, String> map);
    
    Map<String, String> getBoundStateByVehicleId(@Param(value = "id") String id);
    
    Map<String, Object> getDriverByDriverId(@Param(value = "id") String id);
    
    void updatePubDriverVehicleRef(Map<String, String> map);
    
    List<Map<String, Object>> getDriverOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam);
    
    int getDriverOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam);
       
    
    List<Map<String, Object>> getVehicleOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam);
    
    int getVehicleOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam);
    
    List<Map<String, Object>> getPlatenoByPlateno(@Param(value = "plateno") String plateno);
    
    List<Map<String, Object>> getVehicleVinByVin(@Param(value = "vin") String vin);

    Map<String, Object> getOpPlatformInfo();
}