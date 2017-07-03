package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.OpOrderManageMapper;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.PubDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ZF on 2017/5/26.
 */
@Repository
public class OpOrderApiDao {
    @Autowired
    private OpOrderManageMapper opOrderManageMapper;

    public int manualSendOrder(OpOrder object) {
        return opOrderManageMapper.manualSendOrder(object);
    }

    public PubDriver getPubDriver(String id) {
        return opOrderManageMapper.getPubDriver(id);

    }

    public List<OpAccountrules> findModelPriceByModels(Map<String, Object> params) {
        return opOrderManageMapper.findModelPriceByModels(params);
    }

    public OpOrder getOpOrder(String orderno) {
        return opOrderManageMapper.getOpOrder(orderno);
    }

    public void updateOpOrderVehicleByOrderno(OpOrder object) {
        opOrderManageMapper.updateOpOrderVehicleByOrderno(object);
    }

    public Map<String, String> getOpOrderVehicleByOrder(String orderno) {
        return opOrderManageMapper.getOpOrderVehicleByOrder(orderno);
    }

    public int insertOpSendrecord(OpSendrecord object) {
        return opOrderManageMapper.insertOpSendrecord(object);
    }


}
