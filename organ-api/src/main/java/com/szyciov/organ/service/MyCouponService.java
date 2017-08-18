package com.szyciov.organ.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.dto.coupon.MyCouponDTO;
import com.szyciov.organ.dao.MyCouponDao;

@Service("MyCouponService")
public class MyCouponService {
	private MyCouponDao dao;

	@Resource(name = "MyCouponDao")
	public void setDao(MyCouponDao dao) {
		this.dao = dao;
	}
	
	public MyCouponDTO getMyCouponDTOList(String id){
		return dao.getMyCouponDTOList(id);
	};
}
