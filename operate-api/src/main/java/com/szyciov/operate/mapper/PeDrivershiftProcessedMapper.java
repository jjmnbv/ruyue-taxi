package com.szyciov.operate.mapper;

import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.dto.driverShiftManagent.ProcessedQueryDto;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.param.PeDrivershiftProcessedParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PeDrivershiftProcessedMapper {
	
    int deleteByPrimaryKey(@Param(value = "id") String id);

    int save(PeDrivershiftProcessed peDrivershiftProcessed);


    List<ProcessedQueryDto> listProcessed(PeDrivershiftProcessedParam processedParam);

    int getlistProcessedCount(PeDrivershiftProcessedParam processedParam);

    Date getLastProcessedTime(@Param(value = "relieveddriverid") String relieveddriverid,
                              @Param(value = "leasescompanyid") String leasescompanyid,
                              @Param(value = "platformtype") String platformtype);

    PeDrivershiftProcessed getProcessed(@Param(value = "id") String id);

    int updateOrderDriverId(@Param(value = "orderno") String orderno,
                            @Param(value = "newDriverId") String newDriverId);


    List<String> listTaxiOrderno(@Param(value = "oldDriverId") String oldDriverId,
                                 @Param(value = "leasesCompanyId") String leasesCompanyId);

    List<PubDriverSelectDto> listShiftRecordDriver(@Param(value = "platformtype")String platformtype,
                                                   @Param(value = "vehicleid")String vehicleid,
                                                   @Param(value = "shifitType")String shifitType,
                                                   @Param(value = "queryText")String queryText);
}