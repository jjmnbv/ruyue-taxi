package com.szyciov.carservice.mapper;

import java.util.Map;

public interface BaiduApiMapper {

	public Map<String, Object> getOrderByOrderno(String orderno);
}