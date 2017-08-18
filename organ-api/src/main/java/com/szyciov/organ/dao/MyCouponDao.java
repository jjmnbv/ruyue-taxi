package com.szyciov.organ.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.coupon.MyCouponDTO;
import com.szyciov.organ.mapper.MyCouponMapper;

@Repository("MyCouponDao")
public class MyCouponDao {
	public MyCouponDao() {
	}

	private MyCouponMapper mapper;

	@Resource
	public void setMapper(MyCouponMapper mapper) {
		this.mapper = mapper;
	}
	
	public MyCouponDTO getMyCouponDTOList(String id){
		return mapper.getMyCouponDTOList(id);
	};
}
