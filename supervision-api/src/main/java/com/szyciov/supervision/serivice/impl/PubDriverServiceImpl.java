package com.szyciov.supervision.serivice.impl;


import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import com.szyciov.supervision.mapper.PubDriverMapper;
import com.szyciov.supervision.serivice.PubDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by lzw on 2017/8/18.
 */
@Service("pubDriverService")
public class PubDriverServiceImpl  implements PubDriverService{
    @Autowired
    private PubDriverMapper pubDriverMapper;


    /**
     * 司机上班
     * @param map
     * @return
     */
    @Override
    public DriverOnWork onWork(Map<String, String> map) {
        DriverOnWork driverOnWork =pubDriverMapper.onWork(map.get("driverId"));
        if(driverOnWork!=null) {
            driverOnWork.setOnWorkTime(map.get("onWorkTime"));
            driverOnWork.setAddress("440001");
        }
        return driverOnWork;
    }

    /**
     * 司机下班
     * @param map
     * @return
     */
    @Override
    public DriverOffWork offWork(Map<String, String> map) {
        DriverOffWork driverOffWork =pubDriverMapper.offWork(map.get("driverId"));
        if(driverOffWork!=null) {
            driverOffWork.setOnWorkTime(map.get("onWorkTime"));
            driverOffWork.setOffWorkTime(map.get("offWorkTime"));
            driverOffWork.setAddress("440001");
            driverOffWork.setDriveCount("20");
            driverOffWork.setDriveMile("130");
            driverOffWork.setDriveTime("560");
            driverOffWork.setPrice("1236");
            driverOffWork.setFactPrice("1236");

        }
        return driverOffWork;
    }
}
