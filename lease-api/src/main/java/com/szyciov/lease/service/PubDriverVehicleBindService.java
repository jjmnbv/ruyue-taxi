package com.szyciov.lease.service;

import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.dto.driverVehicleBindManage.CarBindRecordDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.lease.dao.PubDriverVehicleBindDao;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindRecordDto;
import com.szyciov.lease.param.drivervehiclebind.CarBindRecordQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PubDriverVehicleBindService {

    private Logger logger = LoggerFactory.getLogger(PubDriverVehicleBindService.class);
	
    @Autowired
    private PubDriverVehicleBindDao bindDao;

    @Autowired
    private PubDriverVehicleRefService refService;

    /**
     * 保存绑定类信息
     * @param bindDto
     * @return
     */
    public int saveBind(BindDto bindDto){

        //获取车辆绑定信息
        List<VehicleBindInfoDto> listBindInfo = this.refService.listAllVehicleBindInfo(bindDto.getVehicleid());

        int bindNum = 0;
        String bindInfos = "/";
        if(listBindInfo==null || listBindInfo.size()==0){
            logger.warn("------------未获取到车辆绑定司机信息-----------bindDto：{}", JSONObject.fromObject(bindDto));
        }else{
            bindNum = listBindInfo.size();
            bindInfos = this.refService.getVehicleBindInfos(listBindInfo);
        }

        PubDriverVehicleBind bind = new PubDriverVehicleBind();

        bind.setId(GUIDGenerator.newGUID());
        //司机ID
        bind.setDriverid(bindDto.getDriverID());
        //车辆ID
        bind.setVehicleid(bindDto.getVehicleid());
        //司机类型
        bind.setDrivertype(bindDto.getDrivertype());
        //绑定状态
        bind.setBindstate(bindDto.getBindStatus());
        //已绑定人数
        bind.setBindpersonnum(bindNum);
        //操作原因
        bind.setUnbindreason(bindDto.getUnbindreason());
        //绑定信息
        bind.setBinddirverinfo(bindInfos);
        //创建时间
        bind.setCreatetime(new Date());
        //创建人
        bind.setCreater(bindDto.getCreater());
        //操作人
        bind.setOperator(bindDto.getCreater());
        //解绑人数
        bind.setUnbindpersonnum(bindDto.getUnBindNum());
        //解绑司机信息
        bind.setUnbinddirverinfo(bindDto.getUnBindDriverInfo());

        return bindDao.save(bind);
    }


    /**
     * 查询网约车绑定日志
     * @param param 查询条件
     * @return
     */
    public PageBean listCarBindRecord(CarBindRecordQueryParam param){

        PageBean pageBean = new PageBean();
        pageBean.setsEcho(param.getsEcho());

        if(StringUtils.isEmpty(param.getQueryType())){
            pageBean.setiTotalDisplayRecords(0);
            pageBean.setiTotalRecords(0);
            pageBean.setAaData(new ArrayList());
        }else {
            List<CarBindRecordDto> list = bindDao.listCarBindRecord(param);

            int iTotalRecords = bindDao.countCarBindRecord(param);
            int iTotalDisplayRecords = iTotalRecords;
            pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
            pageBean.setiTotalRecords(iTotalRecords);
            pageBean.setAaData(list);
        }

        return pageBean;
    }

    /**
     * 查询出租车绑定日志
     * @param param 查询条件
     * @return
     */
    public PageBean listTaxiBindRecord(CarBindRecordQueryParam param){

        PageBean pageBean = new PageBean();
        pageBean.setsEcho(param.getsEcho());

        if(StringUtils.isEmpty(param.getQueryType())){
            pageBean.setiTotalDisplayRecords(0);
            pageBean.setiTotalRecords(0);
            pageBean.setAaData(new ArrayList());
        }else {
            List<TaxiBindRecordDto> list = bindDao.listTaxiBindRecord(param);
            int iTotalRecords = bindDao.countTaxiBindRecord(param);
            int iTotalDisplayRecords = iTotalRecords;
            pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
            pageBean.setiTotalRecords(iTotalRecords);
            pageBean.setAaData(list);
        }

        return pageBean;
    }


}