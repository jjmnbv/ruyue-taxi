package com.szyciov.operate.dao;

import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.dto.driverShiftManagent.ProcessedQueryDto;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.operate.mapper.PeDrivershiftProcessedMapper;
import com.szyciov.param.PeDrivershiftProcessedParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PeDrivershiftProcessedDao {

    @Autowired
    private PeDrivershiftProcessedMapper processedMapper;

    public  int save(PeDrivershiftProcessed peDrivershiftProcessed){
        return processedMapper.save(peDrivershiftProcessed);
    }


    public List<ProcessedQueryDto> listProcessed(PeDrivershiftProcessedParam processedParam){
        return processedMapper.listProcessed(processedParam);
    }

    public int getlistProcessedCount(PeDrivershiftProcessedParam processedParam){
        return processedMapper.getlistProcessedCount(processedParam);
    }


    /**
     * 返回最后一次接班时间
     * @param relieveddriverid      接班司机ID
     * @param leasescompanyid       租赁公司id
     * @param platformtype          系统类型
     * @return
     */
    public Date getLastProcessedTime(String relieveddriverid,String leasescompanyid,String platformtype){
        return processedMapper.getLastProcessedTime(relieveddriverid,leasescompanyid,platformtype);
    }


    /**
     * 根据id 返回已交接班对象
     * @param id 已交接班id
     * @return
     */
    public PeDrivershiftProcessed getProcessed(String id){
        return processedMapper.getProcessed(id);
    }

    /**
     * 修改订单司机
     * @param orderno       订单号
     * @param newDriverId   新的司机ID
     * @return
     */
    public int updateOrderDriverId(String orderno,String newDriverId){
        return processedMapper.updateOrderDriverId(orderno, newDriverId);
    }

    /**
     * 返回出租车订单
     * @param oldDriverId   交班司机ID
     * @return
     */
    public List<String> listTaxiOrderno(String oldDriverId,String leasesCompanyId){
        return processedMapper.listTaxiOrderno(oldDriverId,leasesCompanyId);
    }


    public List<PubDriverSelectDto> listShiftRecordDriver(String platformtype, String vehicleid, String shifitType, String queryText){

        return processedMapper.listShiftRecordDriver(platformtype,vehicleid,shifitType,queryText);

    }


 }