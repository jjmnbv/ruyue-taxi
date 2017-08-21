package com.szyciov.coupon.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.OrgOrganDao;
import com.szyciov.coupon.dao.PubCouponDao;
import com.szyciov.coupon.dao.PubCouponUseCityDao;
import com.szyciov.coupon.dao.PubCouponUseDao;
import com.szyciov.coupon.dto.OrgOrganInfoDTO;
import com.szyciov.coupon.param.PubCouponQueryParam;
import com.szyciov.coupon.rabbitMq.coupon.SenderCouponQueue;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.entity.coupon.PubCouponUse;
import com.szyciov.entity.coupon.PubCouponUseCity;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.DataStateEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.param.coupon.CouponExpenseParam;
import com.szyciov.param.coupon.CouponReserveParam;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 抵用券service
 * @author LC
 * @date 2017/8/8
 */
@Service
public class PubCouponService {

    @Resource
    private PubCouponDao couponDao;

    @Resource
    private PubCouponUseDao couponUseDao;

    @Resource
    private PubCouponUseCityDao couponUseCityDao;

    @Resource
    private RedisService redisService;

    @Resource
    private OrgOrganDao organDao;

    @Resource
    private SenderCouponQueue senderCouponQueue;

    /**
     * 返回抵用券列表
     * @param param
     * @return
     */
    public List<CouponInfoDTO> listAllCoupon(PubCouponQueryParam param)throws Exception{

        //获取不限范围的抵用券
        List<CouponInfoDTO> couponInfoDTOS = new ArrayList<CouponInfoDTO>() ;
        couponInfoDTOS.addAll(couponDao.listAllScopeCoupon(param));
        couponInfoDTOS.addAll(couponDao.listCityScopeCoupon(param));

        return couponInfoDTOS;
    }


    /**
     * 返回最大抵用券
     * @param param
     * @return
     */
    public CouponInfoDTO getMaxMoneyCoupon(PubCouponQueryParam param)throws Exception{

        //获取不限范围的抵用券
        List<CouponInfoDTO> couponInfoDTOS = couponDao.listCoupon(param);
        if(couponInfoDTOS!=null && couponInfoDTOS.size()>0) {
            Collections.sort(couponInfoDTOS, new MoneyComparator());
            return couponInfoDTOS.get(0);
        }
        return null;
    }


    /**
     * 抵用券预约
     * @param param
     */
    public synchronized boolean couponReserve(CouponReserveParam param)throws Exception{
        //如果选择了抵用券才需要锁定
        if(StringUtils.isNotEmpty(param.getCouponId())) {
            //是否可使用
            if (!this.canUse(param.getCouponId())) {
                return false;
            }

            PubCouponUse pubCouponUse = new PubCouponUse();
            pubCouponUse.setId(GUIDGenerator.newGUID());
            pubCouponUse.setCouponidref(param.getCouponId());
            pubCouponUse.setBillingorderid(param.getOrderId());
            pubCouponUse.setCouponmoney(param.getMoney());
            pubCouponUse.setDiscountamount(param.getMoney());
            pubCouponUse.setUsestate(CouponEnum.LOCK_STATE_LOCKED.code);
            pubCouponUse.setUsetype(param.getUseType());
            pubCouponUse.setStatus(DataStateEnum.SUCCESS.code);
            pubCouponUse.setCreater(param.getUserId());
            pubCouponUse.setCreatetime(LocalDateTime.now());

            couponUseDao.saveCouponUse(pubCouponUse);
        }
        return true;
    }

    /**
     * 执行待生成抵用券
     * @param userId
     */
    public void executeStayGenerate(String userId,String cityCode){
        String keyStr = RedisKeyEnum.COUPON_STAY_SEND+userId;

        Map<Object, Object> allMap = redisService.hGetAll(keyStr);

        for(Entry<Object,Object> entry: allMap.entrySet()){
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            GenerateCouponParam couponParam = GsonUtil.fromJson(val,GenerateCouponParam.class);
            if(StringUtils.isEmpty(couponParam.getCityCode())){
                couponParam.setCityCode(cityCode);
            }

            //放入队列执行生成抵用券
            senderCouponQueue.pushGenerateMsg(GsonUtil.toJson(couponParam));
            redisService.hmDel(keyStr,key);
        }

    }



