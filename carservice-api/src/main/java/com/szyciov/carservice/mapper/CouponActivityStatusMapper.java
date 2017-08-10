package com.szyciov.carservice.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.szyciov.dto.coupon.PubCouponActivityDto;

public interface CouponActivityStatusMapper {

	List<PubCouponActivityDto> getAllNotStartCouponActivity(@Param("sendstarttime")String dateStr);

	List<PubCouponActivityDto> getAllExpiredCouponActivity(@Param("sendendtime")String dateStr);

	void updateAllNotStartCouponActivity(List<PubCouponActivityDto> notStartActivitys);

	void updateExpiredCouponActivity(List<PubCouponActivityDto> expiredActivitys);

}
