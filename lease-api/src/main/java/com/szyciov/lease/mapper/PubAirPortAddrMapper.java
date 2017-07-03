package com.szyciov.lease.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szyciov.entity.PubAirPortAddr;

public interface PubAirPortAddrMapper {
	List<PubAirPortAddr> getPubAirPortAddrList(@Param(value = "city") String city);
}
