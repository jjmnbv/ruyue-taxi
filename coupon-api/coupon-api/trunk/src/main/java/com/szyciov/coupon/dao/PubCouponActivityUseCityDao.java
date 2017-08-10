package com.szyciov.coupon.dao;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.CouponActivityDTO;
import com.szyciov.coupon.mapper.PubCouponActivityMapper;
import com.szyciov.coupon.mapper.PubCouponActivityUseCityMapper;
import com.szyciov.coupon.param.CouponActivityQueryParam;
import org.springframework.stereotype.Repository;

/**
 * 抵用券活动发放城市DAO
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class PubCouponActivityUseCityDao {

    @Resource
    private PubCouponActivityUseCityMapper mapper;

    public List<String> listUseCityCode(String activityId){
        return mapper.listUseCityCode(activityId);
    }
}
 