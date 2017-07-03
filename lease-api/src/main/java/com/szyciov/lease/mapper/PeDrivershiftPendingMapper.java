package com.szyciov.lease.mapper;

import com.szyciov.dto.driverShiftManagent.PendingDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.param.PeDrivershiftPendingParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeDrivershiftPendingMapper {


    int save(PeDrivershiftPending peDrivershiftPending);


    List<PendingDto> listPending(PeDrivershiftPendingParam pendingParam);

    int updateRelievedtype(PeDrivershiftPending peDrivershiftPending);

    int removeById(@Param(value = "id") String id);

    int getlistPendingCount(PeDrivershiftPendingParam pendingParam);

    PeDrivershiftPending getPendingInfo(PeDrivershiftPending peDrivershiftPending);

    Integer getUnfinishedOrderCount(String id);
}