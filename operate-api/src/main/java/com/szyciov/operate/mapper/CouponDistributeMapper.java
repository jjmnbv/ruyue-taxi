package com.szyciov.operate.mapper;

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

	boolean checkCouponActivity(String name);

	void delCouponActivity(String id);

	void invalidCouponActivity(PubCouponActivity activy);

	List<PubCouponActivityDto> getCouponActivityByQuery(PubCouponActivityQueryParam queryParam);

	int getCouponActivityListCountByQuery(PubCouponActivityQueryParam queryParam);

	List<Object> getCouponActivityNames(@Param("name")String name);

	List<Object> getAlreadyCouponRuleNames(@Param("rulename")String rulename);

	List<Object> getPeUsers(@Param("account")String account);

	List<PubCityAddr> getBusinessCitys(@Param("sendservicetype") String sendservicetype,@Param("accountrule") String accountRule,@Param("sendrule") String sendRule);

	void updateCouponActivity(PubCouponActivity couponActivity);

	void addCouponActivityUsers(List<PubCouponSendUser> userList);

	void delCouponActivityCitys(String id);

	void delCouponActivityUsers(String id);

	PubCouponActivityDto editCoupon(String id);

	List<Object> getCouponRuleNames();

	PubCoupon getCoupon(String sendruleidref);

	PubCouponRule getPubCouponRuleById(String sendruleidref);

	List<Object> getReceivedCouponUsers(Map<String, Object> map);

	List<PubCouponReceiveDto> GetPubReceivedCouponList(PubReceivedCouponQueryParam queryParam);

	int GetPubReceivedCouponListCount(PubReceivedCouponQueryParam queryParam);

	List<Map<String, Object>> record(String id);

	List<PubCityAddr> getCouponActivityCitys(String id);

	List<PeUser> getCouponActivityUsers(String id);

	PubCouponActivityDto getCouponActivityByID(@Param("id")String id);

}
