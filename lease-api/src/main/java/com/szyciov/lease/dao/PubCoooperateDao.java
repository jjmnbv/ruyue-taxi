package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.szyciov.dto.pubCoooperate.DriverInformationDto;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.mapper.PubCoooperateMapper;
import com.szyciov.op.param.PubCoooperateQueryParam;

@Repository("PubCoooperateDao")
public class PubCoooperateDao {
    public PubCoooperateDao() {
    }

    @Autowired
    private PubCoooperateMapper mapper;

    public List<PubCoooperate> getPubCoooperateList(PubCoooperateQueryParam queryParam){
        return mapper.getPubCoooperateList(queryParam);
    };

    public int getPubCoooperateListCount(PubCoooperateQueryParam queryParam){
        return mapper.getPubCoooperateListCount(queryParam);
    };

    public List<PubCoooperate> getCompanyNameList(String id){
        return mapper.getCompanyNameList(id);
    };

    public List<PubCoooperate>  getResourceInformationList(PubCoooperateQueryParam queryParam){
        return mapper.getResourceInformationList(queryParam);
    };

    public int getResourceInformationListCount(PubCoooperateQueryParam queryParam){
        return mapper.getResourceInformationListCount(queryParam);
    };

    public List<DriverInformationDto> getDriverInformationList(PubCoooperateQueryParam queryParam){
        return mapper.getDriverInformationList(queryParam);
    };

    public int getDriverInformationListCount(PubCoooperateQueryParam queryParam){
        return mapper.getDriverInformationListCount(queryParam);
    };

    public List<DriverInformationDto> getModelsList(PubCoooperateQueryParam queryParam){
        return mapper.getModelsList(queryParam);
    };

    public DriverInformationDto getOriginalModels(Map<String, String> map){
        return mapper.getOriginalModels(map);
    };

    public List<LeVehiclemodels> getLeVehiclemodels(Map<String, String> map){
        return mapper.getLeVehiclemodels(map);
    };

    public void createPubVehicleModelsRef(PubVehicleModelsRef p){
        mapper.createPubVehicleModelsRef(p);
    };

    public void createPubVehicleModelsRefHistory(PubVehicleModelsRef p){
        mapper.createPubVehicleModelsRefHistory(p);
    };

    public void updatePubVehicleModelsRef(PubVehicleModelsRef p){
        mapper.updatePubVehicleModelsRef(p);
    };
    
    public List<Map<String, Object>> select2QueryJobnum(PubCoooperateQueryParam queryParam){
    	return mapper.select2QueryJobnum(queryParam);
    };
    
    public List<Map<String, Object>> select2QueryDriverInformation(PubCoooperateQueryParam queryParam){
    	return mapper.select2QueryDriverInformation(queryParam);
    };
}
