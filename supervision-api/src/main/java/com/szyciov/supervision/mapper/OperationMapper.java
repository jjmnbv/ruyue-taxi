package com.szyciov.supervision.mapper;

import com.szyciov.supervision.api.dto.operation.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
@Mapper
public interface OperationMapper {
    /**
     * 营运上线
     * @param driverId
     * @return
     */
    VehicleOperationOnline vehicleOperationOnline(String driverId);

    /**
     * 营运下线
     * @param driverId
     * @return
     */
    VehicleOperationOffline vehicleOperationOffline(String driverId);

    /**
     * 营运出发
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    OperationDeparture operationDeparture(@Param("orderno") String orderno, @Param("ordertype")String ordertype, @Param("usetype") String usetype);

    /**
     * 计算空驾驶里程需要的数据
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    Map<String,Double> getWaitMile(@Param("orderno") String orderno, @Param("ordertype")String ordertype, @Param("usetype") String usetype);

    /**
     * 营运到达
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    OperationArrival operationArrival(@Param("orderno") String orderno, @Param("ordertype")String ordertype, @Param("usetype") String usetype);

    /**
     * 营运支付
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    OperationPay operationPay(@Param("orderno") String orderno, @Param("ordertype")String ordertype, @Param("usetype") String usetype);
}
