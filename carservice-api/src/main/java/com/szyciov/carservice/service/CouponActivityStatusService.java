package com.szyciov.carservice.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.CouponActivityStatusDao;
import com.szyciov.dto.coupon.PubCouponActivityDto;

@Service(value="couponActivityStatusService")
public class CouponActivityStatusService {

	@Resource(name="couponActivityStatusDao")
	private CouponActivityStatusDao dao;

	public CouponActivityStatusDao getDao() {
		return dao;
	}

	public void setDao(CouponActivityStatusDao dao) {
		this.dao = dao;
	}

	public List<PubCouponActivityDto> getAllNotStartCouponActivity(String dateStr) {
		return dao.getAllNotStartCouponActivity(dateStr);
	}

	public List<PubCouponActivityDto> getAllExpiredCouponActivity(String dateStr) {
		return dao.getAllExpiredCouponActivity(dateStr);
	}

	public void updateAllNotStartCouponActivity(List<PubCouponActivityDto> notStartActivitys) {
		  dao.updateAllNotStartCouponActivity(notStartActivitys);
	}

	public void updateAllExpiredCouponActivity(List<PubCouponActivityDto> expiredActivitys) {
		  dao.updateExpiredCouponActivity(expiredActivitys);
	}
	
	
}
