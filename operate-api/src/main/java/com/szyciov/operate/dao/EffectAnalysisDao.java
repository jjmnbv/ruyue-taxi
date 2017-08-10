package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.coupon.CouponUsageDto;
import com.szyciov.operate.mapper.EffectAnalysisMapper;
import com.szyciov.param.CouponUsageQueryParam;
import com.szyciov.param.UserRechargeQueryParam;

@Repository("effectAnalysisDao")
public class EffectAnalysisDao {

	@Resource
	private EffectAnalysisMapper mapper;

	public EffectAnalysisMapper getMapper() {
		return mapper;
	}

	public void setMapper(EffectAnalysisMapper mapper) {
		this.mapper = mapper;
	}

	public List<Object> getCouponUsageSendCitys(String city) {
		return mapper.getCouponUsageSendCitys(city);
	}

	public List<CouponUsageDto> queryCouponUsageList(CouponUsageQueryParam queryParam) {
		return mapper.queryCouponUsageList(queryParam);
	}

	public int queryCouponUsageListCount(CouponUsageQueryParam queryParam) {
		return mapper.queryCouponUsageListCount(queryParam);
	}

	public List<Map> queryUsedCouponList() {
		return mapper.queryUsedCouponList();
	}

	public Integer queryRegisterCount(UserRechargeQueryParam queryParam) {
		return mapper.queryRegisterCount(queryParam);
	}

	public Integer queryChargeCount(UserRechargeQueryParam queryParam) {
		return mapper.queryRechargeCount(queryParam);
	}
	
}
