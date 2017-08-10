package com.szyciov.coupon.dao;

import javax.annotation.Resource;

import com.szyciov.coupon.mapper.PubCouponRuleMapper;
import com.szyciov.entity.coupon.PubCouponRule;
import org.springframework.stereotype.Repository;

/**
 * 抵用券规则DAO
 * @author LC
 * @date 2017/7/27
 */
@Repository
public class PubCouponRuleDao {

    @Resource
    private PubCouponRuleMapper pubCouponRuleMapper;

    private PubCouponRule getPubCouponRule(){
        return null;
    }

}
 