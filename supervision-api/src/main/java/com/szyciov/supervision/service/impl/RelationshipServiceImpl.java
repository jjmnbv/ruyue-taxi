package com.szyciov.supervision.service.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.relationship.HumanVehicleInfo;
import com.szyciov.supervision.mapper.RelationshipMapper;
import com.szyciov.supervision.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
@Service("relationshipService")
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    private RelationshipMapper relationshipMapper;
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case HumanVehicleInfo:
                return  this.humanVehicleInfo(map);
        }
        return null;
    }

    @Override
    public HumanVehicleInfo humanVehicleInfo(Map<String, String> map) {
        return null;
    }
}
