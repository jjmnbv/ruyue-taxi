package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.dto.coupon.CouponUsageDto;
import com.szyciov.param.CouponUsageQueryParam;
import com.szyciov.param.UserRechargeQueryParam;

public interface EffectAnalysisMapper {

	List<Object> getCouponUsageSendCitys(@Param("city") String city,@Param("lecompanyid") String lecompanyid);

	List<CouponUsageDto> queryCouponUsageList(CouponUsageQueryParam queryParam);

	int queryCouponUsageListCount(CouponUsageQueryParam queryParam);

	List<Map> queryUsedCouponList();

	Integer queryRegisterCount(UserRechargeQueryParam queryParam);

	Integer queryRechargeCount(UserRechargeQueryParam queryParam);

}
