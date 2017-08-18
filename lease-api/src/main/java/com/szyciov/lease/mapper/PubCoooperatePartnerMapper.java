package com.szyciov.lease.mapper;


import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.lease.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryLeaseCompanyAdminVo;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryPubCoooperateVo;

import java.util.List;

/**
 * .
 * 战略合作-联盟资源管理-战略伙伴
 * Created by 李帅 on 2017/8/7.
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
