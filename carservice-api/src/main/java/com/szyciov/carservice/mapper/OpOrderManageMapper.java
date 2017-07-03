package com.szyciov.carservice.mapper;

import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.PubDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by ZF on 2017/5/26.
 */
public interface OpOrderManageMapper {

    int manualSendOrder(OpOrder object);

    PubDriver getPubDriver(String id);

    List<OpAccountrules> findModelPriceByModels(Map<String, Object> params);

    OpOrder getOpOrder(String orderno);

    void updateOpOrderVehicleByOrderno(OpOrder object);

    Map<String, String> getOpOrderVehicleByOrder(String orderno);

    int insertOpSendrecord(OpSendrecord object);


}
