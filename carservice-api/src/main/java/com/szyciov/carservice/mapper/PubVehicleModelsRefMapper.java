package com.szyciov.carservice.mapper;

import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by ZF on 2017/8/11.
 */
public interface PubVehicleModelsRefMapper {

    int insertPubVehicleModelsRef(List list);

    List<String> queryVehicleByVehcline(Map<String, Object> map);

    int deleteVehicleModeRef(Map<String, Object> map);

    String queryVehiclemodelsByVehcline(Map<String, Object> map);

    int insertPubVehicleModelsRefHistory(List list);
}
