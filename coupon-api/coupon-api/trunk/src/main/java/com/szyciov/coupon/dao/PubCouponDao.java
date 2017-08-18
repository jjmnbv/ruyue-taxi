package com.szyciov.coupon.dao;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.mapper.PubCouponMapper;
import com.szyciov.coupon.param.PubCouponQueryParam;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.entity.coupon.PubCoupon;
import org.springframework.stereotype.Repository;

/**
 * 抵用券DAO
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class PubCouponDao {

    @Resource
    private PubCouponMapper pubCouponMapper;

    public void savePubCoupon(PubCoupon pubCoupon){
        pubCouponMapper.savePubCoupon(pubCoupon);
    }

    /**
     * 获取不限范围的抵用券
     * @param param
     * @return
     */
    public List<CouponInfoDTO> listAllScopeCoupon(PubCouponQueryParam param){
        return pubCouponMapper.listAllScopeCoupon(param);
    }

    public List<CouponInfoDTO> listCityScopeCoupon(PubCouponQueryParam param){
        return pubCouponMapper.listCityScopeCoupon(param);
    }

    /**
     * 获取抵用券列表
     * @param param
     * @return
     */
    public List<CouponInfoDTO> listCoupon(PubCouponQueryParam param){
        return pubCouponMapper.listCoupon(param);
    }

    /**
     * 更新抵用券状态
     * @param pubCoupon
     */
    public void updateCouponState(PubCoupon pubCoupon){
        pubCouponMapper.updateState(pubCoupon);
    }

    /**
     * 根据ID返回抵用券信息
     * @param id
     * @return
     */
    public PubCoupon getCouponById(String id){
        return pubCouponMapper.getCouponById(id);
    }

    /**
     * 删除未使用的抵用券
     * @param pubCoupon
     */
    public void removeCouponByUserId(PubCoupon pubCoupon){
        pubCouponMapper.removeCouponByUserId(pubCoupon);
    }

    /**
     * 删除未使用的抵用券 根据时间
     * @param pubCoupon
     */
    public void timeOutCouponByTime(PubCoupon pubCoupon){
        pubCouponMapper.timeOutCouponByTime(pubCoupon);
    }

}
 