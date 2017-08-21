package com.szyciov.supervision.serivice.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.operation.*;
import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.mapper.OperationMapper;
import com.szyciov.supervision.serivice.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 经营信息数据
 * Created by lzw on 2017/8/21.
 */
@Service("operationService")
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationMapper operationMapper;

    /**
     *
     * @param commandEnum
     * @param map
     * @return
     */
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case VehicleOperationOnline:
                return  this.vehicleOperationOnline(map);
            case VehicleOperationOffline:
                return  this.vehicleOperationOffline(map);
            case OperationPay:
                return  this.operationPay(map);
            case OperationDeparture:
                return  this.operationDeparture(map);
            case OperationArrival:
                return this.operationArrival(map);


        }
        return null;
    }

    /**
     * 营运上线
     * @param map
     * @return
     */
    @Override
    public VehicleOperationOnline vehicleOperationOnline(Map<String, String> map) {
        String driverId=map.get("driverId");
        VehicleOperationOnline vehicleOperationOnline=operationMapper.vehicleOperationOnline(driverId);
        if(vehicleOperationOnline!=null){
            vehicleOperationOnline.setLoginTime(map.get("loginTime"));
            vehicleOperationOnline.setEncrypt(CacheHelper.getMapEncrypt());
            vehicleOperationOnline.setLongitude(map.get("longitude"));
            vehicleOperationOnline.setLatitude(map.get("latitude"));
        }
        return vehicleOperationOnline;
    }

    /**
     * 营运下线
     * @param map
     * @return
     */
    @Override
    public VehicleOperationOffline vehicleOperationOffline(Map<String, String> map) {
        String driverId=map.get("driverId");
        VehicleOperationOffline vehicleOperationOffline=operationMapper.vehicleOperationOffline(driverId);
        if(vehicleOperationOffline!=null){
            vehicleOperationOffline.setLogoutTime(map.get("logoutTime"));
            vehicleOperationOffline.setEncrypt(CacheHelper.getMapEncrypt());
            vehicleOperationOffline.setLongitude(map.get("longitude"));
            vehicleOperationOffline.setLatitude(map.get("latitude"));
        }
        return vehicleOperationOffline;
    }

    /**
     * 营运支付
     * @param map
     * @return
     */
    @Override
    public OperationPay operationPay(Map<String, String> map) {
        return null;
    }

    /**
     * 营运出发
     * @param map
     * @return
     */
    @Override
    public OperationDeparture operationDeparture(Map<String, String> map) {
        return null;
    }

    /**
     * 营运到达
     * @param map
     * @return
     */
    @Override
    public OperationArrival operationArrival(Map<String, String> map) {
        return null;
    }
}
