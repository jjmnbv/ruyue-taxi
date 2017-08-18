package com.szyciov.operate.mapper;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.op.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.op.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryLeaseCompanyAdminVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryPubCoooperateVo;

import java.util.List;

/**
 * Created by ZF on 2017/7/24.
 */
public interface PubCoooperatePartnerMapper {
    List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam model);

    List<Select2Entity> queryLeasecompany(QueryPubCoooperatePartnerLeaseCompanyParam param);

    int reviewLeasecompany(ReviewLeasecompanyParam model);

    int disableCoooperate(DisableCoooperateParam model);

    QueryCooagreementViewVo queryCooagreementView(String coooperateId);

    List<QueryLeaseCompanyAdminVo> queryLeaseCompanyAdminByCoooId(String coooid);

    int insertLeUserNews(LeUserNews news);

    int deleteLeaseOrganRef(String coooId);

    int deleteVehicleModelRef(String coooId);
}
