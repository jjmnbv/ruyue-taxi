package com.szyciov.lease.dao;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubCooresource;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.mapper.PubCoooperateUnionMapper;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryApplyResourceVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryCooagreementViewVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryPubCoooperateVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryResourceVo;
import com.szyciov.op.entity.OpPlatformInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZF on 2017/8/1.
 */
@Repository
public class PubCoooperateUnionDao {
    @Autowired
    private PubCoooperateUnionMapper pubCoooperateUnionMapper;


    public List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam param) {
        List list = this.pubCoooperateUnionMapper.queryPubCoooperateData(param);
        return list;
    }

    public List<Select2Entity> queryLeasecompany(String companyid) {
        List list = this.pubCoooperateUnionMapper.queryLeasecompany(companyid);
        return list;
    }

    public QueryCooagreementViewVo queryCooagreementView(String coooperateId) {
        QueryCooagreementViewVo vo = this.pubCoooperateUnionMapper.queryCooagreementView(coooperateId);
        return vo;
    }

    public void disableCoooperate(DisableCoooperateParam param) {
        this.pubCoooperateUnionMapper.disableCoooperate(param);
    }

    public List<Select2Entity> queryResourceVehclineSelect2(QueryResourceCitySelect2Param param) {
        List list = this.pubCoooperateUnionMapper.queryResourceVehclineSelect2(param);
        return list;
    }

    public List<Select2Entity> queryResourceCitySelect2(String coooId) {
        List list = this.pubCoooperateUnionMapper.queryResourceCitySelect2(coooId);
        return list;
    }

    public List<QueryResourceVo> queryResource(QueryResourceParam param) {
        List list = this.pubCoooperateUnionMapper.queryResource(param);
        return list;
    }

    public void insertResourceBatch(List<PubCooresource> list) {
        this.pubCoooperateUnionMapper.insertResourceBatch(list);
    }

    public List<String> queryCooVehicleId(String coooId) {
        List<String> list = this.pubCoooperateUnionMapper.queryCooVehicleId(coooId);
        return list;
    }

    public void deleteResourceByVehicleId(DeleteCooresourceVehicleBatchParam param) {
        this.pubCoooperateUnionMapper.deleteResourceByVehicleId(param);
    }

    public List<Select2Entity> queryCooVehicleModeSelect2(String coooId) {
        List<Select2Entity> list = this.pubCoooperateUnionMapper.queryCooVehicleModeSelect2(coooId);
        return list;
    }

    public PubCoooperate selectPubCoooopertateByPrimaryKey(String id) {
        return this.pubCoooperateUnionMapper.selectPubCoooopertateByPrimaryKey(id);
    }

    public LeLeasescompany queryApplyLeaseCompany(QueryApplyLeaseCompanyParam param) {
        return this.pubCoooperateUnionMapper.queryApplyLeaseCompany(param);
    }

    public OpPlatformInfo queryApplyOpCompany(QueryApplyLeaseCompanyParam param) {
        return this.pubCoooperateUnionMapper.queryApplyOpCompany(param);
    }

    public List<QueryApplyResourceVo> queryApplyResource(QueryApplyResourceParam param) {
        List<QueryApplyResourceVo> list = this.pubCoooperateUnionMapper.queryApplyResource(param);
        return list;
    }


    public List<Select2Entity> queryApplyResourceVehclineSelect2(QueryApplyResourceVehclineSelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionMapper.queryApplyResourceVehclineSelect2(param);
        return list;
    }

    public List<Select2Entity> queryApplyResourceCitySelect2(QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionMapper.queryApplyResourceCitySelect2(param);
        return list;
    }

    public PubCoooperate queryApplyCoooperate(QueryApplyCoooperateParam param) {
        return this.pubCoooperateUnionMapper.queryApplyCoooperate(param);
    }

    public PubCooagreement queryApplyCooagreement(QueryApplyCooagreementParam param) {
        return this.pubCoooperateUnionMapper.queryApplyCooagreement(param);
    }

    public List<Select2Entity> queryApplyCooVehicleModeSelect2(QueryApplyResourceCitySelect2Param param) {
        List<Select2Entity> list = this.pubCoooperateUnionMapper.queryApplyCooVehicleModeSelect2(param);
        return list;
    }

    public void insertPubCoooperateSelective(PubCoooperate pubCoooperate) {
        this.pubCoooperateUnionMapper.insertPubCoooperateSelective(pubCoooperate);
    }
}
