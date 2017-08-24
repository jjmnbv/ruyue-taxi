package com.szyciov.supervision.service;

import com.szyciov.supervision.api.dto.gps.DriverLocationInfo;
import com.szyciov.supervision.api.dto.gps.PassengerLocationInfo;

import java.util.Map;

/**
 * GPS服务
 * Created by lzw on 2017/8/21.
 */
public interface GpsService extends CommonApiService {
    /**
     * 驾驶员定位
     * @param map
     * @return
     */
    DriverLocationInfo driverLocationInfo(Map<String,String> map);

    /**
     * 乘客定位
     * @param map
     * @return
     */
    PassengerLocationInfo passengerLocationInfo(Map<String,String> map);
}
