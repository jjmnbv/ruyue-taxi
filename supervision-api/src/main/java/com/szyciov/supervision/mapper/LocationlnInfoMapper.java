package com.szyciov.supervision.mapper;

import com.szyciov.supervision.api.dto.gps.DriverLocationInfo;
import com.szyciov.supervision.api.dto.gps.PassengerLocationInfo;

import java.util.Map;

public interface LocationlnInfoMapper {
    //驾驶员定位信息
    DriverLocationInfo driverLocationInfo(Map<String, String> map);
    //乘客定位信息
    PassengerLocationInfo passengerLocationInfo(Map<String, String> map);
}
