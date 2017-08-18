package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.PubCooresourceDao;
import com.szyciov.dto.pubCooresource.UpdatePubCooresourceStatusDto;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ZF on 2017/8/11.
 */
@Service
public class PubCooresourceService {
    @Autowired
    private PubCooresourceDao pubCooresourceDao;

    @Deprecated
    public void updatePubCooresourceStatus(UpdatePubCooresourceStatusDto param) {
        if (param.getType() == 0) {
            Map<String, Object> cond1 = new HashedMap();

            cond1.put("leasecompanyid", param.getLeaseCompanyId());
            cond1.put("enabled", param.getStatus());
            cond1.put("vehicleid", param.getVehicleId());
            this.pubCooresourceDao.updatePubCooresourceStatus(cond1);
        } else if (param.getType() == 1) {
            Map<String, Object> cond1 = new HashedMap();
            cond1.put("vehicleid", param.getVehicleId());
            int driverCount = this.pubCooresourceDao.queryTaxiEnableDriver(cond1);
            int status = 0;
            if(param.getStatus() == 1){
                if(driverCount == 1){
                    status = 1;
                }
            }

            Map<String, Object> cond2 = new HashedMap();

            cond2.put("leasecompanyid", param.getLeaseCompanyId());
            cond2.put("vehicleid", param.getVehicleId());
            cond1.put("enabled", status);
            this.pubCooresourceDao.updatePubCooresourceStatus(cond2);
        }
    }
}
