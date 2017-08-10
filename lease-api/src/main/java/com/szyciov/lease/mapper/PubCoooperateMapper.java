package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.dto.pubCoooperate.DriverInformationDto;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.op.param.PubCoooperateQueryParam;

public interface PubCoooperateMapper {

    List<PubCoooperate> getPubCoooperateList(PubCoooperateQueryParam queryParam);

    int getPubCoooperateListCount(PubCoooperateQueryParam queryParam);

    List<PubCoooperate> getCompanyNameList(String id);

    List<PubCoooperate>  getResourceInformationList(PubCoooperateQueryParam queryParam);

    int getResourceInformationListCount(PubCoooperateQueryParam queryParam);

    List<DriverInformationDto> getDriverInformationList(PubCoooperateQueryParam queryParam);

    int getDriverInformationListCount(PubCoooperateQueryParam queryParam);

    List<DriverInformationDto> getModelsList(PubCoooperateQueryParam queryParam);

    DriverInformationDto getOriginalModels(Map<String, String> map);

    List<LeVehiclemodels> getLeVehiclemodels(Map<String, String> map);

    void createPubVehicleModelsRef(PubVehicleModelsRef p);

    void createPubVehicleModelsRefHistory(PubVehicleModelsRef p);

    void updatePubVehicleModelsRef(PubVehicleModelsRef p);
}