package com.szyciov.lease.mapper;

import com.szyciov.entity.*;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.param.pubCoooperateUnion.*;
import com.szyciov.lease.vo.pubCoooperateUnion.*;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.param.Select2Param;

import java.util.List;

/**
 * Created by ZF on 2017/8/1.
 */
public interface PubCoooperateUnionMapper {

    List<QueryPubCoooperateVo> queryPubCoooperateData(QueryPubCoooperateParam param);

    List<Select2Entity> queryCoonoSelect2(Select2Param param);

    List<Select2Entity> queryLeasecompany(Select2Param param);

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

    int insertOpUserNews(OpUsernews news);

    List<QueryApplayCooCompanyAdminVo> queryApplayCooCompanyAdmin(String company);
}
