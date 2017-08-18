package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.PubCooresourceService;
import com.szyciov.dto.pubCooresource.UpdatePubCooresourceStatusDto;
import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehclineDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/PubCooresource")
public class PubCooresourceController {
    @Autowired
    private PubCooresourceService pubCooresourceService;

    @RequestMapping(method = RequestMethod.POST, value = "updateVehicleModelsRefByVehcline")
    @Deprecated
    public JSONObject updatePubCooresourceStatus(@RequestBody UpdatePubCooresourceStatusDto param) {
        JSONObject obj = new JSONObject();
        try {
//            this.pubCooresourceService.updatePubCooresourceStatus(param);
            obj.put("status", "0");
        } catch (Exception ex) {
            obj.put("status", "1");
            obj.put("message", ex.getMessage());
        }
        return obj;
    }


}
