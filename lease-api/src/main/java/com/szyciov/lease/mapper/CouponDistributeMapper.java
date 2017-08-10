package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.dto.coupon.PubCouponReceiveDto;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.entity.coupon.PubCouponActivity;
import com.szyciov.entity.coupon.PubCouponActivityUseCity;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponSendUser;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.PeUser;
import com.szyciov.param.PubCouponActivityQueryParam;
import com.szyciov.param.PubReceivedCouponQueryParam;

public interface CouponDistributeMapper {

	void addCouponActivity(PubCouponActivity activity);

	void addCouponActivityCitys(List<PubCouponActivityUseCity> cityList);

	boolean checkCouponActivity(@Param("name")String name, @Param("lecompanyid")String lecompanyid);

	void delCouponActivity(String id);

	void invalidCouponActivity(PubCouponActivity activy);

	List<PubCouponActivityDto> getCouponActivityByQuery(PubCouponActivityQueryParam queryParam);

	int getCouponActivityListCountByQuery(PubCouponActivityQueryParam queryParam);

	List<Object> getCouponActivityNames(@Param("lecompanyid")String lecompanyid,@Param("name")String name);

	List<Object> getAlreadyCouponRuleNames(@Param("lecompanyid")String lecompanyid,@Param("rulename")String rulename);

	List<PubCityAddr> getBusinessCitys(@Param("lecompanyid")String lecompanyid,@Param("sendservicetype") String sendruleservice,@Param("accountrule") String accountRule,@Param("sendrule") String sendRule);

	void updateCouponActivity(PubCouponActivity couponActivity);

	void delCouponActivityCitys(String id);

	PubCouponActivityDto editCoupon(String id);

	List<Object> getCouponRuleNames(@Param("lecompanyid")String lecompanyid, @Param("sendruletarget")String sendruletarget);

	PubCoupon getCoupon(String sendruleidref);

	PubCouponRule getPubCouponRuleById(String sendruleidref);

	List<PubCouponReceiveDto> GetPubReceivedCouponList(PubReceivedCouponQueryParam queryParam);

	int GetPubReceivedCouponListCount(PubReceivedCouponQueryParam queryParam);

	List<Map<String, Object>> organUserRecord(String id);

	List<PubCityAddr> getCouponActivityCitys(String id);

	List<PeUser> getCouponActivityUsers(String id);

	int GetOrganReceivedCouponListCount(PubReceivedCouponQueryParam queryParam);

	List<PubCouponReceiveDto> GetOrganReceivedCouponList(PubReceivedCouponQueryParam queryParam);

	int GetOrganUserReceivedCouponListCount(PubReceivedCouponQueryParam queryParam);

	List<PubCouponReceiveDto> GetOrganUserReceivedCouponList(PubReceivedCouponQueryParam queryParam);

}
