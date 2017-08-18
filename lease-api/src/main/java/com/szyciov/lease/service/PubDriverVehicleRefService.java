package com.szyciov.lease.service;

import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.dto.driverVehicleBindManage.UnBindDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.dto.pubCooresource.UpdatePubCooresourceStatusDto;
import com.szyciov.entity.City;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.enums.*;
import com.szyciov.lease.dao.LeLeasescompanyDao;
import com.szyciov.lease.dao.PubDriverDao;
import com.szyciov.lease.dao.PubDriverVehicleRefDao;
import com.szyciov.lease.dao.PubVehicleDao;
import com.szyciov.lease.dto.drivervehiclebind.car.CarBindInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.car.UnBindVehicleInfoDto;
import com.szyciov.lease.dto.drivervehiclebind.taxi.TaxiBindInfoDto;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.drivervehiclebind.CarBindInfoQueryParam;
import com.szyciov.lease.param.drivervehiclebind.TaxiBindInfoQueryParam;
import com.szyciov.message.DriverVehicleBindMessage;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人车绑定service
 */
@Service
public class PubDriverVehicleRefService {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleBindService.class);


    @Autowired
    private PubDriverVehicleRefDao refDao;

    @Autowired
    private PubDriverVehicleBindService bindService;

    @Autowired
    private PubDriverDao driverDao;

    @Autowired
    private PubVehicleDao vehicleDao;

    @Autowired
    private PubDriverService pubDriverService;

    @Autowired
    private LeLeasescompanyDao leasescompanyDao;

    @Autowired
    private PeDrivershiftPendingService pendingService;

    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 保存绑定关系
     * @param bindDto
     * @return
     */
    private String saveBind(BindDto bindDto) {

        String id = GUIDGenerator.newGUID();

        PubDriverVehicleRef ref = new PubDriverVehicleRef();

        ref.setId(id);

        ref.setVehicleid(bindDto.getVehicleid());

        ref.setDriverid(bindDto.getDriverID());

        ref.setCreatetime(new Date());

        ref.setCreater(bindDto.getCreater());

        refDao.save(ref);

        //添加至绑定信息表
        bindService.saveBind(bindDto);

        return id;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void saveCarBind(BindDto bindDto) throws Exception {
        this.saveBind(bindDto);
        //修改司机相关状态
        this.updateDriverBind(bindDto.getDriverID(), bindDto.getCreater(), null);
        //更新车辆相关状态
        this.updateCarVehicleBind(bindDto.getVehicleid(), bindDto.getCreater(), bindDto.getDriverID());

        this.updateCooresourceStatus(bindDto.getLeaseCompanyId(),bindDto.getVehicleid(),Integer.parseInt(VehicleEnum.VEHICLE_TYPE_CAR.code),0);

        //发送绑定消息
        this.sendBindMessage(bindDto);
    }

    /**
     * 出租车绑定
     *
     * @param bindDto
     */
    @Transactional(rollbackFor = {Exception.class})
    public void saveTaxiBind(BindDto bindDto) throws Exception {

        //绑定
        this.saveBind(bindDto);
        //获取车辆对应的绑定司机
        List<VehicleBindInfoDto> list = this.listAllVehicleBindInfo(bindDto.getVehicleid());
        if(list!=null&&list.size()>0){
            //获取对应绑定车辆信息
            PubVehicle pubVehicle = vehicleDao.getById(bindDto.getVehicleid());

            if(pubVehicle!=null) {
                //更新车辆相关状态
                this.updateTaxiVehicleBind(pubVehicle, bindDto.getCreater(), bindDto.getDriverID(), list);
                //更改司机相关状态
                this.updateTaxiDriverOfBind(list,pubVehicle,bindDto);

                this.updateCooresourceStatus(bindDto.getLeaseCompanyId(),bindDto.getVehicleid(),Integer.parseInt(VehicleEnum.VEHICLE_TYPE_TAXI.code),0);
            }else{
                logger.error("绑定车辆不存在，车辆ID：{}",bindDto.getVehicleid());
            }
        }

        //发送绑定消息
        this.sendBindMessage(bindDto);

    }

    /**
     * 修改出租车司机撞他
     * @param list          车辆绑定司机集合
     * @param pubVehicle    车辆信息
     * @param bindDto       绑定信息dto
     */
    private void updateTaxiDriverOfBind(List<VehicleBindInfoDto> list,PubVehicle pubVehicle,BindDto bindDto){

        DriverEnum driverEnum =  DriverEnum.PASS_WORK_STATUS_NO;
        //如果仅有一条绑定关系，司机状态改为无对班
        if (list.size() == 1) {
            driverEnum =  DriverEnum.PASS_WORK_STATUS_NO;
        } else if (list.size() > 1){
            if (StringUtils.isNotEmpty(pubVehicle.getDriverId())) {
                //获取车辆对应司机信息
                PubDriver driver = driverDao.getByPubDriverId(pubVehicle.getDriverId());
                //如果司机状态为当班，则后续绑定司机状态为歇班
                if(DriverEnum.PASS_WORK_STATUS_ON.code.equals(driver.getPassworkStatus())||
                    DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(driver.getPassworkStatus())){
                    driverEnum =  DriverEnum.PASS_WORK_STATUS_OFF;
                }else{
                    //如果不为当班，则后续绑定状态为未排班
                    driverEnum =  DriverEnum.PASS_WORK_STATUS_NOPLAN;
                    //如果车辆对应司机状态不为未排班，则将该司机状态改为未排班
                    if(!DriverEnum.PASS_WORK_STATUS_NOPLAN.code.equals(driver.getPassworkStatus())){
                        //修改司机相关状态
                        this.updateDriverPassWork(driver.getId(), bindDto.getCreater(), driverEnum);
                    }
                }
            }
        }
        //修改司机相关状态
        this.updateDriverBind(bindDto.getDriverID(), bindDto.getCreater(), driverEnum);


    }

    /**
     * 发送绑定消息
     *
     * @param bindDto
     */
    private void sendBindMessage(BindDto bindDto) {

        //获取司机电话
        PubDriver driver = driverDao.getById(bindDto.getDriverID());
        PubVehicle vehicle = vehicleDao.getById(bindDto.getVehicleid());
        LeLeasescompany leasesCompany = leasescompanyDao.getLeLeasescompanyById(vehicle.getLeasesCompanyId());
        //车牌号
        String plateNoStr = vehicle.getPlateNoProvinceName() + vehicle.getPlateNoCityName() + vehicle.getPlateNo();


        String content = "您已和【" + plateNoStr + "】绑定";
        //发送推送
        DriverVehicleBindMessage um = new DriverVehicleBindMessage(driver.getPhone(), content, BindingStateEnum.BINDING,WindowVisualEnum.TANCHUANG.msg);
        MessageUtil.sendMessage(um);

        content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PubDriverVehicleRefService.bind", plateNoStr, leasesCompany.getServicesPhone());
        //保存系统消息
        this.saveDriverNews("35","绑定成功",bindDto.getDriverID(),content);

    }

    /**
     * 返回司机绑定信息集合
     * @param driverId
     * @return
     */
    public List<PubDriverVehicleRef> listDriverBindInfo(String driverId){
        return this.refDao.listPubDriverVehicleRef(null, driverId);
    }

    /**
     * 返回车辆绑定信息集合
     * @param vehicleId
     * @return

    public List<PubDriverVehicleRef> listVehicleBindInfo(String vehicleId){
        return this.refDao.listPubDriverVehicleRef(vehicleId, null);
    } */


    /**
     * 解除绑定
     * @param unBindDto
     */

    private boolean unBind(UnBindDto unBindDto){

        //改为查询结果集，如果存在同一个司机、车辆有多条绑定记录(脏数据)，则解绑时一起解绑；
        List<PubDriverVehicleRef> refList = this.refDao.listPubDriverVehicleRef(unBindDto.getVehicleId(),unBindDto.getDriverId());
        if(refList!=null && refList.size()>0) {
            for(PubDriverVehicleRef ref:refList) {
                //删除绑定信息
                this.refDao.deleteByPrimaryKey(ref.getId());
            }
            return true;
        }else{
            logger.warn("未找到绑定记录：vehicleID:{},driverID:{}",unBindDto.getVehicleId(),unBindDto.getDriverId());
            return false;
        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void unCarBind(UnBindDto unBindDto)throws Exception{

        boolean isSuccess = this.unBind(unBindDto);

        if(isSuccess) {
            BindDto bindDto = this.createBindDto(unBindDto);
            bindDto.setUnBindDriverInfo(pubDriverService.getNameAndPhoneStr(unBindDto.getDriverId()));
            bindDto.setUnBindNum(1);
            //保存绑定记录表
            this.bindService.saveBind(bindDto);

            //修改车辆状态
            this.updateCarVehicleUnbind(unBindDto.getVehicleId(), unBindDto.getCreater());
            //修改司机状态为解绑
            this.updateDriverUnBind(unBindDto.getDriverId(), unBindDto.getCreater(),null);

            this.updateCooresourceStatus(unBindDto.getLeaseCompanyId(),unBindDto.getVehicleId(),Integer.parseInt(VehicleEnum.VEHICLE_TYPE_CAR.code),1);

            //发送解除绑定消息
            this.sendUnbindMessage(unBindDto);
        }

    }

    /**
     * 创建绑定信息
     * @param unBindDto
     * @return
     */
    private BindDto createBindDto(UnBindDto unBindDto){

        BindDto bindDto = new BindDto();
        bindDto.setDriverID(unBindDto.getDriverId());
        bindDto.setVehicleid(unBindDto.getVehicleId());
        bindDto.setDrivertype(unBindDto.getDrivertype());
        bindDto.setBindStatus(BindingStateEnum.UN_BINDING.code);
        bindDto.setCreater(unBindDto.getCreater());
        bindDto.setUnbindreason(unBindDto.getUnBindStr());
        return bindDto;
    }

    /**
     * 解除绑定
     * @param unBindDto
     */
    @Transactional(rollbackFor = {Exception.class})
    public void unTaxiBind(UnBindDto unBindDto)throws Exception{

        StringBuffer unBind = new StringBuffer();
        int unBindNum = 0;
        //是否无对班
        boolean isProcessing = false;
        //循环司机集合
        for(String taxiDriver:unBindDto.getTaxiDrivers()){
            //将ID设置为要解除的ID
            unBindDto.setDriverId(taxiDriver);
            PubDriver driver = driverDao.getByPubDriverId(taxiDriver);
            //是否接班司机
            if(DriverEnum.PASS_WORK_STATUS_PROCESSIND.code.equals(driver.getPassworkStatus())){
                isProcessing = true;
            }
            //解除绑定
            boolean isSuccess =  this.unBind(unBindDto);
            if(isSuccess) {
                unBindNum++;

                //如果是接班司机，需要判断是否推送消息
                if(isProcessing){
                    this.sendMessage2PendingDriver(unBindDto.getVehicleId());
                }
                //修改车辆状态
                this.updateTaxiVehicleUnBind(unBindDto.getVehicleId(), unBindDto.getCreater(),unBindDto.getDriverId());
                //修改司机状态为解绑
                this.updateDriverUnBind(unBindDto.getDriverId(), unBindDto.getCreater(),DriverEnum.PASS_WORK_STATUS_NO);

                //更改绑定关系
                this.updateCooresourceStatus(driver.getLeasesCompanyId(),unBindDto.getVehicleId(),Integer.parseInt(VehicleEnum.VEHICLE_TYPE_TAXI.code),1);

                //发送解除绑定消息
                this.sendUnbindMessage(unBindDto);

                String str = pubDriverService.getNameAndPhoneStr(taxiDriver);
                if (StringUtils.isNotEmpty(str)) {
                    unBind.append(str + ",");
                }
            }
        }

        BindDto bindDto = this.createBindDto(unBindDto);
        //记录解绑司机
        if(StringUtils.isNotEmpty(unBind.toString())) {
            bindDto.setUnBindDriverInfo(unBind.toString().substring(0, unBind.toString().length()-1));
            bindDto.setUnBindNum(unBindNum);
        }

        this.bindService.saveBind(bindDto);
    }

    /**
     * 更改状态 车辆绑定关系
     * @param leaseCompanyId    租赁公司ID
     * @param vehicleId         车辆ID
     * @param type              车辆类型
     * @param status            绑定状态
     */
    private void updateCooresourceStatus(String leaseCompanyId,String vehicleId,int type,int status)throws Exception{
        UpdatePubCooresourceStatusDto updatePubCooresourceStatusDto = new UpdatePubCooresourceStatusDto();
        updatePubCooresourceStatusDto.setLeaseCompanyId(leaseCompanyId);
        updatePubCooresourceStatusDto.setType(type);
        updatePubCooresourceStatusDto.setVehicleId(vehicleId);
        updatePubCooresourceStatusDto.setStatus(status);

        JSONObject jsonObject =  templateHelper.dealRequestWithTokenCarserviceApiUrl("/PubCooresource/updateVehicleModelsRefByVehcline",
            HttpMethod.POST, null, updatePubCooresourceStatusDto,JSONObject.class);

        if(jsonObject!=null){
            if(!jsonObject.getString("status").equals("0")){
                throw new Exception("更改车辆绑定关系失败");
            }
        }
    }

    /**
     * 发送推送给交班司机
     * @param vehicleId
     */
    private void sendMessage2PendingDriver(String vehicleId)throws Exception{
        PubVehicle vehicle = vehicleDao.getById(vehicleId);
        PubDriver pendingDriver = driverDao.getByPubDriverId(vehicle.getDriverId());
        JSONObject contentJson = null;
        //如果只剩一个绑定司机，则发送推送
        if(vehicle.getBindPersonNum()==1) {
            contentJson = new JSONObject();
            contentJson.put("operation","off");
            contentJson.put("content","您当前没有对班司机，请收工");
        }else if(vehicle.getBindPersonNum()>1){
            contentJson = new JSONObject();
            contentJson.put("operation","field");
            contentJson.put("content","交班失败");
        }

        if(contentJson!=null) {
            //删除对应交班记录
            pendingService.removeShfitApplyByDriver(pendingDriver.getId());

            DriverVehicleBindMessage message = new DriverVehicleBindMessage(pendingDriver.getPhone(),contentJson.getString("content"),PushObjFactory.HINT_SHIFT_DRIVER_UNBIND,contentJson.getString("operation"));
            MessageUtil.sendMessage(message);
        }
    }

    /**
     * 发送解除绑定消息
     * @param unBindDto
     */
    private void sendUnbindMessage(UnBindDto unBindDto){

        //获取司机电话
        PubDriver driver = driverDao.getById(unBindDto.getDriverId());
        PubVehicle vehicle = vehicleDao.getById(unBindDto.getVehicleId());
        LeLeasescompany leasesCompany = leasescompanyDao.getLeLeasescompanyById(vehicle.getLeasesCompanyId());
        //车牌号
        String plateNoStr = vehicle.getPlateNoProvinceName()+vehicle.getPlateNoCityName()+vehicle.getPlateNo();

        String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.PubDriverVehicleRefService.unbind", plateNoStr,leasesCompany.getServicesPhone());
        //保存系统消息
        this.saveDriverNews("34","解绑成功",unBindDto.getDriverId(),content);


        String type = "";
        //如果为空闲状态
        if(DriverEnum.WORK_STATUS_LEISURE.code.equals(driver.getWorkStatus())){
            content = "您已和【"+plateNoStr+"】解绑，请尽快完成车辆交接";
            type = WindowVisualEnum.FUCHUANG.code;
        }else if(DriverEnum.WORK_STATUS_OFFLINE.code.equals(driver.getWorkStatus())){
            content = "您已和【"+plateNoStr+"】解绑";
            type = WindowVisualEnum.TANCHUANG.code;
        }
        //发送短信及推送
        DriverVehicleBindMessage um = new DriverVehicleBindMessage(driver.getPhone(),content, BindingStateEnum.UN_BINDING,type);
        MessageUtil.sendMessage(um);


    }




    /**
     * 保存司机系统消息
     * @param type
     */
    private void saveDriverNews(String type ,String title,String userid,String content){
        PubDriverNews driverNews = new PubDriverNews();
        driverNews.setId(GUIDGenerator.newGUID());
        driverNews.setType(SystemNewsEnum.NEWS_TYPE_SYSTEM.code);
        driverNews.setNewsstate(SystemNewsEnum.NEWS_STATE_UNREAD.code);
        driverNews.setUserid(userid);
        driverNews.setCreatetime(new Date());
        driverNews.setUpdatetime(new Date());
        driverNews.setStatus(1);

        //添加系统消息
        JSONObject newscontent = new JSONObject();
        newscontent.put("type", type);
        newscontent.put("title", title);
        newscontent.put("content", content);
        driverNews.setContent(newscontent.toString());

        templateHelper.dealRequestWithTokenCarserviceApiUrl("/DriverNews/addDriverNews", HttpMethod.POST, null,
                driverNews,JSONObject.class);
    }

    /**
     * 解绑定验证
     * @param unBindDto
     * @return
     */
    public String verifyTaxiUnBind(UnBindDto unBindDto){
        if(unBindDto.getTaxiDrivers()!=null) {
            //有对班司机情况下判断 验证是否存在当班司机
            for (String driverid : unBindDto.getTaxiDrivers()) {
                PubDriver pubDriver = driverDao.getByPubDriverId(driverid);
                if (DriverEnum.PASS_WORK_STATUS_ON.code.equals(pubDriver.getPassworkStatus())) {
                    return "【" + pubDriver.getName() + "】处于当班中，不能解绑，如需解绑，则需先执行交班或回收车辆！";
                }
                if (DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(pubDriver.getPassworkStatus())) {
                    return "【" + pubDriver.getName() + "】处于交接班中，不能解绑，如需解绑，则需等待交接班完成！";
                }

                //改为查询结果集，如果存在同一个司机、车辆有多条绑定记录(脏数据)，则解绑时一起解绑；
                List<PubDriverVehicleRef> refList = this.refDao.listPubDriverVehicleRef(unBindDto.getVehicleId(),driverid);
                //如果有不存在绑定关系，则提示
                if(refList==null || refList.size()==0) {
                    return "【" + pubDriver.getName() + "】已被其他客服解绑！";
                }
            }

            //获取车辆
            PubVehicle vehicle = vehicleDao.getById(unBindDto.getVehicleId());
            //获取对应司机
            PubDriver driver = driverDao.getById(vehicle.getDriverId());
            if(driver!=null) {
                //如果对应司机状态不为交班中或当班，则该车辆状态为未排班
                if (!(DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(driver.getPassworkStatus())
                        ||DriverEnum.PASS_WORK_STATUS_ON.code.equals(driver.getPassworkStatus()))) {
                    //循环本次解绑司机
                    for(String driverId:unBindDto.getTaxiDrivers()) {
                        //判断出租车司机是否存在未完成订单
                        if (refDao.countValidTaxiOrder(driverId) >= 1) {
                            driver = driverDao.getById(vehicle.getDriverId());
                            return "【"+driver.getName()+"】存在未完成订单，不可执行解绑操作!";
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 是否存在有效的网约车订单
     * @param driverId
     * @return
     */
    public boolean isExistsValidCarOrder(String driverId){
        if(this.refDao.countValidOpCarOrder(driverId)>0){
            return true;
        }
        if(this.refDao.countValidOrgCarOrder(driverId)>0){
            return true;
        }
        return false;
    }

    /**
     * 返回车辆绑定人数
     * @param vehicleId     司机ID
     * @return
     */
    private int getBindedCount(String vehicleId){
        List<VehicleBindInfoDto> listBindInfo = this.listAllVehicleBindInfo(vehicleId);
        if(listBindInfo==null){
            return 0;
        }else{
            return listBindInfo.size();
        }
    }


    /**
     * 更新司机相关状态
     * @param id                记录ID
     * @param personId          处理人ID
     */
    private void updateDriverStatus(String id,String personId,
                                    BindingStateEnum bindEnum,
                                    DriverEnum passWorkStatus,
                                    DriverEnum workStatus){
        PubDriver driver = new PubDriver();
        driver.setBoundState(bindEnum.code);
        if(passWorkStatus!=null) {
            driver.setPassworkStatus(passWorkStatus.code);
        }
        if(workStatus!=null) {
            driver.setWorkStatus(workStatus.code);
        }
        driver.setId(id);
        driver.setUpdater(personId);
        driver.setUpdateTime(new Date());
        driverDao.updatePubDriverStatus(driver);
    }



    /**
     * 绑定操作更新司机状态
     * @param id                记录ID
     * @param personId          处理人ID
     * @param passwork          交接班状态
     */
    private void updateDriverBind(String id,String personId,DriverEnum passwork){
        this.updateDriverStatus(id,personId,BindingStateEnum.BINDING,passwork,DriverEnum.WORK_STATUS_OFFLINE);
    }

    /**
     * 更新司机排班状态
     * @param id                记录ID
     * @param personId          处理人ID
     * @param passwork          交接班状态
     */
    private void updateDriverPassWork(String id,String personId,DriverEnum passwork){
        this.updateDriverStatus(id,personId,BindingStateEnum.BINDING,passwork,null);
    }



    /**
     * 解绑操作更新司机状态
     * @param id                记录ID
     * @param personId          处理人ID
     */
    private void updateDriverUnBind(String id,String personId,DriverEnum passwork){
        this.updateDriverStatus(id,personId,BindingStateEnum.UN_BINDING,passwork,DriverEnum.WORK_STATUS_UNBIND);
    }

    /**
     * 修改司机状态为无对半
     */
    private void updateDriver2No(String id,String personId){
        PubDriver driver = new PubDriver();
        driver.setPassworkStatus(DriverEnum.PASS_WORK_STATUS_NO.code);
        driver.setId(id);
        driver.setUpdater(personId);
        driver.setUpdateTime(new Date());
        driverDao.updatePubDriverStatus(driver);
    }

    /**
     * 修改网约车车辆状态
     * @param vehicleId         车辆ID
     * @param personId          处理人ID
     */
    private void updateCarVehicleUnbind(String vehicleId, String personId){
        //默认未绑定
        BindingStateEnum bindingStateEnum = BindingStateEnum.UN_BINDING;

        PubVehicle vehicle = new PubVehicle();
        vehicle.setId(vehicleId);
        vehicle.setUpdateTime(new Date());
        vehicle.setUpdater(personId);
        vehicle.setBoundState(bindingStateEnum.code);
        vehicle.setBindPersonNum(0);
        vehicleDao.updateVehicleById(vehicle);

        //修改车辆对应司机ID
        vehicle.setDriverId(null);
        vehicleDao.updateDriverId(vehicle);
    }

    /**
     * 修改网约车车辆状态
     * @param vehicleId         车辆ID
     * @param personId          处理人ID
     * @param driverId          绑定司机ID
     */
    private void updateCarVehicleBind(String vehicleId, String personId,String driverId){

        PubVehicle vehicle = new PubVehicle();
        vehicle.setId(vehicleId);
        vehicle.setUpdateTime(new Date());
        vehicle.setUpdater(personId);
        vehicle.setBoundState(BindingStateEnum.BINDING.code);
        vehicle.setBindPersonNum(1);
        vehicleDao.updateVehicleById(vehicle);

        vehicle.setDriverId(driverId);
        //修改车辆对应司机ID
        vehicleDao.updateDriverId(vehicle);
    }


    /**
     * 修改车辆状态
     * @param pubVehicle         车辆对象
     * @param personId          处理人ID
     * @param bindDriver        绑定司机司机ID
     */
    private void updateTaxiVehicleBind(PubVehicle pubVehicle, String personId,String bindDriver,List<VehicleBindInfoDto> list){

        PubVehicle vehicle = new PubVehicle();
        vehicle.setId(pubVehicle.getId());
        vehicle.setUpdateTime(new Date());
        vehicle.setUpdater(personId);
        vehicle.setBoundState( BindingStateEnum.BINDING.code);
        vehicle.setBindPersonNum(list.size());
        vehicleDao.updateVehicleById(vehicle);

        //如果当前车辆对应司机ID为空，则改为绑定司机Id
        if(StringUtils.isEmpty(pubVehicle.getDriverId())){
            vehicle.setDriverId(bindDriver);
            //修改车辆对应司机ID
            vehicleDao.updateDriverId(vehicle);
        }

    }

    /**
     * 修改车辆状态
     * @param vehicleId         车辆ID
     * @param personId          处理人ID
     * @param unBindDriver     解绑司机ID
     */
    private void updateTaxiVehicleUnBind(String vehicleId, String personId,String unBindDriver){
        //默认未绑定
        BindingStateEnum bindingStateEnum = BindingStateEnum.UN_BINDING;
        int bindCount = 0;
        //要替换的driverid
        String driverId = null;

        //返回该车辆绑定信息
        List<VehicleBindInfoDto> list = this.listAllVehicleBindInfo(vehicleId);

        //如果不为空，则标记为已绑定,并随机抽取一个绑定司机ID作为关联ID,网约车只会有一条记录
        if (list != null && list.size() > 0) {
            bindingStateEnum = BindingStateEnum.BINDING;
            //设置绑定人数
            bindCount = list.size();
            //设置司机关联ID
            if(StringUtils.isEmpty(driverId)) {
                driverId = list.get(0).getDriverID();
            }
            //如果剩余1条绑定信息 将司机状态 改为无对班
            if(bindCount==1){
                this.updateDriver2No(driverId,personId);
            }
        }


        PubVehicle vehicle = new PubVehicle();
        vehicle.setId(vehicleId);
        vehicle.setUpdateTime(new Date());
        vehicle.setUpdater(personId);
        vehicle.setDriverId(driverId);
        vehicle.setBoundState(bindingStateEnum.code);
        vehicle.setBindPersonNum(bindCount);
        vehicleDao.updateVehicleById(vehicle);

        //获取解绑司机ID信息
        PubVehicle pubVehicle = vehicleDao.getById(vehicleId);
        //如果当前车辆关联司机ID等于解绑司机ID的时候，才进行修改
        if(unBindDriver.equals(pubVehicle.getDriverId())){
            //修改车辆对应司机ID
            vehicleDao.updateDriverId(vehicle);
        }

    }

    /**
     * 返回车辆待接班司机信息
     * @param vehicleid     车辆ID
     * @param driverId      当班司机ID
     * @return
     */
    public String getVehicleBindDetails(String vehicleid,String driverId){

        List<VehicleBindInfoDto> listBindDetail = this.listVehicleBindDetail(vehicleid,driverId);

        if(listBindDetail !=null && listBindDetail.size()>0){

            return  this.getVehicleBindInfos(listBindDetail);

        }

        return "/";
    }


    /**
     * 查看该车辆或司机是否被绑定
     * @param vehicleid
     * @param driverId
     * @return
     */
    public int isExistsBind(String vehicleid,String driverId){
        return this.refDao.isExistsBind(vehicleid,driverId);
    }

    /**
     * 返回车辆待接班信息集合
     * @param vehicleid   车辆ID
     * @param driverId    当班司机ID
     * @return
     */
    public List<VehicleBindInfoDto> listVehicleBindDetail(String vehicleid,String driverId){
        return refDao.listVehicleBindInfo(vehicleid,driverId);
    }


    /**
     * 返回车辆所有绑定司机信息
     * @param vehicleid
     * @return
     */
    public String getAllVehicleBindInfos(String vehicleid){

        List<VehicleBindInfoDto> listBindInfo = this.listAllVehicleBindInfo(vehicleid);

        if(listBindInfo !=null && listBindInfo.size()>0){

            return  this.getVehicleBindInfos(listBindInfo);

        }

        return "/";
    }


    /**
     * 拼接车辆绑定的司机信息
     * @param listDto
     * @return
     */
    protected String getVehicleBindInfos(List<VehicleBindInfoDto> listDto){

        StringBuffer sb = new StringBuffer();
        int i =0;
        for(VehicleBindInfoDto dto:listDto){
            sb.append(dto.toString());
            if(++i<listDto.size()){
                sb.append("、");
            }
        }
        return sb.toString();
    }



    /**
     * 返回车辆所有绑定信息集合
     * @param vehicleid   车辆ID
     * @return
     */
    public List<VehicleBindInfoDto> listAllVehicleBindInfo(String vehicleid){

        return refDao.listVehicleBindInfo(vehicleid,null);
    }


    /**
     * 返回存在有效订单的数据
     * @param list
     * @return
     */
    public VehicleBindInfoDto countValidTaxiOrder(List<VehicleBindInfoDto> list){
        for(VehicleBindInfoDto dto:list){
            if(this.refDao.countValidTaxiOrder(dto.getDriverID())>0){
                return dto;
            }
        }
        return null;
    }

    /**
     * 判断该车辆与司机是否存在对应关系
     * @param driverId          司机ID
     * @param vehicleId         车辆ID
     * @return
     */
    public boolean isBind(String driverId,String vehicleId){
        //返回绑定数量
        int count = refDao.getRefCount(driverId,vehicleId);
        //如果为1  则为已绑定，否则为未绑定
        if(count==1){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 返回网约车绑定信息
     * @param param 查询条件
     * @return
     */
    public PageBean listCarBindinfo(CarBindInfoQueryParam param){
        List<CarBindInfoDto> list = refDao.listCarBindinfo(param);

        PageBean pageBean = new PageBean();
        pageBean.setsEcho(param.getsEcho());

        int iTotalRecords = refDao.countListCarBindinfo(param);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 返回未绑定车辆信息
     * @param param
     * @return
     */
    public PageBean listUnBindVehicleByQuery(CarBindInfoQueryParam param) {
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(param.getsEcho());
        List<UnBindVehicleInfoDto> list = refDao.listUnVehicleRefByQuery(param);
        int iTotalRecords = refDao.countListUnVehicleRefByQuery(param);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 返回网约车绑定的服务车型及城市
     * @return
     */
    public  List<CarBindInfoDto> listCarBindModelAndCity(CarBindInfoQueryParam param){
        return refDao.listCarBindModelAndCity(param);
    }


    /**
     * 返回出租车绑定列表
     * @param param
     * @return
     */
    public PageBean listTaxiVehicleRef(TaxiBindInfoQueryParam param){
        PageBean pageBean = new PageBean();
        pageBean.setsEcho(param.getsEcho());
        param.setVehicletype(VehicleEnum.VEHICLE_TYPE_TAXI.code);
        List<TaxiBindInfoDto> list = refDao.listVehicleRef(param);
        //设置所有绑定司机信息
        for(TaxiBindInfoDto info:list){
            info.setBindDriverInfos(this.getAllVehicleBindInfos(info.getId()));
        }
        int iTotalRecords = refDao.countlistVehicleRef(param);
        int iTotalDisplayRecords = iTotalRecords;
        pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
        pageBean.setiTotalRecords(iTotalRecords);
        pageBean.setAaData(list);
        return pageBean;
    }

    /**
     * 返回所有车辆的城市，用于下拉框赋值
     * @param param
     * @return
     */
    public List<City> listVehicleRefCity(TaxiBindInfoQueryParam param){
        return this.refDao.listVehicleRefCity(param);
    }


    /**
     * 返回所有已经处于在线状态的绑定司机信息
     * @return
     */
    public List<VehicleBindInfoDto> listVehicleBindInfoOfOnline(String vehicleid){

        return this.refDao.listVehicleBindInfoOfOnline(vehicleid);
    }
    
    /**
     * 返回归属车企
     * @return
     */
    public List<Map<String, Object>> getBelongLeasecompany() {
    	return this.refDao.getBelongLeasecompany();
    }

}