    /**
     * 抵用券消费
     * @param param
     */
    public synchronized boolean couponExpense(CouponExpenseParam param)throws Exception{
        //预约过的ID是否为空
        boolean isOldCouponId = StringUtils.isEmpty(param.getOldCouponId());
        //新的抵用券ID是否为空
        boolean isCouponId = StringUtils.isEmpty(param.getCouponId());


        //如果预约过的抵用券ID为空，则需新增记录
        if(isOldCouponId&&!isCouponId){
            if(!this.canUse(param.getCouponId())){
                return false;
            }
            //保存使用信息记录
            this.saveCouponUseByExpense(param);

        //如果预约过的抵用券ID不为空，新抵用券ID为空，则为解除预约
        }else if(isCouponId&&!isOldCouponId){
            //取消预约
            this.unReserve(param);
        //两次抵用券ID不一致，则为支付时修改抵用券，支付同时需解除之前抵用券的预约
        } if(!param.getOldCouponId().equals(param.getCouponId())){
            if(!this.canUse(param.getCouponId())){
                return false;
            }

            this.saveCouponUseByExpense(param);
            //取消预约
            this.unReserve(param);
        }else{
            //如果没有预约过，则提示券已被使用
            if(!this.isReserve(param.getCouponId(),param.getOrderId())){
                return false;
            }
            PubCouponUse pubCouponUse = new PubCouponUse();
            pubCouponUse.setId(param.getUseId());
            pubCouponUse.setDiscountamount(param.getDiscountamount());
            pubCouponUse.setCouponidref(param.getCouponId());
            pubCouponUse.setUsestate(CouponEnum.COUPON_STATUS_USED.code);
            pubCouponUse.setBillingorderid(param.getOrderId());
            pubCouponUse.setUpdater(param.getUserId());
            pubCouponUse.setUpdatetime(LocalDateTime.now());
            pubCouponUse.setActualamount(param.getDiscountamount());
            couponUseDao.updateCouponUse(pubCouponUse);
        }

        //修改抵用券状态
        this.updateCouponState(param);
        return true;
    }

    /**
     * 根据消费信息保存抵用券消费
     * @param param
     */
    private void saveCouponUseByExpense(CouponExpenseParam param)throws Exception{

        PubCouponUse pubCouponUse = new PubCouponUse();
        pubCouponUse.setId(GUIDGenerator.newGUID());
        pubCouponUse.setCouponidref(param.getCouponId());
        pubCouponUse.setBillingorderid(param.getOrderId());
        pubCouponUse.setCouponmoney(param.getMoney());
        pubCouponUse.setDiscountamount(param.getDiscountamount());
        pubCouponUse.setUsestate(CouponEnum.COUPON_STATUS_USED.code);
        pubCouponUse.setUsetype(param.getUseType());
        pubCouponUse.setStatus(DataStateEnum.SUCCESS.code);
        pubCouponUse.setCreater(param.getUserId());
        pubCouponUse.setCreatetime(LocalDateTime.now());
        pubCouponUse.setActualamount(param.getDiscountamount());
        //保存消费记录
        couponUseDao.saveCouponUse(pubCouponUse);
    }

    /**
     * 修改抵用券状态
     * @param param
     */
    private void updateCouponState(CouponExpenseParam param){
        PubCoupon pubCoupon = new PubCoupon();
        pubCoupon.setCouponstatus(CouponEnum.COUPON_STATUS_USED.code);
        pubCoupon.setId(param.getCouponId());
        pubCoupon.setUpdater(param.getUserId());
        pubCoupon.setUpdatetime(LocalDateTime.now());
        //修改抵用券状态
        couponDao.updateCouponState(pubCoupon);
    }



    /**
     * 抵用券是否已经使用
     * @return
     */
    private boolean  isUsed(String couponId)throws Exception{
        PubCouponUse useParam = new PubCouponUse();
        useParam.setCouponidref(couponId);
        useParam.setUsestate(CouponEnum.COUPON_STATUS_USED.code);
        List<PubCouponUse> list = couponUseDao.listByCouponUseParam(useParam);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }




    /**
     * 是否可以使用
     * @param couponId
     * @return
     */
    public boolean canUse(String couponId)throws Exception{

        boolean isLocked = this.isLocked(couponId);

        boolean isUsed = this.isUsed(couponId);

        if(isLocked||isUsed){
            return false;
        }

        return  true;
    }

    /**
     * 判断抵用券是否在使用城市范围之内
     * @param city
     * @param couponId
     * @return
     */
    public boolean isOnCity(String city,String couponId){
        //判断抵用券是否为不限制使用城市
        PubCoupon coupon = couponDao.getCouponById(couponId);
        if(coupon!=null) {
            if (CouponEnum.USE_TYPE_FIXED.code.equals(coupon.getUsetype())) {
                return true;
            }
        }
        //判断抵用券的使用城市是否有效
        PubCouponUseCity useCity = couponUseCityDao.getUseCityByCityAndCouponId(city,couponId);
        if(useCity!=null){
            return  true;
        }
        return false;
    }


