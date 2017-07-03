package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.OpTaxiOrderManageMapper;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by ZF on 2017/5/27.
 */
@Repository
public class OpTaxiOrderManageDao {
    @Autowired
    private OpTaxiOrderManageMapper opTaxiOrderManageMapper;

    public OpTaxiOrder getOpTaxiOrder(String orderno) {
        return opTaxiOrderManageMapper.getOpTaxiOrder(orderno);
    }

    public int getOpSendmodelCountByUser(OpUser object) {
        return opTaxiOrderManageMapper.getOpSendmodelCountByUser(object);
    }

    public void insertOpTaxisendrecord(OpTaxisendrecord object) {
        opTaxiOrderManageMapper.insertOpTaxisendrecord(object);
    }

    public int getDriverCountByQuery(OrderManageQueryParam queryParam) {
        return opTaxiOrderManageMapper.getDriverCountByQuery(queryParam);
    }

    public void updateOpTaxiOrderByOrderno(OpTaxiOrder object) {
        opTaxiOrderManageMapper.updateOpTaxiOrderByOrderno(object);
    }

    public Map<String, Object> getInServiceOrderByDriver(String driverid) {
        return opTaxiOrderManageMapper.getInServiceOrderByDriver(driverid);
    }

    public PubVehicle getById(String id) {
        return opTaxiOrderManageMapper.getById(id);
    }

}
