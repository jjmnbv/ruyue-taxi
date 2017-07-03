package com.szyciov.carservice.mapper;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubVehicle;

import java.util.Map;

/**
 * Created by ZF on 2017/5/27.
 */
public interface OpTaxiOrderManageMapper {
    OpTaxiOrder getOpTaxiOrder(String orderno);

    int getOpSendmodelCountByUser(OpUser object);

    void insertOpTaxisendrecord(OpTaxisendrecord object);

    int getDriverCountByQuery(OrderManageQueryParam queryParam);

    void updateOpTaxiOrderByOrderno(OpTaxiOrder object);

    Map<String, Object> getInServiceOrderByDriver(String driverid);

    PubVehicle getById(String id);
}
