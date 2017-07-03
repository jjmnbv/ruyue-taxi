package com.szyciov.operate.service;

import com.szyciov.operate.mapper.VehcMapper;
import com.szyciov.param.VehcParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liubangwei_lc on 2017/6/27.
 */
@Service("vehcService")
public class VehcService {

    @Autowired
    private VehcMapper vehcMapper;

    public VehcParam getVehcById(String vehcId){

        VehcParam vehc = vehcMapper.getVehcById(vehcId);
        return vehc;
    }
}
