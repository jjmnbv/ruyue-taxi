package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.PubVehicleModelsRefMapper;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ZF on 2017/8/11.
 */
@Repository
public class PubVehicleModelsRefDao {
    @Autowired
    private PubVehicleModelsRefMapper pubVehicleModelsRefMapper;

    public int insertPubVehicleModelsRef(List list) {
        return this.pubVehicleModelsRefMapper.insertPubVehicleModelsRef(list);
    }

    public int insertPubVehicleModelsRefHistory(List list) {
        return this.pubVehicleModelsRefMapper.insertPubVehicleModelsRefHistory(list);
    }


    public List<String> queryVehicleByVehcline(Map<String, Object> map) {
        return this.pubVehicleModelsRefMapper.queryVehicleByVehcline(map);
    }

    public int deleteVehicleModeRef(Map<String, Object> map) {
        return this.pubVehicleModelsRefMapper.deleteVehicleModeRef(map);
    }

    public String queryVehiclemodelsByVehcline(Map<String, Object> map) {
        return this.pubVehicleModelsRefMapper.queryVehiclemodelsByVehcline(map);
    }
}
