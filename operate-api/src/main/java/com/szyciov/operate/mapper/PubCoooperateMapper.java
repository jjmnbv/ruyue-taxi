package com.szyciov.operate.mapper;

import com.szyciov.op.param.DisableCoooperateParam;
import com.szyciov.op.param.QueryPubCoooperateParam;
import com.szyciov.op.param.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperate.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperate.QueryLeasecompanyVo;
import com.szyciov.op.vo.pubCoooperate.QueryPubCoooperateVo;

import java.util.List;

/**
 * Created by ZF on 2017/7/24.
 */
public interface PubCoooperateMapper {
    List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam model);

    List<QueryLeasecompanyVo> queryLeasecompany(String companyid);

    int reviewLeasecompany(ReviewLeasecompanyParam model);

    int disableCoooperate(DisableCoooperateParam model);

    QueryCooagreementViewVo queryCooagreementView(String coooperateId);

}
