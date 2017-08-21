package com.szyciov.supervision.serivice;

import com.szyciov.supervision.api.dto.operation.*;

import java.util.Map;

/**
 * 经营信息数据
 * Created by lzw on 2017/8/21.
 */
public interface OperationService extends CommonApiService  {
    /**
     * 车辆营运上线
     * @param map
     * @return
     */
    VehicleOperationOnline vehicleOperationOnline(Map<String,String> map);

    /**
     * 车辆营运下线
     * @param map
     * @return
     */
    VehicleOperationOffline vehicleOperationOffline(Map<String,String> map);

    /**
     * 营运支付
     * @param map
     * @return
     */
    OperationPay operationPay(Map<String,String> map);

    /**
     * 营运出发
     * @param map
     * @return
     */
    OperationDeparture operationDeparture(Map<String,String> map);

    /**
     * 营运到达
     * @param map
     * @return
     */
    OperationArrival operationArrival(Map<String,String> map);
}
