package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.PubVehicleModelsRefService;
import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehclineDto;
import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehicleDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZF on 2017/8/11.
 */
@RestController
@RequestMapping(value = "api/PubVehicleModelsRef")
public class PubVehicleModelsRefController {
    @Autowired
    private PubVehicleModelsRefService pubVehicleModelsRefService;

    /**
     * 通过车系更新车辆和车型关系
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateVehicleModelsRefByVehcline")
    public JSONObject updateVehicleModelsRefByVehcline(@RequestBody UpdateVehicleModelsRefByVehclineDto param) {
        JSONObject obj = new JSONObject();
        try {
            this.pubVehicleModelsRefService.updateVehicleModelsRefByVehcline(param);
            obj.put("status", "0");
        } catch (Exception ex) {
            obj.put("status", "1");
            obj.put("message", ex.getMessage());
        }
        return obj;
    }

    /**
     * 通过车辆ID更新车辆和车型关系
     *
     * @param param 参数
     * @return 状态
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateVehicleModelsRefByVehicle")
    public JSONObject updateVehicleModelsRefByVehicle(@RequestBody UpdateVehicleModelsRefByVehicleDto param) {
        JSONObject obj = new JSONObject();
        try {
            this.pubVehicleModelsRefService.updateVehicleModelsRefByVehicle(param);
            obj.put("status", "0");
        } catch (Exception ex) {
            obj.put("status", "1");
            obj.put("message", ex.getMessage());
        }
        return obj;
    }
}
