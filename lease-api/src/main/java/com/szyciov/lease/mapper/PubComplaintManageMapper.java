package com.szyciov.lease.mapper;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.param.pubComplaintManage.QueryPubComplaintsParam;
import com.szyciov.lease.param.pubComplaintManage.UpdateProcessRecordParam;
import com.szyciov.lease.vo.pubComplaintManage.QueryPubComplaintsVo;
import com.szyciov.param.Select2Param;

import java.util.List;

/**
 * Created by ZF on 2017/8/14.
 */
public interface PubComplaintManageMapper {
    List<QueryPubComplaintsVo> queryPubComplaints(QueryPubComplaintsParam param);

    List<Select2Entity> queryPubComplaintsUsers(Select2Param param);

    List<Select2Entity> queryPubComplaintsordernos(Select2Param param);

    List<Select2Entity> queryPubComplaintsJobNum(Select2Param param);

    List<Select2Entity> queryPubComplaintsDrivers(Select2Param param);

    List<Select2Entity> queryPubComplaintsFullplateno(Select2Param param);

    List<Select2Entity> queryPubComplaintsTypes(Select2Param param);

    int updateProcessRecord(UpdateProcessRecordParam param);
}
