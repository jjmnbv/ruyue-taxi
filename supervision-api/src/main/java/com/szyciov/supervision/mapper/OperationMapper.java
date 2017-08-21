package com.szyciov.supervision.mapper;

import com.szyciov.supervision.api.dto.operation.VehicleOperationOffline;
import com.szyciov.supervision.api.dto.operation.VehicleOperationOnline;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lzw on 2017/8/21.
 */
@Mapper
public interface OperationMapper {
    VehicleOperationOnline vehicleOperationOnline(String driverId);
    VehicleOperationOffline vehicleOperationOffline(String driverId);
}
