package com.szyciov.supervision.service.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.gps.DriverLocationInfo;
import com.szyciov.supervision.api.dto.gps.PassengerLocationInfo;
import com.szyciov.supervision.mapper.GpsMapper;
import com.szyciov.supervision.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * GPS 服务
 * Created by lzw on 2017/8/21.
 */
@Service("gpsService")
public class GpsServiceImpl implements GpsService {
    @Autowired
    private GpsMapper gpsMapper;
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case DriverLocationInfo:
                return  this.driverLocationInfo(map);
            case PassengerLocationInfo:
                return  this.passengerLocationInfo(map);
        }
        return null;
    }

    /**
     * 驾驶员定位
     * @param map
     * @return
     */
    @Override
    public DriverLocationInfo driverLocationInfo(Map<String, String> map) {
        return null;
    }

    /**
     * 乘客定位
     * @param map
     * @return
     */
    @Override
    public PassengerLocationInfo passengerLocationInfo(Map<String, String> map) {
        return null;
    }
}