    /**
     * 抵用券是否锁定
     * @return
     */
    private boolean  isLocked(String couponId)throws Exception{
        PubCouponUse useParam = new PubCouponUse();
        useParam.setCouponidref(couponId);
        useParam.setUsestate(CouponEnum.LOCK_STATE_LOCKED.code);
        List<PubCouponUse> list = couponUseDao.listByCouponUseParam(useParam);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 抵用券是否与该订单预约过
     * @param couponId  抵用券ID
     * @param orderId   订单ID
     * @return
     */
    private boolean isReserve(String couponId,String orderId)throws Exception{
        PubCouponUse useParam = new PubCouponUse();
        useParam.setCouponidref(couponId);
        useParam.setBillingorderid(orderId);
        useParam.setUsestate(CouponEnum.LOCK_STATE_LOCKED.code);
        List<PubCouponUse> list = couponUseDao.listByCouponUseParam(useParam);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 解除预约
     * @param param  抵用券消费参数
     * @return
     */
    private void unReserve(CouponExpenseParam param)throws Exception{
        PubCouponUse useParam = new PubCouponUse();
        useParam.setCouponidref(param.getOldCouponId());
        useParam.setBillingorderid(param.getOrderId());
        useParam.setStatus(DataStateEnum.DELETED.code);
        useParam.setUpdater(param.getUserId());
        useParam.setUpdatetime(LocalDateTime.now());
        couponUseDao.removeUseByOrderIdAndCouponId(useParam);
    }

    /**
     * 废弃抵用券
     * @param userId
     */
    public void coponAbandon(String userId) throws Exception{

        PubCoupon pubCoupon = new PubCoupon();
        pubCoupon.setUserid(userId);
        pubCoupon.setStatus(DataStateEnum.DELETED.code);
        pubCoupon.setUpdater(userId);
        pubCoupon.setUpdatetime(LocalDateTime.now());
        pubCoupon.setCouponstatus(CouponEnum.COUPON_STATUS_UN_USE.code);
        couponDao.removeCouponByUserId(pubCoupon);
    }

    /**
     * 自动生成抵用券  主要针对机构活动、个人发券
     * @param dto
     */
    public void aotuGenerateCoupon(PubCouponActivityDto dto){

        //PubCouponActivityDto dto = GsonUtil.fromJson(jsonStr,PubCouponActivityDto.class);

        if(CouponRuleTypeEnum.ACTIVITY.value.equals(dto.getSendruletype())){
            generateOrganCoupon(dto);
        }
        if(CouponRuleTypeEnum.MANUAL.value.equals(dto.getSendruletype())){
            generateManualCoupon(dto);
        }
    }

    /**
     * 生成机构客户活动抵用券
     * @param dto
     */
    private void generateOrganCoupon(PubCouponActivityDto dto){
        List<OrgOrganInfoDTO> organList = organDao.listOrganByLecompanyId(dto.getLecompanyid());
        if(organList!=null && organList.size()>0){
            for(OrgOrganInfoDTO orgorgan:organList){
                //机构发券
                GenerateCouponParam param = new GenerateCouponParam();
                param.setType(CouponRuleTypeEnum.ACTIVITY.value);
                param.setUserType(dto.getSendruletarget());
                param.setCompanyId(dto.getLecompanyid());
                param.setUserId(orgorgan.getId());
                param.setCityCode(orgorgan.getCity());
                //放入队列执行生成抵用券
                senderCouponQueue.pushGenerateMsg(GsonUtil.toJson(param));
            }

        }

    }

    /**
     * 生成人工抵用券
     * @param dto
     */
    private void generateManualCoupon(PubCouponActivityDto dto){
        //发放用户不为空
        if(StringUtils.isNotEmpty(dto.getUsers())) {
            String[] userArray = dto.getUsers().split(",");
            //发放用户数
            for (String userId : userArray) {
                //每人发放张数
                for (int i = 0; i < dto.getSendcount(); i++) {
                    //人工发券
                    GenerateCouponParam param = new GenerateCouponParam();
                    param.setType(CouponRuleTypeEnum.MANUAL.value);
                    param.setUserType(dto.getSendruletarget());
                    param.setCompanyId(dto.getLecompanyid());
                    param.setUserId(userId);
                    //放入队列执行生成抵用券
                    senderCouponQueue.pushGenerateMsg(GsonUtil.toJson(param));
                }
            }
        }

    }

    /**
     * 删除未使用的抵用券 根据时间
     * @param pubCoupon
     */
    public void timeOutCouponByTime(PubCoupon pubCoupon){
        couponDao.timeOutCouponByTime(pubCoupon);
    }



    // 自定义比较器：按抵用券金额进行排序
    static class MoneyComparator implements Comparator,Serializable {
        @Override
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            CouponInfoDTO p1 = (CouponInfoDTO) object1;
            CouponInfoDTO p2 = (CouponInfoDTO) object2;
            return p2.getMoney().compareTo(p1.getMoney());
        }
    }
}
 