package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.op.param.PubDriverVehicleBindQueryParam;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.operate.mapper.PubDriverVehicleRefMapper;

/**
 * 人车绑定信息dao
 */
@Service
public class PubDriverVehicleRefDao {
	
    @Autowired
    private PubDriverVehicleRefMapper refMapper;

    public int save(PubDriverVehicleRef pubDriverVehicleRef){
        return refMapper.save(pubDriverVehicleRef);
    }

    public int deleteByPrimaryKey(String id){
        return refMapper.deleteByPrimaryKey(id);
    }


    public List<VehicleBindInfoDto> listVehicleBindInfo(String vehicleid,String driverId){
        return refMapper.listVehicleBindInfo(vehicleid,driverId);
    }

    public int getRefCount(String driverId,String vcehicleId){
        return refMapper.getRefCount(driverId,vcehicleId);
    }
    
    
    
    
    public List<Map<String, Object>> getDriverByNameOrPhone(Map<String, String> map) {
    	return refMapper.getDriverByNameOrPhone(map);
    }
    
    public List<Map<String, Object>> getDriverByJobnum(Map<String, String> map) {
    	return refMapper.getDriverByJobnum(map);
    }
    
    public List<Map<String, Object>> getDriverInfoListByQuery(PubDriverVehicleRefQueryParam queryParam) {
    	return refMapper.getDriverInfoListByQuery(queryParam);
    }
    
    public int getDriverInfoListCountByQuery(PubDriverVehicleRefQueryParam queryParam) {
    	return refMapper.getDriverInfoListCountByQuery(queryParam);
    }
    
    public List<Map<String, Object>> getVehiclemodels() {
    	return refMapper.getVehiclemodels();
    }
    
    public List<Map<String, Object>> getCityaddr() {
    	return refMapper.getCityaddr();
    }
    
    public List<Map<String, Object>> getVehcbrand(String vehcbrandname) {
    	return refMapper.getVehcbrand(vehcbrandname);
    }

    public List<Map<String, Object>> getVehcbrandByCity(Map<String, String> map) {
    	return refMapper.getVehcbrandByCity(map);
    }
    
    public List<Map<String, Object>> getUnbandCarsListByCity(PubDriverVehicleRefQueryParam queryParam) {
    	return refMapper.getUnbandCarsListByCity(queryParam);
    }
    
    public int getUnbandCarsListCountByCity(PubDriverVehicleRefQueryParam queryParam) {
    	return refMapper.getUnbandCarsListCountByCity(queryParam);
    }
    
    public void updateDriverWorkStatus(Map<String, String> map) {
    	refMapper.updateDriverWorkStatus(map);
    }
    
    public void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind) {
    	refMapper.createPubDriverVehicleBind(pubDriverVehicleBind);
    }
    
    public void createPubDriverVehicleRef(PubDriverVehicleRef pubDriverVehicleRef) {
    	refMapper.createPubDriverVehicleRef(pubDriverVehicleRef);
    }
    
    public void updateVehicleBindState(Map<String, Object> map) {
    	refMapper.updateVehicleBindState(map);
    }
    
    public int getUncompleteCountByDriverId(Map<String, String> map) {
    	return refMapper.getUncompleteCountByDriverId(map);
    }
    
    public Map<String, String> getBoundStateByVehicleId(String id) {
    	return refMapper.getBoundStateByVehicleId(id);
    }
    
    public Map<String, Object> getDriverByDriverId(String id) {
    	return refMapper.getDriverByDriverId(id);
    }
    
    public void updatePubDriverVehicleRef(Map<String, String> map) {
    	refMapper.updatePubDriverVehicleRef(map);
    }
    
    public List<Map<String, Object>> getDriverOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refMapper.getDriverOpRecordListByQuery(queryParam);
    }
    
    public int getDriverOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refMapper.getDriverOpRecordListCountByQuery(queryParam);
    }
    
    public List<Map<String, Object>> getVehicleOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refMapper.getVehicleOpRecordListByQuery(queryParam);
    }
    
    public int getVehicleOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refMapper.getVehicleOpRecordListCountByQuery(queryParam);
    }
    
    public List<Map<String, Object>> getPlatenoByPlateno(String plateno) {
    	return refMapper.getPlatenoByPlateno(plateno);
    }
    
    public List<Map<String, Object>> getVehicleVinByVin(String vin) {
    	return refMapper.getVehicleVinByVin(vin);
    }
    
    public Map<String, Object> getOpPlatformInfo() {
    	return refMapper.getOpPlatformInfo();
    }
    
}