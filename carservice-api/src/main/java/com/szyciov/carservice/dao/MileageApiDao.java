package com.szyciov.carservice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.carservice.mapper.MileageApiMapper;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.entity.PubOrdermileagecalcLog;
import com.szyciov.op.entity.PubVehicle;
import org.springframework.stereotype.Repository;

/**
 * Created by shikang on 2017/5/19.
 */
@Repository("MileageApiDao")
public class MileageApiDao {

    private MileageApiMapper mileageApiMapper;
    @Resource
    public void setMileageApiMapper(MileageApiMapper mileageApiMapper) {
        this.mileageApiMapper = mileageApiMapper;
    }

    public List<PubOrdergpsdata> getOrdergpsdataByOrderno(PubOrdergpsdata object) {
        return mileageApiMapper.getOrdergpsdataByOrderno(object);
    }

    public List<PubOrdergpsdata> getOrdergpsdataHistoryByOrderno(PubOrdergpsdata object) {
        return mileageApiMapper.getOrdergpsdataHistoryByOrderno(object);
    }

    public void insertPubOrdermileagecalcLog(PubOrdermileagecalcLog object) {
        mileageApiMapper.insertPubOrdermileagecalcLog(object);
    }

    public PubVehicle getVehicleById(String vehicleId) {
        return mileageApiMapper.getVehicleById(vehicleId);
    }

    public void updateDriverGps(PubDriver object) {
        mileageApiMapper.updateDriverGps(object);
    }

    public void insertPubDrivertrack(PubDrivertrack object) {
        mileageApiMapper.insertPubDrivertrack(object);
    }

    public PubDriver getPubDriverById(String driverid) {
        return mileageApiMapper.getPubDriverById(driverid);
    }

    public String getOrderByDriver(PubDriver object) {
        return mileageApiMapper.getOrderByDriver(object);
    }

    public void insertPubOrdergpsdata(PubOrdergpsdata object) {
        mileageApiMapper.insertPubOrdergpsdata(object);
    }

    public List<PubOrdergpsdata> getPubOrdergpsdataByMigration() {
        return mileageApiMapper.getPubOrdergpsdataByMigration();
    }

    public void insertPubOrdergpsdataHistory(PubOrdergpsdata object) {
        mileageApiMapper.insertPubOrdergpsdataHistory(object);
    }

    public void deletePubOrdergpsdataById(String id) {
        mileageApiMapper.deletePubOrdergpsdataById(id);
    }

    public PubOrdergpsdata getBaiduOffset(PubOrdergpsdata object) {
        return mileageApiMapper.getBaiduOffset(object);
    }

    public List<Map<String, Object>> getOrderMileageCalctypeCount(Map<String, Object> param) {
        return mileageApiMapper.getOrderMileageCalctypeCount(param);
    }

    public String getOrderMileageCalctypePhone() {
        return mileageApiMapper.getOrderMileageCalctypePhone();
    }
}
