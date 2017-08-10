package com.szyciov.lease.dao;

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
import com.szyciov.lease.mapper.CouponDistributeMapper;
import com.szyciov.op.entity.PeUser;
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

	public boolean checkCouponActivity(String name, String lecompanyid) {
		return mapper.checkCouponActivity(name,lecompanyid);
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

	public List<Object> getCouponActivityNames(String lecompanyid, String name) {
		return mapper.getCouponActivityNames(lecompanyid,name);
	}

	public List<Object> getAlreadyCouponRuleNames(String lecompanyid,String rulename) {
		return mapper.getAlreadyCouponRuleNames(lecompanyid,rulename);
	}

	public List<PubCityAddr> getBusinessCitys(String lecompanyid, String sendruleservice, String accountRule, String sendRule) {
		return mapper.getBusinessCitys(lecompanyid,sendruleservice,accountRule,sendRule);
	}

	public void updateCouponActivity(PubCouponActivity couponActivity) {
		 mapper.updateCouponActivity(couponActivity);
	}

	public void delCouponActivityCity(String id) {
        mapper.delCouponActivityCitys(id);
	}

	public PubCouponActivityDto editCoupon(String id) {
		return mapper.editCoupon(id);
	}

	public List<Object> getCouponRuleNames(String lecompanyid, String sendruletarget) {
		return mapper.getCouponRuleNames(lecompanyid,sendruletarget);
	}

	public PubCouponRule getPubCouponRuleById(String sendruleidref) {
		return mapper.getPubCouponRuleById(sendruleidref);
	}

	public List<PubCouponReceiveDto> GetPubReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetPubReceivedCouponList(queryParam);
	}

	public int GetPubReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetPubReceivedCouponListCount(queryParam);
	}

	public List<Map<String, Object>> organUserRecord(String id) {
		return mapper.organUserRecord(id);
	}

	public List<PubCityAddr> getCouponActivityCitys(String id) {
		return mapper.getCouponActivityCitys(id);
	}

	public List<PeUser> getCouponActivityUsers(String id) {
		return mapper.getCouponActivityUsers(id);
	}

	public int GetOrganReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetOrganReceivedCouponListCount(queryParam);
	}

	public List<PubCouponReceiveDto> GetOrganReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetOrganReceivedCouponList(queryParam);
	}

	public int GetOrganUserReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetOrganUserReceivedCouponListCount(queryParam);
	}

	public List<PubCouponReceiveDto> GetOrganUserReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return mapper.GetOrganUserReceivedCouponList(queryParam);
	}
	
	
}
