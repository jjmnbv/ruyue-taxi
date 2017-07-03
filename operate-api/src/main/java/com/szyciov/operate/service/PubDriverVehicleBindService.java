package com.szyciov.operate.service;

import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.operate.dao.PubDriverVehicleBindDao;
import com.szyciov.util.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PubDriverVehicleBindService {
	
    @Autowired
    private PubDriverVehicleBindDao bindDao;

    /**
     * 保存绑定类信息
     * @param bindDto
     * @param bindNum
     * @param bindInfos
     * @return
     */
    public int saveBind(BindDto bindDto,Integer bindNum,String bindInfos){

        PubDriverVehicleBind bind = new PubDriverVehicleBind();
        bind.setId(GUIDGenerator.newGUID());
        //司机ID
        bind.setDriverid(bindDto.getDriverID());
        //车辆ID
        bind.setVehicleid(bindDto.getVehicleid());
        //司机类型
        bind.setDrivertype(bindDto.getDrivertype());
        //绑定状态
        bind.setBindstate(BindingStateEnum.BINDING.code);
        //已绑定人数
        bind.setBindpersonnum(bindNum);
        //绑定信息
        bind.setBinddirverinfo(bindInfos);
        //创建时间
        bind.setCreatetime(new Date());
        //创建人
        bind.setCreater(bindDto.getCreater());
        //操作人
        bind.setOperator(bindDto.getCreater());

        return bindDao.save(bind);
    }

    /**
     * 保存解绑类信息
     * @param vehicleBind
     * @return
     */
    public int saveUnBind(PubDriverVehicleBind vehicleBind){
        return bindDao.save(vehicleBind);
    }

    public int deleteByPrimaryKey(String id){
        return bindDao.deleteByPrimaryKey(id);
    }

}