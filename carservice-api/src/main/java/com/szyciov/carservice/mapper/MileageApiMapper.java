package com.szyciov.carservice.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.entity.PubOrdermileagecalcLog;
import com.szyciov.op.entity.PubVehicle;

/**
 * Created by shikang on 2017/5/19.
 */
public interface MileageApiMapper {

    List<PubOrdergpsdata> getOrdergpsdataByOrderno(PubOrdergpsdata object);

    List<PubOrdergpsdata> getOrdergpsdataHistoryByOrderno(PubOrdergpsdata object);

    void insertPubOrdermileagecalcLog(PubOrdermileagecalcLog object);

    PubVehicle getVehicleById(String vehicleId);

    void updateDriverGps(PubDriver object);

    void insertPubDrivertrack(PubDrivertrack object);

    PubDriver getPubDriverById(String driverid);

    String getOrderByDriver(PubDriver object);

    void insertPubOrdergpsdata(PubOrdergpsdata object);

    List<PubOrdergpsdata> getPubOrdergpsdataByMigration();

    void insertPubOrdergpsdataHistory(PubOrdergpsdata object);

    void deletePubOrdergpsdataById(String id);

    PubOrdergpsdata getBaiduOffset(PubOrdergpsdata object);

    List<Map<String, Object>> getOrderMileageCalctypeCount(Map<String, Object> param);

    String getOrderMileageCalctypePhone();

}
