package com.szyciov.carservice.mapper;

import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.entity.PubOrdermileagecalcLog;
import com.szyciov.op.entity.PubVehicle;

import java.util.List;

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

}
