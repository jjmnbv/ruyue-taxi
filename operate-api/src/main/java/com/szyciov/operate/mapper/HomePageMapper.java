package com.szyciov.operate.mapper;

import java.util.Map;

public interface HomePageMapper {
	Map<String,Object> getCompayData(Map<String,Object> homeDataParam);
	Map<String,Object> getDataByTime(Map<String,Object> homeDataParam);
}
