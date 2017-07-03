package com.szyciov.carservice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.DriverMapper;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.entity.PubDriver;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.lease.param.PubDriverSelectParam;

@Repository("driverDao")
public class DriverDao {
	
	private DriverMapper driverMapper;

	@Resource
	public void setDriverMapper(DriverMapper driverMapper) {
		this.driverMapper = driverMapper;
	}
	
	public List<Map<String, Object>> getDriverByQuery(OrderManageQueryParam queryParam) {
		return driverMapper.getDriverByQuery(queryParam);
	}
	
	public List<PubDriverSelectDto> listPubDriverBySelect(PubDriverSelectParam param) {
		return driverMapper.listPubDriverBySelect(param);
	}

	public List<PubDriverSelectDto> listPubDriverBySelectJobNum(PubDriverSelectParam param) {
		return driverMapper.listPubDriverBySelectJobNum(param);
	}
	
	public List<PubDriver> getOpDriverInBound(PubDriverInBoundParam param) {
		if(param.isSimple()){
			return driverMapper.getOpDriverInBoundWithSimple(param);
		}
		return driverMapper.getOpDriverInBound(param);
	}
	
	public List<PubDriver> getLeDriverInBound(PubDriverInBoundParam param) {
		if(param.isSimple()){
			return driverMapper.getLeDriverInBoundWithSimple(param);
		}
		return driverMapper.getLeDriverInBound(param);
	}
	
	public List<LeVehiclemodels> getLeDriverLevels(PubDriverInBoundParam param){
		return driverMapper.getLeDriverLevels(param);
	}
	
	public List<PubDriver> getSpecialDriver(PubDriverInBoundParam param) {
        return driverMapper.getSpecialDriver(param);
    }

    public List<String> getRightNowUnExceptDriver(String usetime) {
        return driverMapper.getRightNowUnExceptDriver(usetime);
    }
    public List<String> getOrderUnExceptDriver(String usetime) {
        return driverMapper.getOrderUnExceptDriver(usetime);
    }

	public boolean updatePushNum(PubDriverInBoundParam param) {
		if(OrderEnum.USETYPE_PERSONAL.code.equals(param.getUsetype())){
			driverMapper.updateOpPushNum(param);
		}else{
			driverMapper.updateLePushNum(param);
		}
		return true;
	}

	public DriverInfo getDriverInfoById(String id){
		return driverMapper.getDriverInfoById(id);
	}

}
