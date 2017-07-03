package com.szyciov.lease.mapper;

import com.szyciov.dto.driverVehicleBindManage.CarBindRecordDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindRecordDto;
import com.szyciov.lease.param.drivervehiclebind.CarBindRecordQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PubDriverVehicleBindMapper {
	
    int deleteByPrimaryKey(@Param(value = "id") String id);

    int save(PubDriverVehicleBind pubDriverVehicleBind);

    PubDriverVehicleBind getPubDriverVehicleBindById(@Param(value = "id") String id);

    int updateByPrimaryKey(PubDriverVehicleBind pubDriverVehicleBind);


    List<CarBindRecordDto> listCarBindRecord(CarBindRecordQueryParam param);

    int countCarBindRecord(CarBindRecordQueryParam param);


    List<TaxiBindRecordDto>  listTaxiBindRecord(CarBindRecordQueryParam param);

    int countTaxiBindRecord(CarBindRecordQueryParam param);



}