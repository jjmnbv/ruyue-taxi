package com.szyciov.operate.mapper;

import com.szyciov.param.VehcParam;
import org.apache.ibatis.annotations.Param;

public interface VehcMapper {

    VehcParam getVehcById(@Param("vehcId") String vehcId);

}