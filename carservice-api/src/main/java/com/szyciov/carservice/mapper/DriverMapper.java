package com.szyciov.carservice.mapper;

import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.entity.PubDriver;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.lease.param.PubDriverSelectParam;

import java.util.List;
import java.util.Map;

public interface DriverMapper {

    public List<Map<String, Object>> getDriverByQuery(OrderManageQueryParam queryParam);

    public List<PubDriverSelectDto> listPubDriverBySelect(PubDriverSelectParam param);

    List<PubDriverSelectDto> listPubDriverBySelectJobNum(PubDriverSelectParam param);

    public List<PubDriver> getOpDriverInBoundWithSimple(PubDriverInBoundParam param);

    public List<PubDriver> getOpDriverInBound(PubDriverInBoundParam param);

    public List<PubDriver> getLeDriverInBoundWithSimple(PubDriverInBoundParam param);

    public List<PubDriver> getLeDriverInBound(PubDriverInBoundParam param);

    public List<LeVehiclemodels> getLeDriverLevels(PubDriverInBoundParam param);
    
    public List<PubDriver> getSpecialDriver(PubDriverInBoundParam param);

    public void updateOpPushNum(PubDriverInBoundParam param);
    
    public void updateLePushNum(PubDriverInBoundParam param);


    public DriverInfo getDriverInfoById(String id);

    List<String> getRightNowUnExceptDriver(String usetime);
    List<String> getOrderUnExceptDriver(String usetime);
}
