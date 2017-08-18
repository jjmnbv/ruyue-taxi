package com.szyciov.lease.dao;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.mapper.PubCoooperatePartnerMapper;
import com.szyciov.lease.param.pubCoooperatePartner.DisableCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperateParam;
import com.szyciov.lease.param.pubCoooperatePartner.QueryPubCoooperatePartnerLeaseCompanyParam;
import com.szyciov.lease.param.pubCoooperatePartner.ReviewLeasecompanyParam;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryCooagreementViewVo;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryLeaseCompanyAdminVo;
import com.szyciov.lease.vo.pubCoooperatePartner.QueryPubCoooperateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * .
 * 战略合作-联盟资源管理-战略伙伴
 * Created by 李帅 on 2017/8/7.
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

    public int deleteLeaseOrganRef(String coooId) {
        return this.pubCoooperatePartnerMapper.deleteLeaseOrganRef(coooId);
    }

    public int deleteVehicleModelRef(String coooId) {
        return this.pubCoooperatePartnerMapper.deleteVehicleModelRef(coooId);
    }
}
