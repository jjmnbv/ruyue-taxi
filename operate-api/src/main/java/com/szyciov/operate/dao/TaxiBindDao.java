package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.op.param.TaxiBindQueryParam;
import com.szyciov.operate.mapper.TaxiBindMapper;

@Repository("TaxiBindDao")
public class TaxiBindDao {
	public TaxiBindDao() {
	}

	private TaxiBindMapper mapper;

	@Resource
	public void setMapper(TaxiBindMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<Map<String, Object>> getVehicleInfoListByQuery(TaxiBindQueryParam queryParam) {
    	return mapper.getVehicleInfoListByQuery(queryParam);
    }
	
    public int getVehicleInfoListCountByQuery(TaxiBindQueryParam queryParam) {
    	return mapper.getVehicleInfoListCountByQuery(queryParam);
    }
	
    public List<Map<String, Object>> getCityaddr() {
    	return mapper.getCityaddr();
    }
	
    public List<Map<String, Object>> getOndutyDriver(String driver) {
    	return mapper.getOndutyDriver(driver);
    }
	
    public List<Map<String, Object>> getVehcbrandVehcline(String vehcbrandname) {
    	return mapper.getVehcbrandVehcline(vehcbrandname);
    }
       
    public List<Map<String, Object>> getDriverByNameOrPhone(Map<String, String> map) {
    	return mapper.getDriverByNameOrPhone(map);
    }
	
	public List<Map<String, Object>> getDriverByJobnum(Map<String, String> map) {
		return mapper.getDriverByJobnum(map);
	}
	
	public List<Map<String, Object>> getUnbindDriverListByQuery(PubDriverVehicleRefQueryParam queryParam) {
		return mapper.getUnbindDriverListByQuery(queryParam);
	}
	
	public int getUnbindDriverListCountByQuery(PubDriverVehicleRefQueryParam queryParam) {
		return mapper.getUnbindDriverListCountByQuery(queryParam);
	}
	
	public void updateDriverWorkStatus(Map<String, Object> map) {
		mapper.updateDriverWorkStatus(map);
	}
	
	public void updateVehicleBindState(Map<String, Object> map) {
		mapper.updateVehicleBindState(map);
	}
	
	public void updateVehicleBindStateAllUnbind(Map<String, String> map) {
		mapper.updateVehicleBindStateAllUnbind(map);
	}
	
	public void updateDriverid(Map<String, String> map) {
		mapper.updateDriverid(map);
	}
	
	public void createPubDriverVehicleRef(PubDriverVehicleRef pubDriverVehicleRef) {
		mapper.createPubDriverVehicleRef(pubDriverVehicleRef);
	}
	
	public void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind) {
		mapper.createPubDriverVehicleBind(pubDriverVehicleBind);
	}
	
	public int checkBindstate(Map<String, String> map) {
		return mapper.checkBindstate(map);
	}
    
	public List<Map<String, Object>> getDriverByVehicleid(String vehicleid) {
		return mapper.getDriverByVehicleid(vehicleid);
	}
	
    public List<Map<String, Object>> getBindDriverByVehicleid(String vehicleid) {
    	return mapper.getBindDriverByVehicleid(vehicleid);
    }
	
    public void updatePubDriverVehicleRef(Map<String, String> map) {
    	mapper.updatePubDriverVehicleRef(map);
    }
	
    public Map<String, Object> getBindDriverStateByVehicleid(Map<String, String> map) {
    	return mapper.getBindDriverStateByVehicleid(map);
    }
    
    public Map<String, Object> getDriverByDriverId(String id) {
    	return mapper.getDriverByDriverId(id);
    }
    
    public int getUncompleteCountByDriverId(Map<String, String> map) {
    	return mapper.getUncompleteCountByDriverId(map);
    }
    
    public List<Map<String, Object>> getOperateRecordListByVehicleid(TaxiBindQueryParam queryParam) {
    	return mapper.getOperateRecordListByVehicleid(queryParam);
    }
	
    public int getOperateRecordListCountByVehicleid(TaxiBindQueryParam queryParam) {
    	return mapper.getOperateRecordListCountByVehicleid(queryParam);
    }
    
    public List<Map<String, Object>> listVehicleBindInfoOfOnline(String vehicleid) {
    	return mapper.listVehicleBindInfoOfOnline(vehicleid);
    }
    
    public void createDrivershiftProcessed(PeDrivershiftProcessed peDrivershiftProcessed) {
    	mapper.createDrivershiftProcessed(peDrivershiftProcessed);
    }
    
    public Map<String, Object> getOpPlatformInfo() {
    	return mapper.getOpPlatformInfo();
    }
    
    public Map<String, Object> getVehicleByVehicleId(String vehicleid) {
    	return mapper.getVehicleByVehicleId(vehicleid);
    }
    
    public void updateOtherDriverWorkStatus(Map<String, String> map) {
    	mapper.updateOtherDriverWorkStatus(map);
    }
    
    public PeDrivershiftPending getPendingInfo(PeDrivershiftPending peDrivershiftPending) {
        return mapper.getPendingInfo(peDrivershiftPending);
    }
    
    public int removeById(String id) {
        return mapper.removeById(id);
    }
}
