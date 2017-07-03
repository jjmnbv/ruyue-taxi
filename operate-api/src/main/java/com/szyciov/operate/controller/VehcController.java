package com.szyciov.operate.controller;

import com.szyciov.operate.service.VehcService;
import com.szyciov.param.VehcParam;
import com.szyciov.util.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liubangwei_lc on 2017/6/27.
 */
@Controller
public class VehcController extends BaseController {

    private static final Logger logger = Logger.getLogger(VehcController.class);

    @Autowired
    private VehcService vehcService;

    @RequestMapping(value = "api/Vehc/GetVehcById/{vehcId}", method = RequestMethod.GET)
    @ResponseBody
    public VehcParam getVehcById(@PathVariable String vehcId)  {
        return vehcService.getVehcById(vehcId);
    }

}
