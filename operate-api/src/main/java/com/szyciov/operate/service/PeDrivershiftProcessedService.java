package com.szyciov.operate.service;

import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.dto.driverShiftManagent.ProcessedQueryDto;
import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.message.ProcessedMessage;
import com.szyciov.op.entity.OpTaxidriverchanges;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.operate.dao.*;
import com.szyciov.operate.dto.VehicleQueryDto;
import com.szyciov.param.PeDrivershiftProcessedParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeDrivershiftProcessedService {

    Logger logger = LoggerFactory.getLogger(PeDrivershiftProcessedService.class);

    @Autowired
    private PeDrivershiftProcessedDao processedDao;

    @Autowired
    private PubDriverService driverService;

    @Autowired
    private LeLeasescompanyDao leasescompanyDao;

    @Autowired
    private PeDrivershiftPendingDao pendingDao;

    @Autowired
    private PubDriverDao driverDao;

    @Autowired
    private PubDriverVehicleRefService refService;

    @Autowired
    private PubVehicleDao pubVehicleDao;

    @Autowired
    private TaxiOrderManageDao taxiOrderManageDao;


    /**
     * 保存交接班已处理记录
     * @param processedSaveDto
     * @return
     */
    @Transactional
    public boolean save(ProcessedSaveDto processedSaveDto)throws Exception{
        PeDrivershiftPending paramPend = new PeDrivershiftPending();
        paramPend.setId(processedSaveDto.getPendingId());
        //是否存在待交班
        PeDrivershiftPending pending = pendingDao.getPendingInfo(paramPend);

        if(pending!=null){
            //绑定关系是否存在
            boolean isBind = refService.isBind(pending.getDriverid(),pending.getVehicleid());

            if(isBind){

                PeDrivershiftProcessed processed = new PeDrivershiftProcessed();
                processed.setId(pending.getId());
                processed.setLeasescompanyid(pending.getLeasescompanyid());
                processed.setOndutydriverid(pending.getDriverid());
                processed.setOndutydriverinfo(pending.getDriverinfo());
                processed.setOnlinetime(pending.getOnlinetime());
                processed.setPlatformtype(pending.getPlatformtype());
                processed.setProcessperson(processedSaveDto.getProcessperson());
                processed.setProcesspersonname(processedSaveDto.getProcesspersonname());
                processed.setProcesstime(new Date());
                processed.setRelieveddriverid(processedSaveDto.getRelieveddriverid());
                processed.setRelieveddriverinfo(processedSaveDto.getRelieveddriverInfo());
                processed.setRelievedtype(processedSaveDto.getRelievedtype());
                processed.setShifttype(processedSaveDto.getShifttype());
                processed.setUpdater(processedSaveDto.getProcessperson());
                processed.setUpdatetime(new Date());
                processed.setVehicleid(pending.getVehicleid());
                processed.setApplytime(pending.getApplytime());
                processed.setCreater(pending.getCreater());
                processed.setCreatetime(pending.getCreatetime());
                processed.setOvertimetime(pending.getOvertimetime());
                //保存数据至 已处理表
                int count = processedDao.save(processed);
                //保存成功 才jinxing
                if(count!=1){
                    logger.warn("处理交接班影响条数为0：{}"+ JSONObject.fromObject(processed));
                    return false;
                }

                //获取租赁公司信息
                LeLeasescompany leasesCompany = leasescompanyDao.getLeLeasescompanyById(processed.getLeasescompanyid());

                //删除 待处理表记录
                pendingDao.removeById(pending.getId());

                //交班成功调用逻辑
                if(PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code.equals(processedSaveDto.getShifttype())){
                    //修改交班司机 为歇班状态
                    this.updateDriverPassWork(processed.getOndutydriverid(),processed.getUpdater(),DriverEnum.PASS_WORK_STATUS_OFF);
                    //修改接班司机 为当班状态
                    this.updateDriverPassWork(processed.getRelieveddriverid(),processed.getUpdater(),DriverEnum.PASS_WORK_STATUS_ON);
                    //修改车辆 当班司机ID 为接班司机ID
                    this.updateVehicleDriverId(processed.getVehicleid(),processed.getRelieveddriverid(),processed.getUpdater());
                    //交班成功发送短信及推送
                    this.sendProcessedMessage(processed,leasesCompany);
                    //修改订单状态
                    this.changeOrder(processed.getOndutydriverid(),processed.getRelieveddriverid(),leasesCompany.getId());

                }

                //车辆回收调用逻辑
                if(PeDrivershiftEnum.SHIFT_TYPE_RECYCLE.code.equals(processedSaveDto.getShifttype())){
                    //更改司机状态
                    this.updateDriverStatus(processed.getVehicleid(),processed.getUpdater());
                    //获取司机电话
                    String driverPhone = driverService.getById(processed.getOndutydriverid()).getPhone();

                    //获取车牌号码
                    PubVehicleQueryParam pubVehicleQueryParam = new PubVehicleQueryParam();
                    pubVehicleQueryParam.setVehicleId(processed.getVehicleid());
                    pubVehicleQueryParam.setPlatformtype(PlatformTypeByDb.OPERATING.code);
                    pubVehicleQueryParam.setiDisplayLength(10);
                    List<VehicleQueryDto> list = pubVehicleDao.listVehicleAndBindInfo(pubVehicleQueryParam);
                    String platonStr="";
                    if(list!=null &&list.size()>0){
                        platonStr= list.get(0).getPlatenoStr();
                    }

                    this.sendRecycleMessage(driverPhone,platonStr,leasesCompany);
                }

                //清空交班司机在线时长
                this.cleanDriverOnlineTime(processed.getOndutydriverid(),processed.getUpdater());

                return true;
            }

            return false;
        }

        return false;
    }


    /**
     * 更新更改司机状态
     */
    private void updateDriverStatus(String vehicleId,String updater){

        List<VehicleBindInfoDto> list = refService.listAllVehicleBindInfo(vehicleId);
        for(VehicleBindInfoDto dto:list) {
            PubDriver driver = new PubDriver();
            driver.setPassworkStatus(DriverEnum.PASS_WORK_STATUS_NOPLAN.code);
            driver.setWorkStatus(DriverEnum.WORK_STATUS_OFFLINE.code);
            driver.setId(dto.getDriverID());
            driver.setUpdater(updater);
            driver.setUpdateTime(new Date());
            driverDao.updatePubDriverStatus(driver);
        }
    }
    /**
     * 发送交班成功短信及推送
     * @param processed
     * @param leasesCompany
     */
    private void sendProcessedMessage(PeDrivershiftProcessed processed,LeLeasescompany leasesCompany){

        //获取交班司机对象
        PubDriver ondutyDriver = driverService.getById(processed.getOndutydriverid());
        //获取接班司机对象
        PubDriver reliveDriver = driverService.getById(processed.getRelieveddriverid());

        logger.info("------------------获取司机电话："+reliveDriver.getPhone());
        //String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PeDrivershiftProcessedService.processed",
        //        ondutyDriver.getName()+ondutyDriver.getPhone(),leasesCompany.getPhone(),leasesCompany.getShortName());
        String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PeDrivershiftProcessedService.processed",
                ondutyDriver.getName()+ondutyDriver.getPhone(),leasesCompany.getPhone());
        logger.info("------------------发送内容："+content);
        //发送短信
        ProcessedMessage um = new ProcessedMessage(reliveDriver.getPhone(),content,
                PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code,null);
        MessageUtil.sendMessage(um);
        logger.info("------------------短信放入队列完毕！---------------");

        if(PeDrivershiftEnum.RELIVED_TYPE_AUTONOMOUSLY.code.equals(processed.getRelievedtype())){
            logger.info("------------------发送自主交班推送---------------");
            /**************交班司机推送****************/
            String message = reliveDriver.getName()+"司机已经接班，您可以好好休息了！";
            //发送短信及推送
            um = new ProcessedMessage(ondutyDriver.getPhone(),message,
                    PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code, PushObjFactory.HINT_SHIFT_PENDINGED);
            MessageUtil.sendMessage(um);
            logger.info("------------------发送内容:{}",message);
        }


        if(PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.code.equals(processed.getRelievedtype())){
            logger.info("------------------发送人工交班推送---------------");
            /**************交班司机推送****************/
            String message = reliveDriver.getName()+"司机已由客服指派接班，您可以好好休息了！";
            logger.info("------------------发送内容:{}",message);
            //发送推送
            um = new ProcessedMessage(ondutyDriver.getPhone(),message,
                    PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code,PushObjFactory.HINT_SHIFT_PENDINGED);
            MessageUtil.sendMessage(um);



            logger.info("------------------发送人工交班推送---------------");
            /**************接班司机推送****************/
            message = "客服已指派您接班，请尽快打卡上班，努力加油哦！";
            logger.info("------------------发送内容:{}",message);
            //发送推送
            um = new ProcessedMessage(reliveDriver.getPhone(),message,
                    PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code,PushObjFactory.HINT_SHIFT_PROCESSED);
            MessageUtil.sendMessage(um);
        }

    }


    /**
     * 发送车辆回收短信及推送
     * @param driverPhone
     * @param platonStr
     * @param leasesCompany
     */
    private void sendRecycleMessage(String driverPhone,String platonStr,LeLeasescompany leasesCompany){
        //获取短信内容模板
        //String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PeDrivershiftProcessedService.recycle",
         //       platonStr,leasesCompany.getPhone(),leasesCompany.getShortName());
    	String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PeDrivershiftProcessedService.recycle",
    	                  platonStr,leasesCompany.getPhone());

        logger.info("------------------获取司机电话："+driverPhone);
        logger.info("------------------发送内容："+content);
        //车辆回收短信

        ProcessedMessage um  = new ProcessedMessage(driverPhone,content,
                PeDrivershiftEnum.SHIFT_TYPE_RECYCLE.code,PushObjFactory.HINT_SHIFT_RECYCLE);
        MessageUtil.sendMessage(um);

        logger.info("------------------放入队列完毕！！");
    }

    /**
     * 订单变更
     */
    private void changeOrder(String ondutydriverid,String relieveddriverid,String leasesCompanyId){
        List<String> orderNolist = processedDao.listTaxiOrderno(ondutydriverid,leasesCompanyId);
        if(orderNolist!=null&&orderNolist.size()>0){
            for(String orderNo:orderNolist){
                //变更订单司机
                processedDao.updateOrderDriverId(orderNo,relieveddriverid);
                //保存订单记录
                this.saveOrderChange(ondutydriverid,relieveddriverid,orderNo);
            }
        }
    }

    /**
     * 保存订单变更记录
     * @param beforeDriverid    更换前司机id
     * @param afterDriverid     更换后司机id
     * @param orderno           订单号
     */
    private void saveOrderChange(String beforeDriverid,
                                 String afterDriverid,
                                 String orderno){
        //添加更换司机记录
        OpTaxidriverchanges taxidriverchanges = new OpTaxidriverchanges();
        taxidriverchanges.setId(GUIDGenerator.newGUID());
        taxidriverchanges.setOrderno(orderno);
        taxidriverchanges.setBeforedriverid(beforeDriverid);
        taxidriverchanges.setAfterdriverid(afterDriverid);
        taxidriverchanges.setReason("交接班");
        taxidriverchanges.setOperator(beforeDriverid);
        taxiOrderManageDao.insertOpTaxidriverchanges(taxidriverchanges);
    }

    /**
     * 根据ID 返回交接班已处理对象
     * @param id        交接班已处理ID
     * @return
     */
    public  PeDrivershiftProcessed getProcessed(String id){
        return processedDao.getProcessed(id);
    }


    /**
     * 更新司机状态
     * @param id                记录ID
     * @param personId          处理人ID
     * @param driverEnum        司机枚举
     */
    public void updateDriverPassWork(String id,String personId,DriverEnum driverEnum){
        PubDriver driver = new PubDriver();
        driver.setPassworkStatus(driverEnum.code);
        driver.setId(id);
        driver.setUpdater(personId);
        driver.setUpdateTime(new Date());

        driverDao.updatePubDriverStatus(driver);

    }

    /**
     * 清除交班司机在线时长
     * @param id
     * @param personId
     */
    private void cleanDriverOnlineTime(String id,String personId){
        PubDriver driver = new PubDriver();
        driver.setId(id);
        driver.setUpdater(personId);
        driver.setUpdateTime(new Date());
        driverDao.cleanPubDriverOnlineTime(driver);
    }

    /**
     * 修改车辆状态
     * @param vehicleId         车辆ID
     * @param personId          处理人ID
     * @param vehicleStatus     车辆状态

    public void updateVehicleStatus(String vehicleId,String personId,String vehicleStatus){
        PubVehicle vehicle = new PubVehicle();
        vehicle.setVehicleStatus(vehicleStatus);
        vehicle.setId(vehicleId);
        vehicle.setUpdateTime(new Date());
        vehicle.setUpdater(personId);
        pubVehicleDao.updateVehicleById(vehicle);
    }*/

    /**
     * 修改车辆当班司机ID
     * @param vehicleId     车辆ID
     * @param driverId      司机ID
     * @param personId      处理人ID
     */
    private void updateVehicleDriverId(String vehicleId,String driverId,String personId){
        PubVehicle vehicle = new PubVehicle();
        vehicle.setDriverId(driverId);
        vehicle.setId(vehicleId);
        vehicle.setUpdater(personId);
        vehicle.setUpdateTime(new Date());
        pubVehicleDao.updateDriverId(vehicle);
    }



    /**
     * 保存指派当班
     * @param processed 交班保存对象
     * @return
     */
    public boolean saveAssign(PeDrivershiftProcessed processed){
        processed.setId(GUIDGenerator.newGUID());
        //保存数据至 已处理表
        processedDao.save(processed);

        //修改接班司机 为当班状态
        this.updateDriverPassWork(processed.getRelieveddriverid(),processed.getUpdater(),DriverEnum.PASS_WORK_STATUS_ON);

        //修改车辆 当班司机ID
        this.updateVehicleDriverId(processed.getVehicleid(),processed.getRelieveddriverid(),processed.getUpdater());

        return true;

    }



    /**
     * 查询已处理交班列表
     * @param processedParam      已处理交班查询对象
     * @return
     */
    public PageBean listProcessed(PeDrivershiftProcessedParam processedParam){

        List<ProcessedQueryDto> list = processedDao.listProcessed(processedParam);

        PageBean pageBean = new PageBean();
        pageBean.setsEcho(processedParam.getsEcho());

        int iTotalRecords = processedDao.getlistProcessedCount(processedParam);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 获取交接班司机记录
     * @param platformtype  系统类型
     * @param vehicleid     车辆ID
     * @param shifitType   交班类型
     * @return
     */
    public List<PubDriverSelectDto> listShiftRecordDriver(String platformtype, String vehicleid, String shifitType, String queryText){

        return processedDao.listShiftRecordDriver(platformtype,vehicleid,shifitType,queryText);

    }


}