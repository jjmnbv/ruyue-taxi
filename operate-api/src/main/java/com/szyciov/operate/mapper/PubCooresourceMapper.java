package com.szyciov.operate.mapper;

import com.szyciov.entity.*;
import com.szyciov.op.param.*;
import com.szyciov.op.vo.pubCooresource.QueryCooresourceVo;
import com.szyciov.op.vo.pubCooresource.QueryHavingCooLeasecompanyVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceInfoVo;
import com.szyciov.op.vo.pubCooresource.QueryPubCooresourceManageVo;

import java.util.List;

/**
 * Created by ZF on 2017/7/24.
 */
public interface PubCooresourceMapper {
    List<QueryCooresourceVo> queryPubCooresourceData(QueryPubCooresourceParam param);

    List<QueryHavingCooLeasecompanyVo> queryHavingCooLeasecompany(String companyid);

    List<QueryPubCooresourceInfoVo> queryPubCooresourceInfoData(QueryPubCooresourceInfoParam param);

    List<Select2Entity> queryPubCooresourceInfoDriverSelect(QueryPubCooresourceInfoDriverSelectParam param);

    List<QueryPubCooresourceManageVo> queryPubCooresourceManageData(QueryPubCooresourceManageParam param);

    List<Select2Entity> queryPubCooresourceManageDriverSelect(QueryPubCooresourceManageDriverSelectParam param);

    List<Select2Entity> queryPubCooresourceManageVehicleModelSelect();

    PubVehicleModelsRef queryPubVehicleModelsRefByCoocId(String coocId);

    int updatePubVehicleModelsRefByPrimaryKeySelective(PubVehicleModelsRef ref);

    int insertPubVehicleModelsRefHistory(PubVehicleModelsRefHistory history);

    int insertPubVehicleModelsRefSelective(PubVehicleModelsRef ref);

    PubCooresource selectPubCooresourceByPrimaryKey(String id);
}
