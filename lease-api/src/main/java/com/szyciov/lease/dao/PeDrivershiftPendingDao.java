package com.szyciov.lease.dao;

import com.szyciov.dto.driverShiftManagent.PendingDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.lease.mapper.PeDrivershiftPendingMapper;
import com.szyciov.param.PeDrivershiftPendingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeDrivershiftPendingDao {

    @Autowired
    private PeDrivershiftPendingMapper pendingMapper;

    public int save(PeDrivershiftPending peDrivershiftPending){

        return pendingMapper.save(peDrivershiftPending);
    }

    public List<PendingDto> listPending(PeDrivershiftPendingParam pendingParam){
        return pendingMapper.listPending(pendingParam);
    }


    public int updateRelievedtype(PeDrivershiftPending peDrivershiftPending){
        return pendingMapper.updateRelievedtype(peDrivershiftPending);
    }

    public int removeById(String id){
        return pendingMapper.removeById(id);
    }

    public int getlistPendingCount(PeDrivershiftPendingParam pendingParam){
        return pendingMapper.getlistPendingCount(pendingParam);
    }

    /**
     * 根据查询对象条件返回待交接班对象，如查询有多个结果集，且仅只返回一个
     * @param peDrivershiftPending 查询对象
     * @return
     */
    public PeDrivershiftPending getPendingInfo(PeDrivershiftPending peDrivershiftPending){
        return  pendingMapper.getPendingInfo(peDrivershiftPending);
    }


    public void removeShfitApply(String id){ pendingMapper.removeById(id);}

    /**
     * 获取出租车未完成订单数量
     * @param id   车辆ID
     * @return
     */
    public Integer getUnfinishedOrderCount(String id){
        return pendingMapper.getUnfinishedOrderCount(id);
    }
}