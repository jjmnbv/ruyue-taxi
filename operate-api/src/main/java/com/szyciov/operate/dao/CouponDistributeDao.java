package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.dto.coupon.PubCouponReceiveDto;
import com.szyciov.entity.coupon.PubCouponActivity;
import com.szyciov.entity.coupon.PubCouponActivityUseCity;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponSendUser;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.PeUser;
import com.szyciov.operate.mapper.CouponDistributeMapper;
import com.szyciov.param.PubCouponActivityQueryParam;
import com.szyciov.param.PubReceivedCouponQueryParam;

@Repository("couponDistributeDao")
public class CouponDistributeDao {

	@Resource
	private CouponDistributeMapper mapper;

	public CouponDistributeMapper getMapper() {
		return mapper;
	}

	public void setMapper(CouponDistributeMapper mapper) {
		this.mapper = mapper;
	}

	public void addCouponActivity(PubCouponActivity activity) {
		mapper.addCouponActivity(activity);
	}

	public void addCouponActivityCitys(List<PubCouponActivityUseCity> cityList) {
        mapper.addCouponActivityCitys(cityList);		
	}

	public boolean checkCouponActivity(String name) {
		return mapper.checkCouponActivity(name);
	}

	public void delCouponActivity(String id) {
		mapper.delCouponActivity(id);
	}

	public void invalidCouponActivity(PubCouponActivity activy) {
        mapper.invalidCouponActivity(activy);		
	}

	public List<PubCouponActivityDto> getCouponActivityByQuery(PubCouponActivityQueryParam queryParam) {
		return mapper.getCouponActivityByQuery(queryParam);
	}

	public int getCouponActivityListCountByQuery(PubCouponActivityQueryParam queryParam) {
	    return mapper.getCouponActivityListCountByQuery(queryParam);
	}

	public List<Object> getCouponActivityNames(String name) {
		return mapper.getCouponActivityNames(name);
	}

	public List<Object> getAlreadyCouponRuleNames(String rulename) {
		return mapper.getAlreadyCouponRuleNames(rulename);
	}

	public List<Object> getPeUsers(String account) {
		return mapper.getPeUsers(account);
	}

	public List<PubCityAddr> getBusinessCitys(String sendservicetype, String accountRule, String sendRule) {
		return mapper.getBusinessCitys(sendservicetype,accountRule,sendRule);
	}

	public void updateCouponActivity(PubCouponActivity couponActivity) {
		 mapper.updateCouponActivity(couponActivity);
	}

	public void addCouponActivityUsers(List<PubCouponSendUser> userList) {
        mapper.addCouponActivityUsers(userList);		
	}

	public void delCouponActivityCity(String id) {
        mapper.delCouponActivityCitys(id);
	}

	public void delCouponActivityUsers(String id) {
        mapper.delCouponActivityUsers(id);		
	}

	public PubCouponActivityDto editCoupon(String id) {
		return mapper.editCoupon(id);
	}

	public List<Object> getCouponRuleNames() {
		return mapper.getCouponRuleNames();
	}

	public PubCouponRule getPubCouponRuleById(String sendruleidref) {
		return mapper.getPubCouponRuleById(sendruleidref);
	}

	public List<Object> getReceivedCouponUsers(Map<String, Object> map) {
		return mapper.getReceivedCouponUsers(map);
	}

	public List<PubCouponReceiveDto> GetPubReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetPubReceivedCouponList(queryParam);
	}

	public int GetPubReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetPubReceivedCouponListCount(queryParam);
	}

	public List<Map<String, Object>> record(String id) {
		return mapper.record(id);
	}

	public List<PubCityAddr> getCouponActivityCitys(String id) {
		return mapper.getCouponActivityCitys(id);
	}

	public List<PeUser> getCouponActivityUsers(String id) {
		return mapper.getCouponActivityUsers(id);
	}

	public PubCouponActivityDto getCouponActivityByID(String id) {
		return mapper.getCouponActivityByID(id);
	}
	
	
}
