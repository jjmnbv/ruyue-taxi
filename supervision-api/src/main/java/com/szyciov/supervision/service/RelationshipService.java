package com.szyciov.supervision.service;

import com.szyciov.supervision.api.dto.relationship.HumanVehicleInfo;

import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
public interface RelationshipService extends CommonApiService  {
    /**
     * 人车对应关系
     * @param map
     * @return
     */
    HumanVehicleInfo humanVehicleInfo(Map<String,String> map);
}
