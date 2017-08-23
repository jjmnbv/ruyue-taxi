package com.szyciov.supervision.mapper;

import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by lzw on 2017/8/18.
 */
@Mapper
public interface OrderMapper {
    DriverOnWork driverOnWork(String driverId);

    DriverOffWork driverOffWork(String driverId);
}
