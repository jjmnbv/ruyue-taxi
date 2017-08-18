package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.lease.dao.PubCoooperateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.dto.pubCoooperate.DriverInformationDto;
import com.szyciov.entity.PubCoooperate;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.op.param.PubCoooperateQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("PubCoooperateService")
public class PubCoooperateService {

    @Autowired
    private PubCoooperateDao dao;
    @Resource(name = "PubCoooperateDao")
    public void setDao(PubCoooperateDao dao) {
        this.dao = dao;
    }

    public PageBean getPubCoooperateByQuery(PubCoooperateQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<PubCoooperate> list = dao.getPubCoooperateList(queryParam);
        int iTotalRecords = dao.getPubCoooperateListCount(queryParam);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    public List<PubCoooperate> getCompanyNameList(String id){
        return dao.getCompanyNameList(id);
    };

    public PageBean getResourceInformationByQuery(PubCoooperateQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<PubCoooperate> list = dao.getResourceInformationList(queryParam);
        int iTotalRecords = dao.getResourceInformationListCount(queryParam);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    public PageBean getDriverInformationByQuery(PubCoooperateQueryParam queryParam) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(queryParam.getsEcho());
        List<DriverInformationDto> list = dao.getDriverInformationList(queryParam);
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getDistributionVel()==null || list.get(i).getDistributionVel().equals("")){
                list.get(i).setDistributionVel("未分配");
            }

        }
        int iTotalRecords = dao.getDriverInformationListCount(queryParam);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }
    public List<DriverInformationDto> getModelsList(PubCoooperateQueryParam queryParam){
        return dao.getModelsList(queryParam);
    };

    public DriverInformationDto getOriginalModels(Map<String, String> map){
        return dao.getOriginalModels(map);
    };

    public List<LeVehiclemodels> getLeVehiclemodels(Map<String, String> map){
        return dao.getLeVehiclemodels(map);
    };
    public Map<String, String> createPubVehicleModelsRef(PubVehicleModelsRef p){
        Map<String, String> map = new HashMap<>();
        dao.createPubVehicleModelsRef(p);
        dao.createPubVehicleModelsRefHistory(p);
        map.put("ResultSign", "Successful");
        map.put("MessageKey", "分配成功");
        return map;
    };
    public Map<String, String> updatePubVehicleModelsRef(PubVehicleModelsRef p){
        Map<String, String> map = new HashMap<>();
        dao.updatePubVehicleModelsRef(p);
        p.setId(GUIDGenerator.newGUID());
        dao.createPubVehicleModelsRefHistory(p);
        map.put("ResultSign", "Successful");
        map.put("MessageKey", "修改成功");
        return map;
    };

    public List<Map<String, Object>> select2QueryJobnum(PubCoooperateQueryParam queryParam){
    	return dao.select2QueryJobnum(queryParam);
    };
    
    public List<Map<String, Object>> select2QueryDriverInformation(PubCoooperateQueryParam queryParam){
    	return dao.select2QueryDriverInformation(queryParam);
    };
//	public Map<String, String> deletePubCooagreement(String id){
//		Map<String, String> map = new HashMap<>();
//		dao.deletePubCooagreement(id);
//		map.put("ResultSign", "Successful");
//		map.put("MessageKey", "删除成功");
// 		return map;
//	};
}
