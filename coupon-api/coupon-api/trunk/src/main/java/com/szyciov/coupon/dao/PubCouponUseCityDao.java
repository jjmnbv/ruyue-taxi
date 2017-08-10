package com.szyciov.coupon.dao;

import javax.annotation.Resource;

import com.szyciov.coupon.mapper.PubCouponUseCityMapper;
import com.szyciov.entity.coupon.PubCouponUseCity;
import org.springframework.stereotype.Repository;

/**
 * 抵用券使用城市DAO
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class PubCouponUseCityDao {

    @Resource
    private PubCouponUseCityMapper useCityMapper;

    public void saveUseCity(PubCouponUseCity useCity){
        useCityMapper.saveUseCity(useCity);
    }


    public PubCouponUseCity getUseCityByCityAndCouponId(String city,String couponidref){
        return useCityMapper.getUseCityByCityAndCouponId(city,couponidref);
    }

}
 