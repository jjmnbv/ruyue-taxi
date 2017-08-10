package com.szyciov.coupon.dao;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.mapper.PubCouponUseMapper;
import com.szyciov.entity.coupon.PubCouponUse;
import org.springframework.stereotype.Repository;

/**
 * 抵用券消费dao
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class PubCouponUseDao {

    @Resource
    private PubCouponUseMapper useMapper;


    public void saveCouponUse(PubCouponUse couponUse){
        useMapper.saveCouponUse(couponUse);
    }


    public List<PubCouponUse> listByCouponUseParam(PubCouponUse couponUse){
        return useMapper.listByCouponUseParam(couponUse);
    }

    /**
     * 更新抵用券ID，实际抵用金额，抵用券状态等信息
     * @param couponUse
     */
    public void updateCouponUse(PubCouponUse couponUse){
        useMapper.updateCouponUse(couponUse);
    }

    /**
     * 根据订单ID、抵用券ID删除使用数据
     * @param couponUse
     */
    public void removeUseByOrderIdAndCouponId(PubCouponUse couponUse){
        useMapper.removeUseByOrderIdAndCouponId(couponUse);
    }

    /**
     * 根据订单ID，消费装填获取消费信息
     * @param usestate
     * @param billingorderid
     * @return
     */
    public  PubCouponUse getCouponUseByOrder(String usestate,String billingorderid){
        return useMapper.getCouponUseByOrder(usestate, billingorderid);
    }
}
 