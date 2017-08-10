package com.szyciov.carservice.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.CouponActivityStatusMapper;
import com.szyciov.dto.coupon.PubCouponActivityDto;

@Repository(value="couponActivityStatusDao")
public class CouponActivityStatusDao {

	@Resource
	private CouponActivityStatusMapper mapper;
	
	public CouponActivityStatusMapper getMapper() {
		return mapper;
	}

	public void setMapper(CouponActivityStatusMapper mapper) {
		this.mapper = mapper;
	}

	public List<PubCouponActivityDto> getAllNotStartCouponActivity(String dateStr) {
		return mapper.getAllNotStartCouponActivity(dateStr);
	}

	public List<PubCouponActivityDto> getAllExpiredCouponActivity(String dateStr) {
		return mapper.getAllExpiredCouponActivity(dateStr);
	}

	public void updateAllNotStartCouponActivity(List<PubCouponActivityDto> notStartActivitys) {
		 mapper.updateAllNotStartCouponActivity(notStartActivitys);
	}

	public void updateExpiredCouponActivity(List<PubCouponActivityDto> expiredActivitys) {
		 mapper.updateExpiredCouponActivity(expiredActivitys);
	}
}
