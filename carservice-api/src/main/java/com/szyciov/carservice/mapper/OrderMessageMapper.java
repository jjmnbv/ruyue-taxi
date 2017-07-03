package com.szyciov.carservice.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LC on 2016/11/23.
 */
public interface OrderMessageMapper {


    public List<String> findOrgRoleId(@Param("dynamicid") String dynamicid,@Param("paymethod") String paymethod);


    public List<String> findOpRoleId(@Param("cityCode")String cityCode);
}
 