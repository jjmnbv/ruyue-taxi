package com.szyciov.operate.mapper;

import com.szyciov.entity.PubDriverVehicleBind;
import org.apache.ibatis.annotations.Param;

public interface PubDriverVehicleBindMapper {
	
    int deleteByPrimaryKey(@Param(value = "id") String id);

    int save(PubDriverVehicleBind pubDriverVehicleBind);

    PubDriverVehicleBind selectByPrimaryKey(@Param(value = "id") String id);

    int updateByPrimaryKey(PubDriverVehicleBind pubDriverVehicleBind);


	
}