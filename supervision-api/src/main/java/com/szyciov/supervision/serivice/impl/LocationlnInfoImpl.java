package com.szyciov.supervision.serivice.impl;

import com.szyciov.supervision.api.dto.gps.DriverLocationInfo;
import com.szyciov.supervision.api.dto.gps.PassengerLocationInfo;
import com.szyciov.supervision.mapper.LocationlnInfoMapper;
import com.szyciov.supervision.serivice.LocationlnInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class LocationlnInfoImpl implements LocationlnInfoService {

    private  @Autowired LocationlnInfoMapper locationlnInfoMapper;

    /**
     * 驾驶员定位信息
     * @param map
     * @return
     */
    @Override
    public DriverLocationInfo driverLocationInfo(Map<String, String> map) {
        int ordertype = Integer.valueOf(map.get("ordertype"));
        int usetype = Integer.valueOf(map.get("usetype"));
        String orader ="";
        if (ordertype == 4){
        		orader ="op_taxiorder";
        }else{
        	if(usetype == 0 || usetype == 1){
                orader ="org_order";
                }else{
                orader ="op_order";
                }
        }
        map.put("orader",orader);
        DriverLocationInfo driverLocationInfo = locationlnInfoMapper.driverLocationInfo(map);
        driverLocationInfo.setCompanyId("RY");
        driverLocationInfo.setLatitude(map.get("latitude"));
        driverLocationInfo.setLongitude(map.get("longitude"));
        driverLocationInfo.setDriverId(map.get("driverId"));
        return driverLocationInfo;
    }

    /**
     * 乘客定位信息
     * @param map
     * @return
     */
    @Override
    public PassengerLocationInfo passengerLocationInfo(Map<String, String> map) {

        int ordertype = Integer.valueOf(map.get("ordertype"));
        int usetype = Integer.valueOf(map.get("usetype"));
        String orader ="";
        if (ordertype == 4){
            orader ="op_taxiorder";
        }else{
            if(usetype == 0 || usetype == 1){
                orader ="org_order";
            }else{
                orader ="op_order";
            }
        }
        map.put("orader",orader);
        PassengerLocationInfo passengerLocationInfo = locationlnInfoMapper.passengerLocationInfo(map);
        passengerLocationInfo.setCompanyId("RY");
        passengerLocationInfo.setLatitude(map.get("latitude"));
        passengerLocationInfo.setLongitude(map.get("longitude"));
        return passengerLocationInfo;
    }
}
