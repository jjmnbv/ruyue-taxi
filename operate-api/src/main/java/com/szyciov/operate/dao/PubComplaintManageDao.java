package com.szyciov.operate.dao;

import com.szyciov.op.param.pubComplaintManage.QueryPubComplaintsParam;
import com.szyciov.op.param.pubComplaintManage.UpdateProcessRecordParam;
import com.szyciov.op.vo.pubComplaintManage.QueryPubComplaintsVo;
import com.szyciov.operate.mapper.PubComplaintManageMapper;
import com.szyciov.param.Select2Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZF on 2017/8/14.
 */
@Repository
public class PubComplaintManageDao {
    @Autowired
    private PubComplaintManageMapper pubComplaintManageMapper;

    public List<QueryPubComplaintsVo> queryPubComplaints(QueryPubComplaintsParam param) {
        List<QueryPubComplaintsVo> list = this.pubComplaintManageMapper.queryPubComplaints(param);
        return list;
    }

    public List queryPubComplaintsUsers(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsUsers(param);
        return list;
    }

    public List queryPubComplaintsordernos(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsordernos(param);
        return list;
    }

    public List queryPubComplaintsJobNum(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsJobNum(param);
        return list;
    }

    public List queryPubComplaintsDrivers(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsDrivers(param);
        return list;
    }

    public List queryPubComplaintsFullplateno(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsFullplateno(param);
        return list;
    }

    public List queryPubComplaintsTypes(Select2Param param) {
        List list = this.pubComplaintManageMapper.queryPubComplaintsTypes(param);
        return list;
    }

    public void updateProcessRecord(UpdateProcessRecordParam param) {
        this.pubComplaintManageMapper.updateProcessRecord(param);
    }
}
