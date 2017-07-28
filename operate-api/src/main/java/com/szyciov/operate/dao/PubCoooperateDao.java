package com.szyciov.operate.dao;

import com.szyciov.op.param.DisableCoooperateParam;
import com.szyciov.op.param.QueryPubCoooperateParam;
import com.szyciov.op.param.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperate.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperate.QueryLeasecompanyVo;
import com.szyciov.op.vo.pubCoooperate.QueryPubCoooperateVo;
import com.szyciov.operate.mapper.PubCoooperateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZF on 2017/7/24.
 */
@Repository("PubCoooperateDao")
public class PubCoooperateDao {
    @Autowired
    private PubCoooperateMapper pubCoooperateMapper;


    public List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam model) {
        List<QueryPubCoooperateVo> list = this.pubCoooperateMapper.queryPubCoooperateData(model);
        return list;
    }

    public List<QueryLeasecompanyVo> queryLeasecompany(String companyid) {
        List<QueryLeasecompanyVo> list = this.pubCoooperateMapper.queryLeasecompany(companyid);
        return list;
    }

    public int reviewLeasecompany(ReviewLeasecompanyParam model) {
        return this.pubCoooperateMapper.reviewLeasecompany(model);
    }

    public int disableCoooperate(DisableCoooperateParam model) {
        return this.pubCoooperateMapper.disableCoooperate(model);
    }

    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        return this.pubCoooperateMapper.queryCooagreementView(coooperateId);
    }

}
