package com.szyciov.lease.dao;

import com.szyciov.dto.driverVehicleBindManage.CarBindRecordDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindRecordDto;
import com.szyciov.lease.mapper.PubDriverVehicleBindMapper;
import com.szyciov.lease.param.drivervehiclebind.CarBindRecordQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PubDriverVehicleBindDao {

    @Autowired
    private PubDriverVehicleBindMapper bindMapper;


    public int save(PubDriverVehicleBind vehicleBind){
        return bindMapper.save(vehicleBind);
    }

    public int deleteByPrimaryKey(@Param(value = "id") String id){
        return bindMapper.deleteByPrimaryKey(id);
    }


    public PubDriverVehicleBind getPubDriverVehicleBindById(String id){
        return bindMapper.getPubDriverVehicleBindById(id);
    }


    public List<CarBindRecordDto> listCarBindRecord(CarBindRecordQueryParam param){
        return bindMapper.listCarBindRecord(param);
    }

    public int countCarBindRecord(CarBindRecordQueryParam param){
        return bindMapper.countCarBindRecord(param);
    }


   public List<TaxiBindRecordDto>  listTaxiBindRecord(CarBindRecordQueryParam param){
       return bindMapper.listTaxiBindRecord(param);
   }

   public int countTaxiBindRecord(CarBindRecordQueryParam param){
        return bindMapper.countTaxiBindRecord(param);
    }
}