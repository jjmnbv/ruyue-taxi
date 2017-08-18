package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.PubVehicleModelsRefDao;
import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehclineDto;
import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehicleDto;
import com.szyciov.entity.PubVehicleModelsRef;
import com.szyciov.entity.PubVehicleModelsRefHistory;
import com.szyciov.util.GUIDGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class PubVehicleModelsRefService {
    @Autowired
    private PubVehicleModelsRefDao pubVehicleModelsRefDao;

    public void updateVehicleModelsRefByVehcline(UpdateVehicleModelsRefByVehclineDto param) {
        // 根据车系、租赁ID、查询车辆ID集合
        Map<String, Object> condi = new HashedMap();
        condi.put("vehclineid", param.getVehclineId());
        condi.put("platformtype", param.getPlatform());
        condi.put("leasescompanyid", param.getLeaseCompanyId());
        List<String> vehicleIdList = this.pubVehicleModelsRefDao.queryVehicleByVehcline(condi);

        // 删除原有车辆和车型关系 -- 根据租赁ID 和 车型ID
        Map<String, Object> condi2 = new HashedMap();
        condi2.put("companyid", param.getLeaseCompanyId());
        condi2.put("vehiclemodelsid", param.getVehiclemodels());
        condi2.put("leasecompanyid", param.getLeaseCompanyId());
        this.pubVehicleModelsRefDao.deleteVehicleModeRef(condi2);

        // 循环插入数据
        PubVehicleModelsRef ref;
        PubVehicleModelsRefHistory history;
        List refList = new ArrayList();
        List refHistoryList = new ArrayList();
        for (String vehicleId : vehicleIdList) {
            ref = new PubVehicleModelsRef();
            ref.setStatus(1);
            ref.setVehicleid(vehicleId);
            ref.setUpdatetime(new Date());
            ref.setCreatetime(new Date());
            ref.setCreater(param.getUpdater());
            ref.setUpdater(param.getUpdater());
            ref.setId(GUIDGenerator.newGUID());
            ref.setCompanyid(param.getLeaseCompanyId());
            ref.setVehiclemodelsid(param.getVehiclemodels());
            ref.setLeasecompanyid(param.getLeaseCompanyId());
            ref.setAssociationtype(1);
            refList.add(ref);

            history = new PubVehicleModelsRefHistory();
            history.setStatus(1);
            history.setVehicleid(vehicleId);
            history.setUpdatetime(new Date());
            history.setCreatetime(new Date());
            history.setCreater(param.getUpdater());
            history.setUpdater(param.getUpdater());
            history.setId(GUIDGenerator.newGUID());
            history.setCompanyid(param.getLeaseCompanyId());
            history.setVehiclemodelsid(param.getVehiclemodels());
            history.setLeasecompanyid(param.getLeaseCompanyId());
            history.setAssociationtype(1);
            refHistoryList.add(history);
        }
        // 保存关联关系
        this.pubVehicleModelsRefDao.insertPubVehicleModelsRef(refList);
        this.pubVehicleModelsRefDao.insertPubVehicleModelsRefHistory(refHistoryList);
    }

    public void updateVehicleModelsRefByVehicle(UpdateVehicleModelsRefByVehicleDto param) {
        // 根据租赁公司 平台 车系id 查询服务车型
        Map<String, Object> condi = new HashedMap();
        condi.put("vehclineid", param.getVehclineId().get(0));
        condi.put("platformtype", param.getPlatform());
        condi.put("leasescompanyid", param.getLeaseCompanyId());
        String vehiclemodelsId = this.pubVehicleModelsRefDao.queryVehiclemodelsByVehcline(condi);

        if (vehiclemodelsId == null || "".equals(vehiclemodelsId)) {
            return;
        }

        // 删除原有车辆和车型关系 -- 根据租赁ID 和 车型ID 车辆ID
        Map<String, Object> condi2 = new HashedMap();
        condi2.put("companyid", param.getLeaseCompanyId());
        condi2.put("vehicleid", param.getVehicleId());
        condi2.put("leasecompanyid", param.getLeaseCompanyId());
        this.pubVehicleModelsRefDao.deleteVehicleModeRef(condi2);

        // 插入当前数据
        PubVehicleModelsRef ref = new PubVehicleModelsRef();
        ref.setStatus(1);
        ref.setVehicleid(param.getVehicleId());
        ref.setUpdatetime(new Date());
        ref.setCreatetime(new Date());
        ref.setCreater(param.getUpdater());
        ref.setUpdater(param.getUpdater());
        ref.setId(GUIDGenerator.newGUID());
        ref.setCompanyid(param.getLeaseCompanyId());
        ref.setVehiclemodelsid(vehiclemodelsId);
        ref.setLeasecompanyid(param.getLeaseCompanyId());
        ref.setAssociationtype(1);
        // 保存关联关系
        List refList = new ArrayList();
        refList.add(ref);
        this.pubVehicleModelsRefDao.insertPubVehicleModelsRef(refList);

        PubVehicleModelsRefHistory history = new PubVehicleModelsRefHistory();
        history.setStatus(1);
        history.setVehicleid(param.getVehicleId());
        history.setUpdatetime(new Date());
        history.setCreatetime(new Date());
        history.setCreater(param.getUpdater());
        history.setUpdater(param.getUpdater());
        history.setId(GUIDGenerator.newGUID());
        history.setCompanyid(param.getLeaseCompanyId());
        history.setVehiclemodelsid(vehiclemodelsId);
        history.setLeasecompanyid(param.getLeaseCompanyId());
        List refHistoryList = new ArrayList();
        refHistoryList.add(history);
        this.pubVehicleModelsRefDao.insertPubVehicleModelsRefHistory(refHistoryList);
    }


}
