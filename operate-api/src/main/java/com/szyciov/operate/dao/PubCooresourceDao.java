package com.szyciov.operate.dao;

import com.szyciov.entity.PubCooresource;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.param.*;
import com.szyciov.op.vo.pubCooresource.QueryCooresourceVo;
import com.szyciov.op.vo.pubCooresource.QueryHavingCooLeasecompanyVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceInfoVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceManageVo;
import com.szyciov.operate.mapper.PubCooresourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ZF on 2017/7/25.
 */
@Repository("PubCooresourceDao")
public class PubCooresourceDao {
    @Autowired
    private PubCooresourceMapper pubCooresourceMapper;

    public List<QueryCooresourceVo> queryPubCooresourceData(QueryPubCooresourceParam param) {
        List<QueryCooresourceVo> list = this.pubCooresourceMapper.queryPubCooresourceData(param);
        return list;
    }

    public List<QueryHavingCooLeasecompanyVo> queryHavingCooLeasecompany(String companyid, String keyword) {
        List<QueryHavingCooLeasecompanyVo> list = this.pubCooresourceMapper.queryHavingCooLeasecompany(companyid, keyword);
        return list;
    }

    public List<QueryPubCooresourceInfoVo> queryPubCooresourceInfoData(QueryPubCooresourceInfoParam param) {
        List<QueryPubCooresourceInfoVo> list = this.pubCooresourceMapper.queryPubCooresourceInfoData(param);
        return list;
    }

    public List<Select2Entity> queryPubCooresourceInfoDriverSelect(QueryPubCooresourceInfoDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceMapper.queryPubCooresourceInfoDriverSelect(param);
        return list;
    }


    public List<QueryPubCooresourceManageVo> queryPubCooresourceManageData(QueryPubCooresourceManageParam param) {
        List<QueryPubCooresourceManageVo> list = this.pubCooresourceMapper.queryPubCooresourceManageData(param);
        return list;
    }

    public List<Select2Entity> queryPubCooresourceManageDriverSelect(QueryPubCooresourceManageDriverSelectParam param) {
        List<Select2Entity> list = this.pubCooresourceMapper.queryPubCooresourceManageDriverSelect(param);
        return list;
    }

    public List<Select2Entity> queryPubCooresourceManageVehicleModelSelect() {
        List<Select2Entity> list = this.pubCooresourceMapper.queryPubCooresourceManageVehicleModelSelect();
        return list;
    }


    public PubVehicleModelsRef queryPubVehicleModelsRefByCoooId(String coocId) {
        PubVehicleModelsRef ref = this.pubCooresourceMapper.queryPubVehicleModelsRefByCoocId(coocId);
        return ref;
    }

    public void updatePubVehicleModelsRefByPrimaryKeySelective(PubVehicleModelsRef ref) {
        this.pubCooresourceMapper.updatePubVehicleModelsRefByPrimaryKeySelective(ref);
    }

    public void insertPubVehicleModelsRefHistory(PubVehicleModelsRefHistory history) {
        this.pubCooresourceMapper.insertPubVehicleModelsRefHistory(history);
    }

    public void insertPubVehicleModelsRefSelective(PubVehicleModelsRef history) {
        this.pubCooresourceMapper.insertPubVehicleModelsRefSelective(history);
    }

    public PubCooresource selectPubCooresourceByPrimaryKey(String id) {
        return this.pubCooresourceMapper.selectPubCooresourceByPrimaryKey(id);
    }

}
