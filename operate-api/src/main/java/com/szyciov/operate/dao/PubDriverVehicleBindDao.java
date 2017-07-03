package com.szyciov.operate.dao;

import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.operate.mapper.PubDriverVehicleBindMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	
}