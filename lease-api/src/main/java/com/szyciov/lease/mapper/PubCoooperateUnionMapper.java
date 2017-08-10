package com.szyciov.lease.mapper;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubCooresource;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryApplyResourceVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryCooagreementViewVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryPubCoooperateVo;
import com.szyciov.lease.vo.pubCoooperateUnion.QueryResourceVo;
import com.szyciov.op.entity.OpPlatformInfo;

import java.util.List;

/**
 * Created by ZF on 2017/8/1.
 */
public interface PubCoooperateUnionMapper {

    List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam param);

    List<Select2Entity> queryLeasecompany(String companyid);

    QueryCooagreementViewVo queryCooagreementView(String coooperateId);

    int disableCoooperate(DisableCoooperateParam param);

    List<QueryResourceVo> queryResource(QueryResourceParam param);

    List<Select2Entity> queryResourceVehclineSelect2(QueryResourceCitySelect2Param param);

    List<Select2Entity> queryResourceCitySelect2(String coooId);

    int insertResourceBatch(List<PubCooresource> list);

    List<String> queryCooVehicleId(String coooId);

    int deleteResourceByVehicleId(DeleteCooresourceVehicleBatchParam param);

    List<Select2Entity> queryCooVehicleModeSelect2(String coooId);

    PubCoooperate selectPubCoooopertateByPrimaryKey(String id);

    LeLeasescompany queryApplyLeaseCompany(QueryApplyLeaseCompanyParam param);

    OpPlatformInfo queryApplyOpCompany(QueryApplyLeaseCompanyParam param);

    List<QueryApplyResourceVo> queryApplyResource(QueryApplyResourceParam param);

    List<Select2Entity> queryApplyResourceVehclineSelect2(QueryApplyResourceVehclineSelect2Param param);

    List<Select2Entity> queryApplyResourceCitySelect2(QueryApplyResourceCitySelect2Param param);

    PubCoooperate queryApplyCoooperate(QueryApplyCoooperateParam param);

    PubCooagreement queryApplyCooagreement(QueryApplyCooagreementParam param);

    List<Select2Entity> queryApplyCooVehicleModeSelect2(QueryApplyResourceCitySelect2Param param);

    int insertPubCoooperateSelective(PubCoooperate pubCoooperate);
}
