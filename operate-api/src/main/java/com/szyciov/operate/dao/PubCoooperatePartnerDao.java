package com.szyciov.operate.dao;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.op.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.op.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.op.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.op.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryLeaseCompanyAdminVo;
import com.szyciov.op.vo.pubCoooperatePartner.QueryPubCoooperateVo;
import com.szyciov.operate.mapper.PubCoooperatePartnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by ZF on 2017/7/24.
 */
@Repository
public class PubCoooperatePartnerDao {
    @Autowired
    private PubCoooperatePartnerMapper pubCoooperatePartnerMapper;


    public List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam model) {
        List<QueryPubCoooperateVo> list = this.pubCoooperatePartnerMapper.queryPubCoooperateData(model);
        return list;
    }

    public List<Select2Entity> queryLeasecompany(QueryPubCoooperatePartnerLeaseCompanyParam param) {
        List<Select2Entity> list = this.pubCoooperatePartnerMapper.queryLeasecompany(param);
        return list;
    }

    public int reviewLeasecompany(ReviewLeasecompanyParam model) {
        return this.pubCoooperatePartnerMapper.reviewLeasecompany(model);
    }

    public int disableCoooperate(DisableCoooperateParam model) {
        return this.pubCoooperatePartnerMapper.disableCoooperate(model);
    }

    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        return this.pubCoooperatePartnerMapper.queryCooagreementView(coooperateId);
    }

    public List<QueryLeaseCompanyAdminVo> queryLeaseCompanyAdminByCoooId(String coooid) {
        return this.pubCoooperatePartnerMapper.queryLeaseCompanyAdminByCoooId(coooid);
    }

    public int insertLeUserNews(LeUserNews news) {
        return this.pubCoooperatePartnerMapper.insertLeUserNews(news);
    }
}
